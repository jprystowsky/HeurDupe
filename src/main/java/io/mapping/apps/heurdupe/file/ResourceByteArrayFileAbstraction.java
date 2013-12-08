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

package io.mapping.apps.heurdupe.file;

import io.mapping.apps.heurdupe.algorithm.FileTransformationAlgorithm;
import io.mapping.apps.heurdupe.algorithm.FileTransformationAlgorithmData;
import org.springframework.core.io.Resource;

import java.io.IOException;

public interface ResourceByteArrayFileAbstraction extends FileAbstraction<Resource, byte[]> {
	@Override
	FileTransformationAlgorithm<byte[], FileTransformationAlgorithmData<byte[]>> getFileTransformationAlgorithm();

	@Override
	void setFileTransformationAlgorithm(FileTransformationAlgorithm<byte[], FileTransformationAlgorithmData<byte[]>> fileTransformationAlgorithm);

	@Override
	String getPath() throws IOException;

	@Override
	byte[] getAbstraction() throws IOException;

	@Override
	Resource getBacking();

	@Override
	void setBacking(Resource backing);
}
