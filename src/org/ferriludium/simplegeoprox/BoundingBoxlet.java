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

import com.javadocmd.simplelatlng.LatLng;

/**
 * Holds the coordinates representing the coordinates of a near-rectangular section of the globe: the west- and east-most longitude; and the north- and south-most latitude.  NOTE that it is not merely the corners because the globe isn't rectilinear, so this form allows us to create the box with the most inclusive size so that we don't miss any intended portion of the map.
 * TODO: this won't handle points close to the poles very well. Fix that.
 * @author Cornelius Perkins (ccperkins at bitbucket and github)
 *
 */
public class BoundingBoxlet {
	/** The "upper left" (NW) corner of the boxlet */
	final private LatLng upperLeft;
	/** The "lower right" (SE) corner of the boxlet */
	final private LatLng lowerRight;
	
	/** The northern border of the region */
	final Double latitudeNorth;
	/** The southern border of the region */
	final Double latitudeSouth;
	/** The western border of the region */
	final Double longitudeWest;
	/** The eastern border of the region */
	final Double longitudeEast;
	
	/** Builder class to construct a BoundingBox (this exists because four doubles might get confusing). */
	public static class Builder {
		private Double latitudeNorth;
		private Double latitudeSouth;
		private Double longitudeWest;
		private Double longitudeEast;
		public Builder() { super(); }
		public Builder setLatitudeNorth(double val) { latitudeNorth = val; return this;}
		public Builder setLatitudeSouth(double val) { latitudeSouth = val; return this;}
		public Builder setLongitudeWest(double val) { longitudeWest = val; return this;}
		public Builder setLongitudeEast(double val) { longitudeEast = val; return this;}
		
		public Builder setLatitudeNorth(LatLng loc) { latitudeNorth = loc.getLatitude(); return this;}
		public Builder setLatitudeSouth(LatLng loc) { latitudeSouth = loc.getLatitude(); return this;}
		public Builder setLongitudeWest(LatLng loc) { longitudeWest = loc.getLongitude(); return this;}
		public Builder setLongitudeEast(LatLng loc) { longitudeEast = loc.getLongitude(); return this;}
		
		public BoundingBoxlet build() {
			return new BoundingBoxlet(latitudeNorth, latitudeSouth, longitudeWest, longitudeEast);
		}
	}
	private BoundingBoxlet(Double latitudeNorth, Double latitudeSouth, Double longitudeWest, Double longitudeEast) {
		super();
		this.latitudeNorth = latitudeNorth;
		this.latitudeSouth = latitudeSouth;
		this.longitudeWest = longitudeWest;
		this.longitudeEast = longitudeEast;
		upperLeft = new LatLng(latitudeNorth, longitudeWest);
		lowerRight = new LatLng(latitudeSouth, longitudeEast);
	}
	public LatLng getUpperLeft() {
		return upperLeft;
	}
	public LatLng getLowerRight() {
		return lowerRight;
	}
	@Override
	public String toString() {
		return "["
				+ "{UL: " + getUpperLeft()
				+ " to "
				+ "LR: " + getLowerRight()
				+ "} latNorth=" + latitudeNorth 
				+ ", latSouth=" + latitudeSouth 
				+ ", longWest=" + longitudeWest 
				+ ", longEast=" + longitudeEast
				+ "]";
	}
	
}
