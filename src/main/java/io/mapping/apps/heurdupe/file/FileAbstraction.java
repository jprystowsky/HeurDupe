package io.mapping.apps.heurdupe.file;

import io.mapping.apps.heurdupe.algorithm.FileTransformationAlgorithm;
import io.mapping.apps.heurdupe.algorithm.FileTransformationAlgorithmData;

import java.io.IOException;

public interface FileAbstraction<T, U> {
	public FileTransformationAlgorithm<U, FileTransformationAlgorithmData<U>> getFileTransformationAlgorithm();
	public void setFileTransformationAlgorithm(FileTransformationAlgorithm<U, FileTransformationAlgorithmData<U>> fileTransformationAlgorithm);
	public String getPath() throws IOException;
	public U getAbstraction() throws IOException;
	public T getBacking();
	public void setBacking(T backing);
}
