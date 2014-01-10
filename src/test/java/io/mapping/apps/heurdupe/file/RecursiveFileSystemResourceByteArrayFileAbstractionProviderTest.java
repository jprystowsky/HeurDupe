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

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.NavigableSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RecursiveFileSystemResourceByteArrayFileAbstractionProviderTest {
	private RecursiveFileSystemResourceByteArrayFileAbstractionProvider<ResourceByteArrayFileAbstraction> mFileAbstractionProvider;

	@Before
	public void setUp() throws Exception {
		mFileAbstractionProvider = new RecursiveFileSystemResourceByteArrayFileAbstractionProvider<>();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void shouldSetFileAbstractionCreator() throws Exception {
		FileAbstractionCreator<Resource, byte[], ResourceByteArrayFileAbstraction> mockCreator = mock(FileAbstractionCreator.class);

		mFileAbstractionProvider.setFileAbstractionCreator(mockCreator);

		assertEquals("File abstraction creator should be set creator", mockCreator, mFileAbstractionProvider.getFileAbstractionCreator());

	}

	@Test
	public void shouldReturnEmptySetForNullStartFile() throws Exception {
		Set<ResourceByteArrayFileAbstraction> files = mFileAbstractionProvider.getFiles(null);

		assertNotNull("Should receive empty set and not null for null start file", files);
		assertEquals("Size of set for null file should be zero", 0, files.size());
	}

	@Test
	public void shouldReturnSingletonSetForLeafFile() throws Exception {
		File mockFile = mock(File.class);
		when(mockFile.isDirectory()).thenReturn(Boolean.FALSE);
		when(mockFile.isFile()).thenReturn(Boolean.TRUE);
		when(mockFile.listFiles()).thenReturn(null);

		Resource mockResource = mock(Resource.class);
		when(mockResource.getFile()).thenReturn(mockFile);

		ResourceByteArrayFileAbstraction mockFileAbstraction = mock(ResourceByteArrayFileAbstraction.class);
		when(mockFileAbstraction.getBacking()).thenReturn(mockResource);

		NavigableSet<ResourceByteArrayFileAbstraction> files = mFileAbstractionProvider.getFiles(mockFileAbstraction);

		assertNotNull("Set of files should not be null", files);
		assertEquals("Set of files should contain one element", 1, files.size());
		assertEquals("Set should consist of the single input file abstraction", mockFileAbstraction, files.first());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void shouldReturnChildFileSetForTerminalBranchDirectory() throws Exception {
		File mockFile = mock(File.class);
		when(mockFile.isFile()).thenReturn(Boolean.TRUE);
		when(mockFile.getAbsolutePath()).thenReturn("/file");

		File mockDirectory = mock(File.class);
		when(mockDirectory.isDirectory()).thenReturn(Boolean.TRUE);
		when(mockDirectory.isFile()).thenReturn(Boolean.FALSE);
		when(mockDirectory.listFiles()).thenReturn(new File[] { mockFile });

		Resource mockResource = mock(Resource.class);
		when(mockResource.getFile()).thenReturn(mockDirectory);

		ResourceByteArrayFileAbstraction mockFileAbstraction = mock(ResourceByteArrayFileAbstraction.class);
		when(mockFileAbstraction.getBacking()).thenReturn(mockResource);

		FileAbstractionCreator<Resource, byte[], ResourceByteArrayFileAbstraction> mockCreator = mock(FileAbstractionCreator.class);
		when(mockCreator.createFileAbstraction()).thenReturn(mockFileAbstraction);

		mFileAbstractionProvider.setFileAbstractionCreator(mockCreator);
		NavigableSet<ResourceByteArrayFileAbstraction> files = mFileAbstractionProvider.getFiles(mockFileAbstraction);

		assertNotNull("Set of files should not be null", files);
		assertEquals("Set of files should contain one element", 1, files.size());
		assertEquals("Set should consist of the child of the single input file abstraction", mockFileAbstraction, files.first());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void shouldReturnGrandchildFileSetForNonTerminalBranchDirectory() throws Exception {
		File mockFile = mock(File.class);
		when(mockFile.isFile()).thenReturn(Boolean.TRUE);
		when(mockFile.getAbsolutePath()).thenReturn("/file");

		File mockDirectory = mock(File.class);
		when(mockDirectory.isDirectory()).thenReturn(Boolean.TRUE);
		when(mockDirectory.isFile()).thenReturn(Boolean.FALSE);
		when(mockDirectory.listFiles()).thenReturn(new File[] { mockFile });

		Resource mockResource = mock(Resource.class);
		when(mockResource.getFile()).thenReturn(mockDirectory);

		File mockHigherDirectory = mock(File.class);
		when(mockHigherDirectory.isDirectory()).thenReturn(Boolean.TRUE);
		when(mockHigherDirectory.isFile()).thenReturn(Boolean.FALSE);
		when(mockHigherDirectory.listFiles()).thenReturn(new File[] { mockDirectory });

		Resource mockHigherResource = mock(Resource.class);
		when(mockHigherResource.getFile()).thenReturn(mockHigherDirectory);

		ResourceByteArrayFileAbstraction mockFileAbstraction = mock(ResourceByteArrayFileAbstraction.class);
		when(mockFileAbstraction.getBacking()).thenReturn(mockHigherResource);

		FileAbstractionCreator<Resource, byte[], ResourceByteArrayFileAbstraction> mockCreator = mock(FileAbstractionCreator.class);
		when(mockCreator.createFileAbstraction()).thenReturn(mockFileAbstraction);

		mFileAbstractionProvider.setFileAbstractionCreator(mockCreator);
		NavigableSet<ResourceByteArrayFileAbstraction> files = mFileAbstractionProvider.getFiles(mockFileAbstraction);

		assertNotNull("Set of files should not be null", files);
		assertEquals("Set of files should contain one element", 1, files.size());
		assertEquals("Set should consist of the grandchild of the single input file abstraction", mockFileAbstraction, files.first());
	}
}
