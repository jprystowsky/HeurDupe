package io.mapping.apps.heurdupe.file;

public abstract class FileAbstractionCreator<T, U, V extends FileAbstraction<T, U>> {
	//public abstract FileAbstraction<T, U> createFileAbstraction();
	public abstract V createFileAbstraction();
}
