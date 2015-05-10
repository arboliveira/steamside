package br.com.arbo.steamside.api.steamcategories;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.arbo.steamside.steam.client.library.Library;

@RestController
@RequestMapping("steam-categories")
public class SteamCategoriesController {

	@RequestMapping("steam-categories.json")
	public List<SteamCategoryDTO> steamCategories()
	{
		return SteamCategoryDTO.valueOf(library.allSteamCategories());
	}

	@Inject
	private Library library;

}
