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

import java.util.Arrays;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

public class LatLngUtils {
	/**
	 * Returns the bounding box with the given NW and SE corners.
	 * @param cornerNW
	 * @param cornerSE
	 * @return
	 */
	public static BoundingBox getBoundingBox(LatLng cornerNW, LatLng cornerSE) {
		BoundingBox ret = new BoundingBox();
		if ( crossesAntiMeridian(cornerNW, cornerSE)) {
			ret.add (new BoundingBoxlet.Builder()
				                            .setLongitudeWest(cornerNW.getLongitude()) 
				                            .setLongitudeEast(180) 
				                            .setLatitudeNorth(cornerNW.getLatitude())
				                            .setLatitudeSouth(cornerSE.getLatitude())
				                            .build());
			ret.add (new BoundingBoxlet.Builder()
				                            .setLongitudeWest(-180) 
				                            .setLongitudeEast(cornerSE.getLongitude()) 
				                            .setLatitudeNorth(cornerNW.getLatitude())
				                            .setLatitudeSouth(cornerSE.getLatitude())
				                            .build());
			
		} else { // just one box, from the smallest to the largest
			ret.add (new BoundingBoxlet.Builder().setLongitudeWest(cornerNW.getLongitude()) 
				                                 .setLongitudeEast(cornerSE.getLongitude()) 
				                                 .setLatitudeNorth(cornerNW.getLatitude())
				                                 .setLatitudeSouth(cornerSE.getLatitude()) 
				                                 .build());
		}
		return ret;
	}
	/**
	 * Returns the bounding box holding the circle whose center and radius are given.  
	 * Boxes, really, since the anti-meridian and the poles complicate this.
	 * 
	 * @param center
	 * @param radius
	 * @param lengthUnit
	 * @return
	 */
	public static BoundingBox getBoundingBox(LatLng start, double radius, LengthUnit lengthUnit) {
		LatLng northCenter = LatLngTool.travel(start, LatLngTool.Bearing.NORTH, radius, lengthUnit);
		LatLng southCenter = LatLngTool.travel(start, LatLngTool.Bearing.SOUTH, radius, lengthUnit);
		LatLng westCenter = LatLngTool.travel(start, LatLngTool.Bearing.WEST, radius, lengthUnit);
		LatLng eastCenter = LatLngTool.travel(start, LatLngTool.Bearing.EAST, radius, lengthUnit);
		
		LatLng llNW = LatLngTool.travel(northCenter, LatLngTool.Bearing.WEST, radius, lengthUnit);
		LatLng llNE = LatLngTool.travel(northCenter, LatLngTool.Bearing.EAST, radius, lengthUnit);
		LatLng llSW = LatLngTool.travel(southCenter, LatLngTool.Bearing.WEST, radius, lengthUnit);
		LatLng llSE = LatLngTool.travel(southCenter, LatLngTool.Bearing.EAST, radius, lengthUnit);
		
		double north = max(llNW.getLatitude(), northCenter.getLatitude(), llNE.getLatitude());
		double south = min(llSW.getLatitude(), southCenter.getLatitude(), llSE.getLatitude());
		
		// Did we cross the antimeridian at either the top or bottom of the box?
		if ( crossesAntiMeridian(llNW, llNE) || crossesAntiMeridian(llSW, llSE)) { // There'll be two boxes, split across the antimeridian.  
			/*
			 * Farthest west is the largest (and will be positive), farthest east will be the minimum(and negative)
			 * So the two boxes go:
			 *    1 from the smallest positive to the positive antimeridian (180)
			 *    2 from the negative antimeridian (-180) to the largest negative 
			 */
			double west = getSmallestPositive (llNW.getLongitude(), westCenter.getLongitude(), llSW.getLongitude());
			double east = getLargestNegative (llNE.getLongitude(), eastCenter.getLongitude(), llSE.getLongitude());
			
			BoundingBox ret = new BoundingBox();
			ret.add (new BoundingBoxlet.Builder()
				                            .setLongitudeWest(west) 
				                            .setLongitudeEast(180) 
				                            .setLatitudeSouth(south) 
				                            .setLatitudeNorth(north)
				                            .build());
			ret.add (new BoundingBoxlet.Builder()
				                            .setLongitudeWest(-180) 
				                            .setLongitudeEast(east) 
				                            .setLatitudeSouth(south) 
				                            .setLatitudeNorth(north)
				                            .build());
			return ret;
			
		} else { // just one box, from the smallest to the largest
			double west = min (westCenter.getLongitude(), llNW.getLongitude(), llSW.getLongitude());
			double east = max (eastCenter.getLongitude(), llNE.getLongitude(), llSE.getLongitude());
		
			BoundingBox ret = new BoundingBox();
			ret.add (new BoundingBoxlet.Builder().setLongitudeWest(west) 
				                                 .setLongitudeEast(east) 
				                                 .setLatitudeSouth(south) 
				                                 .setLatitudeNorth(north)
				                                 .build());
			return ret;
		}
	}
	
	public static Double min (Double val1, Double val2, Double val3) {
		Double smallest;
		if(val1 <= val2 && val1 <= val3){
		    smallest = val1 ;
		}else if(val2 <= val3 && val2 <= val1 ){
		    smallest = val2 ;
		}else{
		    smallest = val3;
		}		
		return smallest;
	}
	public static Double max (Double val1, Double val2, Double val3) {
		Double largest;
		if(val1 >= val2 && val1 >= val3){
		    largest = val1 ;
		}else if(val2 >= val3 && val2 >= val1 ){
		    largest = val2 ;
		}else{
		    largest = val3;
		}		
		return largest;
	}
	
	public static Double getSmallestPositive (double val1, double... values) {
		Double smallest=null;
		if (val1 >= 0) 
			smallest=val1;
		for (double val: values) {
			if ((val >= 0) && ((smallest == null) || (val < smallest)) ) {
				smallest = val;
			}
		}
		if (smallest == null)
			throw new IllegalArgumentException ("Unable to getSmallestPositive: no supplied value is positive (" + val1 + ", " + Arrays.toString(values));
		return smallest;
	}
	public static Double getLargestNegative (double val1, double ... vals) {
		Double largest= null;
		if (val1 <= 0) 
			largest=val1;
		for (double val: vals) {
			if ((val <= 0) && ((largest == null) || (val > largest)) ) {
				largest = val;
			}
		}
		if (largest == null)
			throw new IllegalArgumentException ("Unable to getLargestNegative: no supplied value is negative (" + val1 + ", " + Arrays.toString(vals) );
		return largest;
	}
	
	private static boolean crossesAntiMeridian (LatLng locWest, LatLng locEast) {
		return crossesAntiMeridian (locWest.getLongitude(), locEast.getLongitude());
	}
	/**
	 * Determines if the West->East path between the two given points crosses the antimeridian.
	 * Note that it is possible to describe a path where the "west" point is farther east than 
	 * the "east" point, causing a trip "round the world".
	 * @param lngWest
	 * @param lngEast
	 * @return
	 */
	public static boolean crossesAntiMeridian (double lngWest, double lngEast) {
		// If both are the same, can't cross the antimeridian.
		if (lngWest == lngEast) 
			return false;
		
		// If both positive or both negative, cross only if going long way around (west past east.
		if (   (lngWest >= 0 && lngEast >= 0)
		    || (lngWest <= 0 && lngEast <= 0))
			return (lngWest > lngEast);
		else // one is positive, one is negative: cross if the west side is positive
			return (lngWest >= 0);
	}
	public static String describeLongitudeRange(double lngWest, double lngEast) {
		StringBuilder sb = new StringBuilder();
			/*
			 * Farthest west is the largest (and will be positive), farthest east will be the minimum(and negative)
			 * So the two boxes go:
			 *    1 from the smallest positive to the positive antimeridian (180)
			 *    2 from the negative antimeridian (-180) to the largest negative 
			 */
		if (crossesAntiMeridian(lngWest, lngEast)) {
			double west = getSmallestPositive (lngWest, lngEast);
			double east = getLargestNegative (lngWest, lngEast);
			sb.append("from " + west + " to 180, then from -180 to " + east + " (range=" + ((180-west) + (east+180)) + " degrees)");
			
		} else {
			double longitude1 = Math.min(lngWest, lngEast);
			double longitude2 = Math.max(lngWest, lngEast);
			sb.append("from " + longitude1 + " to 180, then from -180 to " + longitude2 + " (range = " + (longitude2-longitude1) + " degrees)");
			
		}
		return sb.toString();
		
	}	

}
