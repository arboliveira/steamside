package br.com.arbo.steamside.app.injection;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Container {

	private final AnnotationConfigApplicationContext ctx;

	public Container() {
		ctx = new AnnotationConfigApplicationContext();
	}

	public Container addComponent(final Class< ? > aClass) {
		ctx.register(aClass);
		return this;
	}

	public <T> Container addComponent(final Class<T> aClass, final T instance) {
		ctx.getBeanFactory().registerSingleton(aClass.getName(), instance);
		return this;
	}

	public <T> T getComponent(final Class<T> aClass) {
		if (!ctx.isActive()) ctx.refresh();
		return ctx.getBean(aClass);
	}
}
