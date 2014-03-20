package br.com.arbo.steamside.api.steamcategories;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.library.Library;

@Controller
@RequestMapping("steam-categories")
public class SteamCategoriesController {

	@RequestMapping("steam-categories.json")
	@ResponseBody
	public List<SteamCategoryDTO> steamCategories()
	{
		return SteamCategoryDTO.valueOf(library.allSteamCategories());
	}

	@Inject
	private Library library;

}
