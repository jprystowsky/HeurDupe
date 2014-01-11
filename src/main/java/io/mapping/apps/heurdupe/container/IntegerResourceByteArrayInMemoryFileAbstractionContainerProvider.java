/*
 * Copyright 2014 Jacob Miles Prystowsky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.mapping.apps.heurdupe.container;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class IntegerResourceByteArrayInMemoryFileAbstractionContainerProvider implements FileAbstractionContainerProvider<Integer, ResourceByteArrayInMemoryFileAbstractionContainer> {
	private final Map<Integer, ResourceByteArrayInMemoryFileAbstractionContainer> mContainerMap = new HashMap<>();

	@Override
	public ResourceByteArrayInMemoryFileAbstractionContainer getContainer(final Integer integer) {
		if (integer == null) {
			// Speedup for bad input
			return null;
		} else if (mContainerMap.size() >= 1) {
			// Main case
			if (mContainerMap.keySet().contains(integer)) {
				return mContainerMap.get(integer);
			} else {
				return createNewContainer(integer);
			}
		} else {
			// Speedup for initial run
			return createNewContainer(integer);
		}
	}

	@Override
	public int size() {
		return mContainerMap.size();
	}

	@Override
	public void addContainer(final Integer reduction, final ResourceByteArrayInMemoryFileAbstractionContainer container) {
		if (container != null) {
			if (!mContainerMap.keySet().contains(reduction)) {
				mContainerMap.put(reduction, container);
			}
		}
	}

	@Override
	public Iterator<Integer> getReductionIterator() {
		return mContainerMap.keySet().iterator();
	}

	private ResourceByteArrayInMemoryFileAbstractionContainer createNewContainer(final Integer integer) {
		mContainerMap.put(integer, new ResourceByteArrayInMemoryFileAbstractionContainer());
		return mContainerMap.get(integer);
	}
}
