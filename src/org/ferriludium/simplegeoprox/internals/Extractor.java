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

package org.ferriludium.simplegeoprox.internals;

/** 
 * Extracts a comparable value from an entry so that entries can be compared and sorted.
 * Note that the returned value need not be a simple value class (e.g., Double).  It 
 * can be any class which correctly implements Comparable and which can be constructed
 * from an entry.
 *
 * @param <E> Type of entries.
 * @param <V> Type of extracted value.
 */
public abstract class Extractor<E,V extends Comparable<V>> {
	/**
	 * Extracts a caller-chosen value from an object.
	 * @param obj
	 * @return
	 */
	public abstract V extract(E obj);
	
	/**
	 * Caller-supplied descriptive tag for this particular extractor.
	 */
	final private String desc;
	
	/**
	 * Constructor, supplying descriptor.
	 * @param desc
	 */
	public Extractor(String desc) {
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		return desc;
	}
}	