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

package org.ferriludium.fesimplegeoprox.internals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ferriludium.simplegeoprox.internals.Extractor;
import org.ferriludium.simplegeoprox.internals.IndexComparator;
import org.ferriludium.simplegeoprox.internals.IndexComparator.SortOrder;
import org.junit.Test;

public class IndexComparatorTest {
	@Test public void testSimpleCompare_V_GTR_E() {
		SampleRow row = new SampleRow(null, 5, 9, null);
		Extractor<SampleRow, Integer> val1Extractor = new Val1Extractor();
		IndexComparator<SampleRow, Integer> val1ComparatorAscending = new IndexComparator<>(val1Extractor, SortOrder.ASCENDING);
		int comp = val1ComparatorAscending.compare(99, row);
		assertTrue (comp > 0);
	}
	@Test public void testSimpleCompare_E_GTR_V() {
		SampleRow row = new SampleRow(null, 5, 9, null);
		Extractor<SampleRow, Integer> val1Extractor = new Val1Extractor();
		IndexComparator<SampleRow, Integer> val1ComparatorAscending = new IndexComparator<>(val1Extractor, SortOrder.ASCENDING);
		int comp = val1ComparatorAscending.compare(row, 99);
		assertTrue (comp < 0);
	}
	@Test public void testSimpleCompare_V_GTR_E_2() {
		SampleRow row = new SampleRow(null, 555, 999, null);
		Extractor<SampleRow, Integer> val1Extractor = new Val1Extractor();
		IndexComparator<SampleRow, Integer> val1ComparatorAscending = new IndexComparator<>(val1Extractor, SortOrder.ASCENDING);
		int comp = val1ComparatorAscending.compare(99, row);
		assertTrue (comp < 0);
	}
	@Test public void testSimpleCompare_E_GTR_V_2() {
		SampleRow row = new SampleRow(null, 555, 999, null);
		Extractor<SampleRow, Integer> val1Extractor = new Val1Extractor();
		IndexComparator<SampleRow, Integer> val1ComparatorAscending = new IndexComparator<>(val1Extractor, SortOrder.ASCENDING);
		int comp = val1ComparatorAscending.compare(row, 99);
		assertTrue (comp > 0);
	}

	@Test
	public void testSortingMultipleWays() {
		List<SampleRow> list = populateUnsortedList();
		Extractor<SampleRow, Integer> val1Extractor = new Val1Extractor();
		Extractor<SampleRow, Integer> val2Extractor = new Val2Extractor();
		
		IndexComparator<SampleRow, Integer> val1ComparatorAscending = new IndexComparator<>(val1Extractor, SortOrder.ASCENDING);
		IndexComparator<SampleRow, Integer> val2ComparatorAscending = new IndexComparator<>(val2Extractor, SortOrder.ASCENDING);
		IndexComparator<SampleRow, Integer> val1ComparatorDescending = new IndexComparator<>(val1Extractor, SortOrder.DESCENDING);
		IndexComparator<SampleRow, Integer> val2ComparatorDescending = new IndexComparator<>(val2Extractor, SortOrder.DESCENDING);
		
		// Before
		assertEquals(toString(list), "add1:2/10, add2:1/9, add3:13/8, add4:5/7, add5:8/6, add6:5/5, add7:9/4, add8:5/3, add9:0/2, add0:3/1, addA:10/0");
		
		// after val1Ascending
		Collections.sort(list, val1ComparatorAscending);
		assertEquals(toString(list), "add9:0/2, add2:1/9, add1:2/10, add0:3/1, add4:5/7, add6:5/5, add8:5/3, add5:8/6, add7:9/4, addA:10/0, add3:13/8");
		
		// after val2Ascending
		Collections.sort(list, val2ComparatorAscending);
		assertEquals(toString(list), "addA:10/0, add0:3/1, add9:0/2, add8:5/3, add7:9/4, add6:5/5, add5:8/6, add4:5/7, add3:13/8, add2:1/9, add1:2/10");
		
		// after val1Descending
		Collections.sort(list, val1ComparatorDescending);
		assertEquals(toString(list), "add3:13/8, addA:10/0, add7:9/4, add5:8/6, add8:5/3, add6:5/5, add4:5/7, add0:3/1, add1:2/10, add2:1/9, add9:0/2");
		
		// after val2Descending
		Collections.sort(list, val2ComparatorDescending);
		assertEquals(toString(list), "add1:2/10, add2:1/9, add3:13/8, add4:5/7, add5:8/6, add6:5/5, add7:9/4, add8:5/3, add9:0/2, add0:3/1, addA:10/0");
	}

	
	List<SampleRow> populateUnsortedList() {
		List<SampleRow> list = new ArrayList<>();
		list.add(new SampleRow("add1", 2, 10, null));
		list.add(new SampleRow("add2", 1, 9, null));
		list.add(new SampleRow("add3", 13, 8, null));
		list.add(new SampleRow("add4", 5, 7, null));
		list.add(new SampleRow("add5", 8, 6, null));
		list.add(new SampleRow("add6", 5, 5, null));
		list.add(new SampleRow("add7", 9, 4, null));
		list.add(new SampleRow("add8", 5, 3, null));
		list.add(new SampleRow("add9", 0, 2, null));
		list.add(new SampleRow("add0", 3, 1, null));
		list.add(new SampleRow("addA", 10, 0, null));
		return list;
	}
	List<SampleRow> populateBiggerUnsortedList() {
		List<SampleRow> list = new ArrayList<>();
		list.add(new SampleRow("add1", 2, 10, null));
		list.add(new SampleRow("add2", 1, 9, null));
		list.add(new SampleRow("add3", 13, 8, null));
		list.add(new SampleRow("add4", 5, 7, null));
		list.add(new SampleRow("add5", 8, 6, null));
		list.add(new SampleRow("add6", 5, 5, null));
		list.add(new SampleRow("add7", 9, 4, null));
		list.add(new SampleRow("add8", 5, 3, null));
		list.add(new SampleRow("add9", 0, 2, null));
		list.add(new SampleRow("add0", 3, 1, null));
		list.add(new SampleRow("addA", 10, 0, null));
		
		list.add(new SampleRow("addB", 2, 110, null));
		list.add(new SampleRow("addC", 1, 19, null));
		list.add(new SampleRow("addD", 13, 18, null));
		list.add(new SampleRow("addE", 5, 17, null));
		list.add(new SampleRow("addF", 8, 16, null));
		list.add(new SampleRow("addG", 5, 15, null));
		list.add(new SampleRow("addH", 9, 14, null));
		list.add(new SampleRow("addI", 5, 13, null));
		list.add(new SampleRow("addJ", 0, 12, null));
		list.add(new SampleRow("addK", 3, 11, null));
		list.add(new SampleRow("addL", 10, 10, null));
		
		list.add(new SampleRow("addM", 2, 1, null));
		list.add(new SampleRow("addN", 1, 4, null));
		list.add(new SampleRow("addO", 13, 4, null));
		list.add(new SampleRow("addP", 5, 4, null));
		list.add(new SampleRow("addQ", 8, 4, null));
		list.add(new SampleRow("addS", 5, 4, null));
		list.add(new SampleRow("addT", 9, 2, null));
		list.add(new SampleRow("addU", 5, 2, null));
		list.add(new SampleRow("addV", 0, 1, null));
		list.add(new SampleRow("addW", 3, 0, null));
		list.add(new SampleRow("addX", 10, -1, null));
		return list;
	}
	@Test
	public void test_sortAscending_by_val1() {
		List<SampleRow> list = populateUnsortedList();
		Extractor<SampleRow, Integer> val1Extractor = new Val1Extractor();
		
		IndexComparator<SampleRow, Integer> val1ComparatorAscending = new IndexComparator<>(val1Extractor, SortOrder.ASCENDING);
		
		// Before
		assertEquals(toString(list), "add1:2/10, add2:1/9, add3:13/8, add4:5/7, add5:8/6, add6:5/5, add7:9/4, add8:5/3, add9:0/2, add0:3/1, addA:10/0");
		
		// after val1Ascending
		Collections.sort(list, val1ComparatorAscending);
		assertEquals(toString(list), "add9:0/2, add2:1/9, add1:2/10, add0:3/1, add4:5/7, add6:5/5, add8:5/3, add5:8/6, add7:9/4, addA:10/0, add3:13/8");
		
	}


	@Test
	public void test_sortAscending_by_val2() {
		List<SampleRow> list = populateUnsortedList();
		Extractor<SampleRow, Integer> val2Extractor = new Val2Extractor();
		
		IndexComparator<SampleRow, Integer> val2ComparatorAscending = new IndexComparator<>(val2Extractor, SortOrder.ASCENDING);
		
		// Before
		assertEquals(toString(list), "add1:2/10, add2:1/9, add3:13/8, add4:5/7, add5:8/6, add6:5/5, add7:9/4, add8:5/3, add9:0/2, add0:3/1, addA:10/0");
		
		// after val2Ascending
		Collections.sort(list, val2ComparatorAscending);
		assertEquals(toString(list), "addA:10/0, add0:3/1, add9:0/2, add8:5/3, add7:9/4, add6:5/5, add5:8/6, add4:5/7, add3:13/8, add2:1/9, add1:2/10");
		
	}


	@Test
	public void test_sortDescending_by_val1() {
		List<SampleRow> list = populateUnsortedList();
		Extractor<SampleRow, Integer> val1Extractor = new Val1Extractor();
		
		IndexComparator<SampleRow, Integer> val1ComparatorDescending = new IndexComparator<>(val1Extractor, SortOrder.DESCENDING);
		
		// Before
		assertEquals(toString(list), "add1:2/10, add2:1/9, add3:13/8, add4:5/7, add5:8/6, add6:5/5, add7:9/4, add8:5/3, add9:0/2, add0:3/1, addA:10/0");
		
		// after val1Descending
		Collections.sort(list, val1ComparatorDescending);
		assertEquals(toString(list), "add3:13/8, addA:10/0, add7:9/4, add5:8/6, add4:5/7, add6:5/5, add8:5/3, add0:3/1, add1:2/10, add2:1/9, add9:0/2");
	}


	@Test
	public void test_sortDescending_by_val2() {
		List<SampleRow> list = populateUnsortedList();
		Extractor<SampleRow, Integer> val2Extractor = new Val2Extractor();
		
		IndexComparator<SampleRow, Integer> val2ComparatorDescending = new IndexComparator<>(val2Extractor, SortOrder.DESCENDING);
		
		// Before
		assertEquals(toString(list), "add1:2/10, add2:1/9, add3:13/8, add4:5/7, add5:8/6, add6:5/5, add7:9/4, add8:5/3, add9:0/2, add0:3/1, addA:10/0");
		
		// after val2Descending
		Collections.sort(list, val2ComparatorDescending);
		assertEquals(toString(list), "add1:2/10, add2:1/9, add3:13/8, add4:5/7, add5:8/6, add6:5/5, add7:9/4, add8:5/3, add9:0/2, add0:3/1, addA:10/0");
	}



	
	String toString(List<SampleRow> list) {
		StringBuilder sb = new StringBuilder();
		for (SampleRow row: list) {
			if (sb.length() > 0) sb.append(", ");
			sb.append(row.key + ":" + row.val1 + "/" + row.val2);
		}
		return sb.toString();
	}
	class Val1Extractor extends Extractor<SampleRow, Integer> {
		Val1Extractor () {
			super ("Val1");
		}
		@Override 
		public Integer extract(SampleRow obj) { return obj.val1; }
	}		
	class Val2Extractor extends Extractor<SampleRow, Integer> {
		Val2Extractor () {
			super ("Val2");
		}
		@Override 
		public Integer extract(SampleRow obj) { return obj.val2; }
	}		
		
}
