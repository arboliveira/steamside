package br.com.arbo.steamside.api.favorites;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.app.injection.ContainerWeb;
import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.favorites.Favorites;
import br.com.arbo.steamside.json.app.AppDTO;
import br.com.arbo.steamside.json.appcollection.AppCollectionDTO;
import br.com.arbo.steamside.json.appcollection.ToDTO;
import br.com.arbo.steamside.spring.SteamsideApplicationContext;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;

@Controller
@RequestMapping("favorites")
public class FavoritesController implements ApplicationContextAware {

	@RequestMapping("favorites.json")
	@ResponseBody
	public List<AppDTO> favorites() {
		@SuppressWarnings("null")
		final List<App> list = from.query(favorites);
		final AppCollectionDTO dto = new ToDTO(appinfo).convert(list);
		return dto.apps;
	}

	@Override
	public void setApplicationContext(
			final ApplicationContext applicationContext)
			throws BeansException {
		final ContainerWeb container =
				((SteamsideApplicationContext) applicationContext)
						.getContainer();
		this.favorites = container.getComponent(Favorites.class);
		this.appinfo = container.getComponent(InMemory_appinfo_vdf.class);
		this.from = container.getComponent(CollectionFromVdf.class);
	}

	private Favorites favorites;
	private InMemory_appinfo_vdf appinfo;
	private CollectionFromVdf from;
}
