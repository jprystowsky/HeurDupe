package io.mapping.apps.heurdupe.file;

import java.util.Iterator;

public interface FileAbstractionContainer {
	public void addFile(FileAbstraction fileAbstraction);
	public void removeFile(FileAbstraction fileAbstraction);
	public boolean containsFile(FileAbstraction fileAbstraction);
	public Iterator<FileAbstraction> getIterator();
	public long getSize();
}
