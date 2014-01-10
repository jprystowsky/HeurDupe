package io.mapping.apps.heurdupe;

import io.mapping.apps.heurdupe.file.FileAbstractionCreator;
import io.mapping.apps.heurdupe.file.ResourceByteArrayFileAbstraction;
import io.mapping.apps.heurdupe.file.ResourceByteArrayFileAbstractionImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;

@Configuration
@ComponentScan
public class ApplicationConfiguration {

	@Bean
	@Scope(value = "prototype")
	@SuppressWarnings("unchecked")
	public <T extends ResourceByteArrayFileAbstraction> T springResourceFileAbstraction() {
		return (T) new ResourceByteArrayFileAbstractionImpl();
	}

	@Bean
	public FileAbstractionCreator<Resource, byte[], ResourceByteArrayFileAbstraction> fileAbstractionCreator() {
		return new FileAbstractionCreator<Resource, byte[], ResourceByteArrayFileAbstraction>() {
			@Override
			public ResourceByteArrayFileAbstraction createFileAbstraction() {
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
