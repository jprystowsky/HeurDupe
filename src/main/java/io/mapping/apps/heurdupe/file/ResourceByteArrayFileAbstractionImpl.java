package io.mapping.apps.heurdupe.file;

import io.mapping.apps.heurdupe.algorithm.FileTransformationAlgorithm;
import io.mapping.apps.heurdupe.algorithm.FileTransformationAlgorithmData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class ResourceByteArrayFileAbstractionImpl implements ResourceByteArrayFileAbstraction, Comparable<ResourceByteArrayFileAbstraction> {
	private Resource mResource;

	@Autowired private FileTransformationAlgorithm<byte[], FileTransformationAlgorithmData<byte[]>> mFileTransformationAlgorithm;

	@Override
	public FileTransformationAlgorithm<byte[], FileTransformationAlgorithmData<byte[]>> getFileTransformationAlgorithm() {
		return mFileTransformationAlgorithm;
	}

	@Override
	public void setFileTransformationAlgorithm(final FileTransformationAlgorithm<byte[], FileTransformationAlgorithmData<byte[]>> fileTransformationAlgorithm) {
		this.mFileTransformationAlgorithm = fileTransformationAlgorithm;
	}

	@Override
	public String getPath() throws IOException {
		return mResource.getFilename();
	}

	@Override
	public byte[] getAbstraction() throws IOException {
		byte[] representation = null;

		InputStream stream = mResource.getInputStream();

		try {
			mFileTransformationAlgorithm.setInputStream(stream);
			representation = mFileTransformationAlgorithm.transform().getAbstraction();
		} catch (IOException e) {
			throw e;
		} finally {
			stream.close();
			return representation;
		}
	}

	@Override
	public Resource getBacking() {
		return mResource;
	}

	@Override
	public void setBacking(final Resource backing) {
		this.mResource = backing;
	}

	@Override
	public int compareTo(final ResourceByteArrayFileAbstraction o) {
		return this.hashCode() - o.hashCode();
	}
}
