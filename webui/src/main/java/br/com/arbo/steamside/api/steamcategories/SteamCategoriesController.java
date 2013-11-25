package br.com.arbo.steamside.api.steamcategories;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.apps.Apps.CategoryWithAppsVisitor;
import br.com.arbo.steamside.apps.AppsHome;
import br.com.arbo.steamside.container.SharedConfigConsume;
import br.com.arbo.steamside.types.Category;

@Controller
@RequestMapping("steam-categories")
public class SteamCategoriesController {

	@RequestMapping("steam-categories.json")
	@ResponseBody
	public List<SteamCategoryDTO> steamCategories() {
		final List<SteamCategoryDTO> dto =
				new ArrayList<SteamCategoryDTO>();
		sharedconfig.data().apps()
				.accept(new CategoryWithAppsVisitor() {

					@Override
					public void visit(final Category each,
							final AppsHome itsApps) {
						dto.add(new SteamCategoryDTO(each));
					}
				});
		return dto;
	}

	@Inject
	private SharedConfigConsume sharedconfig;

}
