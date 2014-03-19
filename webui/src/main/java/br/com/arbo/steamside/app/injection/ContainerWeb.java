package br.com.arbo.steamside.app.injection;

import java.util.HashMap;

import org.eclipse.jdt.annotation.NonNull;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class ContainerWeb {

	public ContainerWeb(final AnnotationConfigWebApplicationContext ctx) {
		this.ctx = ctx;
	}

	public ContainerWeb addComponent(final Class< ? > aClass) {
		guardRegisterTwice(aClass);
		annotatedClasses.put(aClass, aClass);
		return this;
	}

	public <T> ContainerWeb addComponent(
			final Class<T> anInterface,
			final Class< ? extends T> implementor) {
		guardRegisterTwice(anInterface);
		annotatedClasses.put(anInterface, implementor);
		return this;
	}

	public void flush() {
		final Class< ? >[] array =
				annotatedClasses.values().toArray(new Class< ? >[] {});
		ctx.register(array);
	}

	@NonNull
	public <T> T getComponent(final Class<T> aClass) {
		final T component = ctx.getBean(aClass);
		if (component == null)
			throw new NullPointerException(
					"Forgot to register: " + aClass);
		return component;
	}

	public <T> void replaceComponent(
			final Class<T> anInterface,
			final Class< ? extends T> implementor) {
		final Class< ? > previous = annotatedClasses.remove(anInterface);
		if (previous == null)
			throw new RuntimeException("Never registered: " + anInterface);
		addComponent(anInterface, implementor);
	}

	private void guardRegisterTwice(final Class< ? > key) {
		Class< ? > already = annotatedClasses.get(key);
		if (already != null)
			throw new RuntimeException("Registering twice: " + key
					+ "(already " + already + ")");
	}

	private final AnnotationConfigWebApplicationContext ctx;
	private final HashMap<Class< ? >, Class< ? >> annotatedClasses =
			new HashMap<Class< ? >, Class< ? >>(20);
}
