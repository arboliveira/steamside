package br.com.arbo.steamside.api.favorites;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.api.app.AppsDTO;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.favorites.Favorites;
import br.com.arbo.steamside.settings.Settings;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppCriteria;
import br.com.arbo.steamside.steam.client.library.Library;

@Controller
@RequestMapping("favorites")
public class FavoritesController {

	@RequestMapping("favorites.json")
	@ResponseBody
	public List<AppDTO> favorites()
	{
		return new AppsDTO(
				library.allApps(new AppCriteria() {

					{
						gamesOnly = settings.gamesOnly();
					}
				}).filter(favorites).map(App::appid),
				apiAppSettings.limit(), library, queries).jsonable();
	}

	@Inject
	@NonNull
	private Favorites favorites;
	@Inject
	private Library library;
	@Inject
	private TagsQueries queries;
	@Inject
	Settings settings;
	@Inject
	br.com.arbo.steamside.api.app.AppSettings apiAppSettings;
}
