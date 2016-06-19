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

import java.util.ArrayList;
import java.util.List;

import org.ferriludium.simplegeoprox.internals.Constraint;
import org.ferriludium.simplegeoprox.internals.Constraints;
import org.ferriludium.simplegeoprox.internals.Extractor;
import org.ferriludium.simplegeoprox.internals.Index;
import org.ferriludium.simplegeoprox.internals.IndexComparator;
import org.junit.Test;

public class IndexTest {

	List<SampleRow> create_Dataset1() {
		List<SampleRow> list = new ArrayList<>();
		list.add(new SampleRow("add1", 1, 10, null));
		list.add(new SampleRow("add2", 3, 9, null));
		list.add(new SampleRow("add3", 5, 8, null));
		list.add(new SampleRow("add4", 5, 7, null));
		list.add(new SampleRow("add5", 5, 6, null));
		list.add(new SampleRow("add6", 8, 5, null));
		list.add(new SampleRow("add7", 9, 4, null));
		return list;
	}
	List<SampleRow> create_Dataset2() {
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
	
	
	Index<SampleRow, Integer> createTestIndexVal1_Dataset1() {
		List<SampleRow> list = create_Dataset1();
		Extractor<SampleRow, Integer> val1Extractor = new Val1Extractor();
		Index<SampleRow, Integer> index = new Index<SampleRow, Integer>("test1", val1Extractor, IndexComparator.SortOrder.ASCENDING, list);
		return index;
	}
	Index<SampleRow, Integer> createTestIndexVal2_Dataset1() {
		List<SampleRow> list = create_Dataset1();
		Extractor<SampleRow, Integer> val2Extractor = new Val2Extractor();
		Index<SampleRow, Integer> index = new Index<SampleRow, Integer>("test2", val2Extractor, IndexComparator.SortOrder.ASCENDING, list);
		return index;
	}
	@Test public void test_getAllBetween_5_8_in_Dataset1() {
		Index<SampleRow, Integer> index = createTestIndexVal1_Dataset1();
		List<SampleRow> list = index.getAllBetween(5, 8);
		assertEquals(4, list.size());
		assertEquals(new Integer(5), (list.get(0)).val1);
		assertEquals(new Integer(8), (list.get(3)).val1);
	}
	@Test public void test_getAllBetween_5_999_in_Dataset1() {
		Index<SampleRow, Integer> index = createTestIndexVal1_Dataset1();
		List<SampleRow> list = index.getAllBetween(5, 999);
		assertEquals(5, list.size());
		assertEquals(new Integer(5), (list.get(0)).val1);
		assertEquals(new Integer(9), (list.get(4)).val1);
	}
	@Test public void test_getAllBetween_0_999_in_Dataset1() {
		Index<SampleRow, Integer> index = createTestIndexVal1_Dataset1();
		List<SampleRow> list = index.getAllBetween(0, 999);
		assertEquals(7, list.size());
		assertEquals(new Integer(1), (list.get(0)).val1);
		assertEquals(new Integer(9), (list.get(6)).val1);
	}
	
	@Test public void test_getAllBetween_val1_5_8_in_Dataset1_using_builder1() {
		Index<SampleRow, Integer> index1 = createTestIndexVal1_Dataset1();
		/*
		* Constraints.Builder<SampleRow, Integer> builder = new Constraints.Builder<>();
		* Constraint<SampleRow, Integer> constraint1 = new Constraint<SampleRow, Integer>(new Val1Extractor(), 5, 8);
		* Constraints<SampleRow, Integer> constraints = (builder.and(constraint1)).build();
		*/
		
		Constraints<SampleRow, Integer> constraints = (new Constraints.Builder<SampleRow, Integer> ())
				                                            .and(new Constraint<SampleRow, Integer>(new Val1Extractor(), 5, 8))
				                                            .build();
		List<SampleRow> list = index1.getAllBetween(5, 8, constraints);
		assertEquals(4, list.size());
		assertEquals(new Integer(5), (list.get(0)).val1);
		assertEquals(new Integer(8), (list.get(3)).val1);
		
	}
	@Test public void test_getAllBetween_val1_5_8_val2_6_7_in_Dataset1_builder2_Each() {
		Index<SampleRow, Integer> index1 = createTestIndexVal1_Dataset1();
		Constraint<SampleRow, Integer> constraint1 = new Constraint<SampleRow, Integer>(new Val1Extractor(), 5, 8);
		Constraint<SampleRow, Integer> constraint2 = new Constraint<SampleRow, Integer>(new Val2Extractor(), 6, 7);
		Constraints.Builder<SampleRow, Integer> builder = new Constraints.Builder<>();
		Constraints<SampleRow, Integer> constraints = (builder
				          .and(constraint1)
				          .and(constraint2)).build();
		List<SampleRow> list = index1.getAllPasses_byCheckEach(constraints);
		String res = SampleRow.toString(list);
		assertEquals("add4(5,7), add5(5,6)", res);
		
		list = index1.getAllPasses_bySubset(constraints);
		res = SampleRow.toString(list);
		assertEquals("add4(5,7), add5(5,6)", res);
	}
	@Test public void test_getAllBetween_val1_5_8_val2_6_7_in_Dataset1_builder2_Subset() {
		Index<SampleRow, Integer> index1 = createTestIndexVal1_Dataset1();
		Constraint<SampleRow, Integer> constraint1 = new Constraint<SampleRow, Integer>(new Val1Extractor(), 5, 8);
		Constraint<SampleRow, Integer> constraint2 = new Constraint<SampleRow, Integer>(new Val2Extractor(), 6, 7);
		Constraints.Builder<SampleRow, Integer> builder = new Constraints.Builder<>();
		Constraints<SampleRow, Integer> constraints = (builder
				          .and(constraint1)
				          .and(constraint2)).build();
		List<SampleRow> list = index1.getAllPasses_bySubset(constraints);
		String res = SampleRow.toString(list);
		assertEquals("add4(5,7), add5(5,6)", res);
		
		list = index1.getAllPasses_bySubset(constraints);
		res = SampleRow.toString(list);
		assertEquals("add4(5,7), add5(5,6)", res);
	}
		

	@Test public void testFindExclusiveDown_find_5_in_Dataset1() {
		testFind(createTestIndexVal1_Dataset1(), 5, 1, Index.InclusionMode.EXCLUSIVE_DOWN, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindInclusiveDown_find_5_in_Dataset1() {
		testFind(createTestIndexVal1_Dataset1(), 5, 2, Index.InclusionMode.INCLUSIVE_DOWN, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindExclusiveUp_find_5_in_Dataset1() {
		testFind(createTestIndexVal1_Dataset1(), 5, 5, Index.InclusionMode.EXCLUSIVE_UP, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindInclusiveUp_find_5_in_Dataset1() {
		testFind(createTestIndexVal1_Dataset1(), 5, 4, Index.InclusionMode.INCLUSIVE_UP, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindExclusiveDown_find_unfound_low_in_Dataset1() {
		testFind(createTestIndexVal1_Dataset1(), 0, 0, Index.InclusionMode.EXCLUSIVE_DOWN, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindInclusiveDown_find_unfound_low_in_Dataset1() {
		testFind(createTestIndexVal1_Dataset1(), 0, 0, Index.InclusionMode.INCLUSIVE_DOWN, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindExclusiveUp_find_unfound_low_in_Dataset1() {
		testFind(createTestIndexVal1_Dataset1(), 0, 0, Index.InclusionMode.EXCLUSIVE_UP, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindInclusiveUp_find_unfound_low_in_Dataset1() {
		testFind(createTestIndexVal1_Dataset1(), 0, 0, Index.InclusionMode.INCLUSIVE_UP, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindExclusiveDown_find_unfound_high_in_Dataset1() {
		testFind(createTestIndexVal1_Dataset1(), 99, 7, Index.InclusionMode.EXCLUSIVE_DOWN, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindInclusiveDown_find_unfound_high_in_Dataset1() {
		testFind(createTestIndexVal1_Dataset1(), 99, 7, Index.InclusionMode.INCLUSIVE_DOWN, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindExclusiveUp_find_unfound_high_in_Dataset1() {
		testFind(createTestIndexVal1_Dataset1(), 99, 7, Index.InclusionMode.EXCLUSIVE_UP, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindInclusiveUp_find_unfound_high_in_Dataset1() {
		testFind(createTestIndexVal1_Dataset1(), 99, 7, Index.InclusionMode.INCLUSIVE_UP, Index.MatchRequirement.EXACT_ONLY);
	}
	

	@Test public void testFindExclusiveDown_find_unfound_6_exact_in_Dataset1() {
		testFind(createTestIndexVal1_Dataset1(), 6, -1, Index.InclusionMode.EXCLUSIVE_DOWN, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindInclusiveDown_find_unfound_6_exact_in_Dataset1() {
		testFind(createTestIndexVal1_Dataset1(), 6, -1, Index.InclusionMode.INCLUSIVE_DOWN, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindExclusiveUp_find_unfound_6_exact_in_Dataset1() {
		testFind(createTestIndexVal1_Dataset1(), 6, -1, Index.InclusionMode.EXCLUSIVE_UP, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindInclusiveUp_find_unfound_6_exact_in_Dataset1() {
		testFind(createTestIndexVal1_Dataset1(), 6, -1, Index.InclusionMode.INCLUSIVE_UP, Index.MatchRequirement.EXACT_ONLY);
	}


	@Test public void testFindExclusiveDown_find_unfound_6_inexactok_in_Dataset1() {
		testFind(createTestIndexVal1_Dataset1(), 6, 4, Index.InclusionMode.EXCLUSIVE_DOWN, Index.MatchRequirement.INEXACT_OK);
	}
	@Test public void testFindInclusiveDown_find_unfound_6_inexactok_in_Dataset1() {
		testFind(createTestIndexVal1_Dataset1(), 6, 4, Index.InclusionMode.INCLUSIVE_DOWN, Index.MatchRequirement.INEXACT_OK);
	}
	@Test public void testFindExclusiveUp_find_unfound_6_inexactok_in_Dataset1() {
		testFind(createTestIndexVal1_Dataset1(), 6, 5, Index.InclusionMode.EXCLUSIVE_UP, Index.MatchRequirement.INEXACT_OK);
	}
	@Test public void testFindInclusiveUp_find_unfound_6_inexactok_in_Dataset1() {
		testFind(createTestIndexVal1_Dataset1(), 6, 5, Index.InclusionMode.INCLUSIVE_UP, Index.MatchRequirement.INEXACT_OK);
	}

	

	Index<SampleRow, Integer> createTestIndexVal1_Dataset2() {
		List<SampleRow> list = new ArrayList<>();
		list.add(new SampleRow("add1", 1, 10, null));
		list.add(new SampleRow("add2", 3, 9, null));
		list.add(new SampleRow("add3", 5, 8, null));
		list.add(new SampleRow("add4", 5, 7, null));
		list.add(new SampleRow("add5", 5, 6, null));
		Extractor<SampleRow, Integer> val1Extractor = new Val1Extractor();
		Index<SampleRow, Integer> index = new Index<SampleRow, Integer>("test", val1Extractor, IndexComparator.SortOrder.ASCENDING, list);
		return index;
	}
	@Test public void testFindExclusiveDown_find_5_in_Dataset2() {
		testFind(createTestIndexVal1_Dataset2(), 5, 1, Index.InclusionMode.EXCLUSIVE_DOWN, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindInclusiveDown_find_5_in_Dataset2() {
		testFind(createTestIndexVal1_Dataset2(), 5, 2, Index.InclusionMode.INCLUSIVE_DOWN, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindExclusiveUp_find_5_in_Dataset2() {
		testFind(createTestIndexVal1_Dataset2(), 5, 5, Index.InclusionMode.EXCLUSIVE_UP, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindInclusiveUp_find_5_in_Dataset2() {
		testFind(createTestIndexVal1_Dataset2(), 5, 4, Index.InclusionMode.INCLUSIVE_UP, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindExclusiveDown_find_unfound_low_in_Dataset2() {
		testFind(createTestIndexVal1_Dataset2(), 0, 0, Index.InclusionMode.EXCLUSIVE_DOWN, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindInclusiveDown_find_unfound_low_in_Dataset2() {
		testFind(createTestIndexVal1_Dataset2(), 0, 0, Index.InclusionMode.INCLUSIVE_DOWN, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindExclusiveUp_find_unfound_low_in_Dataset2() {
		testFind(createTestIndexVal1_Dataset2(), 0, 0, Index.InclusionMode.EXCLUSIVE_UP, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindInclusiveUp_find_unfound_low_in_Dataset2() {
		testFind(createTestIndexVal1_Dataset2(), 0, 0, Index.InclusionMode.INCLUSIVE_UP, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindExclusiveDown_find_unfound_high_in_Dataset2() {
		testFind(createTestIndexVal1_Dataset2(), 99, 5, Index.InclusionMode.EXCLUSIVE_DOWN, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindInclusiveDown_find_unfound_high_in_Dataset2() {
		testFind(createTestIndexVal1_Dataset2(), 99, 5, Index.InclusionMode.INCLUSIVE_DOWN, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindExclusiveUp_find_unfound_high_in_Dataset2() {
		testFind(createTestIndexVal1_Dataset2(), 99, 5, Index.InclusionMode.EXCLUSIVE_UP, Index.MatchRequirement.EXACT_ONLY);
	}


	Index<SampleRow, Integer> createTestIndexVal1_Dataset3() {
		List<SampleRow> list = new ArrayList<>();
		list.add(new SampleRow("add3", 5, 8, null));
		list.add(new SampleRow("add4", 5, 7, null));
		list.add(new SampleRow("add5", 5, 6, null));
		list.add(new SampleRow("add6", 8, 5, null));
		list.add(new SampleRow("add7", 9, 4, null));
		Extractor<SampleRow, Integer> val1Extractor = new Val1Extractor();
		Index<SampleRow, Integer> index = new Index<SampleRow, Integer>("test", val1Extractor, IndexComparator.SortOrder.ASCENDING, list);
		return index;
	}

	@Test
	public void testFindExclusiveDown_find_5_in_Dataset3() {
		testFind(createTestIndexVal1_Dataset3(), 5, 0, Index.InclusionMode.EXCLUSIVE_DOWN, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test
	public void testFindInclusiveDown_find_5_in_Dataset3() {
		testFind(createTestIndexVal1_Dataset3(), 5, 0, Index.InclusionMode.INCLUSIVE_DOWN, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindExclusiveUp_find_5_in_Dataset3() {
		testFind(createTestIndexVal1_Dataset3(), 5, 3, Index.InclusionMode.EXCLUSIVE_UP, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindInclusiveUp_find_5_in_Dataset3() {
		testFind(createTestIndexVal1_Dataset3(), 5, 2, Index.InclusionMode.INCLUSIVE_UP, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindExclusiveDown_find_unfound_low_in_Dataset3() {
		testFind(createTestIndexVal1_Dataset3(), 0, 0, Index.InclusionMode.EXCLUSIVE_DOWN, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindInclusiveDown_find_unfound_low_in_Dataset3() {
		testFind(createTestIndexVal1_Dataset3(), 0, 0, Index.InclusionMode.INCLUSIVE_DOWN, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindExclusiveUp_find_unfound_low_in_Dataset3() {
		testFind(createTestIndexVal1_Dataset3(), 0, 0, Index.InclusionMode.EXCLUSIVE_UP, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindInclusiveUp_find_unfound_low_in_Dataset3() {
		testFind(createTestIndexVal1_Dataset3(), 0, 0, Index.InclusionMode.INCLUSIVE_UP, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindExclusiveDown_find_unfound_high_in_Dataset3() {
		testFind(createTestIndexVal1_Dataset3(), 99, 5, Index.InclusionMode.EXCLUSIVE_DOWN, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindInclusiveDown_find_unfound_high_in_Dataset3() {
		testFind(createTestIndexVal1_Dataset3(), 99, 5, Index.InclusionMode.INCLUSIVE_DOWN, Index.MatchRequirement.EXACT_ONLY);
	}
	@Test public void testFindExclusiveUp_find_unfound_high_in_Dataset3() {
		testFind(createTestIndexVal1_Dataset3(), 99, 5, Index.InclusionMode.EXCLUSIVE_UP, Index.MatchRequirement.EXACT_ONLY);
	}

	Index<SampleRow, Integer> createTestIndexVal1_Dataset0_empty() {
		List<SampleRow> list = new ArrayList<>();
		Extractor<SampleRow, Integer> val1Extractor = new Val1Extractor();
		Index<SampleRow, Integer> index = new Index<SampleRow, Integer>("test", val1Extractor, IndexComparator.SortOrder.ASCENDING, list);
		return index;
	}	
	@Test public void testFindExclusiveUp_find_unfound_in_Dataset0_empty() {
		testFind(createTestIndexVal1_Dataset0_empty(), 99, 0, Index.InclusionMode.EXCLUSIVE_UP, Index.MatchRequirement.EXACT_ONLY);
	}

	List<SampleRow> createDataset4() {
		List<SampleRow> list = new ArrayList<>();
		list.add(new SampleRow("add2", 3, 9, null));
		list.add(new SampleRow("add3", 5, 8, null));
		list.add(new SampleRow("add4", 5, 7, null));
		list.add(new SampleRow("add5", 5, 6, null));
		return list;
	}
	Index<SampleRow, Integer> createTestIndexVal1_Dataset4() {
		List<SampleRow> list = createDataset4();
		Extractor<SampleRow, Integer> val1Extractor = new Val1Extractor();
		Index<SampleRow, Integer> index = new Index<SampleRow, Integer>("test", val1Extractor, IndexComparator.SortOrder.ASCENDING, list);
		return index;
	}	
	Index<SampleRow, Integer> createTestIndexVal2_Dataset4() {
		List<SampleRow> list = createDataset4();
		Extractor<SampleRow, Integer> val1Extractor = new Val2Extractor();
		Index<SampleRow, Integer> index = new Index<SampleRow, Integer>("test", val1Extractor, IndexComparator.SortOrder.ASCENDING, list);
		return index;
	}	
	@Test public void testFindInclusiveUp_find_unfound_1_nearest_in_Dataset4() {
		testFind(createTestIndexVal1_Dataset4(), 1, 0, Index.InclusionMode.INCLUSIVE_UP, Index.MatchRequirement.INEXACT_OK);
	}
	@Test public void testFindInclusiveUp_find_unfound_2_nearest_in_Dataset4() {
		testFind(createTestIndexVal1_Dataset4(), 2, 0, Index.InclusionMode.INCLUSIVE_UP, Index.MatchRequirement.INEXACT_OK);
	}

	private void testFind (Index<SampleRow, Integer> index, Integer target, int expected, Index.InclusionMode mode, Index.MatchRequirement matchReq) {
		int idx = index.find(target, mode, matchReq);
		assertEquals(expected, idx);
	}
	@Test public void testIterate_1_Dataset4() {
		Index<SampleRow, Integer> index = createTestIndexVal1_Dataset4();
		String res = SampleRow.toString(index.iterator());
		assertEquals ("add2(3,9), add3(5,8), add4(5,7), add5(5,6)", res);
	}
	@Test public void testIterate_2_Dataset4() {
		Index<SampleRow, Integer> index = createTestIndexVal2_Dataset4();
		String res = SampleRow.toString(index.iterator());
		assertEquals ("add5(5,6), add4(5,7), add3(5,8), add2(3,9)", res);
	}
	@Test public void testIterate_1_and_2_Dataset4() {
		List<SampleRow> list = createDataset4();
		Index<SampleRow, Integer> index1 = new Index<SampleRow, Integer>("test", new Val1Extractor(), IndexComparator.SortOrder.ASCENDING, list);
		Index<SampleRow, Integer> index2 = new Index<SampleRow, Integer>("test", new Val2Extractor(), IndexComparator.SortOrder.ASCENDING, list);
		String res = SampleRow.toString(index1.iterator());
		assertEquals ("add2(3,9), add3(5,8), add4(5,7), add5(5,6)", res);
		res = SampleRow.toString(index2.iterator());
		assertEquals ("add5(5,6), add4(5,7), add3(5,8), add2(3,9)", res);
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
