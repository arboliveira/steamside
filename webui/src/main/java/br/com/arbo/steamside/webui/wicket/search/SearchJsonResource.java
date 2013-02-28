package br.com.arbo.steamside.webui.wicket.search;

import java.util.List;

import org.apache.wicket.request.resource.IResource.Attributes;

import br.com.arbo.steamside.search.Search;
import br.com.arbo.steamside.webui.appdto.AppCollectionDTO;
import br.com.arbo.steamside.webui.appdto.AppDTO;
import br.com.arbo.steamside.webui.wicket.json.JsonResource;

class SearchJsonResource implements JsonResource.Needs<List<AppDTO>> {

	@Override
	public List<AppDTO> fetchValue(final Attributes a) {
		final String query = a.getParameters().get("query").toString();
		final AppCollectionDTO results = Search.search(query);
		final List<AppDTO> value = results.apps;
		return value;
	}
}