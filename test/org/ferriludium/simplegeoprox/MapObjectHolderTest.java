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

import org.ferriludium.simplegeoprox.FeSimpleGeoProxTest.TestClientMapObject;
import org.junit.Test;

import com.javadocmd.simplelatlng.LatLng;

public class MapObjectHolderTest {

	@Test
	public void test() {
		LatLng loc = new LatLng(41.154873, -73.4377622);
		TestClientMapObject obj = new TestClientMapObject("Test1", loc);
		assertEquals("TestClientMapObject [desc=Test1]", obj.toString());
	}
	@Test
	public void testCreateAndPrint() {
		LatLng loc = new LatLng(41.154873, -73.4377622);
		MapObjectHolder<TestClientMapObject> obj = makeObj ("Test2", loc);
		assertEquals("TestClientMapObject [desc=Test2] @(41.154873,-73.437762)", obj.toString());
	}
	
	MapObjectHolder<TestClientMapObject> makeObj(String desc, LatLng loc) {
		return new MapObjectHolder<TestClientMapObject> (loc, new TestClientMapObject(desc, loc));
	}
	

}
