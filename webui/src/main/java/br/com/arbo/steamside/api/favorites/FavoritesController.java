package br.com.arbo.steamside.api.favorites;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.api.app.AppSettings;
import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.favorites.FavoritesOfUser;
import br.com.arbo.steamside.settings.Settings;
import br.com.arbo.steamside.steam.client.library.Library;
import br.com.arbo.steamside.types.CollectionName;

@RestController
@RequestMapping("favorites")
public class FavoritesController {

	@RequestMapping("favorites.json")
	public List<AppDTO> favorites()
	{
		return new FavoritesController_favorites_json(
			ofUser, library, settings,
			apiAppSettings.limit(), tags).jsonable();
	}

	@RequestMapping(value = "set/{name}")
	public void set(
		@PathVariable String name) throws NotFound
	{
		CollectionsData c = tags.collections();
		CollectionI in = c.find(new CollectionName(name));
		c.favorite(in);
	}

	@Inject
	private FavoritesOfUser ofUser;
	@Inject
	private Library library;
	@Inject
	private TagsData tags;
	@Inject
	Settings settings;
	@Inject
	AppSettings apiAppSettings;
}
