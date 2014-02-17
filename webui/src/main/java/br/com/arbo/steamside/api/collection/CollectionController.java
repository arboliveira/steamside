package br.com.arbo.steamside.api.collection;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.apps.Apps.AppIdVisitor;
import br.com.arbo.steamside.data.collections.CollectionHomeXmlFile;
import br.com.arbo.steamside.json.app.AppDTO;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.NotFound;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

@Controller
@RequestMapping("collection")
public class CollectionController {

	@RequestMapping(value = "collection.json", params = "name")
	@ResponseBody
	public List<AppDTO> json(@RequestParam final String name)
			throws
			br.com.arbo.steamside.data.collections.NotFound
	{
		final Data_appinfo_vdf _appinfo = this.appinfo;
		final List<AppDTO> result = new LinkedList<AppDTO>();
		data.on(new CollectionName(name)).accept(new AppIdVisitor() {

			@Override
			public void each(final AppId appid) {
				try {
					result.add(toDTO(appid));
				} catch (final NotFound e) {
					return;
				}
			}

			private AppDTO toDTO(final AppId appid) {
				return AppDTO.valueOf(appid, _appinfo);
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
			@PathVariable final @NonNull String appid)
			throws
			br.com.arbo.steamside.data.collections.NotFound
	{
		data.add(new CollectionName(name), new AppId(appid));
	}

	@Inject
	private Data_appinfo_vdf appinfo;

	@Inject
	private CollectionHomeXmlFile data;

}
