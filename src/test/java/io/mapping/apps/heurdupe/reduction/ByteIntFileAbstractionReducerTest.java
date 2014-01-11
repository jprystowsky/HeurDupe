/*
 * Copyright 2013 Jacob Miles Prystowsky
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

package io.mapping.apps.heurdupe.reduction;

import io.mapping.apps.heurdupe.reduction.ByteIntFileAbstractionReducer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Repeat;

import java.util.Arrays;
import java.util.Random;

public class ByteIntFileAbstractionReducerTest {
	private ByteIntFileAbstractionReducer mAbstractionReducer;

	@Before
	public void setUp() throws Exception {
		mAbstractionReducer = new ByteIntFileAbstractionReducer();
	}

	@Test
	@Repeat(10)
	public void shouldReduceAbstractionUsingHashcode() throws Exception {
		Random random = new Random();

		int randomSize = random.nextInt(1025);

		byte[] bytes = new byte[randomSize];
		random.nextBytes(bytes);

		Assert.assertEquals("Reducer must produce a hashcode integer", (long) Arrays.hashCode(bytes), (long) mAbstractionReducer.reduceAbstraction(bytes));
	}
}
