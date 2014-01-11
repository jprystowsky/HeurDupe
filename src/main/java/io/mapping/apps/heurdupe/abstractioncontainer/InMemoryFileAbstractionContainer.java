package io.mapping.apps.heurdupe.abstractioncontainer;

import io.mapping.apps.heurdupe.file.FileAbstraction;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class InMemoryFileAbstractionContainer<TBacking,TRepresentation> implements FileAbstractionContainer<TBacking,TRepresentation> {
	private final Set<FileAbstraction<TBacking,TRepresentation>> mFileAbstractionSet = new HashSet<>();

	@Override
	public void addFile(FileAbstraction<TBacking,TRepresentation> fileAbstraction) {
		mFileAbstractionSet.add(fileAbstraction);
	}

	@Override
	public void removeFile(FileAbstraction<TBacking,TRepresentation> fileAbstraction) {
		mFileAbstractionSet.remove(fileAbstraction);
	}

	@Override
	public boolean containsFile(FileAbstraction<TBacking,TRepresentation> fileAbstraction) {
		return mFileAbstractionSet.contains(fileAbstraction);
	}

	@Override
	public Iterator<FileAbstraction<TBacking,TRepresentation>> getIterator() {
		return mFileAbstractionSet.iterator();
	}

	@Override
	public long getSize() {
		return (long) mFileAbstractionSet.size();
	}
}
