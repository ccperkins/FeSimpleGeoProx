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

import java.util.ArrayList;
import java.util.List;

public class BoundingBox {
	public final List<BoundingBoxlet> boxes = new ArrayList<BoundingBoxlet>();

	public BoundingBox() {
		this(null);
	}
	public BoundingBox(List<BoundingBoxlet> boxes) {
		super();
		if (boxes != null)
			this.boxes.addAll(boxes);
	}
	public BoundingBox add(BoundingBoxlet box) {
		boxes.add(box);
		return this;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (BoundingBoxlet box: boxes) {
			if (sb.length() > 0) sb.append("; ");
			sb.append(box.toString());
		}
		return sb.toString();
	}
}
