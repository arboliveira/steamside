package br.com.arbo.org.picocontainer;

import org.eclipse.jdt.annotation.NonNull;
import org.picocontainer.MutablePicoContainer;

public class MutablePicoContainerX {

	public Object replaceComponent(
			final Object componentKey,
			final Object componentImplementationOrInstance) {
		final Object o = pico.removeComponent(componentKey);
		pico.addComponent(
				componentKey,
				componentImplementationOrInstance);
		return o;
	}

	private final MutablePicoContainer pico;

	@NonNull
	public <T> T getComponent(final Class<T> componentType) {
		final T component = pico.getComponent(componentType);
		if (component == null)
			throw new NullPointerException(
					"Forgot to register: " + componentType);
		return component;
	}

	public MutablePicoContainerX(final MutablePicoContainer pico) {
		this.pico = pico;
	}
}
