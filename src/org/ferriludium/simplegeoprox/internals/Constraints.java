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
import java.util.List;

/**
 * Holds an ordered list of Constraint items.
 * 
 * TODO: Look into adding more complex relationships ("OR", possibly parentheses) - BUT DO NOT GET CARRIED AWAY: IF THE NEEDS ARE COMPLEX ENOUGH, USER NEEDS AN ACTUAL DATABASE.
 * 
 * @author Cornelius Perkins (ccperkins at bitbucket and github)
 *
 * @param <E>
 * @param <V>
 */
public class Constraints<E, V extends Comparable<V>>  {
	final List<Constraint<E,V>> constraints = new ArrayList<Constraint<E,V>> ();
	public static class Builder<E, V extends Comparable<V>> {
		final List<Constraint<E,V>> constraints = new ArrayList<Constraint<E,V>> ();
		
		public Builder() {
			this(null);
		}
		public Builder(List<Constraint<E,V>> constraints ) {
			super();
			if (constraints != null)
				this.constraints.addAll(constraints);
		}
		public Builder<E,V> and (Constraint<E, V> constraint) {
			this.constraints.add(constraint);
			return this;
		}
		public Constraints<E,V> build() {
			return new Constraints<E, V> (constraints);
		}
	}
	public Constraints(List<Constraint<E,V>> constraints ) {
		super();
		this.constraints.addAll(constraints);
	}

	public boolean passes(E e) {
		for (Constraint<E, V> constraint: constraints) {
			if (! (constraint.passes(e)))
				return false;
		}
		return true;
	}

	public List<Constraint<E, V>> getConstraints() {
		return constraints;
	}

	@Override
	public String toString() {
		if (constraints.size() == 0)
			return "[Where TRUE]";
		StringBuilder sb = new StringBuilder();
		for (Constraint<E,V> constraint: constraints) {
			if (sb.length() > 0) sb.append(" AND ");
			sb.append(constraint);
		}
		return "[Where " + sb.toString() + "]";
	}


}
