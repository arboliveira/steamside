package br.com.arbo.steamside.api.collection;

import java.util.HashSet;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;
import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.Apps.AppIdVisitor;
import br.com.arbo.steamside.apps.Filter;
import br.com.arbo.steamside.apps.FilterCategory;
import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.data.collections.CollectionData;
import br.com.arbo.steamside.json.app.AppDTO;
import br.com.arbo.steamside.json.appcollection.AppCollectionDTO;
import br.com.arbo.steamside.json.appcollection.ToDTO;
import br.com.arbo.steamside.spring.SteamsideApplicationContext;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.Category;
import br.com.arbo.steamside.types.CollectionName;

@Controller
@RequestMapping("collection")
public class CollectionController implements ApplicationContextAware {

	private CollectionFromVdf from;
	private InMemory_appinfo_vdf appinfo;
	private CollectionData data;

	@RequestMapping(value = "collection.json", params = "name")
	@ResponseBody
	public List<AppDTO> json(@RequestParam final String name) {
		final HashSet<String> idsInCollection = new HashSet<String>();
		data.on(new CollectionName(name)).accept(new AppIdVisitor() {

			@Override
			public void each(final AppId appid) {
				idsInCollection.add(appid.appid);
			}
		});
		final Filter filter;
		if (false)
			filter = new FilterCategory(new Category(name));
		else
			filter = new Filter() {

				@Override
				public void consider(final App app) throws Reject {
					if (idsInCollection.contains(app.appid().appid)) return;
					throw new Reject();
				}
			};
		final List<App> list = from.query(filter);
		final AppCollectionDTO query = new ToDTO(this.appinfo).convert(list);
		final List<AppDTO> apps = query.apps;
		return apps;
	}

	@RequestMapping(value = "{name}/add/{appid}")
	public void add(@PathVariable final String name,
			@PathVariable final @NonNull String appid) {
		data.add(new CollectionName(name), new AppId(appid));
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
		this.data = container.getComponent(CollectionData.class);
	}

}
