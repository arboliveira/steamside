package br.com.arbo.steamside.api.steamcategories;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.arbo.steamside.steam.client.home.SteamClientHome;

@RestController
@RequestMapping("steam-categories")
public class SteamCategoriesController
{

	@RequestMapping("steam-categories.json")
	public List<SteamCategoryDTO> steamCategories()
	{
		return steamClientHome.categories().everySteamCategory()
			.map(SteamCategoryDTO::new)
			.collect(Collectors.toList());
	}

	@Inject
	private SteamClientHome steamClientHome;

}
