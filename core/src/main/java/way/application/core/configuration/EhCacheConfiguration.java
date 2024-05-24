package way.application.core.configuration;

import java.time.Duration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class EhCacheConfiguration {

	@Bean
	public CacheManager getCacheManager() {
		CachingProvider provider = Caching.getCachingProvider();
		CacheManager cacheManager = provider.getCacheManager();

		CacheConfiguration<String, Object> configuration = CacheConfigurationBuilder.newCacheConfigurationBuilder(
				String.class, Object.class, ResourcePoolsBuilder
					.heap(100)
					.offheap(10, MemoryUnit.MB))
			.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(10)))
			.build();

		javax.cache.configuration.Configuration<String, Object> cacheConfiguration
			= Eh107Configuration.fromEhcacheCacheConfiguration(configuration);

		cacheManager.createCache("schedules", cacheConfiguration);

		return cacheManager;
	}
}
