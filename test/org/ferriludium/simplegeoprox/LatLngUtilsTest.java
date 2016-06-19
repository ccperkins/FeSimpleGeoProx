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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.util.LengthUnit;

public class LatLngUtilsTest {

	@Test
	public void testGet() {
		testGet(new LatLng(41.154873, -73.4377622), 2.0, LengthUnit.MILE);
	}
	@Test
	public void testGetOverAntiMeridian_1() {
		testGet(new LatLng(41.154873, -179.999), 20.0, LengthUnit.MILE);
	}
	public void testGet(LatLng start, double radius, LengthUnit lengthUnit) {
		BoundingBox boxes = LatLngUtils.getBoundingBox(start, radius, lengthUnit);
		System.out.println(start + ", radius=" + radius + "; Returned " + boxes.boxes.size() + " boxes");
		for (BoundingBoxlet box: boxes.boxes) {
			System.out.println("\tReturned box=" + box );
		}
	}

	
	@Test
	public void testCrossesAntiMeridian_179_to_minus179() {
	testCrossesAntiMeridian (179.0, -179.0, true);
	}
	@Test
	public void testCrossesAntiMeridian_minus179_to_179() {
	testCrossesAntiMeridian (-179.0, 179.0, false); //  long way 'round
	}
	@Test
	public void testCrossesAntiMeridian_1_to_minus1() {
	testCrossesAntiMeridian (1.0, -1.0, true); //  long way 'round
	}
	@Test
	public void testCrossesAntiMeridian_minus1_to_1() {
	testCrossesAntiMeridian (-1.0, 1.0, false);
	}
	@Test
	public void testCrossesAntiMeridian_79_to_78() {
	testCrossesAntiMeridian (79.0, 78.0, true); // long way 'round
	}
	@Test
	public void testCrossesAntiMeridian_78_to_79() {
	testCrossesAntiMeridian (78.0, 79.0, false);
	}
	@Test
	public void testCrossesAntiMeridian_minus79_to_minus78() {
	testCrossesAntiMeridian (-79.0, -78.0, false);
	}
	@Test
	public void testCrossesAntiMeridian_minus78_to_minus79() {
		testCrossesAntiMeridian (-78.0, -79.0, true); // long way 'round
	}
	
	public void testCrossesAntiMeridian(double lng1, double lng2, boolean expected) {
		boolean res = LatLngUtils.crossesAntiMeridian(lng1, lng2);
		assertEquals (expected, res);
	}
	
	public void testSmallestPositive (Double expected, Double val1, Double val2, Double val3) {
		Double res = LatLngUtils.getSmallestPositive(val1, val2, val3);
		assertEquals(expected, res);
	}
	public void testLargestNegative (Double expected, Double val1, Double val2, Double val3) {
		Double res = LatLngUtils.getLargestNegative(val1, val2, val3);
		assertEquals(expected, res);
	}
	
	public void testMin (Double expected, Double val1, Double val2, Double val3) {
		Double res = LatLngUtils.min(val1, val2, val3);
		assertEquals(expected, res);
	}
	public void testMax (Double expected, Double val1, Double val2, Double val3) {
		Double res = LatLngUtils.max(val1, val2, val3);
		assertEquals(expected, res);
	}
		
	@Test
	public void testMin_neg3_neg3_neg3() {
	 testMin(-3.0, -3.0, -3.0, -3.0);
	}
	@Test
	public void testMin_neg3_neg3_neg1() {
	 testMin(-3.0, -3.0, -3.0, -1.0);
	}
	@Test
	public void testMin_neg3_neg3_0() {
	 testMin(-3.0, -3.0, -3.0, 0.0);
	}
	@Test
	public void testMin_neg3_neg3_1() {
	 testMin(-3.0, -3.0, -3.0, 1.0);
	}
	@Test
	public void testMin_neg3_neg3_3() {
	 testMin(-3.0, -3.0, -3.0, 3.0);
	}
	@Test
	public void testMin_neg3_neg1_neg3() {
	 testMin(-3.0, -3.0, -1.0, -3.0);
	}
	@Test
	public void testMin_neg3_neg1_neg1() {
	 testMin(-3.0, -3.0, -1.0, -1.0);
	}
	@Test
	public void testMin_neg3_neg1_0() {
	 testMin(-3.0, -3.0, -1.0, 0.0);
	}
	@Test
	public void testMin_neg3_neg1_1() {
	 testMin(-3.0, -3.0, -1.0, 1.0);
	}
	@Test
	public void testMin_neg3_neg1_3() {
	 testMin(-3.0, -3.0, -1.0, 3.0);
	}
	@Test
	public void testMin_neg3_0_neg3() {
	 testMin(-3.0, -3.0, 0.0, -3.0);
	}
	@Test
	public void testMin_neg3_0_neg1() {
	 testMin(-3.0, -3.0, 0.0, -1.0);
	}
	@Test
	public void testMin_neg3_0_0() {
	 testMin(-3.0, -3.0, 0.0, 0.0);
	}
	@Test
	public void testMin_neg3_0_1() {
	 testMin(-3.0, -3.0, 0.0, 1.0);
	}
	@Test
	public void testMin_neg3_0_3() {
	 testMin(-3.0, -3.0, 0.0, 3.0);
	}
	@Test
	public void testMin_neg3_1_neg3() {
	 testMin(-3.0, -3.0, 1.0, -3.0);
	}
	@Test
	public void testMin_neg3_1_neg1() {
	 testMin(-3.0, -3.0, 1.0, -1.0);
	}
	@Test
	public void testMin_neg3_1_0() {
	 testMin(-3.0, -3.0, 1.0, 0.0);
	}
	@Test
	public void testMin_neg3_1_1() {
	 testMin(-3.0, -3.0, 1.0, 1.0);
	}
	@Test
	public void testMin_neg3_1_3() {
	 testMin(-3.0, -3.0, 1.0, 3.0);
	}
	@Test
	public void testMin_neg3_3_neg3() {
	 testMin(-3.0, -3.0, 3.0, -3.0);
	}
	@Test
	public void testMin_neg3_3_neg1() {
	 testMin(-3.0, -3.0, 3.0, -1.0);
	}
	@Test
	public void testMin_neg3_3_0() {
	 testMin(-3.0, -3.0, 3.0, 0.0);
	}
	@Test
	public void testMin_neg3_3_1() {
	 testMin(-3.0, -3.0, 3.0, 1.0);
	}
	@Test
	public void testMin_neg3_3_3() {
	 testMin(-3.0, -3.0, 3.0, 3.0);
	}
	@Test
	public void testMin_neg1_neg3_neg3() {
	 testMin(-3.0, -1.0, -3.0, -3.0);
	}
	@Test
	public void testMin_neg1_neg3_neg1() {
	 testMin(-3.0, -1.0, -3.0, -1.0);
	}
	@Test
	public void testMin_neg1_neg3_0() {
	 testMin(-3.0, -1.0, -3.0, 0.0);
	}
	@Test
	public void testMin_neg1_neg3_1() {
	 testMin(-3.0, -1.0, -3.0, 1.0);
	}
	@Test
	public void testMin_neg1_neg3_3() {
	 testMin(-3.0, -1.0, -3.0, 3.0);
	}
	@Test
	public void testMin_neg1_neg1_neg3() {
	 testMin(-3.0, -1.0, -1.0, -3.0);
	}
	@Test
	public void testMin_neg1_neg1_neg1() {
	 testMin(-1.0, -1.0, -1.0, -1.0);
	}
	@Test
	public void testMin_neg1_neg1_0() {
	 testMin(-1.0, -1.0, -1.0, 0.0);
	}
	@Test
	public void testMin_neg1_neg1_1() {
	 testMin(-1.0, -1.0, -1.0, 1.0);
	}
	@Test
	public void testMin_neg1_neg1_3() {
	 testMin(-1.0, -1.0, -1.0, 3.0);
	}
	@Test
	public void testMin_neg1_0_neg3() {
	 testMin(-3.0, -1.0, 0.0, -3.0);
	}
	@Test
	public void testMin_neg1_0_neg1() {
	 testMin(-1.0, -1.0, 0.0, -1.0);
	}
	@Test
	public void testMin_neg1_0_0() {
	 testMin(-1.0, -1.0, 0.0, 0.0);
	}
	@Test
	public void testMin_neg1_0_1() {
	 testMin(-1.0, -1.0, 0.0, 1.0);
	}
	@Test
	public void testMin_neg1_0_3() {
	 testMin(-1.0, -1.0, 0.0, 3.0);
	}
	@Test
	public void testMin_neg1_1_neg3() {
	 testMin(-3.0, -1.0, 1.0, -3.0);
	}
	@Test
	public void testMin_neg1_1_neg1() {
	 testMin(-1.0, -1.0, 1.0, -1.0);
	}
	@Test
	public void testMin_neg1_1_0() {
	 testMin(-1.0, -1.0, 1.0, 0.0);
	}
	@Test
	public void testMin_neg1_1_1() {
	 testMin(-1.0, -1.0, 1.0, 1.0);
	}
	@Test
	public void testMin_neg1_1_3() {
	 testMin(-1.0, -1.0, 1.0, 3.0);
	}
	@Test
	public void testMin_neg1_3_neg3() {
	 testMin(-3.0, -1.0, 3.0, -3.0);
	}
	@Test
	public void testMin_neg1_3_neg1() {
	 testMin(-1.0, -1.0, 3.0, -1.0);
	}
	@Test
	public void testMin_neg1_3_0() {
	 testMin(-1.0, -1.0, 3.0, 0.0);
	}
	@Test
	public void testMin_neg1_3_1() {
	 testMin(-1.0, -1.0, 3.0, 1.0);
	}
	@Test
	public void testMin_neg1_3_3() {
	 testMin(-1.0, -1.0, 3.0, 3.0);
	}
	@Test
	public void testMin_0_neg3_neg3() {
	 testMin(-3.0, 0.0, -3.0, -3.0);
	}
	@Test
	public void testMin_0_neg3_neg1() {
	 testMin(-3.0, 0.0, -3.0, -1.0);
	}
	@Test
	public void testMin_0_neg3_0() {
	 testMin(-3.0, 0.0, -3.0, 0.0);
	}
	@Test
	public void testMin_0_neg3_1() {
	 testMin(-3.0, 0.0, -3.0, 1.0);
	}
	@Test
	public void testMin_0_neg3_3() {
	 testMin(-3.0, 0.0, -3.0, 3.0);
	}
	@Test
	public void testMin_0_neg1_neg3() {
	 testMin(-3.0, 0.0, -1.0, -3.0);
	}
	@Test
	public void testMin_0_neg1_neg1() {
	 testMin(-1.0, 0.0, -1.0, -1.0);
	}
	@Test
	public void testMin_0_neg1_0() {
	 testMin(-1.0, 0.0, -1.0, 0.0);
	}
	@Test
	public void testMin_0_neg1_1() {
	 testMin(-1.0, 0.0, -1.0, 1.0);
	}
	@Test
	public void testMin_0_neg1_3() {
	 testMin(-1.0, 0.0, -1.0, 3.0);
	}
	@Test
	public void testMin_0_0_neg3() {
	 testMin(-3.0, 0.0, 0.0, -3.0);
	}
	@Test
	public void testMin_0_0_neg1() {
	 testMin(-1.0, 0.0, 0.0, -1.0);
	}
	@Test
	public void testMin_0_0_0() {
	 testMin(0.0, 0.0, 0.0, 0.0);
	}
	@Test
	public void testMin_0_0_1() {
	 testMin(0.0, 0.0, 0.0, 1.0);
	}
	@Test
	public void testMin_0_0_3() {
	 testMin(0.0, 0.0, 0.0, 3.0);
	}
	@Test
	public void testMin_0_1_neg3() {
	 testMin(-3.0, 0.0, 1.0, -3.0);
	}
	@Test
	public void testMin_0_1_neg1() {
	 testMin(-1.0, 0.0, 1.0, -1.0);
	}
	@Test
	public void testMin_0_1_0() {
	 testMin(0.0, 0.0, 1.0, 0.0);
	}
	@Test
	public void testMin_0_1_1() {
	 testMin(0.0, 0.0, 1.0, 1.0);
	}
	@Test
	public void testMin_0_1_3() {
	 testMin(0.0, 0.0, 1.0, 3.0);
	}
	@Test
	public void testMin_0_3_neg3() {
	 testMin(-3.0, 0.0, 3.0, -3.0);
	}
	@Test
	public void testMin_0_3_neg1() {
	 testMin(-1.0, 0.0, 3.0, -1.0);
	}
	@Test
	public void testMin_0_3_0() {
	 testMin(0.0, 0.0, 3.0, 0.0);
	}
	@Test
	public void testMin_0_3_1() {
	 testMin(0.0, 0.0, 3.0, 1.0);
	}
	@Test
	public void testMin_0_3_3() {
	 testMin(0.0, 0.0, 3.0, 3.0);
	}
	@Test
	public void testMin_1_neg3_neg3() {
	 testMin(-3.0, 1.0, -3.0, -3.0);
	}
	@Test
	public void testMin_1_neg3_neg1() {
	 testMin(-3.0, 1.0, -3.0, -1.0);
	}
	@Test
	public void testMin_1_neg3_0() {
	 testMin(-3.0, 1.0, -3.0, 0.0);
	}
	@Test
	public void testMin_1_neg3_1() {
	 testMin(-3.0, 1.0, -3.0, 1.0);
	}
	@Test
	public void testMin_1_neg3_3() {
	 testMin(-3.0, 1.0, -3.0, 3.0);
	}
	@Test
	public void testMin_1_neg1_neg3() {
	 testMin(-3.0, 1.0, -1.0, -3.0);
	}
	@Test
	public void testMin_1_neg1_neg1() {
	 testMin(-1.0, 1.0, -1.0, -1.0);
	}
	@Test
	public void testMin_1_neg1_0() {
	 testMin(-1.0, 1.0, -1.0, 0.0);
	}
	@Test
	public void testMin_1_neg1_1() {
	 testMin(-1.0, 1.0, -1.0, 1.0);
	}
	@Test
	public void testMin_1_neg1_3() {
	 testMin(-1.0, 1.0, -1.0, 3.0);
	}
	@Test
	public void testMin_1_0_neg3() {
	 testMin(-3.0, 1.0, 0.0, -3.0);
	}
	@Test
	public void testMin_1_0_neg1() {
	 testMin(-1.0, 1.0, 0.0, -1.0);
	}
	@Test
	public void testMin_1_0_0() {
	 testMin(0.0, 1.0, 0.0, 0.0);
	}
	@Test
	public void testMin_1_0_1() {
	 testMin(0.0, 1.0, 0.0, 1.0);
	}
	@Test
	public void testMin_1_0_3() {
	 testMin(0.0, 1.0, 0.0, 3.0);
	}
	@Test
	public void testMin_1_1_neg3() {
	 testMin(-3.0, 1.0, 1.0, -3.0);
	}
	@Test
	public void testMin_1_1_neg1() {
	 testMin(-1.0, 1.0, 1.0, -1.0);
	}
	@Test
	public void testMin_1_1_0() {
	 testMin(0.0, 1.0, 1.0, 0.0);
	}
	@Test
	public void testMin_1_1_1() {
	 testMin(1.0, 1.0, 1.0, 1.0);
	}
	@Test
	public void testMin_1_1_3() {
	 testMin(1.0, 1.0, 1.0, 3.0);
	}
	@Test
	public void testMin_1_3_neg3() {
	 testMin(-3.0, 1.0, 3.0, -3.0);
	}
	@Test
	public void testMin_1_3_neg1() {
	 testMin(-1.0, 1.0, 3.0, -1.0);
	}
	@Test
	public void testMin_1_3_0() {
	 testMin(0.0, 1.0, 3.0, 0.0);
	}
	@Test
	public void testMin_1_3_1() {
	 testMin(1.0, 1.0, 3.0, 1.0);
	}
	@Test
	public void testMin_1_3_3() {
	 testMin(1.0, 1.0, 3.0, 3.0);
	}
	@Test
	public void testMin_3_neg3_neg3() {
	 testMin(-3.0, 3.0, -3.0, -3.0);
	}
	@Test
	public void testMin_3_neg3_neg1() {
	 testMin(-3.0, 3.0, -3.0, -1.0);
	}
	@Test
	public void testMin_3_neg3_0() {
	 testMin(-3.0, 3.0, -3.0, 0.0);
	}
	@Test
	public void testMin_3_neg3_1() {
	 testMin(-3.0, 3.0, -3.0, 1.0);
	}
	@Test
	public void testMin_3_neg3_3() {
	 testMin(-3.0, 3.0, -3.0, 3.0);
	}
	@Test
	public void testMin_3_neg1_neg3() {
	 testMin(-3.0, 3.0, -1.0, -3.0);
	}
	@Test
	public void testMin_3_neg1_neg1() {
	 testMin(-1.0, 3.0, -1.0, -1.0);
	}
	@Test
	public void testMin_3_neg1_0() {
	 testMin(-1.0, 3.0, -1.0, 0.0);
	}
	@Test
	public void testMin_3_neg1_1() {
	 testMin(-1.0, 3.0, -1.0, 1.0);
	}
	@Test
	public void testMin_3_neg1_3() {
	 testMin(-1.0, 3.0, -1.0, 3.0);
	}
	@Test
	public void testMin_3_0_neg3() {
	 testMin(-3.0, 3.0, 0.0, -3.0);
	}
	@Test
	public void testMin_3_0_neg1() {
	 testMin(-1.0, 3.0, 0.0, -1.0);
	}
	@Test
	public void testMin_3_0_0() {
	 testMin(0.0, 3.0, 0.0, 0.0);
	}
	@Test
	public void testMin_3_0_1() {
	 testMin(0.0, 3.0, 0.0, 1.0);
	}
	@Test
	public void testMin_3_0_3() {
	 testMin(0.0, 3.0, 0.0, 3.0);
	}
	@Test
	public void testMin_3_1_neg3() {
	 testMin(-3.0, 3.0, 1.0, -3.0);
	}
	@Test
	public void testMin_3_1_neg1() {
	 testMin(-1.0, 3.0, 1.0, -1.0);
	}
	@Test
	public void testMin_3_1_0() {
	 testMin(0.0, 3.0, 1.0, 0.0);
	}
	@Test
	public void testMin_3_1_1() {
	 testMin(1.0, 3.0, 1.0, 1.0);
	}
	@Test
	public void testMin_3_1_3() {
	 testMin(1.0, 3.0, 1.0, 3.0);
	}
	@Test
	public void testMin_3_3_neg3() {
	 testMin(-3.0, 3.0, 3.0, -3.0);
	}
	@Test
	public void testMin_3_3_neg1() {
	 testMin(-1.0, 3.0, 3.0, -1.0);
	}
	@Test
	public void testMin_3_3_0() {
	 testMin(0.0, 3.0, 3.0, 0.0);
	}
	@Test
	public void testMin_3_3_1() {
	 testMin(1.0, 3.0, 3.0, 1.0);
	}
	@Test
	public void testMin_3_3_3() {
	 testMin(3.0, 3.0, 3.0, 3.0);
	}

	
	@Test
	public void testMax_neg3_neg3_neg3() {
	 testMax(-3.0, -3.0, -3.0, -3.0);
	}
	@Test
	public void testMax_neg3_neg3_neg1() {
	 testMax(-1.0, -3.0, -3.0, -1.0);
	}
	@Test
	public void testMax_neg3_neg3_0() {
	 testMax(0.0, -3.0, -3.0, 0.0);
	}
	@Test
	public void testMax_neg3_neg3_1() {
	 testMax(1.0, -3.0, -3.0, 1.0);
	}
	@Test
	public void testMax_neg3_neg3_3() {
	 testMax(3.0, -3.0, -3.0, 3.0);
	}
	@Test
	public void testMax_neg3_neg1_neg3() {
	 testMax(-1.0, -3.0, -1.0, -3.0);
	}
	@Test
	public void testMax_neg3_neg1_neg1() {
	 testMax(-1.0, -3.0, -1.0, -1.0);
	}
	@Test
	public void testMax_neg3_neg1_0() {
	 testMax(0.0, -3.0, -1.0, 0.0);
	}
	@Test
	public void testMax_neg3_neg1_1() {
	 testMax(1.0, -3.0, -1.0, 1.0);
	}
	@Test
	public void testMax_neg3_neg1_3() {
	 testMax(3.0, -3.0, -1.0, 3.0);
	}
	@Test
	public void testMax_neg3_0_neg3() {
	 testMax(0.0, -3.0, 0.0, -3.0);
	}
	@Test
	public void testMax_neg3_0_neg1() {
	 testMax(0.0, -3.0, 0.0, -1.0);
	}
	@Test
	public void testMax_neg3_0_0() {
	 testMax(0.0, -3.0, 0.0, 0.0);
	}
	@Test
	public void testMax_neg3_0_1() {
	 testMax(1.0, -3.0, 0.0, 1.0);
	}
	@Test
	public void testMax_neg3_0_3() {
	 testMax(3.0, -3.0, 0.0, 3.0);
	}
	@Test
	public void testMax_neg3_1_neg3() {
	 testMax(1.0, -3.0, 1.0, -3.0);
	}
	@Test
	public void testMax_neg3_1_neg1() {
	 testMax(1.0, -3.0, 1.0, -1.0);
	}
	@Test
	public void testMax_neg3_1_0() {
	 testMax(1.0, -3.0, 1.0, 0.0);
	}
	@Test
	public void testMax_neg3_1_1() {
	 testMax(1.0, -3.0, 1.0, 1.0);
	}
	@Test
	public void testMax_neg3_1_3() {
	 testMax(3.0, -3.0, 1.0, 3.0);
	}
	@Test
	public void testMax_neg3_3_neg3() {
	 testMax(3.0, -3.0, 3.0, -3.0);
	}
	@Test
	public void testMax_neg3_3_neg1() {
	 testMax(3.0, -3.0, 3.0, -1.0);
	}
	@Test
	public void testMax_neg3_3_0() {
	 testMax(3.0, -3.0, 3.0, 0.0);
	}
	@Test
	public void testMax_neg3_3_1() {
	 testMax(3.0, -3.0, 3.0, 1.0);
	}
	@Test
	public void testMax_neg3_3_3() {
	 testMax(3.0, -3.0, 3.0, 3.0);
	}
	@Test
	public void testMax_neg1_neg3_neg3() {
	 testMax(-1.0, -1.0, -3.0, -3.0);
	}
	@Test
	public void testMax_neg1_neg3_neg1() {
	 testMax(-1.0, -1.0, -3.0, -1.0);
	}
	@Test
	public void testMax_neg1_neg3_0() {
	 testMax(0.0, -1.0, -3.0, 0.0);
	}
	@Test
	public void testMax_neg1_neg3_1() {
	 testMax(1.0, -1.0, -3.0, 1.0);
	}
	@Test
	public void testMax_neg1_neg3_3() {
	 testMax(3.0, -1.0, -3.0, 3.0);
	}
	@Test
	public void testMax_neg1_neg1_neg3() {
	 testMax(-1.0, -1.0, -1.0, -3.0);
	}
	@Test
	public void testMax_neg1_neg1_neg1() {
	 testMax(-1.0, -1.0, -1.0, -1.0);
	}
	@Test
	public void testMax_neg1_neg1_0() {
	 testMax(0.0, -1.0, -1.0, 0.0);
	}
	@Test
	public void testMax_neg1_neg1_1() {
	 testMax(1.0, -1.0, -1.0, 1.0);
	}
	@Test
	public void testMax_neg1_neg1_3() {
	 testMax(3.0, -1.0, -1.0, 3.0);
	}
	@Test
	public void testMax_neg1_0_neg3() {
	 testMax(0.0, -1.0, 0.0, -3.0);
	}
	@Test
	public void testMax_neg1_0_neg1() {
	 testMax(0.0, -1.0, 0.0, -1.0);
	}
	@Test
	public void testMax_neg1_0_0() {
	 testMax(0.0, -1.0, 0.0, 0.0);
	}
	@Test
	public void testMax_neg1_0_1() {
	 testMax(1.0, -1.0, 0.0, 1.0);
	}
	@Test
	public void testMax_neg1_0_3() {
	 testMax(3.0, -1.0, 0.0, 3.0);
	}
	@Test
	public void testMax_neg1_1_neg3() {
	 testMax(1.0, -1.0, 1.0, -3.0);
	}
	@Test
	public void testMax_neg1_1_neg1() {
	 testMax(1.0, -1.0, 1.0, -1.0);
	}
	@Test
	public void testMax_neg1_1_0() {
	 testMax(1.0, -1.0, 1.0, 0.0);
	}
	@Test
	public void testMax_neg1_1_1() {
	 testMax(1.0, -1.0, 1.0, 1.0);
	}
	@Test
	public void testMax_neg1_1_3() {
	 testMax(3.0, -1.0, 1.0, 3.0);
	}
	@Test
	public void testMax_neg1_3_neg3() {
	 testMax(3.0, -1.0, 3.0, -3.0);
	}
	@Test
	public void testMax_neg1_3_neg1() {
	 testMax(3.0, -1.0, 3.0, -1.0);
	}
	@Test
	public void testMax_neg1_3_0() {
	 testMax(3.0, -1.0, 3.0, 0.0);
	}
	@Test
	public void testMax_neg1_3_1() {
	 testMax(3.0, -1.0, 3.0, 1.0);
	}
	@Test
	public void testMax_neg1_3_3() {
	 testMax(3.0, -1.0, 3.0, 3.0);
	}
	@Test
	public void testMax_0_neg3_neg3() {
	 testMax(0.0, 0.0, -3.0, -3.0);
	}
	@Test
	public void testMax_0_neg3_neg1() {
	 testMax(0.0, 0.0, -3.0, -1.0);
	}
	@Test
	public void testMax_0_neg3_0() {
	 testMax(0.0, 0.0, -3.0, 0.0);
	}
	@Test
	public void testMax_0_neg3_1() {
	 testMax(1.0, 0.0, -3.0, 1.0);
	}
	@Test
	public void testMax_0_neg3_3() {
	 testMax(3.0, 0.0, -3.0, 3.0);
	}
	@Test
	public void testMax_0_neg1_neg3() {
	 testMax(0.0, 0.0, -1.0, -3.0);
	}
	@Test
	public void testMax_0_neg1_neg1() {
	 testMax(0.0, 0.0, -1.0, -1.0);
	}
	@Test
	public void testMax_0_neg1_0() {
	 testMax(0.0, 0.0, -1.0, 0.0);
	}
	@Test
	public void testMax_0_neg1_1() {
	 testMax(1.0, 0.0, -1.0, 1.0);
	}
	@Test
	public void testMax_0_neg1_3() {
	 testMax(3.0, 0.0, -1.0, 3.0);
	}
	@Test
	public void testMax_0_0_neg3() {
	 testMax(0.0, 0.0, 0.0, -3.0);
	}
	@Test
	public void testMax_0_0_neg1() {
	 testMax(0.0, 0.0, 0.0, -1.0);
	}
	@Test
	public void testMax_0_0_0() {
	 testMax(0.0, 0.0, 0.0, 0.0);
	}
	@Test
	public void testMax_0_0_1() {
	 testMax(1.0, 0.0, 0.0, 1.0);
	}
	@Test
	public void testMax_0_0_3() {
	 testMax(3.0, 0.0, 0.0, 3.0);
	}
	@Test
	public void testMax_0_1_neg3() {
	 testMax(1.0, 0.0, 1.0, -3.0);
	}
	@Test
	public void testMax_0_1_neg1() {
	 testMax(1.0, 0.0, 1.0, -1.0);
	}
	@Test
	public void testMax_0_1_0() {
	 testMax(1.0, 0.0, 1.0, 0.0);
	}
	@Test
	public void testMax_0_1_1() {
	 testMax(1.0, 0.0, 1.0, 1.0);
	}
	@Test
	public void testMax_0_1_3() {
	 testMax(3.0, 0.0, 1.0, 3.0);
	}
	@Test
	public void testMax_0_3_neg3() {
	 testMax(3.0, 0.0, 3.0, -3.0);
	}
	@Test
	public void testMax_0_3_neg1() {
	 testMax(3.0, 0.0, 3.0, -1.0);
	}
	@Test
	public void testMax_0_3_0() {
	 testMax(3.0, 0.0, 3.0, 0.0);
	}
	@Test
	public void testMax_0_3_1() {
	 testMax(3.0, 0.0, 3.0, 1.0);
	}
	@Test
	public void testMax_0_3_3() {
	 testMax(3.0, 0.0, 3.0, 3.0);
	}
	@Test
	public void testMax_1_neg3_neg3() {
	 testMax(1.0, 1.0, -3.0, -3.0);
	}
	@Test
	public void testMax_1_neg3_neg1() {
	 testMax(1.0, 1.0, -3.0, -1.0);
	}
	@Test
	public void testMax_1_neg3_0() {
	 testMax(1.0, 1.0, -3.0, 0.0);
	}
	@Test
	public void testMax_1_neg3_1() {
	 testMax(1.0, 1.0, -3.0, 1.0);
	}
	@Test
	public void testMax_1_neg3_3() {
	 testMax(3.0, 1.0, -3.0, 3.0);
	}
	@Test
	public void testMax_1_neg1_neg3() {
	 testMax(1.0, 1.0, -1.0, -3.0);
	}
	@Test
	public void testMax_1_neg1_neg1() {
	 testMax(1.0, 1.0, -1.0, -1.0);
	}
	@Test
	public void testMax_1_neg1_0() {
	 testMax(1.0, 1.0, -1.0, 0.0);
	}
	@Test
	public void testMax_1_neg1_1() {
	 testMax(1.0, 1.0, -1.0, 1.0);
	}
	@Test
	public void testMax_1_neg1_3() {
	 testMax(3.0, 1.0, -1.0, 3.0);
	}
	@Test
	public void testMax_1_0_neg3() {
	 testMax(1.0, 1.0, 0.0, -3.0);
	}
	@Test
	public void testMax_1_0_neg1() {
	 testMax(1.0, 1.0, 0.0, -1.0);
	}
	@Test
	public void testMax_1_0_0() {
	 testMax(1.0, 1.0, 0.0, 0.0);
	}
	@Test
	public void testMax_1_0_1() {
	 testMax(1.0, 1.0, 0.0, 1.0);
	}
	@Test
	public void testMax_1_0_3() {
	 testMax(3.0, 1.0, 0.0, 3.0);
	}
	@Test
	public void testMax_1_1_neg3() {
	 testMax(1.0, 1.0, 1.0, -3.0);
	}
	@Test
	public void testMax_1_1_neg1() {
	 testMax(1.0, 1.0, 1.0, -1.0);
	}
	@Test
	public void testMax_1_1_0() {
	 testMax(1.0, 1.0, 1.0, 0.0);
	}
	@Test
	public void testMax_1_1_1() {
	 testMax(1.0, 1.0, 1.0, 1.0);
	}
	@Test
	public void testMax_1_1_3() {
	 testMax(3.0, 1.0, 1.0, 3.0);
	}
	@Test
	public void testMax_1_3_neg3() {
	 testMax(3.0, 1.0, 3.0, -3.0);
	}
	@Test
	public void testMax_1_3_neg1() {
	 testMax(3.0, 1.0, 3.0, -1.0);
	}
	@Test
	public void testMax_1_3_0() {
	 testMax(3.0, 1.0, 3.0, 0.0);
	}
	@Test
	public void testMax_1_3_1() {
	 testMax(3.0, 1.0, 3.0, 1.0);
	}
	@Test
	public void testMax_1_3_3() {
	 testMax(3.0, 1.0, 3.0, 3.0);
	}
	@Test
	public void testMax_3_neg3_neg3() {
	 testMax(3.0, 3.0, -3.0, -3.0);
	}
	@Test
	public void testMax_3_neg3_neg1() {
	 testMax(3.0, 3.0, -3.0, -1.0);
	}
	@Test
	public void testMax_3_neg3_0() {
	 testMax(3.0, 3.0, -3.0, 0.0);
	}
	@Test
	public void testMax_3_neg3_1() {
	 testMax(3.0, 3.0, -3.0, 1.0);
	}
	@Test
	public void testMax_3_neg3_3() {
	 testMax(3.0, 3.0, -3.0, 3.0);
	}
	@Test
	public void testMax_3_neg1_neg3() {
	 testMax(3.0, 3.0, -1.0, -3.0);
	}
	@Test
	public void testMax_3_neg1_neg1() {
	 testMax(3.0, 3.0, -1.0, -1.0);
	}
	@Test
	public void testMax_3_neg1_0() {
	 testMax(3.0, 3.0, -1.0, 0.0);
	}
	@Test
	public void testMax_3_neg1_1() {
	 testMax(3.0, 3.0, -1.0, 1.0);
	}
	@Test
	public void testMax_3_neg1_3() {
	 testMax(3.0, 3.0, -1.0, 3.0);
	}
	@Test
	public void testMax_3_0_neg3() {
	 testMax(3.0, 3.0, 0.0, -3.0);
	}
	@Test
	public void testMax_3_0_neg1() {
	 testMax(3.0, 3.0, 0.0, -1.0);
	}
	@Test
	public void testMax_3_0_0() {
	 testMax(3.0, 3.0, 0.0, 0.0);
	}
	@Test
	public void testMax_3_0_1() {
	 testMax(3.0, 3.0, 0.0, 1.0);
	}
	@Test
	public void testMax_3_0_3() {
	 testMax(3.0, 3.0, 0.0, 3.0);
	}
	@Test
	public void testMax_3_1_neg3() {
	 testMax(3.0, 3.0, 1.0, -3.0);
	}
	@Test
	public void testMax_3_1_neg1() {
	 testMax(3.0, 3.0, 1.0, -1.0);
	}
	@Test
	public void testMax_3_1_0() {
	 testMax(3.0, 3.0, 1.0, 0.0);
	}
	@Test
	public void testMax_3_1_1() {
	 testMax(3.0, 3.0, 1.0, 1.0);
	}
	@Test
	public void testMax_3_1_3() {
	 testMax(3.0, 3.0, 1.0, 3.0);
	}
	@Test
	public void testMax_3_3_neg3() {
	 testMax(3.0, 3.0, 3.0, -3.0);
	}
	@Test
	public void testMax_3_3_neg1() {
	 testMax(3.0, 3.0, 3.0, -1.0);
	}
	@Test
	public void testMax_3_3_0() {
	 testMax(3.0, 3.0, 3.0, 0.0);
	}
	@Test
	public void testMax_3_3_1() {
	 testMax(3.0, 3.0, 3.0, 1.0);
	}
	@Test
	public void testMax_3_3_3() {
	 testMax(3.0, 3.0, 3.0, 3.0);
	}

	
	@Test (expected=IllegalArgumentException.class)
	public void testSmallestPositive_neg3_neg3_neg3() {
	 testSmallestPositive(null, -3.0, -3.0, -3.0);
	}
	@Test (expected=IllegalArgumentException.class)
	public void testSmallestPositive_neg3_neg3_neg1() {
	 testSmallestPositive(null, -3.0, -3.0, -1.0);
	}
	@Test
	public void testSmallestPositive_neg3_neg3_0() {
	 testSmallestPositive(0.0, -3.0, -3.0, 0.0);
	}
	@Test
	public void testSmallestPositive_neg3_neg3_1() {
	 testSmallestPositive(1.0, -3.0, -3.0, 1.0);
	}
	@Test
	public void testSmallestPositive_neg3_neg3_3() {
	 testSmallestPositive(3.0, -3.0, -3.0, 3.0);
	}
	@Test (expected=IllegalArgumentException.class)
	public void testSmallestPositive_neg3_neg1_neg3() {
	 testSmallestPositive(null, -3.0, -1.0, -3.0);
	}
	@Test (expected=IllegalArgumentException.class)
	public void testSmallestPositive_neg3_neg1_neg1() {
	 testSmallestPositive(null, -3.0, -1.0, -1.0);
	}
	@Test
	public void testSmallestPositive_neg3_neg1_0() {
	 testSmallestPositive(0.0, -3.0, -1.0, 0.0);
	}
	@Test
	public void testSmallestPositive_neg3_neg1_1() {
	 testSmallestPositive(1.0, -3.0, -1.0, 1.0);
	}
	@Test
	public void testSmallestPositive_neg3_neg1_3() {
	 testSmallestPositive(3.0, -3.0, -1.0, 3.0);
	}
	@Test
	public void testSmallestPositive_neg3_0_neg3() {
	 testSmallestPositive(0.0, -3.0, 0.0, -3.0);
	}
	@Test
	public void testSmallestPositive_neg3_0_neg1() {
	 testSmallestPositive(0.0, -3.0, 0.0, -1.0);
	}
	@Test
	public void testSmallestPositive_neg3_0_0() {
	 testSmallestPositive(0.0, -3.0, 0.0, 0.0);
	}
	@Test
	public void testSmallestPositive_neg3_0_1() {
	 testSmallestPositive(0.0, -3.0, 0.0, 1.0);
	}
	@Test
	public void testSmallestPositive_neg3_0_3() {
	 testSmallestPositive(0.0, -3.0, 0.0, 3.0);
	}
	@Test
	public void testSmallestPositive_neg3_1_neg3() {
	 testSmallestPositive(1.0, -3.0, 1.0, -3.0);
	}
	@Test
	public void testSmallestPositive_neg3_1_neg1() {
	 testSmallestPositive(1.0, -3.0, 1.0, -1.0);
	}
	@Test
	public void testSmallestPositive_neg3_1_0() {
	 testSmallestPositive(0.0, -3.0, 1.0, 0.0);
	}
	@Test
	public void testSmallestPositive_neg3_1_1() {
	 testSmallestPositive(1.0, -3.0, 1.0, 1.0);
	}
	@Test
	public void testSmallestPositive_neg3_1_3() {
	 testSmallestPositive(1.0, -3.0, 1.0, 3.0);
	}
	@Test
	public void testSmallestPositive_neg3_3_neg3() {
	 testSmallestPositive(3.0, -3.0, 3.0, -3.0);
	}
	@Test
	public void testSmallestPositive_neg3_3_neg1() {
	 testSmallestPositive(3.0, -3.0, 3.0, -1.0);
	}
	@Test
	public void testSmallestPositive_neg3_3_0() {
	 testSmallestPositive(0.0, -3.0, 3.0, 0.0);
	}
	@Test
	public void testSmallestPositive_neg3_3_1() {
	 testSmallestPositive(1.0, -3.0, 3.0, 1.0);
	}
	@Test
	public void testSmallestPositive_neg3_3_3() {
	 testSmallestPositive(3.0, -3.0, 3.0, 3.0);
	}
	@Test (expected=IllegalArgumentException.class)
	public void testSmallestPositive_neg1_neg3_neg3() {
	 testSmallestPositive(null, -1.0, -3.0, -3.0);
	}
	@Test (expected=IllegalArgumentException.class)
	public void testSmallestPositive_neg1_neg3_neg1() {
	 testSmallestPositive(null, -1.0, -3.0, -1.0);
	}
	@Test
	public void testSmallestPositive_neg1_neg3_0() {
	 testSmallestPositive(0.0, -1.0, -3.0, 0.0);
	}
	@Test
	public void testSmallestPositive_neg1_neg3_1() {
	 testSmallestPositive(1.0, -1.0, -3.0, 1.0);
	}
	@Test
	public void testSmallestPositive_neg1_neg3_3() {
	 testSmallestPositive(3.0, -1.0, -3.0, 3.0);
	}
	@Test (expected=IllegalArgumentException.class)
	public void testSmallestPositive_neg1_neg1_neg3() {
	 testSmallestPositive(null, -1.0, -1.0, -3.0);
	}
	@Test (expected=IllegalArgumentException.class)
	public void testSmallestPositive_neg1_neg1_neg1() {
	 testSmallestPositive(null, -1.0, -1.0, -1.0);
	}
	@Test
	public void testSmallestPositive_neg1_neg1_0() {
	 testSmallestPositive(0.0, -1.0, -1.0, 0.0);
	}
	@Test
	public void testSmallestPositive_neg1_neg1_1() {
	 testSmallestPositive(1.0, -1.0, -1.0, 1.0);
	}
	@Test
	public void testSmallestPositive_neg1_neg1_3() {
	 testSmallestPositive(3.0, -1.0, -1.0, 3.0);
	}
	@Test
	public void testSmallestPositive_neg1_0_neg3() {
	 testSmallestPositive(0.0, -1.0, 0.0, -3.0);
	}
	@Test
	public void testSmallestPositive_neg1_0_neg1() {
	 testSmallestPositive(0.0, -1.0, 0.0, -1.0);
	}
	@Test
	public void testSmallestPositive_neg1_0_0() {
	 testSmallestPositive(0.0, -1.0, 0.0, 0.0);
	}
	@Test
	public void testSmallestPositive_neg1_0_1() {
	 testSmallestPositive(0.0, -1.0, 0.0, 1.0);
	}
	@Test
	public void testSmallestPositive_neg1_0_3() {
	 testSmallestPositive(0.0, -1.0, 0.0, 3.0);
	}
	@Test
	public void testSmallestPositive_neg1_1_neg3() {
	 testSmallestPositive(1.0, -1.0, 1.0, -3.0);
	}
	@Test
	public void testSmallestPositive_neg1_1_neg1() {
	 testSmallestPositive(1.0, -1.0, 1.0, -1.0);
	}
	@Test
	public void testSmallestPositive_neg1_1_0() {
	 testSmallestPositive(0.0, -1.0, 1.0, 0.0);
	}
	@Test
	public void testSmallestPositive_neg1_1_1() {
	 testSmallestPositive(1.0, -1.0, 1.0, 1.0);
	}
	@Test
	public void testSmallestPositive_neg1_1_3() {
	 testSmallestPositive(1.0, -1.0, 1.0, 3.0);
	}
	@Test
	public void testSmallestPositive_neg1_3_neg3() {
	 testSmallestPositive(3.0, -1.0, 3.0, -3.0);
	}
	@Test
	public void testSmallestPositive_neg1_3_neg1() {
	 testSmallestPositive(3.0, -1.0, 3.0, -1.0);
	}
	@Test
	public void testSmallestPositive_neg1_3_0() {
	 testSmallestPositive(0.0, -1.0, 3.0, 0.0);
	}
	@Test
	public void testSmallestPositive_neg1_3_1() {
	 testSmallestPositive(1.0, -1.0, 3.0, 1.0);
	}
	@Test
	public void testSmallestPositive_neg1_3_3() {
	 testSmallestPositive(3.0, -1.0, 3.0, 3.0);
	}
	@Test
	public void testSmallestPositive_0_neg3_neg3() {
	 testSmallestPositive(0.0, 0.0, -3.0, -3.0);
	}
	@Test
	public void testSmallestPositive_0_neg3_neg1() {
	 testSmallestPositive(0.0, 0.0, -3.0, -1.0);
	}
	@Test
	public void testSmallestPositive_0_neg3_0() {
	 testSmallestPositive(0.0, 0.0, -3.0, 0.0);
	}
	@Test
	public void testSmallestPositive_0_neg3_1() {
	 testSmallestPositive(0.0, 0.0, -3.0, 1.0);
	}
	@Test
	public void testSmallestPositive_0_neg3_3() {
	 testSmallestPositive(0.0, 0.0, -3.0, 3.0);
	}
	@Test
	public void testSmallestPositive_0_neg1_neg3() {
	 testSmallestPositive(0.0, 0.0, -1.0, -3.0);
	}
	@Test
	public void testSmallestPositive_0_neg1_neg1() {
	 testSmallestPositive(0.0, 0.0, -1.0, -1.0);
	}
	@Test
	public void testSmallestPositive_0_neg1_0() {
	 testSmallestPositive(0.0, 0.0, -1.0, 0.0);
	}
	@Test
	public void testSmallestPositive_0_neg1_1() {
	 testSmallestPositive(0.0, 0.0, -1.0, 1.0);
	}
	@Test
	public void testSmallestPositive_0_neg1_3() {
	 testSmallestPositive(0.0, 0.0, -1.0, 3.0);
	}
	@Test
	public void testSmallestPositive_0_0_neg3() {
	 testSmallestPositive(0.0, 0.0, 0.0, -3.0);
	}
	@Test
	public void testSmallestPositive_0_0_neg1() {
	 testSmallestPositive(0.0, 0.0, 0.0, -1.0);
	}
	@Test
	public void testSmallestPositive_0_0_0() {
	 testSmallestPositive(0.0, 0.0, 0.0, 0.0);
	}
	@Test
	public void testSmallestPositive_0_0_1() {
	 testSmallestPositive(0.0, 0.0, 0.0, 1.0);
	}
	@Test
	public void testSmallestPositive_0_0_3() {
	 testSmallestPositive(0.0, 0.0, 0.0, 3.0);
	}
	@Test
	public void testSmallestPositive_0_1_neg3() {
	 testSmallestPositive(0.0, 0.0, 1.0, -3.0);
	}
	@Test
	public void testSmallestPositive_0_1_neg1() {
	 testSmallestPositive(0.0, 0.0, 1.0, -1.0);
	}
	@Test
	public void testSmallestPositive_0_1_0() {
	 testSmallestPositive(0.0, 0.0, 1.0, 0.0);
	}
	@Test
	public void testSmallestPositive_0_1_1() {
	 testSmallestPositive(0.0, 0.0, 1.0, 1.0);
	}
	@Test
	public void testSmallestPositive_0_1_3() {
	 testSmallestPositive(0.0, 0.0, 1.0, 3.0);
	}
	@Test
	public void testSmallestPositive_0_3_neg3() {
	 testSmallestPositive(0.0, 0.0, 3.0, -3.0);
	}
	@Test
	public void testSmallestPositive_0_3_neg1() {
	 testSmallestPositive(0.0, 0.0, 3.0, -1.0);
	}
	@Test
	public void testSmallestPositive_0_3_0() {
	 testSmallestPositive(0.0, 0.0, 3.0, 0.0);
	}
	@Test
	public void testSmallestPositive_0_3_1() {
	 testSmallestPositive(0.0, 0.0, 3.0, 1.0);
	}
	@Test
	public void testSmallestPositive_0_3_3() {
	 testSmallestPositive(0.0, 0.0, 3.0, 3.0);
	}
	@Test
	public void testSmallestPositive_1_neg3_neg3() {
	 testSmallestPositive(1.0, 1.0, -3.0, -3.0);
	}
	@Test
	public void testSmallestPositive_1_neg3_neg1() {
	 testSmallestPositive(1.0, 1.0, -3.0, -1.0);
	}
	@Test
	public void testSmallestPositive_1_neg3_0() {
	 testSmallestPositive(0.0, 1.0, -3.0, 0.0);
	}
	@Test
	public void testSmallestPositive_1_neg3_1() {
	 testSmallestPositive(1.0, 1.0, -3.0, 1.0);
	}
	@Test
	public void testSmallestPositive_1_neg3_3() {
	 testSmallestPositive(1.0, 1.0, -3.0, 3.0);
	}
	@Test
	public void testSmallestPositive_1_neg1_neg3() {
	 testSmallestPositive(1.0, 1.0, -1.0, -3.0);
	}
	@Test
	public void testSmallestPositive_1_neg1_neg1() {
	 testSmallestPositive(1.0, 1.0, -1.0, -1.0);
	}
	@Test
	public void testSmallestPositive_1_neg1_0() {
	 testSmallestPositive(0.0, 1.0, -1.0, 0.0);
	}
	@Test
	public void testSmallestPositive_1_neg1_1() {
	 testSmallestPositive(1.0, 1.0, -1.0, 1.0);
	}
	@Test
	public void testSmallestPositive_1_neg1_3() {
	 testSmallestPositive(1.0, 1.0, -1.0, 3.0);
	}
	@Test
	public void testSmallestPositive_1_0_neg3() {
	 testSmallestPositive(0.0, 1.0, 0.0, -3.0);
	}
	@Test
	public void testSmallestPositive_1_0_neg1() {
	 testSmallestPositive(0.0, 1.0, 0.0, -1.0);
	}
	@Test
	public void testSmallestPositive_1_0_0() {
	 testSmallestPositive(0.0, 1.0, 0.0, 0.0);
	}
	@Test
	public void testSmallestPositive_1_0_1() {
	 testSmallestPositive(0.0, 1.0, 0.0, 1.0);
	}
	@Test
	public void testSmallestPositive_1_0_3() {
	 testSmallestPositive(0.0, 1.0, 0.0, 3.0);
	}
	@Test
	public void testSmallestPositive_1_1_neg3() {
	 testSmallestPositive(1.0, 1.0, 1.0, -3.0);
	}
	@Test
	public void testSmallestPositive_1_1_neg1() {
	 testSmallestPositive(1.0, 1.0, 1.0, -1.0);
	}
	@Test
	public void testSmallestPositive_1_1_0() {
	 testSmallestPositive(0.0, 1.0, 1.0, 0.0);
	}
	@Test
	public void testSmallestPositive_1_1_1() {
	 testSmallestPositive(1.0, 1.0, 1.0, 1.0);
	}
	@Test
	public void testSmallestPositive_1_1_3() {
	 testSmallestPositive(1.0, 1.0, 1.0, 3.0);
	}
	@Test
	public void testSmallestPositive_1_3_neg3() {
	 testSmallestPositive(1.0, 1.0, 3.0, -3.0);
	}
	@Test
	public void testSmallestPositive_1_3_neg1() {
	 testSmallestPositive(1.0, 1.0, 3.0, -1.0);
	}
	@Test
	public void testSmallestPositive_1_3_0() {
	 testSmallestPositive(0.0, 1.0, 3.0, 0.0);
	}
	@Test
	public void testSmallestPositive_1_3_1() {
	 testSmallestPositive(1.0, 1.0, 3.0, 1.0);
	}
	@Test
	public void testSmallestPositive_1_3_3() {
	 testSmallestPositive(1.0, 1.0, 3.0, 3.0);
	}
	@Test
	public void testSmallestPositive_3_neg3_neg3() {
	 testSmallestPositive(3.0, 3.0, -3.0, -3.0);
	}
	@Test
	public void testSmallestPositive_3_neg3_neg1() {
	 testSmallestPositive(3.0, 3.0, -3.0, -1.0);
	}
	@Test
	public void testSmallestPositive_3_neg3_0() {
	 testSmallestPositive(0.0, 3.0, -3.0, 0.0);
	}
	@Test
	public void testSmallestPositive_3_neg3_1() {
	 testSmallestPositive(1.0, 3.0, -3.0, 1.0);
	}
	@Test
	public void testSmallestPositive_3_neg3_3() {
	 testSmallestPositive(3.0, 3.0, -3.0, 3.0);
	}
	@Test
	public void testSmallestPositive_3_neg1_neg3() {
	 testSmallestPositive(3.0, 3.0, -1.0, -3.0);
	}
	@Test
	public void testSmallestPositive_3_neg1_neg1() {
	 testSmallestPositive(3.0, 3.0, -1.0, -1.0);
	}
	@Test
	public void testSmallestPositive_3_neg1_0() {
	 testSmallestPositive(0.0, 3.0, -1.0, 0.0);
	}
	@Test
	public void testSmallestPositive_3_neg1_1() {
	 testSmallestPositive(1.0, 3.0, -1.0, 1.0);
	}
	@Test
	public void testSmallestPositive_3_neg1_3() {
	 testSmallestPositive(3.0, 3.0, -1.0, 3.0);
	}
	@Test
	public void testSmallestPositive_3_0_neg3() {
	 testSmallestPositive(0.0, 3.0, 0.0, -3.0);
	}
	@Test
	public void testSmallestPositive_3_0_neg1() {
	 testSmallestPositive(0.0, 3.0, 0.0, -1.0);
	}
	@Test
	public void testSmallestPositive_3_0_0() {
	 testSmallestPositive(0.0, 3.0, 0.0, 0.0);
	}
	@Test
	public void testSmallestPositive_3_0_1() {
	 testSmallestPositive(0.0, 3.0, 0.0, 1.0);
	}
	@Test
	public void testSmallestPositive_3_0_3() {
	 testSmallestPositive(0.0, 3.0, 0.0, 3.0);
	}
	@Test
	public void testSmallestPositive_3_1_neg3() {
	 testSmallestPositive(1.0, 3.0, 1.0, -3.0);
	}
	@Test
	public void testSmallestPositive_3_1_neg1() {
	 testSmallestPositive(1.0, 3.0, 1.0, -1.0);
	}
	@Test
	public void testSmallestPositive_3_1_0() {
	 testSmallestPositive(0.0, 3.0, 1.0, 0.0);
	}
	@Test
	public void testSmallestPositive_3_1_1() {
	 testSmallestPositive(1.0, 3.0, 1.0, 1.0);
	}
	@Test
	public void testSmallestPositive_3_1_3() {
	 testSmallestPositive(1.0, 3.0, 1.0, 3.0);
	}
	@Test
	public void testSmallestPositive_3_3_neg3() {
	 testSmallestPositive(3.0, 3.0, 3.0, -3.0);
	}
	@Test
	public void testSmallestPositive_3_3_neg1() {
	 testSmallestPositive(3.0, 3.0, 3.0, -1.0);
	}
	@Test
	public void testSmallestPositive_3_3_0() {
	 testSmallestPositive(0.0, 3.0, 3.0, 0.0);
	}
	@Test
	public void testSmallestPositive_3_3_1() {
	 testSmallestPositive(1.0, 3.0, 3.0, 1.0);
	}
	@Test
	public void testSmallestPositive_3_3_3() {
	 testSmallestPositive(3.0, 3.0, 3.0, 3.0);
	}


	
	@Test 
	public void testLargestNegative_neg3_neg3_neg3() {
	 testLargestNegative(-3.0, -3.0, -3.0, -3.0);
	}
	@Test 
	public void testLargestNegative_neg3_neg3_neg1() {
	 testLargestNegative(-1.0, -3.0, -3.0, -1.0);
	}
	@Test
	public void testLargestNegative_neg3_neg3_0() {
	 testLargestNegative(0.0, -3.0, -3.0, 0.0);
	}
	@Test
	public void testLargestNegative_neg3_neg3_1() {
	 testLargestNegative(-3.0, -3.0, -3.0, 1.0);
	}
	@Test
	public void testLargestNegative_neg3_neg3_3() {
	 testLargestNegative(-3.0, -3.0, -3.0, 3.0);
	}
	@Test 
	public void testLargestNegative_neg3_neg1_neg3() {
	 testLargestNegative(-1.0, -3.0, -1.0, -3.0);
	}
	@Test 
	public void testLargestNegative_neg3_neg1_neg1() {
	 testLargestNegative(-1.0, -3.0, -1.0, -1.0);
	}
	@Test
	public void testLargestNegative_neg3_neg1_0() {
	 testLargestNegative(0.0, -3.0, -1.0, 0.0);
	}
	@Test
	public void testLargestNegative_neg3_neg1_1() {
	 testLargestNegative(-1.0, -3.0, -1.0, 1.0);
	}
	@Test
	public void testLargestNegative_neg3_neg1_3() {
	 testLargestNegative(-1.0, -3.0, -1.0, 3.0);
	}
	@Test
	public void testLargestNegative_neg3_0_neg3() {
	 testLargestNegative(0.0, -3.0, 0.0, -3.0);
	}
	@Test
	public void testLargestNegative_neg3_0_neg1() {
	 testLargestNegative(0.0, -3.0, 0.0, -1.0);
	}
	@Test
	public void testLargestNegative_neg3_0_0() {
	 testLargestNegative(0.0, -3.0, 0.0, 0.0);
	}
	@Test
	public void testLargestNegative_neg3_0_1() {
	 testLargestNegative(0.0, -3.0, 0.0, 1.0);
	}
	@Test
	public void testLargestNegative_neg3_0_3() {
	 testLargestNegative(0.0, -3.0, 0.0, 3.0);
	}
	@Test
	public void testLargestNegative_neg3_1_neg3() {
	 testLargestNegative(-3.0, -3.0, 1.0, -3.0);
	}
	@Test
	public void testLargestNegative_neg3_1_neg1() {
	 testLargestNegative(-1.0, -3.0, 1.0, -1.0);
	}
	@Test
	public void testLargestNegative_neg3_1_0() {
	 testLargestNegative(0.0, -3.0, 1.0, 0.0);
	}
	@Test
	public void testLargestNegative_neg3_1_1() {
	 testLargestNegative(-3.0, -3.0, 1.0, 1.0);
	}
	@Test
	public void testLargestNegative_neg3_1_3() {
	 testLargestNegative(-3.0, -3.0, 1.0, 3.0);
	}
	@Test
	public void testLargestNegative_neg3_3_neg3() {
	 testLargestNegative(-3.0, -3.0, 3.0, -3.0);
	}
	@Test
	public void testLargestNegative_neg3_3_neg1() {
	 testLargestNegative(-1.0, -3.0, 3.0, -1.0);
	}
	@Test
	public void testLargestNegative_neg3_3_0() {
	 testLargestNegative(0.0, -3.0, 3.0, 0.0);
	}
	@Test
	public void testLargestNegative_neg3_3_1() {
	 testLargestNegative(-3.0, -3.0, 3.0, 1.0);
	}
	@Test
	public void testLargestNegative_neg3_3_3() {
	 testLargestNegative(-3.0, -3.0, 3.0, 3.0);
	}
	@Test 
	public void testLargestNegative_neg1_neg3_neg3() {
	 testLargestNegative(-1.0, -1.0, -3.0, -3.0);
	}
	@Test 
	public void testLargestNegative_neg1_neg3_neg1() {
	 testLargestNegative(-1.0, -1.0, -3.0, -1.0);
	}
	@Test
	public void testLargestNegative_neg1_neg3_0() {
	 testLargestNegative(0.0, -1.0, -3.0, 0.0);
	}
	@Test
	public void testLargestNegative_neg1_neg3_1() {
	 testLargestNegative(-1.0, -1.0, -3.0, 1.0);
	}
	@Test
	public void testLargestNegative_neg1_neg3_3() {
	 testLargestNegative(-1.0, -1.0, -3.0, 3.0);
	}
	@Test 
	public void testLargestNegative_neg1_neg1_neg3() {
	 testLargestNegative(-1.0, -1.0, -1.0, -3.0);
	}
	@Test 
	public void testLargestNegative_neg1_neg1_neg1() {
	 testLargestNegative(-1.0, -1.0, -1.0, -1.0);
	}
	@Test 
	public void testLargestNegative_neg1_neg1_0() {
	 testLargestNegative(0.0, -1.0, -1.0, 0.0);
	}
	@Test
	public void testLargestNegative_neg1_neg1_1() {
	 testLargestNegative(-1.0, -1.0, -1.0, 1.0);
	}
	@Test
	public void testLargestNegative_neg1_neg1_3() {
	 testLargestNegative(-1.0, -1.0, -1.0, 3.0);
	}
	@Test
	public void testLargestNegative_neg1_0_neg3() {
	 testLargestNegative(0.0, -1.0, 0.0, -3.0);
	}
	@Test
	public void testLargestNegative_neg1_0_neg1() {
	 testLargestNegative(0.0, -1.0, 0.0, -1.0);
	}
	@Test
	public void testLargestNegative_neg1_0_0() {
	 testLargestNegative(0.0, -1.0, 0.0, 0.0);
	}
	@Test
	public void testLargestNegative_neg1_0_1() {
	 testLargestNegative(0.0, -1.0, 0.0, 1.0);
	}
	@Test
	public void testLargestNegative_neg1_0_3() {
	 testLargestNegative(0.0, -1.0, 0.0, 3.0);
	}
	@Test
	public void testLargestNegative_neg1_1_neg3() {
	 testLargestNegative(-1.0, -1.0, 1.0, -3.0);
	}
	@Test
	public void testLargestNegative_neg1_1_neg1() {
	 testLargestNegative(-1.0, -1.0, 1.0, -1.0);
	}
	@Test
	public void testLargestNegative_neg1_1_0() {
	 testLargestNegative(0.0, -1.0, 1.0, 0.0);
	}
	@Test
	public void testLargestNegative_neg1_1_1() {
	 testLargestNegative(-1.0, -1.0, 1.0, 1.0);
	}
	@Test
	public void testLargestNegative_neg1_1_3() {
	 testLargestNegative(-1.0, -1.0, 1.0, 3.0);
	}
	@Test
	public void testLargestNegative_neg1_3_neg3() {
	 testLargestNegative(-1.0, -1.0, 3.0, -3.0);
	}
	@Test
	public void testLargestNegative_neg1_3_neg1() {
	 testLargestNegative(-1.0, -1.0, 3.0, -1.0);
	}
	@Test
	public void testLargestNegative_neg1_3_0() {
	 testLargestNegative(0.0, -1.0, 3.0, 0.0);
	}
	@Test
	public void testLargestNegative_neg1_3_1() {
	 testLargestNegative(-1.0, -1.0, 3.0, 1.0);
	}
	@Test
	public void testLargestNegative_neg1_3_3() {
	 testLargestNegative(-1.0, -1.0, 3.0, 3.0);
	}
	@Test
	public void testLargestNegative_0_neg3_neg3() {
	 testLargestNegative(0.0, 0.0, -3.0, -3.0);
	}
	@Test
	public void testLargestNegative_0_neg3_neg1() {
	 testLargestNegative(0.0, 0.0, -3.0, -1.0);
	}
	@Test
	public void testLargestNegative_0_neg3_0() {
	 testLargestNegative(0.0, 0.0, -3.0, 0.0);
	}
	@Test
	public void testLargestNegative_0_neg3_1() {
	 testLargestNegative(0.0, 0.0, -3.0, 1.0);
	}
	@Test
	public void testLargestNegative_0_neg3_3() {
	 testLargestNegative(0.0, 0.0, -3.0, 3.0);
	}
	@Test
	public void testLargestNegative_0_neg1_neg3() {
	 testLargestNegative(0.0, 0.0, -1.0, -3.0);
	}
	@Test
	public void testLargestNegative_0_neg1_neg1() {
	 testLargestNegative(0.0, 0.0, -1.0, -1.0);
	}
	@Test
	public void testLargestNegative_0_neg1_0() {
	 testLargestNegative(0.0, 0.0, -1.0, 0.0);
	}
	@Test
	public void testLargestNegative_0_neg1_1() {
	 testLargestNegative(0.0, 0.0, -1.0, 1.0);
	}
	@Test
	public void testLargestNegative_0_neg1_3() {
	 testLargestNegative(0.0, 0.0, -1.0, 3.0);
	}
	@Test
	public void testLargestNegative_0_0_neg3() {
	 testLargestNegative(0.0, 0.0, 0.0, -3.0);
	}
	@Test
	public void testLargestNegative_0_0_neg1() {
	 testLargestNegative(0.0, 0.0, 0.0, -1.0);
	}
	@Test
	public void testLargestNegative_0_0_0() {
	 testLargestNegative(0.0, 0.0, 0.0, 0.0);
	}
	@Test
	public void testLargestNegative_0_0_1() {
	 testLargestNegative(0.0, 0.0, 0.0, 1.0);
	}
	@Test
	public void testLargestNegative_0_0_3() {
	 testLargestNegative(0.0, 0.0, 0.0, 3.0);
	}
	@Test
	public void testLargestNegative_0_1_neg3() {
	 testLargestNegative(0.0, 0.0, 1.0, -3.0);
	}
	@Test
	public void testLargestNegative_0_1_neg1() {
	 testLargestNegative(0.0, 0.0, 1.0, -1.0);
	}
	@Test
	public void testLargestNegative_0_1_0() {
	 testLargestNegative(0.0, 0.0, 1.0, 0.0);
	}
	@Test
	public void testLargestNegative_0_1_1() {
	 testLargestNegative(0.0, 0.0, 1.0, 1.0);
	}
	@Test
	public void testLargestNegative_0_1_3() {
	 testLargestNegative(0.0, 0.0, 1.0, 3.0);
	}
	@Test
	public void testLargestNegative_0_3_neg3() {
	 testLargestNegative(0.0, 0.0, 3.0, -3.0);
	}
	@Test
	public void testLargestNegative_0_3_neg1() {
	 testLargestNegative(0.0, 0.0, 3.0, -1.0);
	}
	@Test
	public void testLargestNegative_0_3_0() {
	 testLargestNegative(0.0, 0.0, 3.0, 0.0);
	}
	@Test
	public void testLargestNegative_0_3_1() {
	 testLargestNegative(0.0, 0.0, 3.0, 1.0);
	}
	@Test
	public void testLargestNegative_0_3_3() {
	 testLargestNegative(0.0, 0.0, 3.0, 3.0);
	}
	@Test
	public void testLargestNegative_1_neg3_neg3() {
	 testLargestNegative(-3.0, 1.0, -3.0, -3.0);
	}
	@Test
	public void testLargestNegative_1_neg3_neg1() {
	 testLargestNegative(-1.0, 1.0, -3.0, -1.0);
	}
	@Test
	public void testLargestNegative_1_neg3_0() {
	 testLargestNegative(0.0, 1.0, -3.0, 0.0);
	}
	@Test
	public void testLargestNegative_1_neg3_1() {
	 testLargestNegative(-3.0, 1.0, -3.0, 1.0);
	}
	@Test
	public void testLargestNegative_1_neg3_3() {
	 testLargestNegative(-3.0, 1.0, -3.0, 3.0);
	}
	@Test
	public void testLargestNegative_1_neg1_neg3() {
	 testLargestNegative(-1.0, 1.0, -1.0, -3.0);
	}
	@Test
	public void testLargestNegative_1_neg1_neg1() {
	 testLargestNegative(-1.0, 1.0, -1.0, -1.0);
	}
	@Test
	public void testLargestNegative_1_neg1_0() {
	 testLargestNegative(0.0, 1.0, -1.0, 0.0);
	}
	@Test
	public void testLargestNegative_1_neg1_1() {
	 testLargestNegative(-1.0, 1.0, -1.0, 1.0);
	}
	@Test
	public void testLargestNegative_1_neg1_3() {
	 testLargestNegative(-1.0, 1.0, -1.0, 3.0);
	}
	@Test
	public void testLargestNegative_1_0_neg3() {
	 testLargestNegative(0.0, 1.0, 0.0, -3.0);
	}
	@Test
	public void testLargestNegative_1_0_neg1() {
	 testLargestNegative(0.0, 1.0, 0.0, -1.0);
	}
	@Test
	public void testLargestNegative_1_0_0() {
	 testLargestNegative(0.0, 1.0, 0.0, 0.0);
	}
	@Test
	public void testLargestNegative_1_0_1() {
	 testLargestNegative(0.0, 1.0, 0.0, 1.0);
	}
	@Test
	public void testLargestNegative_1_0_3() {
	 testLargestNegative(0.0, 1.0, 0.0, 3.0);
	}
	@Test
	public void testLargestNegative_1_1_neg3() {
	 testLargestNegative(-3.0, 1.0, 1.0, -3.0);
	}
	@Test
	public void testLargestNegative_1_1_neg1() {
	 testLargestNegative(-1.0, 1.0, 1.0, -1.0);
	}
	@Test
	public void testLargestNegative_1_1_0() {
	 testLargestNegative(0.0, 1.0, 1.0, 0.0);
	}
	@Test (expected=IllegalArgumentException.class)
	public void testLargestNegative_1_1_1() {
	 testLargestNegative(null, 1.0, 1.0, 1.0);
	}
	@Test (expected=IllegalArgumentException.class)
	public void testLargestNegative_1_1_3() {
	 testLargestNegative(null, 1.0, 1.0, 3.0);
	}
	@Test
	public void testLargestNegative_1_3_neg3() {
	 testLargestNegative(-3.0, 1.0, 3.0, -3.0);
	}
	@Test
	public void testLargestNegative_1_3_neg1() {
	 testLargestNegative(-1.0, 1.0, 3.0, -1.0);
	}
	@Test
	public void testLargestNegative_1_3_0() {
	 testLargestNegative(0.0, 1.0, 3.0, 0.0);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testLargestNegative_1_3_1() {
	 testLargestNegative(null, 1.0, 3.0, 1.0);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testLargestNegative_1_3_3() {
	 testLargestNegative(null, 1.0, 3.0, 3.0);
	}
	@Test
	public void testLargestNegative_3_neg3_neg3() {
	 testLargestNegative(-3.0, 3.0, -3.0, -3.0);
	}
	@Test
	public void testLargestNegative_3_neg3_neg1() {
	 testLargestNegative(-1.0, 3.0, -3.0, -1.0);
	}
	@Test
	public void testLargestNegative_3_neg3_0() {
	 testLargestNegative(0.0, 3.0, -3.0, 0.0);
	}
	@Test
	public void testLargestNegative_3_neg3_1() {
	 testLargestNegative(-3.0, 3.0, -3.0, 1.0);
	}
	@Test
	public void testLargestNegative_3_neg3_3() {
	 testLargestNegative(-3.0, 3.0, -3.0, 3.0);
	}
	@Test
	public void testLargestNegative_3_neg1_neg3() {
	 testLargestNegative(-1.0, 3.0, -1.0, -3.0);
	}
	@Test
	public void testLargestNegative_3_neg1_neg1() {
	 testLargestNegative(-1.0, 3.0, -1.0, -1.0);
	}
	@Test
	public void testLargestNegative_3_neg1_0() {
	 testLargestNegative(0.0, 3.0, -1.0, 0.0);
	}
	@Test
	public void testLargestNegative_3_neg1_1() {
	 testLargestNegative(-1.0, 3.0, -1.0, 1.0);
	}
	@Test
	public void testLargestNegative_3_neg1_3() {
	 testLargestNegative(-1.0, 3.0, -1.0, 3.0);
	}
	@Test
	public void testLargestNegative_3_0_neg3() {
	 testLargestNegative(0.0, 3.0, 0.0, -3.0);
	}
	@Test
	public void testLargestNegative_3_0_neg1() {
	 testLargestNegative(0.0, 3.0, 0.0, -1.0);
	}
	@Test
	public void testLargestNegative_3_0_0() {
	 testLargestNegative(0.0, 3.0, 0.0, 0.0);
	}
	@Test
	public void testLargestNegative_3_0_1() {
	 testLargestNegative(0.0, 3.0, 0.0, 1.0);
	}
	@Test
	public void testLargestNegative_3_0_3() {
	 testLargestNegative(0.0, 3.0, 0.0, 3.0);
	}
	@Test
	public void testLargestNegative_3_1_neg3() {
	 testLargestNegative(-3.0, 3.0, 1.0, -3.0);
	}
	@Test
	public void testLargestNegative_3_1_neg1() {
	 testLargestNegative(-1.0, 3.0, 1.0, -1.0);
	}
	@Test
	public void testLargestNegative_3_1_0() {
	 testLargestNegative(0.0, 3.0, 1.0, 0.0);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testLargestNegative_3_1_1() {
	 testLargestNegative(null, 3.0, 1.0, 1.0);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testLargestNegative_3_1_3() {
	 testLargestNegative(null, 3.0, 1.0, 3.0);
	}
	@Test
	public void testLargestNegative_3_3_neg3() {
	 testLargestNegative(-3.0, 3.0, 3.0, -3.0);
	}
	@Test
	public void testLargestNegative_3_3_neg1() {
	 testLargestNegative(-1.0, 3.0, 3.0, -1.0);
	}
	@Test
	public void testLargestNegative_3_3_0() {
	 testLargestNegative(0.0, 3.0, 3.0, 0.0);
	}
	@Test (expected=IllegalArgumentException.class)
	public void testLargestNegative_3_3_1() {
	 testLargestNegative(null, 3.0, 3.0, 1.0);
	}
	@Test (expected=IllegalArgumentException.class)
	public void testLargestNegative_3_3_3() {
	 testLargestNegative(null, 3.0, 3.0, 3.0);
	}

	
	
	// NEED TESTING:
	// crossesAntiMeridian
	// BoundingBox getBoundingBox(LatLng start, double radius, LengthUnit lengthUnit)



	
	
}
