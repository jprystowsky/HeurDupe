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

package io.mapping.apps.heurdupe.algorithm;

import io.mapping.apps.heurdupe.properties.PropertiesProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * An implementation of {@link FileTransformationAlgorithm} transforming byte arrays into instances of
 * {@link InitialByteSample}.
 */

@Component
public class InitialByteSamplingFileTransformationAlgorithm implements FileTransformationAlgorithm<byte[], InitialByteSample> {
	@Autowired private PropertiesProvider mPropertiesProvider;
	@Autowired private InitialByteSample mInitialByteSample;
	private InputStream mInputStream;

	@Override
	public InitialByteSample transform() throws IOException {
		Properties properties = mPropertiesProvider.getProperties();
		int sampleSize = Integer.parseInt(properties.getProperty("sample.size"));

		byte[] sampleBytes = new byte[sampleSize];
		mInputStream.read(sampleBytes);
		mInitialByteSample.setBytes(sampleBytes);

		return mInitialByteSample;
	}

	@Override
	public InputStream getInputStream() {
		return mInputStream;
	}

	@Override
	public void setInputStream(final InputStream inputStream) {
		this.mInputStream = inputStream;
	}

	@Override
	public PropertiesProvider getPropertiesProvider() {
		return mPropertiesProvider;
	}

	@Override
	public void setPropertiesProvider(final PropertiesProvider propertiesProvider) {
		this.mPropertiesProvider = propertiesProvider;
	}

	@Override
	public InitialByteSample getInitialByteSample() {
		return mInitialByteSample;
	}

	@Override
	public void setInitialByteSample(final InitialByteSample initialByteSample) {
		this.mInitialByteSample = initialByteSample;
	}
}
