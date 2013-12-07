package io.mapping.apps.heurdupe.file;

public abstract class FileAbstractionCreator<T, U> {
	public abstract FileAbstraction<T, U> createFileAbstraction();
}
