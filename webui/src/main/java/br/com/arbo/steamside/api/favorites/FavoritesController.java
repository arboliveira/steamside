package br.com.arbo.steamside.api.favorites;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.favorites.FavoritesOfUser;
import br.com.arbo.steamside.settings.Settings;
import br.com.arbo.steamside.steam.client.library.Library;

@Controller
@RequestMapping("favorites")
public class FavoritesController {

	@RequestMapping("favorites.json")
	@ResponseBody
	public List<AppDTO> favorites()
	{
		return new FavoritesController_favorites_json(
				ofUser, library, settings,
				apiAppSettings.limit(), queries).jsonable();
	}

	@Inject
	@NonNull
	private FavoritesOfUser ofUser;
	@Inject
	private Library library;
	@Inject
	private TagsQueries queries;
	@Inject
	Settings settings;
	@Inject
	br.com.arbo.steamside.api.app.AppSettings apiAppSettings;
}
