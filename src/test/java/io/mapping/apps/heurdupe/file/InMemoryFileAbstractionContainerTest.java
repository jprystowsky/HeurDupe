package io.mapping.apps.heurdupe.file;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Iterator;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class InMemoryFileAbstractionContainerTest {
	@Configuration
	static class ContextConfiguration {
		@Bean
		public FileAbstraction fileAbstraction() {
			return mock(FileAbstraction.class);
		}
	}

	private InMemoryFileAbstractionContainer mInMemoryFileAbstractionContainer;
	@Autowired private FileAbstraction mFileAbstraction;

	@Before
	public void setUp() throws Exception {
		mInMemoryFileAbstractionContainer = new InMemoryFileAbstractionContainer();
	}

	@Test
	public void shouldAddAFile() throws Exception {
		mInMemoryFileAbstractionContainer.addFile(mFileAbstraction);

		assertTrue("Container should now contain file", mInMemoryFileAbstractionContainer.containsFile(mFileAbstraction));
	}

	@Test
	public void shouldRemoveFileNeverAdded() throws Exception {
		mInMemoryFileAbstractionContainer.removeFile(mFileAbstraction);

	}

	@Test
	public void shouldRemoveAddedFile() throws Exception {
		mInMemoryFileAbstractionContainer.addFile(mFileAbstraction);
		mInMemoryFileAbstractionContainer.removeFile(mFileAbstraction);

		assertFalse("Container shouldn't contain file any more", mInMemoryFileAbstractionContainer.containsFile(mFileAbstraction));
	}

	@Test
	public void shouldReturnIteratorWhenEmpty() throws Exception {
		Iterator<FileAbstraction> fileAbstractionIterator = mInMemoryFileAbstractionContainer.getIterator();

		assertNotNull("Iterator shouldn't be null", fileAbstractionIterator);
	}

	@Test
	public void shouldReturnIteratorOverFilesAvailable() throws Exception {
		int fileCount = 10;
		for (int i = 0; i < fileCount; i++) {
			mInMemoryFileAbstractionContainer.addFile(mock(FileAbstraction.class));
		}

		Iterator<FileAbstraction> fileAbstractionIterator = mInMemoryFileAbstractionContainer.getIterator();

		assertNotNull("Iterator shouldn't be null", fileAbstractionIterator);
		assertTrue("Iterator should have next element", fileAbstractionIterator.hasNext());

		int reached = 0;
		while (fileAbstractionIterator.hasNext()) {
			fileAbstractionIterator.next();
			reached++;
		}

		assertEquals("Iterator count should match number of files", fileCount, reached);
	}

	@Test
	public void shouldReturnSensibleSize() throws Exception {
		assertTrue("Container should contain at least one file", mInMemoryFileAbstractionContainer.getSize() >= 0);
	}

	@Test
	public void shouldReturnEmptySizeWhenNoFilesAreAdded() throws Exception {
		assertEquals("Size should be empty", 0, mInMemoryFileAbstractionContainer.getSize());
	}

	@Test
	public void shouldReturnCorrectSizeWhenFilesAreAdded() throws Exception {
		int fileCount = 10;

		for (int i = 0; i < fileCount; i++) {
			mInMemoryFileAbstractionContainer.addFile(mock(FileAbstraction.class));
		}

		assertEquals("Size should be the size of the number of files", fileCount, mInMemoryFileAbstractionContainer.getSize());
	}
}
