package io.mapping.apps.heurdupe;

import io.mapping.apps.heurdupe.file.FileAbstraction;
import io.mapping.apps.heurdupe.file.FileAbstractionCreator;
import io.mapping.apps.heurdupe.file.FileAbstractionReducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Application {
	@Autowired private ResourceLoader mResourceLoader;
	@Autowired private FileAbstractionCreator<Resource, byte[]> mFileAbstractionCreator;
	@Autowired private FileAbstractionReducer<byte[], Integer> mFileAbstractionReducer;
	private Logger mLogger = LoggerFactory.getLogger(Application.class);

	public void run() {
		FileAbstraction<Resource, byte[]> fileAbstraction = mFileAbstractionCreator.createFileAbstraction();
		fileAbstraction.setBacking(mResourceLoader.getResource("config.properties"));

		try {
			String file = fileAbstraction.getPath();
			int result = mFileAbstractionReducer.reduceAbstraction(fileAbstraction.getAbstraction());
			mLogger.info(String.format("For '%s,' '%d.'", file, result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
