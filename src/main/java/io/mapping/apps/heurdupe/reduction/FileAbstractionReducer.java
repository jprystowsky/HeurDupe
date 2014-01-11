package io.mapping.apps.heurdupe.reduction;

public interface FileAbstractionReducer<TRepresentation, TReduction> {
	public TReduction reduceAbstraction(TRepresentation abstraction);
}
