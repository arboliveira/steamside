package br.com.arbo.steamside.api.collection;

import java.util.LinkedList;
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
import br.com.arbo.steamside.apps.Apps.AppIdVisitor;
import br.com.arbo.steamside.data.collections.CollectionHomeXmlFile;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.json.app.AppDTO;
import br.com.arbo.steamside.json.appcollection.ToDTO;
import br.com.arbo.steamside.spring.SteamsideApplicationContext;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

@Controller
@RequestMapping("collection")
public class CollectionController implements ApplicationContextAware {

	private InMemory_appinfo_vdf appinfo;
	private CollectionHomeXmlFile data;

	@RequestMapping(value = "collection.json", params = "name")
	@ResponseBody
	public List<AppDTO> json(@RequestParam final String name) throws NotFound {
		final ToDTO toDTO = new ToDTO(this.appinfo);
		final List<AppDTO> result = new LinkedList<AppDTO>();
		data.on(new CollectionName(name)).accept(new AppIdVisitor() {

			@Override
			public void each(final AppId appid) {
				result.add(toDTO.toDTO(appid));
			}
		});
		return result;
	}

	@RequestMapping(value = "{name}/create")
	@ResponseBody
	public void create(@PathVariable final @NonNull String name) {
		data.create(new CollectionName(name));
	}

	@RequestMapping(value = "{name}/add/{appid}")
	@ResponseBody
	public void add(@PathVariable final @NonNull String name,
			@PathVariable final @NonNull String appid) throws NotFound {
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
		this.data = container.getComponent(CollectionHomeXmlFile.class);
	}

}