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

import java.io.IOException;
import java.io.InputStream;

/**
 * An algorithm for transforming a file's representation of type {@code T} into a data representation of type {@code U}.
 * @param <T> the file's data representation.
 * @param <U> an instance of {@link FileTransformationAlgorithmData} of type {@code T}.
 */

public interface FileTransformationAlgorithm<T, U extends FileTransformationAlgorithmData<T>> {
	public InputStream getInputStream();
	public void setInputStream(InputStream inputStream);
	public PropertiesProvider getPropertiesProvider();
	public void setPropertiesProvider(PropertiesProvider propertiesProvider);
	public InitialByteSample getInitialByteSample();
	public void setInitialByteSample(InitialByteSample initialByteSample);
	public U transform() throws IOException;
}
