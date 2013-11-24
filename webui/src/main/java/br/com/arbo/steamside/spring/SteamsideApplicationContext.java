package br.com.arbo.steamside.spring;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import br.com.arbo.steamside.app.injection.ContainerWeb;
import br.com.arbo.steamside.container.ContainerFactory;
import br.com.arbo.steamside.data.collections.CollectionHomeXmlFile;

public class SteamsideApplicationContext
		extends AnnotationConfigWebApplicationContext {

	private final ContainerWeb container;

	public SteamsideApplicationContext() {
		this.container = ContainerFactory.newContainer(this);
		this.container.addComponent(WebConfig.class);
		this.container.addComponent(CollectionHomeXmlFile.class);
	}

	public ContainerWeb getContainer() {
		return container;
	}
}