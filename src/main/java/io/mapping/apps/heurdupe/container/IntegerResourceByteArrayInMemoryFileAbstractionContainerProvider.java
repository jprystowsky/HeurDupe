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
import java.util.Map;

@Component
public class IntegerResourceByteArrayInMemoryFileAbstractionContainerProvider implements FileAbstractionContainerProvider<Integer, ResourceByteArrayInMemoryFileAbstractionContainer> {
	private final Map<Integer, ResourceByteArrayInMemoryFileAbstractionContainer> containerMap = new HashMap<>();

	@Override
	public ResourceByteArrayInMemoryFileAbstractionContainer getContainer(final Integer integer) {
		if (integer == null) {
			// Speedup for bad input
			return null;
		} else if (containerMap.size() >= 1) {
			// Main case
			if (containerMap.keySet().contains(integer)) {
				return containerMap.get(integer);
			} else {
				return createNewContainer(integer);
			}
		} else {
			// Speedup for initial run
			return createNewContainer(integer);
		}
	}

	private ResourceByteArrayInMemoryFileAbstractionContainer createNewContainer(final Integer integer) {
		containerMap.put(integer, new ResourceByteArrayInMemoryFileAbstractionContainer());
		return containerMap.get(integer);
	}
}
