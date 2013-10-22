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

	public <T> Container addComponent(
			final Class< ? extends T> aClass,
			final T instance) {
		ctx.getBeanFactory().registerSingleton(aClass.getName(), instance);
		return this;
	}

	public <T> Container addComponent(
			@SuppressWarnings("unused") final Class<T> anInterface,
			final Class< ? extends T> implementor) {
		ctx.register(implementor);
		return this;
	}

	public Container addComponent(final Object instance) {
		this.addComponent(instance.getClass(), instance);
		return this;
	}

	public <T> T getComponent(final Class<T> aClass) {
		refresh();
		return ctx.getBean(aClass);
	}

	public <T> void replaceComponent(final Class<T> aClass, final T instance) {
		addComponent(aClass, instance);
	}

	public void start() {
		refresh();
		ctx.start();
	}

	public void stop() {
		ctx.stop();
	}

	private void refresh() {
		if (!ctx.isActive()) ctx.refresh();
	}

}
