package io.mapping.apps.heurdupe.properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class SpringStaticPropertiesProviderTest {
	@Configuration
	static class ContextConfiguration {
		@Bean
		public PropertiesProvider propertiesProvider() {
			return new SpringStaticPropertiesProvider();
		}
	}

	private final String sampleSizeProperty = "sample.size";
	@Autowired private PropertiesProvider mPropertiesProvider;

	@Before
	public void setUp() throws Exception {
		assertNotNull("Properties provider must not be null", mPropertiesProvider);
	}

	@Test
	public void shouldNeverReturnNullProperties() throws Exception {
		assertNotNull("Properties should not be null", mPropertiesProvider.getProperties());
	}

	@Test
	public void shouldReturnPropertiesWithProperties() throws Exception {
		assertTrue("Properties file should contain some properties", mPropertiesProvider.getProperties().size() > 0);
	}

	@Test
	public void shouldProvideSampleSizeProperty() throws Exception {
		assertTrue("Properties should include 'sample.size'", mPropertiesProvider.getProperties().containsKey(sampleSizeProperty));
	}

	@Test
	public void shouldProvidePositiveIntegralSampleSizeProperty() throws Exception {
		assertTrue("Property 'sample.size' should be > 0", Integer.valueOf(mPropertiesProvider.getProperties().getProperty(sampleSizeProperty)) > 0);

	}
}
