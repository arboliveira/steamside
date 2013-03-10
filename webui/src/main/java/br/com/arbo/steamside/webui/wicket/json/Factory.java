package br.com.arbo.steamside.webui.wicket.json;

import org.apache.wicket.request.resource.IResource.Attributes;

public interface Factory<T> {

	T produce(Attributes a);

}