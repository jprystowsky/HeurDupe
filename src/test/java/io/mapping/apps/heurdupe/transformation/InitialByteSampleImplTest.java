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

package io.mapping.apps.heurdupe.transformation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class InitialByteSampleImplTest {
	@Configuration
	static class ContextConfiguration {

	}

	private InitialByteSampleImpl mInitialByteSample;

	@Before
	public void setUp() throws Exception {
		mInitialByteSample = new InitialByteSampleImpl();
	}

	@Test
	public void shouldReturnNullBytesInitially() throws Exception {
		assertNull("Bytes should initially be null", mInitialByteSample.getBytes());
	}

	@Test
	public void shouldSetBytes() throws Exception {
		byte[] newBytes = { 1, 2, 3 };

		mInitialByteSample.setBytes(newBytes);
		assertTrue("Bytes should equal", newBytes == mInitialByteSample.getBytes());
	}

	@Test
	public void shouldBeEqualToAnotherInstanceWithMatchingBytes() throws Exception {
		byte[] sampleBytes = new byte[]{ 0 };

		InitialByteSampleImpl stubInstance = spy(new InitialByteSampleImpl());
		when(stubInstance.getBytes()).thenReturn(sampleBytes);

		mInitialByteSample.setBytes(sampleBytes);

		assertEquals("Should equal an instance with matching bytes", mInitialByteSample, stubInstance);
	}

	@Test
	public void shouldBeGreaterThanAnInstaceWithSmallerBytes() throws Exception {
		InitialByteSampleImpl smallerSample = spy(new InitialByteSampleImpl());
		when(smallerSample.getBytes()).thenReturn(new byte[]{-1});

		byte[] sampleBytes = new byte[]{ 0 };

		mInitialByteSample.setBytes(sampleBytes);

		assertTrue("Object should be greater than a smaller object", mInitialByteSample.compareTo(smallerSample) > 0);
	}

	@Test
	public void shouldBeSmallerThanAnInstanceWithLargerBytes() throws Exception {
		InitialByteSampleImpl biggerSample = spy(new InitialByteSampleImpl());
		when(biggerSample.getBytes()).thenReturn(new byte[] { 1 });

		byte[] sampleBytes = new byte[]{ 0 };

		mInitialByteSample.setBytes(sampleBytes);

		assertTrue("Object should be lesser than a bigger object", mInitialByteSample.compareTo(biggerSample) < 0);
	}

	@Test
	public void shouldReturnAbstractionIdenticalToSetBytes() throws Exception {
		byte[] newBytes = new byte[] { 0 };

		mInitialByteSample.setBytes(newBytes);
		assertEquals("Bytes should be equal", newBytes, mInitialByteSample.getRepresentation());
	}
}
