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
 * Class which holds a single geographical object at a map location.
 * 
 * Effectively final, though NOTE that there are no restrictions on the type of objects supplied by 
 * caller, and no attempt is made to clone() them (see various works on why clone() is not to be
 * relied on in random code).
 * 
 * @author Cornelius Perkins (ccperkins at bitbucket and github)
 *
 * @param <T>
 */
public final class MapObjectHolder<T> {
	/** Location of the object. */
	final public LatLng loc; 
	
	/** Client-supplied contents of the geo object */
	final public T clientObject; 
	
	/** Simple constructor, giving the key, the location, and the user-supplied object */
	public MapObjectHolder(LatLng loc, T clientObject) {
		this.loc = loc;
		this.clientObject = clientObject;
	}
	/**
	 * Returns a verbose listing of the GeoObject (key, location, and contents) - NOTE 
	 * that this means the user-supplied contents should support a useful toString method.
	 * @return String representation of the GeoObject
	 */
	@Override
	public String toString() {
		return clientObject.toString() + " @" + loc.toString();
	}
}
