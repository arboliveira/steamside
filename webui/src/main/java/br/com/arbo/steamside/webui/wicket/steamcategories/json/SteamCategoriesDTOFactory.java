package br.com.arbo.steamside.webui.wicket.steamcategories.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.wicket.request.resource.IResource.Attributes;

import br.com.arbo.steamside.types.Category;
import br.com.arbo.steamside.webui.wicket.SharedConfigConsume;
import br.com.arbo.steamside.webui.wicket.json.Factory;

class SteamCategoriesDTOFactory implements Factory<List<SteamCategoryDTO>> {

	private final SharedConfigConsume sharedconfig;

	SteamCategoriesDTOFactory(
			final SharedConfigConsume sharedconfig) {
		this.sharedconfig = sharedconfig;
	}

	@Override
	public List<SteamCategoryDTO> produce(final Attributes a) {
		final Set<String> c = sharedconfig.data().apps().categories;
		final List<SteamCategoryDTO> dto =
				new ArrayList<SteamCategoryDTO>(c.size());
		for (final String one : c)
			dto.add(new SteamCategoryDTO(new Category(one)));
		return dto;
	}
}