package br.com.arbo.steamside.api.steamcategories;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.apps.AppsCollection;
import br.com.arbo.steamside.apps.AppsHome.CategoryWithAppsVisitor;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.types.Category;

@Controller
@RequestMapping("steam-categories")
public class SteamCategoriesController {

	@RequestMapping("steam-categories.json")
	@ResponseBody
	public List<SteamCategoryDTO> steamCategories() {
		final List<SteamCategoryDTO> dto =
				new ArrayList<SteamCategoryDTO>();
		library.accept(new CategoryWithAppsVisitor() {

			@Override
			public void visit(final Category each,
					final AppsCollection itsApps) {
				dto.add(new SteamCategoryDTO(each));
			}
		});
		return dto;
	}

	@Inject
	private Library library;

}
