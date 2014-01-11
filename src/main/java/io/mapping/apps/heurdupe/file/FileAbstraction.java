package io.mapping.apps.heurdupe.file;

import io.mapping.apps.heurdupe.transformation.FileTransformationAlgorithm;
import io.mapping.apps.heurdupe.transformation.FileTransformationAlgorithmData;

import java.io.IOException;

public interface FileAbstraction<TBacking, TRepresentation> {
	public FileTransformationAlgorithm<TRepresentation, FileTransformationAlgorithmData<TRepresentation>> getFileTransformationAlgorithm();
	public void setFileTransformationAlgorithm(FileTransformationAlgorithm<TRepresentation, FileTransformationAlgorithmData<TRepresentation>> fileTransformationAlgorithm);
	public String getPath() throws IOException;
	public TRepresentation getAbstraction() throws IOException;
	public TBacking getBacking();
	public void setBacking(TBacking backing);
}
