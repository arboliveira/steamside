package br.com.arbo.steamside.app.embedded;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;

@Component
public class BannerPrepare
	implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event)
	{
		MutablePropertySources propertySources =
			event.getEnvironment().getPropertySources();
		Map<String, Object> map = new HashMap<>();
		map.put("spring.banner.location", "banner-steamside.txt");
		propertySources.addFirst(
			new MapPropertySource(this.getClass().getName(), map));
	}

}
