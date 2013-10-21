package br.com.arbo.steamside.spring;

import java.util.ArrayList;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;
import br.com.arbo.steamside.data.collections.CollectionHomeXmlFile;

public class SteamsideApplicationContext
		extends AnnotationConfigWebApplicationContext {

	private final MutablePicoContainerX container;

	public SteamsideApplicationContext(final MutablePicoContainerX container) {
		this.container = container;
		final ArrayList<Class< ? >> annotatedClasses =
				new ArrayList<Class< ? >>(2);
		annotatedClasses.add(WebConfig.class);
		annotatedClasses.add(CollectionHomeXmlFile.class);
		final Class< ? >[] array = annotatedClasses.toArray(
				new Class< ? >[] {});
		this.register(array);
	}

	public MutablePicoContainerX getContainer() {
		return container;
	}
}