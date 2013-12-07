package io.mapping.apps.heurdupe.file;

import io.mapping.apps.heurdupe.file.FileAbstraction;
import io.mapping.apps.heurdupe.file.FileAbstractionContainer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class InMemoryFileAbstractionContainer implements FileAbstractionContainer {
	private final Set<FileAbstraction> mFileAbstractionSet = new HashSet<FileAbstraction>();

	@Override
	public void addFile(FileAbstraction fileAbstraction) {
		mFileAbstractionSet.add(fileAbstraction);
	}

	@Override
	public void removeFile(FileAbstraction fileAbstraction) {
		mFileAbstractionSet.remove(fileAbstraction);
	}

	@Override
	public boolean containsFile(FileAbstraction fileAbstraction) {
		return mFileAbstractionSet.contains(fileAbstraction);
	}

	@Override
	public Iterator<FileAbstraction> getIterator() {
		return mFileAbstractionSet.iterator();
	}

	@Override
	public long getSize() {
		return (long) mFileAbstractionSet.size();
	}
}
