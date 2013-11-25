package br.com.arbo.steamside.api.favorites;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.favorites.Favorites;
import br.com.arbo.steamside.json.app.AppDTO;
import br.com.arbo.steamside.json.appcollection.AppCollectionDTO;
import br.com.arbo.steamside.json.appcollection.ToDTO;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;

@Controller
@RequestMapping("favorites")
public class FavoritesController {

	@RequestMapping("favorites.json")
	@ResponseBody
	public List<AppDTO> favorites() {
		@SuppressWarnings("null")
		final List<App> list = from.query(favorites);
		final AppCollectionDTO dto = new ToDTO(appinfo).convert(list);
		return dto.apps;
	}

	@Inject
	private Favorites favorites;
	@Inject
	private InMemory_appinfo_vdf appinfo;
	@Inject
	private CollectionFromVdf from;
}
