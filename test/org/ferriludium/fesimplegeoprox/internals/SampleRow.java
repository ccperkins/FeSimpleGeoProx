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

import java.util.Collection;
import java.util.Iterator;

public final class SampleRow {
	final public String key;
	final public Integer val1;
	final public Integer val2;
	final public String contents;
	public SampleRow(String key, int val1, int val2, String contents) {
		this.key = key;
		this.val1 = val1;
		this.val2 = val2;
		this.contents = contents;
	}
	@Override
	public String toString() {
		//return "SampleRow [key=" + key + ", val1=" + val1 + ", val2=" + val2 + ", contents=" + contents + "]";
		return key + "(" + val1 + "," + val2 + ")";
	}
	
	public static String toString(Collection<SampleRow> collection) {
		StringBuilder sb = new StringBuilder();
		for (SampleRow row: collection) {
			if (sb.length() > 0) sb.append(", ");
			sb.append(row);
		}
		return sb.toString();
	}
	public static String toString(Iterator<SampleRow> iter) {
		StringBuilder sb = new StringBuilder();
		while (iter.hasNext()) {
			if (sb.length() > 0) sb.append(", ");
			sb.append(iter.next());
		}
		return sb.toString();
	}

}
