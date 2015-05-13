package br.com.arbo.org.springframework.boot.builder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import com.google.common.base.Preconditions;

public class Sources {

	private static <T> void checkInterface(Class<T> anInterface)
	{
		Preconditions.checkArgument(
			anInterface.isInterface(),
			"Not an interface: " + anInterface);
	}

	public <T> Sources registerSingleton(
		Class<T> anInterface,
		T singleton)
	{
		checkInterface(anInterface);
		this.put(anInterface, new Singleton(singleton));
		return this;
	}

	public <T> Sources registerSingleton(T singleton)
	{
		this.put(singleton.getClass(), new Singleton(singleton));
		return this;
	}

	public Stream<Object> registerSingleton_args()
	{
		return implementations.values().stream()
			.map(Source::getSingletonToRegister)
			.filter(Optional::isPresent)
			.map(Optional::get);
	}

	public <T> Sources replaceWithImplementor(
		Class<T> anInterface,
		Class< ? extends T> implementor)
	{
		checkInterface(anInterface);
		remove(anInterface);
		return sourceImplementor(anInterface, implementor);
	}

	public <T> Sources replaceWithSingleton(
		Class<T> anInterface,
		T singleton)
	{
		checkInterface(anInterface);
		remove(anInterface);
		return registerSingleton(anInterface, singleton);
	}

	public <T> Sources sourceImplementor(
		Class<T> anInterface,
		Class< ? extends T> implementor)
	{
		checkInterface(anInterface);
		this.put(anInterface, new Implementor(implementor));
		return this;
	}

	public Sources sources(Class< ? >... sources)
	{
		classes.addAll(Arrays.asList(sources));
		return this;
	}

	public Class< ? >[] sources_arg()
	{
		int size = classes.size() + implementations.size();
		Set<Class< ? >> all = new HashSet<>(size);
		all.addAll(classes);
		implementorsToSource().forEach(all::add);
		return all.toArray(new Class< ? >[all.size()]);
	}

	private void checkExists(Class< ? > key)
	{
		Preconditions.checkState(
			implementations.containsKey(key),
			"Never registered: " + key);
	}

	private void checkNotDuplicate(Class< ? > key)
	{
		Preconditions.checkState(
			!implementations.containsKey(key),
			"Registering twice: " + key);
	}

	private Stream<Class< ? >> implementorsToSource()
	{
		return implementations.values().stream()
			.map(Source::getClassToSource)
			.filter(Optional::isPresent)
			.map(Optional::get);
	}

	private void put(Class< ? > key, Source source)
	{
		checkNotDuplicate(key);
		implementations.put(key, source);
	}

	private void remove(Class< ? > key)
	{
		checkExists(key);
		implementations.remove(key);
	}

	static class Implementor implements Source {

		Implementor(Class< ? > classToSource)
		{
			this.classToSource = classToSource;
		}

		@Override
		public Optional<Class< ? >> getClassToSource()
		{
			return Optional.of(this.classToSource);
		}

		@Override
		public Optional<Object> getSingletonToRegister()
		{
			return Optional.empty();
		}

		private Class< ? > classToSource;

	}

	static class Singleton implements Source {

		Singleton(Object singleton)
		{
			this.singleton = singleton;
		}

		@Override
		public Optional<Class< ? >> getClassToSource()
		{
			return Optional.empty();
		}

		@Override
		public Optional<Object> getSingletonToRegister()
		{
			return Optional.of(singleton);
		}

		private Object singleton;

	}

	interface Source {

		Optional<Class< ? >> getClassToSource();

		Optional<Object> getSingletonToRegister();
	}

	private final Map<Class< ? >, Source> implementations = new HashMap<>(20);

	private final Set<Class< ? >> classes = new HashSet<>(20);

}