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
 * Holds a single constraint (e.g., that a particular value in a given entry must be between two bounding values).
 * NOTE: the reason the constructor takes an Index is that we need the comparator and extractor to be consistent.
 * 
 * @author Cornelius Perkins (ccperkins at bitbucket and github)
 *
 * @param <E>
 * @param <V>
 */
public class Constraint<E, V extends Comparable<V>> {
	/** Extracts a value from an entry */
	private final Extractor<E, V> extractor;
	
	/** The lower bound of the constraint */
	private final V minValue;
	
	/** The upper bound of the constraint */
	private final V maxValue;
	
	/** Universal comparator which allows two entries to be compared. */
	private final IndexComparator<E,V> comparator ;
		
	public Constraint(Extractor<E, V> extractor, Bounds<V> bounds) {
		this(extractor, new IndexComparator<E,V>(extractor), bounds);
	}
	public Constraint(Extractor<E, V> extractor, V minValue, V maxValue) {
		this(extractor, new IndexComparator<E,V>(extractor), new Bounds<V>(minValue, maxValue));
	}
	public Constraint(Extractor<E, V> extractor, IndexComparator<E,V> comparator, Bounds<V> bounds) {
		super();
		this.extractor = extractor;
		this.comparator = comparator;
		this.minValue = bounds.lowValue;
		this.maxValue = bounds.highValue;
	}

	public boolean passes(E e) {
		return (   (comparator.compare(e, minValue) >= 0)
		        && (comparator.compare(e, maxValue) <= 0));
	}
	public V getMinValue() {
		return minValue;
	}
	public V getMaxValue() {
		return maxValue;
	}
	@Override
	public String toString() {
		return "( " + extractor + " between min=" + minValue + ", max=" + maxValue + ")";
	}
	
}
