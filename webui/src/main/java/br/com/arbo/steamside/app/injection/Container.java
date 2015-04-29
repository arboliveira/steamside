package br.com.arbo.steamside.app.injection;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Container {

	public Container(final AnnotationConfigApplicationContext ctx)
	{
		this.ctx = ctx;
	}

	public <T> Container addComponent(
			final Class< ? extends T> aClass,
			final T instance)
	{
		registers_put(aClass, new RegisterInstance(aClass, instance));
		return this;
	}

	public Container addComponent(final Class< ? > aClass)
	{
		registerClass(aClass);
		return this;
	}

	public <T> Container addComponent(
			final Class<T> anInterface,
			final Class< ? extends T> implementor)
	{
		registers_put(anInterface, new RegisterClass(implementor));
		return this;
	}

	public Container addComponent(final Object instance)
	{
		this.addComponent(instance.getClass(), instance);
		return this;
	}

	public <T> T getComponent(final Class<T> aClass)
	{
		//
		flush();
		//
		final T component = ctx.getBean(aClass);
		return Objects.requireNonNull(component,
				"Forgot to register: " + aClass);
	}

	public <T> void replaceComponent(
			final Class<T> anInterface,
			final T instance)
	{
		final Register previous = registers.remove(anInterface);
		Optional.ofNullable(previous).orElseThrow(
				() -> new RuntimeException("Never registered: " + anInterface));
		addComponent(anInterface, instance);
	}

	public void start()
	{
		flush();
		ctx.start();
	}

	public void stop()
	{
		ctx.stop();
	}

	void registerClass(final Class< ? > aClass)
	{
		ctx.register(aClass);
	}

	void registerSingleton(final Class< ? > aClass, final Object instance)
	{
		ctx.getBeanFactory().registerSingleton(aClass.getName(), instance);
	}

	private void flush()
	{
		if (ctx.isActive()) return;
		registers_flush();
		ctx.refresh();
	}

	private void guardRegisterTwice(final Class< ? > key)
	{
		if (registers.containsKey(key))
			throw new RuntimeException("Registering twice: " + key);
	}

	private void registers_flush()
	{
		for (final Register r : registers.values())
			r.register();
	}

	private void registers_put(final Class< ? > key, final Register value)
	{
		guardRegisterTwice(key);
		registers.put(key, value);
	}

	interface Register {

		void register();

	}

	class RegisterClass implements Register {

		public RegisterClass(final Class< ? > implementor)
		{
			this.implementor = implementor;
		}

		@Override
		public void register()
		{
			registerClass(implementor);
		}

		private final Class< ? > implementor;

	}

	class RegisterInstance implements Register {

		public RegisterInstance(
				final Class< ? > aClass,
				final Object instance)
		{
			this.aClass = aClass;
			this.instance = instance;
		}

		@Override
		public void register()
		{
			registerSingleton(aClass, instance);
		}

		private final Class< ? > aClass;

		private final Object instance;

	}

	private final AnnotationConfigApplicationContext ctx;

	private final HashMap<Class< ? >, Register> registers = new HashMap<Class< ? >, Register>(
			20);

}
