package br.com.arbo.steamside.webui.wicket.steamcategories.json;

import java.util.ArrayList;
import java.util.List;

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
		final List<SteamCategoryDTO> dto =
				new ArrayList<SteamCategoryDTO>();
		sharedconfig.data().apps()
				.accept(new Category.Visitor() {

					@Override
					public void visit(final Category each) {
						dto.add(new SteamCategoryDTO(each));
					}
				});
		return dto;
	}
}