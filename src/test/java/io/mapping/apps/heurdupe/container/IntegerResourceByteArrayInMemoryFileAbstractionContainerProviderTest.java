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

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Repeat;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class IntegerResourceByteArrayInMemoryFileAbstractionContainerProviderTest {
	private IntegerResourceByteArrayInMemoryFileAbstractionContainerProvider mProvider;

	@Before
	public void setUp() throws Exception {
		mProvider = new IntegerResourceByteArrayInMemoryFileAbstractionContainerProvider();
	}

	@Test
	public void shouldReturnNullForNullRepresentation() throws Exception {
		final ResourceByteArrayInMemoryFileAbstractionContainer container = mProvider.getContainer(null);

		assertNull("Container for null integer should be null", container);
	}

	@Test
	@Repeat(10)
	public void shouldReturnNonNullContainerForValidIntegerRepresentations() throws Exception {
		Integer randomInt = new Random().nextInt();

		final ResourceByteArrayInMemoryFileAbstractionContainer container = mProvider.getContainer(randomInt);

		assertNotNull("Container for random integer should not be null", container);
	}

	@Test
	@Repeat(10)
	public void shouldReturnDistinctContainersForDistinctRepresentations() throws Exception {
		Integer randomInt = new Random().nextInt();
		Integer differentRandomInt = randomInt + 1 % Integer.MAX_VALUE;

		final ResourceByteArrayInMemoryFileAbstractionContainer container = mProvider.getContainer(randomInt);
		final ResourceByteArrayInMemoryFileAbstractionContainer differentContainer = mProvider.getContainer(differentRandomInt);

		assertThat("Containers for different representations should not be equal", container, is(not(differentContainer)));
	}

	@Test
	@Repeat(10)
	public void shouldReturnSameContainerForSameRepresentations() throws Exception {
		Integer randomInt = new Random().nextInt();

		final ResourceByteArrayInMemoryFileAbstractionContainer container = mProvider.getContainer(randomInt);
		final ResourceByteArrayInMemoryFileAbstractionContainer differentContainer = mProvider.getContainer(randomInt);

		assertThat("Containers for the same representation should be the same", container, is(sameInstance(differentContainer)));
	}
}
