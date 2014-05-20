package br.com.arbo.steamside.api.favorites;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.api.app.AppsDTO;
import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.AppCriteria;
import br.com.arbo.steamside.collections.CollectionsQueries;
import br.com.arbo.steamside.favorites.Favorites;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.settings.Settings;

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
				library, queries).jsonable();
	}

	@Inject
	@NonNull
	private Favorites favorites;
	@Inject
	private Library library;
	@Inject
	private CollectionsQueries queries;
	@Inject
	Settings settings;
}
