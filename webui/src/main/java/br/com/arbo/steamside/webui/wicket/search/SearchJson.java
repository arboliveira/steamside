package br.com.arbo.steamside.webui.wicket.search;

import java.util.List;

import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.IResource.Attributes;
import org.apache.wicket.request.resource.ResourceReference;

import br.com.arbo.steamside.webui.appdto.AppCollectionDTO;
import br.com.arbo.steamside.webui.appdto.AppDTO;
import br.com.arbo.steamside.webui.wicket.json.Factory;
import br.com.arbo.steamside.webui.wicket.json.JsonResource;

public class SearchJson extends ResourceReference {

	private Factory<List<AppDTO>> factory;

	public SearchJson() {
		super(SearchJson.class, "search-json");

		this.factory = new Factory<List<AppDTO>>() {

			@Override
			public List<AppDTO> produce(final Attributes a) {
				return fetch(a);
			}
		};
	}

	@Override
	public IResource getResource() {
		return new JsonResource(factory);
	}

	static List<AppDTO> fetch(final Attributes a) {
		final String query =
				a.getParameters()
						.get(Params.PARAM_query).toString();
		final AppCollectionDTO dto = Search.search(query);
		return dto.apps;
	}
}