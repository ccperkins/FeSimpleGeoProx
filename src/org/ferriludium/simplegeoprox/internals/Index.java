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
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.ferriludium.simplegeoprox.internals.IndexComparator.SortOrder;

/**
 * A sorted index allowing a collection to be searched and sorted on a given dimension.  
 * The collection is shallowly copied at time of construction, but caller is responsible
 * to see that there are no later changes to values in the collection later.  
 * 
 * @See the javadoc on the find method for details on how this all works.  It's
 * more complicated than you might guess because of the various use cases:
 * 1) Find a single exact match
 * 2) Find an exact match but start at the lower end of a range of exact matches.
 * 3) Find an exact match but start at the upper end of a range of exact matches.
 * 4) Find the nearest to an exact match start at the lower end of a range of exact matches.
 * 5) Find the nearest to an exact match start at the upper end of a range of exact matches.
 *
 * @param <E> Type of the entries in the collection.
 * @param <V> Type of the value extracted by the extractor (this is what we sort and search on).
 */
public class Index<E,V extends Comparable<V>> implements Iterable<E> {
	
	/** Descriptive, unique identifier for this Index.  */
	private final String id;
	
	/** Universal comparator which allows two entries to be compared. */
	private final IndexComparator<E,V> comparator ;
	
	/** Extracts a value from an entry */
	private final Extractor<E,V> extractor;
	
	/** Ordered, searchable collection of elements */
	private final List<E> contents = new ArrayList<>();
	
	/**
	 * Returns the sort order (ascending, descending).
	 * @return
	 */
	public SortOrder getSortOrder() {
		return comparator.getSortOrder();
	}

	/**
	 * Returns the simple caller-supplied descriptive id for this index. 
	 * @return
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Returns the number of elements in the ordered collection.
	 * @return
	 */
	public int getContentsSize() {
		return contents.size();
	}
	
	/**
	 * Returns the comparator by which this index is sorted.
	 * @return
	 */
	public IndexComparator<E, V> getComparator() {
		return comparator;
	}

	/**
	 * Returns the extractor which pulls the values on which this index is sorted.
	 * @return
	 */
	public Extractor<E, V> getExtractor() {
		return extractor;
	}

	/**
	 * Returns a single element at the given position in the sorted 
	 * internal copy of the contents.
	 * TODO: reconsider whether this should be hidden from view
	 * @param position
	 * @return
	 */
	public E get(int position) {
		return contents.get(position);
	}

	/**
	 * Constructor.
	 * @param id
	 * @param extractor
	 * @param sortOrder
	 * @param contents
	 */
	public Index(String id, Extractor<E, V> extractor, SortOrder sortOrder, Collection<E> contents) {
		this.id = id;
		this.comparator = new IndexComparator<>(extractor, sortOrder);
		this.extractor = extractor;
		if (contents != null) {
			this.contents.addAll(contents);
			Collections.sort(this.contents, comparator);
		}
	}
	

	/** Sets the rules to control what index is returned when an exact match is found (see the section for find()) */
	public enum InclusionMode {
		EXCLUSIVE_DOWN,
		INCLUSIVE_DOWN,
		INCLUSIVE_UP,
		EXCLUSIVE_UP
		};
	/** Sets the rules to control whether an exact match is required or whether inexact searches are acceptable */
	public enum MatchRequirement {
		EXACT_ONLY,
		INEXACT_OK
	}
	
	/**
	 * Returns the index of the entry whose extracted value matches the given target, or -1 if no matching 
	 * entry is found.  NOTE that if multiple matching entries exist, the returned value is specified by 
	 * InclusionMode instead of being undefined.
	 * 
	 * TODO: reconsider whether this should be hidden from view
	 * 
	 * @param target the value sought
	 * @param mode   controls which index to return if multiple matching entries are found
	 * @return the index, or -1 if none are found
	 */
    public int find(V target, InclusionMode mode) {
    	return find(target, mode, MatchRequirement.EXACT_ONLY);
    }
    
	/**
	 * Finds the array index of the entry in our indexing array whose value is nearest to the target value.
	 * Note that this is not the same semantics as the "classic" find, where if an exact match is not 
	 * found, -1 is returned, and which also has undefined behavior if more than one element with a matching
	 * value is found.
	 * 
	 * Regardless of InclusionRules or MatchRequirement, searching for a value less than the minimum value 
	 * found in the contents returns 0, and searching for a value greater than the maximum returns the size
	 * of the array.
	 * 
	 * Searching for a value contained in the scope of the contents yields results which depends on the 
	 * supplied setting of InclusionRules and MatchRequirement.
	 * 
	 * This is slightly complicated, so to minimize words, I'm going to supply a couple of examples which
	 * should explain the behavior.
	 * 
	 * Regardless of MatchRequirement, searching for 5 in:
	 *    1  2  5  5  5  8  9 
	 *       A  B     C  D    
	 * If InclusionMode is:
	 *     EXCLUSIVE_DOWN returns A 
	 *     INCLUSIVE_DOWN returns B
	 *     INCLUSIVE_UP   returns C
	 *     EXCLUSIVE_UP   returns D
     * 
	 * But searching for 3:
	 *    1  2  5  5  5  8  9 
	 *       A  B     
	 * If MatchRequirement is EXACT_ONLY, searching for 3 returns -1. (like classic find()),
	 * but if it is INEXACT_OK, then it follows the InclusionMode:
	 * If InclusionMode is:
	 *     EXCLUSIVE_DOWN returns A
	 *     INCLUSIVE_DOWN returns A
	 *     INCLUSIVE_UP   returns B
	 *     EXCLUSIVE_UP   returns B
	 */
    public int find(V target, InclusionMode mode, MatchRequirement matchReq) {
    	if (contents.size() == 0)
    		return 0;
        int left = 0;
        int right = contents.size() - 1;
        // Be sure target isn't less than the min value
        if (comparator.compare(target, contents.get(left)) < 0)
        	return 0;
        // Be sure target isn't greater than the max value
        if (comparator.compare(target, contents.get(right)) > 0)
        	return contents.size();
        
        int mid = -999;
        while (left <= right) {
            mid = left + (right - left) / 2;
            int comp = comparator.compare(contents.get(mid), target);
            if (comp == 0) {   // Found it - but up, down, what?
            	int idx=mid;
            	switch (mode) {
            	case EXCLUSIVE_DOWN:
            		if ( (idx <= 0) || (idx >= contents.size()) ) return idx;
            		while ((idx > 0) && (comparator.compare(contents.get(idx), target) == 0) ) {
            			idx--;
            		}
            		break;
            	case INCLUSIVE_DOWN:
            		if ( (idx <= 0) || (idx >= contents.size()) ) return idx;
            		while ((idx >= 0) && (comparator.compare(contents.get(idx), target) == 0) ) {
            			idx--;
            		}
            		idx++;
            	break;
            	case INCLUSIVE_UP:
            		if ( (idx < 0) || (idx >= contents.size()) ) return idx;
            		while ((idx < contents.size()) && (comparator.compare(contents.get(idx), target) == 0) ) {
            			idx++;
            		}
            		idx--;
            		break;
            	case EXCLUSIVE_UP:
            		while ((idx < contents.size()) && (comparator.compare(contents.get(idx), target) == 0) ) {
            			idx++;
            		}
            		break;
            	}
            	return idx;
            	
            } else if (comp > 0) {  // mid is > target
            	right = mid - 1;
            } else {  // mid is < target
            	left = mid + 1;
            }
        }
        if (mid == -1) return -1;
        // No match found.  What should we do?
        if (matchReq.equals(MatchRequirement.EXACT_ONLY))
        	return -1;
        else {    // INEXACT_OK
        	if ( (mode.equals(InclusionMode.EXCLUSIVE_DOWN))
        			|| (mode.equals(InclusionMode.INCLUSIVE_DOWN))) {  // Find the one below
        		return mid;
        	} else { // Find the one above
        		return mid+1;
        	}
        } 
    }

	@Override
	public Iterator<E> iterator() {
		return contents.iterator();
	}		

	/**
	 * Returns a sublist of all the elements between the low target and the high target, inclusive.
	 * @param targetLow
	 * @param targetHigh
	 * @return
	 */
	public List<E> getAllBetween (V targetLow, V targetHigh) {
		int loIndex = find(targetLow, InclusionMode.INCLUSIVE_DOWN, MatchRequirement.INEXACT_OK);
		int hiIndex = find(targetHigh, InclusionMode.INCLUSIVE_UP, MatchRequirement.INEXACT_OK);
		return contents.subList(loIndex, Math.min((hiIndex+1), contents.size()));
	}

	/**
	 * Returns the list of contents which pass each of the given constraints, without
	 * any attempt at optimization by creating a subset.
	 */
	public List<E> getAllPasses_byCheckEach (Constraints<E,V> constraints) {
		if (constraints == null) throw new IllegalArgumentException("Null constraints");
		List<E> ret = new ArrayList<E>();
		if (constraints.constraints.size() == 0) return ret;
		for (E e: contents) {
			if (constraints.passes(e)) {
				ret.add(e);
			}
		}
		return ret;
	}
	/**
	 * Returns the list of contents which pass each of the given constraints, and
	 * attempts to speed the process by reducing the contents to a subset which
	 * pass the first constraint.
	 */
	public List<E> getAllPasses_bySubset (Constraints<E,V> constraints) {
		if (constraints == null) throw new IllegalArgumentException("Null constraints");
		List<E> ret = new ArrayList<E>();
		if (constraints.constraints.size() == 0) return ret;
		Constraint<E,V> firstConstraint = constraints.getConstraints().get(0);
		List<E> subset = new ArrayList<E>();
		for (E e: contents) 
			if (firstConstraint.passes(e)) 
				subset.add(e);
		if (constraints.constraints.size() == 1) return subset;
		// We have created a subset which passes the first constraint. Now
		// apply the other constraints to it.
		Constraints.Builder<E,V> builder = new Constraints.Builder<E,V>();
		for (int ii=1; ii <= constraints.getConstraints().size()-1; ii++) {
			builder.and(constraints.getConstraints().get(ii));
		}
		Constraints<E,V> subConstraints = builder.build();
		for (E e: subset) {
			if (subConstraints.passes(e)) {
				ret.add(e);
			}
		}
		return ret;
	}
	
	/** Returns sublist of contents which are between the low and high targets, and also pass the given constraints. */
	public List<E> getAllBetween (V targetLow, V targetHigh, Constraints<E,V> constraints) {
		List<E> ret = new ArrayList<E>();
		int loIndex = find(targetLow, InclusionMode.INCLUSIVE_DOWN, MatchRequirement.INEXACT_OK);
		int hiIndex = find(targetHigh, InclusionMode.INCLUSIVE_UP, MatchRequirement.INEXACT_OK);
		if (hiIndex == contents.size())
			hiIndex -= 1;
		for (int ii=loIndex; ii <= hiIndex; ii++) {
			E e = contents.get(ii);
			if (constraints.passes(e))
				ret.add(e);
		}
		return ret;
	}

}		
