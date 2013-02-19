package br.com.arbo.steamside.webui.wicket.search;

import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

import br.com.arbo.steamside.webui.wicket.json.JsonResource;

public class SearchJson extends ResourceReference {

	public SearchJson() {
		super(SearchJson.class, "search-json");
	}

	@Override
	public IResource getResource() {
		return new JsonResource(new SearchJsonResource());
	}
}