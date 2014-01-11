package io.mapping.apps.heurdupe.reduction;

public interface FileAbstractionReducer<T, U> {
	public U reduceAbstraction(T abstraction);
}
