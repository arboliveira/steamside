package br.com.arbo.steamside.app.injection;

import java.util.HashMap;

import org.eclipse.jdt.annotation.NonNull;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Container {

	public Container(final AnnotationConfigApplicationContext ctx) {
		this.ctx = ctx;
	}

	public <T> Container addComponent(
			final Class<T> anInterface,
			final Class< ? extends T> implementor) {
		registers_put(anInterface, new RegisterClass(implementor));
		return this;
	}

	public Container addComponent(final Class< ? > aClass) {
		registerClass(aClass);
		return this;
	}

	public Container addComponent(final Object instance) {
		this.addComponent(instance.getClass(), instance);
		return this;
	}

	public <T> void replaceComponent(
			final Class<T> anInterface,
			final T instance) {
		final Register previous = registers.remove(anInterface);
		if (previous == null)
			throw new RuntimeException("Never registered: " + anInterface);
		addComponent(anInterface, instance);
	}

	public <T> Container addComponent(
			final Class< ? extends T> aClass,
			final T instance) {
		registers_put(aClass, new RegisterInstance(aClass, instance));
		return this;
	}

	void registerClass(final Class< ? > aClass) {
		ctx.register(aClass);
	}

	void registerSingleton(final Class< ? > aClass, final Object instance) {
		ctx.getBeanFactory().registerSingleton(aClass.getName(), instance);
	}

	@NonNull
	public <T> T getComponent(final Class<T> aClass) {
		//
		flush();
		//
		final T component = ctx.getBean(aClass);
		if (component == null)
			throw new NullPointerException(
					"Forgot to register: " + aClass);
		return component;
	}

	public void start() {
		flush();
		ctx.start();
	}

	public void stop() {
		ctx.stop();
	}

	private void flush() {
		if (ctx.isActive()) return;
		registers_flush();
		ctx.refresh();
	}

	private void registers_flush() {
		for (final Register r : registers.values())
			r.register();
	}

	private final AnnotationConfigApplicationContext ctx;

	interface Register {

		void register();

	}

	class RegisterClass implements Register {

		private final Class< ? > implementor;

		public RegisterClass(final Class< ? > implementor) {
			this.implementor = implementor;
		}

		@Override
		public void register() {
			registerClass(implementor);
		}

	}

	class RegisterInstance implements Register {

		private final Class< ? > aClass;
		private final Object instance;

		public RegisterInstance(
				final Class< ? > aClass,
				final Object instance) {
			this.aClass = aClass;
			this.instance = instance;
		}

		@Override
		public void register() {
			registerSingleton(aClass, instance);
		}

	}

	private void registers_put(final Class< ? > key, final Register value) {
		guardRegisterTwice(key);
		registers.put(key, value);
	}

	private void guardRegisterTwice(final Class< ? > key) {
		if (registers.containsKey(key))
			throw new RuntimeException("Registering twice: " + key);
	}

	private final HashMap<Class< ? >, Register> registers =
			new HashMap<Class< ? >, Register>(20);

}
