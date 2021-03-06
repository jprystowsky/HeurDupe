package io.mapping.apps.heurdupe.container;

import io.mapping.apps.heurdupe.file.FileAbstraction;

import java.util.Iterator;

public interface FileAbstractionContainer<TBacking,TRepresentation> {
	public void addFile(FileAbstraction<TBacking,TRepresentation> fileAbstraction);
	public void removeFile(FileAbstraction<TBacking,TRepresentation> fileAbstraction);
	public boolean containsFile(FileAbstraction<TBacking,TRepresentation> fileAbstraction);
	public Iterator<FileAbstraction<TBacking,TRepresentation>> getIterator();
	public long getSize();
}
