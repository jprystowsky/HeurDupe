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

import io.mapping.apps.heurdupe.transformation.FileTransformationAlgorithm;
import io.mapping.apps.heurdupe.transformation.FileTransformationAlgorithmData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.ByteArrayInputStream;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class ResourceByteArrayFileAbstractionImplTest {
	@Configuration
	static class ContextConfiguration {

	}

	private static String MOCK_PATH = "/etc/file.txt";
	private ResourceByteArrayFileAbstractionImpl mFileAbstraction;

	@Before
	public void setUp() throws Exception {
		mFileAbstraction = new ResourceByteArrayFileAbstractionImpl();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void shouldReturnFileTransformationAlgorithm() throws Exception {
		FileTransformationAlgorithm<byte[], FileTransformationAlgorithmData<byte[]>> fileTransformationAlgorithm =
				(FileTransformationAlgorithm<byte[], FileTransformationAlgorithmData<byte[]>>)
						mock(FileTransformationAlgorithm.class);
		mFileAbstraction.setFileTransformationAlgorithm(fileTransformationAlgorithm);

		FileTransformationAlgorithm<byte[], FileTransformationAlgorithmData<byte[]>> returnedAlgorithm = mFileAbstraction.getFileTransformationAlgorithm();

		assertNotNull("The returned file transformation algorithm should not be null", returnedAlgorithm);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void shouldSetFileTransformationAlgorithm() throws Exception {
		FileTransformationAlgorithm<byte[], FileTransformationAlgorithmData<byte[]>> fileTransformationAlgorithm =
				(FileTransformationAlgorithm<byte[], FileTransformationAlgorithmData<byte[]>>)
						mock(FileTransformationAlgorithm.class);
		mFileAbstraction.setFileTransformationAlgorithm(fileTransformationAlgorithm);

		FileTransformationAlgorithm<byte[], FileTransformationAlgorithmData<byte[]>> returnedAlgorithm = mFileAbstraction.getFileTransformationAlgorithm();

		assertEquals("The returned file transformation algorithm should match", fileTransformationAlgorithm, returnedAlgorithm);
	}

	@Test(expected = NullPointerException.class)
	public void shouldThrowNullPointerExceptionWhenPathRequestedAndResourceUninitialized() throws Exception {
		mFileAbstraction.getPath();
	}

	@Test
	public void shouldReturnResourceFilePathWhenResourceInitialized() throws Exception {
		Resource resource = mock(Resource.class);
		when(resource.getFilename()).thenReturn(MOCK_PATH);

		mFileAbstraction.setBacking(resource);

		assertEquals("Path should match the path of the resource", MOCK_PATH, mFileAbstraction.getPath());
	}

	@Test(expected = NullPointerException.class)
	public void shouldThrowNullPointerExceptionForAbstractionWhenResourceUninitialized() throws Exception {
		mFileAbstraction.getAbstraction();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void shouldReturnTransformationAbstractionFromResourceWhenResourceInitialized() throws Exception {
		// Build some test data
		final byte[] testingBytes = new byte[] { -1, 0, 1 };

		// Mock a backing resource
		Resource mockResource = mock(Resource.class);
		when(mockResource.getInputStream()).thenReturn(new ByteArrayInputStream(testingBytes));
		mFileAbstraction.setBacking(mockResource);

		// Mock file transformation algorithm and algorithm data
		FileTransformationAlgorithmData<byte[]> fileTransformationAlgorithmData =
				(FileTransformationAlgorithmData<byte[]>)
						mock(FileTransformationAlgorithmData.class);
		when(fileTransformationAlgorithmData.getRepresentation()).thenReturn(testingBytes);

		FileTransformationAlgorithm<byte[], FileTransformationAlgorithmData<byte[]>> fileTransformationAlgorithm =
				(FileTransformationAlgorithm<byte[], FileTransformationAlgorithmData<byte[]>>)
						mock(FileTransformationAlgorithm.class);
		when(fileTransformationAlgorithm.transform()).thenReturn(fileTransformationAlgorithmData);

		mFileAbstraction.setFileTransformationAlgorithm(fileTransformationAlgorithm);

		// Get the abstracted bytes
		byte[] abstraction = mFileAbstraction.getAbstraction();

		assertArrayEquals("Abstraction should be delivered", testingBytes, abstraction);
	}

	@Test
	public void shouldReturnBackingForResourceWhenResourceInitialized() throws Exception {
		Resource mockResource = mock(Resource.class);
		mFileAbstraction.setBacking(mockResource);

		Resource returnedResource = mFileAbstraction.getBacking();

		assertEquals("Returned resource should be identical", mockResource, returnedResource);
	}

	@Test
	public void shouldSetBackingWhenInitializing() throws Exception {
		Resource mockResource = mock(Resource.class);

		mFileAbstraction.setBacking(mockResource);

		assertEquals("The backing returned should be the backing that was set", mockResource, mFileAbstraction.getBacking());
	}
}
