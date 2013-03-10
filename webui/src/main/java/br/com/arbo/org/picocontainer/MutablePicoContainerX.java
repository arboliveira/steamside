package br.com.arbo.org.picocontainer;

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

	public MutablePicoContainerX(final MutablePicoContainer pico) {
		this.pico = pico;
	}
}
