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
 * Holds a pair of comparable values.
 * 
 * @author Cornelius Perkins (ccperkins at bitbucket and github)
 *
 * @param <V>
 */
public class Bounds<V extends Comparable<V>>  {
	public final V lowValue;
	public final V highValue;
	public Bounds(V lowValue, V highValue) {
		super();
		this.lowValue = lowValue;
		this.highValue = highValue;
	}
	@Override
	public String toString() {
		return "low=" + lowValue + ", high=" + highValue ;
	}
	

}
