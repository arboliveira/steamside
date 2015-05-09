package br.com.arbo.org.springframework.boot.builder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Preconditions;

public class Sources {

	private static void checkConfiguration(Class< ? > configuration)
	{
		Preconditions.checkArgument(
			configuration.isAnnotationPresent(Configuration.class),
			"Not a " + Configuration.class + ": " + configuration);
	}

	private static <T> void checkInterface(Class<T> anInterface)
	{
		Preconditions.checkArgument(
			anInterface.isInterface(),
			"Not an interface: " + anInterface);
	}

	public SpringApplicationBuilder apply(SpringApplicationBuilder builder)
	{
		Set<Class< ? >> all = new HashSet<>(
			classes.size() + implementors.size());
		all.addAll(classes);
		all.addAll(implementors.values());
		builder.sources(all.toArray(new Class< ? >[] {}));
		return builder;
	}

	public <T> Sources replaceImplementor(
		Class<T> anInterface,
		Class< ? extends T> implementor)
	{
		checkInterface(anInterface);
		remove(anInterface);
		return sourceImplementor(anInterface, implementor);
	}

	public <T> Sources replaceWithConfiguration(
		Class<T> anInterface,
		Class< ? > configuration)
	{
		checkInterface(anInterface);
		remove(anInterface);
		return sourceConfiguration(anInterface, configuration);
	}

	public <T> Sources sourceConfiguration(
		Class<T> anInterface,
		Class< ? > configuration)
	{
		checkInterface(anInterface);
		checkConfiguration(configuration);
		this.put(anInterface, configuration);
		return this;
	}

	public <T> Sources sourceImplementor(
		Class<T> anInterface,
		Class< ? extends T> implementor)
	{
		checkInterface(anInterface);
		this.put(anInterface, implementor);
		return this;
	}

	public Sources sources(Class< ? >... sources)
	{
		classes.addAll(Arrays.asList(sources));
		return this;
	}

	private void checkExists(Class< ? > key)
	{
		Preconditions.checkState(
			implementors.containsKey(key),
			"Never registered: " + key);
	}

	private void checkNotDuplicate(Class< ? > key)
	{
		Preconditions.checkState(
			!implementors.containsKey(key),
			"Registering twice: " + key);
	}

	private void put(Class< ? > key, Class< ? > value)
	{
		checkNotDuplicate(key);
		implementors.put(key, value);
	}

	private void remove(Class< ? > key)
	{
		checkExists(key);
		implementors.remove(key);
	}

	private final Map<Class< ? >, Class< ? >> implementors = new HashMap<>(20);

	private final Set<Class< ? >> classes = new HashSet<>(20);

}