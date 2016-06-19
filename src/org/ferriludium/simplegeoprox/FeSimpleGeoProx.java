/*******************************************************************************
 * Copyright [2016] [Cornelius Perkins]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Contributors:
 *    Cornelius Perkins - initial API and implementation and/or initial documentation
 *    
 * Author Cornelius Perkins (ccperkins at both github and bitbucket)
 *******************************************************************************/ 

package org.ferriludium.simplegeoprox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.ferriludium.simplegeoprox.internals.Constraint;
import org.ferriludium.simplegeoprox.internals.Constraints;
import org.ferriludium.simplegeoprox.internals.Extractor;
import org.ferriludium.simplegeoprox.internals.Index;
import org.ferriludium.simplegeoprox.internals.IndexComparator;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

/**
 * 
 * Collection of user-supplied geographical points which supports fast proximity search by search within a radius or by rectangle.
 * 
 * Makes use of SimpleLatLng by Tyler Coles (also published under the Apache license), so latitude and longitude are represented in that coordinate system.
 * https://github.com/JavadocMD/simplelatlng
 * 
 * + Allows query within circle (radial distance from start point), and rectangle.
 * + Lightweight. The two jars (this and SimpleLatLng) together total less than 100K.
 * + Handles the poles properly.
 * 
 * - LIMITATION: Changes to caller's view of the location of objects will not be reflected in search results.  At this point, object locations must be held constant (or destroy the proximap and recreate with the new locations)
 * - LIMITATION: Persistence is the responsibility of caller.
 * - LIMITATION: Shares the limitations of LatLng: specifically it ignores elevation in calculating distance.
 * 
 * USAGE: construct an instance of this and give it a collection of GeoObject instances (@See GeoObject).  A GeoObject is a set of coordinates (LatLng) and a user-given object, and a key.
 * 
 * All classes in this assembly are effectively final, except that there is no restriction placed on the user-supplied objects in GeoObject.
 * 
 * 
 * @author Cornelius Perkins (ccperkins at bitbucket and github)
 *
 * @param <K> Type of the key in the GeoObject (on which the only restriction is that K must be suitable for use in a HashMap (must obey the equals/hashCode contract).
 * @param <T> Type of the client object.
 */
public class FeSimpleGeoProx<T> implements Iterable<MapObjectHolder<T>> {
	private final Set<MapObjectHolder<T>> map = new HashSet<>();
	private Index<MapObjectHolder<T>, Double> longitudeIndex;
	private final LongitudeExtractor longitudeExtractor = new LongitudeExtractor();
	private final LatitudeExtractor latitudeExtractor = new LatitudeExtractor();
	

	public FeSimpleGeoProx(Collection<MapObjectHolder<T>> mapObjects) {
		this(mapObjects, IndexComparator.SortOrder.ASCENDING);
	}
	public FeSimpleGeoProx(Collection<MapObjectHolder<T>> mapObjects, IndexComparator.SortOrder sortOrder) {
		super();
		for (MapObjectHolder<T> geoObject : mapObjects) {
			map.add(geoObject);
		}
		longitudeIndex = new Index<MapObjectHolder<T>, Double>(LONGITUDE, longitudeExtractor, sortOrder, mapObjects);
	}

	private final static String LONGITUDE="Longitude";
	private final static String LATITUDE="Latitude";

	@Override
	public Iterator<MapObjectHolder<T>> iterator() {
		return map.iterator();
	}		


	/**
	 * Returns all points in the collection within the given radius of the start point.
	 * @param start Location (@See LatLng) of the center point
	 * @param radius Search radius
	 * @param lengthUnit Units of the search (@See LengthUnit)
	 * @return Collection of points which fall within the given radial distance of the strt point.
	 */
	public Collection<MapObjectHolder<T>> find (LatLng start, double radius, LengthUnit lengthUnit) {
		// Step 1: get the bounding box (note: may have multiple parts)
		BoundingBox boxes = LatLngUtils.getBoundingBox(start, radius, lengthUnit);

		// Step 2: get the candidate points (all those in the bounding box, not worrying about radial distance)
		List<MapObjectHolder<T>> candidatePoints = new ArrayList<MapObjectHolder<T>> ();
		for (BoundingBoxlet box: boxes.boxes) {
			candidatePoints.addAll(find(box));
		}

		// Step 3: filter off the corners.
		Set<MapObjectHolder<T>> ret = new HashSet<MapObjectHolder<T>> ();
		for (MapObjectHolder<T> candidate: candidatePoints) {
			if (LatLngTool.distance(start, candidate.loc, lengthUnit) <= radius) {
				ret.add(candidate);
			}
		}
		return ret;
	}
	
	/**
	 * Returns all points in the collection within the given "rectangle" defined by NW and SE corners.
	 * @param cornerNW
	 * @param cornerSE
	 * @return Collection of points which fall within the given pseudo-rectangle defined by NW and SE corners.
	 */
	public Collection<MapObjectHolder<T>> find (LatLng cornerNW, LatLng cornerSE) {
		// Step 1: convert the corners to a bounding box (note: may have multiple parts if the box crosses the anti-meridian)
		BoundingBox boxes = LatLngUtils.getBoundingBox(cornerNW, cornerSE);

		// Step 2: get the candidate points (all those in the bounding box, not worrying about radial distance)
		List<MapObjectHolder<T>> candidatePoints = new ArrayList<MapObjectHolder<T>> ();
		for (BoundingBoxlet box: boxes.boxes) {
			candidatePoints.addAll(find(box));
		}

		return candidatePoints;
	}	
	
	/**
	 * Retrieves all points within the given bounding boxlet (@See BoundingBoxlet).
	 * @param box
	 * @return collection of points
	 */
	public Collection<MapObjectHolder<T>> find (BoundingBoxlet box) {
		// get the candidate points (all those in the bounding box, not worrying about radial distance)
		List<MapObjectHolder<T>> candidatePoints = new ArrayList<MapObjectHolder<T>> ();
		Constraints<MapObjectHolder<T>, Double> latitudeConstraints = (new Constraints.Builder<MapObjectHolder<T>, Double> ())
				.and(new Constraint<MapObjectHolder<T>, Double>(latitudeExtractor, box.latitudeSouth, box.latitudeNorth))
				.build();		
		List<MapObjectHolder<T>> boxPoints = longitudeIndex.getAllBetween(box.longitudeWest, box.longitudeEast, latitudeConstraints);
		candidatePoints.addAll(boxPoints);
		return candidatePoints;
	}
	
	/**
	 * Retrieves all points within the given set of latitude and longitude dimensions.
	 * @param latitudeNorth
	 * @param latitudeSouth
	 * @param longitudeWest
	 * @param longitudeEast
	 * @return collection of points.
	 */
	Collection<MapObjectHolder<T>> find (Double latitudeNorth, Double latitudeSouth, Double longitudeWest, Double longitudeEast) {
		BoundingBoxlet box = new BoundingBoxlet.Builder()
			                                      .setLatitudeNorth(latitudeNorth)
			                                      .setLatitudeSouth(latitudeSouth) 
			                                      .setLongitudeWest(longitudeWest) 
			                                      .setLongitudeEast(longitudeEast) 
			                                      .build();		
		return find(box);
	}

	private class LongitudeExtractor extends Extractor<MapObjectHolder<T>, Double> {
		LongitudeExtractor () {
			super (LONGITUDE);
		}
		@Override 
		public Double extract(MapObjectHolder<T> obj) { return obj.loc.getLongitude(); }
	}		
	private class LatitudeExtractor extends Extractor<MapObjectHolder<T>, Double> {
		LatitudeExtractor () {
			super (LATITUDE);
		}
		@Override 
		public Double extract(MapObjectHolder<T> obj) { return obj.loc.getLatitude(); }
	}		
}
