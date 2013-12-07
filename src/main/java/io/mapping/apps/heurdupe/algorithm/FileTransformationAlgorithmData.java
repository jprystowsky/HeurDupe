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

/**
 * Represents the data provided by a {@link FileTransformationAlgorithm}.
 * @param <T> the fundamental data type representing the file.
 */

public interface FileTransformationAlgorithmData<T> {
	/**
	 * Gets the fundamental data representing the file.
	 * @return the fundamental backing data, of type {@code T}.
	 */
	public T getAbstraction();
}
