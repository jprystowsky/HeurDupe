package io.mapping.apps.heurdupe;

import io.mapping.apps.heurdupe.file.FileAbstraction;
import io.mapping.apps.heurdupe.file.FileAbstractionCreator;
import io.mapping.apps.heurdupe.file.ResourceByteArrayFileAbstractionImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;

@Configuration
@ComponentScan
public class ApplicationConfiguration {

	@Bean
	@Scope(value = "prototype")
	public FileAbstraction<Resource, byte[]> springResourceFileAbstraction() {
		return new ResourceByteArrayFileAbstractionImpl();
	}

	@Bean
	public FileAbstractionCreator<Resource, byte[]> fileAbstractionCreator() {
		return new FileAbstractionCreator<Resource, byte[]>() {
			@Override
			public FileAbstraction<Resource, byte[]> createFileAbstraction() {
				return springResourceFileAbstraction();
			}
		};
	}

	public static void main(String... args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
		Application application = context.getBean(Application.class);
		application.run();
	}
}
