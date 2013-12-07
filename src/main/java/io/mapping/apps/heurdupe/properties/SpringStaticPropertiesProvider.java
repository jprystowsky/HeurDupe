package io.mapping.apps.heurdupe.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@Component
public class SpringStaticPropertiesProvider implements PropertiesProvider {
	private static final String CONFIG_FILE_PATH = "config.properties";
	private static final Logger sLogger = LoggerFactory.getLogger(PropertiesProvider.class);

	private static Properties sProperties;

	@Override
	public Properties getProperties() {
		if (sProperties == null) {
			synchronized (this) {
				if (sProperties == null) {
					sLogger.trace(String.format("Lazily loading properties file '%s'", CONFIG_FILE_PATH));

					Resource propResource = new ClassPathResource(CONFIG_FILE_PATH);

					try {
						sProperties = PropertiesLoaderUtils.loadProperties(propResource);
					} catch (IOException e) {
						sLogger.error(String.format("Unable to load properties file '%s'", CONFIG_FILE_PATH));
					}
				}
			}
		}

		return sProperties;
	}
}
