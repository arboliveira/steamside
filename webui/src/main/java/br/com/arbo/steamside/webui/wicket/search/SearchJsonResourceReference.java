package br.com.arbo.steamside.webui.wicket.search;

import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

public class SearchJsonResourceReference extends ResourceReference {

	public SearchJsonResourceReference() {
		super(SearchJsonResourceReference.class, "search-json-resource");
	}

	@Override
	public IResource getResource() {
		return new SearchJsonResource();
	}
}