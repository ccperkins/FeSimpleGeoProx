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
 * Holds a pair of Constraint items.
 * 
 * @author Cornelius Perkins (ccperkins at bitbucket and github)
 *
 * @param <E>
 * @param <V>
 */
public class ConstraintPair<E, V extends Comparable<V>>  {
	private final Constraint<E, V> constraint1;
	private final Constraint<E, V> constraint2;
	
	public ConstraintPair(Extractor<E, V> extractor, Bounds<V> bounds1, Bounds<V> bounds2) {
		this.constraint1 = new Constraint<E,V> (extractor, bounds1.lowValue, bounds1.highValue);
		this.constraint2 = new Constraint<E,V> (extractor, bounds2.lowValue, bounds2.highValue);
	}

	public boolean passes(E e) {
		return (   (constraint1.passes(e))
				&& (constraint2.passes(e))	);
	}
}
