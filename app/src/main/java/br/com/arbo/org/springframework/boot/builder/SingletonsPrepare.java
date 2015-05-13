package br.com.arbo.org.springframework.boot.builder;

import java.util.stream.Stream;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;

class SingletonsPrepare
implements ApplicationListener<ApplicationPreparedEvent> {

	SingletonsPrepare(Stream<Object> singletons)
	{
		this.singletons = singletons;
	}

	@Override
	public void onApplicationEvent(ApplicationPreparedEvent event)
	{
		ConfigurableListableBeanFactory beanFactory =
			event.getApplicationContext().getBeanFactory();

		singletons.forEach(
			bean -> beanFactory.registerSingleton(
				bean.getClass().getName(), bean));
	}

	private final Stream<Object> singletons;

}
