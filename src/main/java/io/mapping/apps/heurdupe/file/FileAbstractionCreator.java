package io.mapping.apps.heurdupe.file;

public abstract class FileAbstractionCreator<TBacking, TRepresentation, TAbstraction extends FileAbstraction<TBacking, TRepresentation>> {
	public abstract TAbstraction createFileAbstraction();
}
