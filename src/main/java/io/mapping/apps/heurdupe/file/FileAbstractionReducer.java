package io.mapping.apps.heurdupe.file;

public interface FileAbstractionReducer<T, U> {
	public U reduceAbstraction(T abstraction);
}
