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

import io.mapping.apps.heurdupe.properties.PropertiesProvider;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class InitialByteSamplingFileTransformationAlgorithmTest {
	@Configuration
	static class ContextConfiguration {
	}

	// Mock input and output bytes
	private final String mSampleInput = "abc";
	private final byte[] mExpectedBytes = "ab".getBytes();

	// Set up mock properties
	private final String mSampleSizeProperty = "sample.size";
	private final int mSampleSizePropertyValue = 2;

	private InitialByteSamplingFileTransformationAlgorithm mAlgorithm;

	@Before
	public void setUp() throws Exception {
		mAlgorithm = new InitialByteSamplingFileTransformationAlgorithm();
	}

	@Test
	public void shouldHaveNullInputStreamWhenUninitialized() throws Exception {
		assertNull("Input stream should start out null", mAlgorithm.getInputStream());
	}

	@Test
	public void shouldSetInputStream() throws Exception {
		InputStream mockStream = mock(InputStream.class);
		mAlgorithm.setInputStream(mockStream);

		assertEquals("Input stream should be mock stream", mAlgorithm.getInputStream(), mockStream);
	}

	@Test
	public void shouldReturnPropertiesProvider() throws Exception {
		PropertiesProvider propertiesProvider = mock(PropertiesProvider.class);

		mAlgorithm.setPropertiesProvider(propertiesProvider);

		assertNotNull("Properties provider should not be null", mAlgorithm.getPropertiesProvider());
	}

	@Test
	public void shouldSetPropertiesProvider() throws Exception {
		PropertiesProvider propertiesProvider = mock(PropertiesProvider.class);

		mAlgorithm.setPropertiesProvider(propertiesProvider);

		assertEquals("Properties provider should be set", mAlgorithm.getPropertiesProvider(), propertiesProvider);
	}

	@Test(expected = NullPointerException.class)
	public void shouldThrowNullPointerExceptionWhenTransformingWithoutPropertiesProviderInitialization() throws Exception {
		mAlgorithm.transform();
	}

	@Test(expected = NullPointerException.class)
	public void shouldReadSampleSizeFromProperties() throws Exception {
		Properties properties = mock(Properties.class);
		when(properties.getProperty(mSampleSizeProperty)).thenReturn(String.valueOf(mSampleSizePropertyValue));

		PropertiesProvider propertiesProvider = mock(PropertiesProvider.class);
		when(propertiesProvider.getProperties()).thenReturn(properties);

		mAlgorithm.setPropertiesProvider(propertiesProvider);

		mAlgorithm.transform();

		verify(propertiesProvider, atLeastOnce()).getProperties();
		verify(properties, atLeastOnce()).getProperty(mSampleSizeProperty);
	}

	@Test
	public void shouldTransformInputDataCorrectly() throws Exception {
		Properties properties = mock(Properties.class);
		when(properties.getProperty(mSampleSizeProperty)).thenReturn(String.valueOf(mSampleSizePropertyValue));

		PropertiesProvider propertiesProvider = mock(PropertiesProvider.class);
		when(propertiesProvider.getProperties()).thenReturn(properties);

		mAlgorithm.setPropertiesProvider(propertiesProvider);

		// Set up testing input stream
		InputStream inputStream = IOUtils.toInputStream(mSampleInput);

		/**
		 * Set up (partial) mock for initial byte sample to avoid using a second concrete implementation.
		 * This has some code smell, but the algorithm is responsible for creating a class whose type is
		 * unknown at runtime, but nevertheless must save values given to it.
		 */
		InitialByteSample initialByteSample = spy(new InitialByteSample() {
			private byte[] mBytes;

			@Override
			public byte[] getBytes() {
				return mBytes;
			}

			@Override
			public void setBytes(final byte[] bytes) {
				this.mBytes = bytes;
			}

			@Override
			public byte[] getRepresentation() {
				return mBytes;
			}
		});

		mAlgorithm.setInitialByteSample(initialByteSample);

		mAlgorithm.setInputStream(inputStream);

		InitialByteSample sample = mAlgorithm.transform();

		verify(initialByteSample, atLeastOnce()).setBytes(any(byte[].class));

		assertNotNull("Transformation shouldn't be null", sample);
		assertTrue("Transformation representation should equal sampled input bytes", Arrays.equals(sample.getRepresentation(), mExpectedBytes));
		assertTrue("Byte sample size should be the appropriate size", mSampleSizePropertyValue == sample.getRepresentation().length);
	}
}
