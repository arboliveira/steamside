package br.com.arbo.steamside.webui.wicket.steamcategories.json;

import java.util.List;

import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

import br.com.arbo.steamside.webui.wicket.SharedConfigConsume;
import br.com.arbo.steamside.webui.wicket.json.Factory;
import br.com.arbo.steamside.webui.wicket.json.JsonResource;

public class SteamCategoriesJson extends ResourceReference {

	private final Factory<List<SteamCategoryDTO>> fetch;

	public SteamCategoriesJson(final SharedConfigConsume sharedconfig) {
		super(SteamCategoriesJson.class, "steam-categories-json");
		fetch = new SteamCategoriesDTOFactory(sharedconfig);
	}

	@Override
	public IResource getResource() {
		return new JsonResource(fetch);
	}
}