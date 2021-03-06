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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
@Scope("prototype")
public class RecursiveFileSystemResourceByteArrayFileAbstractionProvider<TAbstraction extends ResourceByteArrayFileAbstraction> implements ResourceByteArrayFileAbstractionProvider<TAbstraction> {
	private NavigableSet<TAbstraction> mFiles = new TreeSet<>();
	@Autowired private FileAbstractionCreator<Resource, byte[], ResourceByteArrayFileAbstraction> mFileAbstractionCreator;
	private Logger mLogger = LoggerFactory.getLogger(RecursiveFileSystemResourceByteArrayFileAbstractionProvider.class);

	public FileAbstractionCreator<Resource, byte[], ResourceByteArrayFileAbstraction> getFileAbstractionCreator() {
		return mFileAbstractionCreator;
	}

	public void setFileAbstractionCreator(FileAbstractionCreator<Resource, byte[], ResourceByteArrayFileAbstraction> fileAbstractionCreator) {
		this.mFileAbstractionCreator = fileAbstractionCreator;
	}

	@Override
	@SuppressWarnings("unchecked")
	public NavigableSet<TAbstraction> getFileAbstractions(final TAbstraction startFile) throws IOException {
		if (startFile == null) {
			return mFiles;
		}

		if (startFile.getBacking().getFile().isFile()) {
			mFiles.add(startFile);

			return mFiles;
		}

		final ResourceLoader resourceLoader = new FileSystemResourceLoader();

		final Queue<File> fileQueue = new LinkedList<>(Arrays.asList(getChildFiles(startFile.getBacking().getFile())));

		File thisFile = null;

		while ((thisFile = fileQueue.poll()) != null) {
			if (thisFile.isFile()) {
				final TAbstraction fileAbstraction = (TAbstraction) mFileAbstractionCreator.createFileAbstraction();
				mLogger.info(String.format("Trying to read in: '%s'", "file:" + thisFile.getAbsolutePath()));
				fileAbstraction.setBacking(resourceLoader.getResource("file:" + thisFile.getAbsolutePath()));
				mFiles.add(fileAbstraction);
			} else if (thisFile.isDirectory()) {
				fileQueue.addAll(Arrays.asList(getChildFiles(thisFile)));
			}
		}

		return mFiles;
	}

	private File[] getChildFiles(File startFile) {
		if (!startFile.isDirectory()) {
			return null;
		} else {
			return startFile.listFiles();
		}
	}
}
