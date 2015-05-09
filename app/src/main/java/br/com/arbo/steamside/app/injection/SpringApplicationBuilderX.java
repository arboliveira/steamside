package br.com.arbo.steamside.app.injection;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Preconditions;

public class SpringApplicationBuilderX {

	public SpringApplicationBuilderX()
	{
		this.builder = new SpringApplicationBuilder()
			.web(false)
			.headless(false);
	}

	public SpringApplication build()
	{
		registers.apply();
		return builder.build();
	}

	public <T> SpringApplicationBuilderX replaceWithConfiguration(
		Class<T> anInterface,
		Class< ? > configuration)
	{
		Register previous = registers.remove(anInterface);
		Optional.ofNullable(previous).orElseThrow(
			() -> new RuntimeException("Never registered: " + anInterface));

		return sourceConfiguration(anInterface, configuration);
	}

	public <T> SpringApplicationBuilderX sourceConfiguration(
		Class<T> anInterface,
		Class< ? > configuration)
	{
		Preconditions.checkArgument(
			configuration.isAnnotationPresent(Configuration.class),
			"Not a " + Configuration.class + ": " + configuration);

		registers.put(anInterface, new RegisterClass(configuration));
		return this;
	}

	public <T> SpringApplicationBuilderX sourceImplementor(
		Class<T> anInterface,
		Class< ? extends T> implementor)
	{
		registers.put(anInterface, new RegisterClass(implementor));
		return this;
	}

	public SpringApplicationBuilderX sources(Class< ? >... sources)
	{
		builder.sources(sources);
		return this;
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
			sources(implementor);
		}

		private final Class< ? > implementor;

	}

	class Registers {

		void apply()
		{
			for (final Register r : map.values())
				r.register();
		}

		void put(final Class< ? > key, final Register value)
		{
			checkDuplicate(key);
			map.put(key, value);
		}

		Register remove(Class< ? > key)
		{
			return map.remove(key);
		}

		private void checkDuplicate(final Class< ? > key)
		{
			Preconditions.checkState(
				!map.containsKey(key),
				"Registering twice: " + key);
		}

		private final HashMap<Class< ? >, Register> map = new HashMap<>(20);

	}

	private final SpringApplicationBuilder builder;

	private final Registers registers = new Registers();

}
