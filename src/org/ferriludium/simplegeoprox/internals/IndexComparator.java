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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Class which allows two entries to be compared along the axis of the extracted
 * value. A comparator is defined by an Extractor and a SortOrder (which defaults
 * to ascending).
 * 
 * @author Cornelius Perkins (ccperkins at bitbucket and github)
 *
 * @param <E> Type of the entries
 * @param <V> Type of the value which is actually compared.
 */
public class IndexComparator <E,V extends Comparable<V>> implements Comparator<E> {
	public enum SortOrder { ASCENDING, DESCENDING };
	private final List<Extractor<E,V>> extractors;
	private final SortOrder sortOrder;	
	
	public IndexComparator(Extractor<E, V> extractor) {
		this (new ArrayList<Extractor<E,V>> ());
		extractors.add(extractor);
	}
	public IndexComparator(List<Extractor<E, V>> extractors) {
		this (extractors, SortOrder.ASCENDING);
	}
	public IndexComparator(Extractor<E, V> extractor, SortOrder sortOrder) {
		this (new ArrayList<Extractor<E,V>> (), sortOrder);
		extractors.add(extractor);
	}
	public IndexComparator(List<Extractor<E, V>> extractors, SortOrder sortOrder) {
		super();
		this.extractors = extractors;
		this.sortOrder = sortOrder;		
	}

	@Override
	public int compare(E o1, E o2) {
		for (Extractor<E,V> extractor: extractors) {
			int res = extractor.extract(o1).compareTo(extractor.extract(o2));
			if (SortOrder.ASCENDING.equals(sortOrder))
				return res;
			else
				return -1 * res;
		}
		return 0;
	}
	
	public int compare(E row, V val) {
		for (Extractor<E,V> extractor: extractors) {
			int res = extractor.extract(row).compareTo(val);
			if (SortOrder.ASCENDING.equals(sortOrder))
				return res;
			else
				return -1 * res;
		}
		return 0;
	}
	public int compare(V val, E row) {
		for (Extractor<E,V> extractor: extractors) {
			V extractedVal = extractor.extract(row);
			int res = extractedVal.compareTo(val);
			//int res = extractor.extract(row).compareTo(val);
			if (SortOrder.ASCENDING.equals(sortOrder))
				return -1 * res;
			else
				return res;
		}
		return 0;
	}
	
	public SortOrder getSortOrder() {
		return sortOrder;
	}
	
}
