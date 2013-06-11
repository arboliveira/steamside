package br.com.arbo.steamside.api.collection;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;
import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.FilterCategory;
import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.json.app.AppDTO;
import br.com.arbo.steamside.json.appcollection.AppCollectionDTO;
import br.com.arbo.steamside.json.appcollection.ToDTO;
import br.com.arbo.steamside.spring.SteamsideApplicationContext;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;
import br.com.arbo.steamside.types.Category;

@Controller
@RequestMapping("collection")
public class CollectionController implements ApplicationContextAware {

	private CollectionFromVdf from;
	private InMemory_appinfo_vdf appinfo;

	@SuppressWarnings("static-method")
	@RequestMapping(value = "collection.json", params = "name")
	@ResponseBody
	public List<AppDTO> byName(@RequestParam final String name) {
		final List<App> list = from
				.query(new FilterCategory(new Category(name)));
		final AppCollectionDTO query = new ToDTO(this.appinfo).convert(list);
		final List<AppDTO> apps = query.apps;
		return apps;
	}

	@Override
	public void setApplicationContext(
			final ApplicationContext applicationContext)
			throws BeansException {
		final MutablePicoContainerX container =
				((SteamsideApplicationContext) applicationContext)
						.getContainer();
		this.appinfo = container.getComponent(InMemory_appinfo_vdf.class);
		this.from = container.getComponent(CollectionFromVdf.class);
	}

}
