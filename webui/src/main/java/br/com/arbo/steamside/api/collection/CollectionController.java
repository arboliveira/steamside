package br.com.arbo.steamside.api.collection;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.api.app.AppsDTO;
import br.com.arbo.steamside.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionImpl;
import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.system.SystemCollectionsHome;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

@Controller
@RequestMapping("collection")
public class CollectionController {

	@Inject
	public CollectionController(Library library, CollectionsData data) {
		this.library = library;
		this.data = data;
		this.sys = new SystemCollectionsHome(library, data);
	}

	@RequestMapping(value = "{name}/add/{appid}")
	@ResponseBody
	public void add(@PathVariable final @NonNull String name,
			@PathVariable final @NonNull String appid)
		throws
		br.com.arbo.steamside.data.collections.NotFound
	{
		data.tag(new CollectionName(name), new AppId(appid));
	}

	@RequestMapping(value = "copy-all-steam-categories")
	@ResponseBody
	public void copyAllSteamCategories()
	{
		new CopyAllSteamCategories(data, library).execute();
	}

	@RequestMapping(value = "{name}/create")
	@ResponseBody
	public void create(@PathVariable final @NonNull String name)
	{
		data.add(new CollectionImpl(new CollectionName(name),
				CollectionI.IsSystem.NO));
	}

	@RequestMapping(value = "collection.json", params = "name")
	@ResponseBody
	public List<AppDTO> json(@RequestParam final String name)
		throws
		br.com.arbo.steamside.data.collections.NotFound
	{
		boolean gamesOnly = true;
		final Stream<AppId> apps =
				sys.appsOf(new CollectionName(name)).map(Tag::appid);
		final Stream<AppId> end =
				gamesOnly ?
						apps.filter(this::isGame)
						: apps;
		return AppsDTO.valueOfAppIds(end, library);
	}

	@RequestMapping(value = "collections.json")
	@ResponseBody
	public List<CollectionDTO> jsonCollections()
	{
		return sys
				.all()
				.map(CollectionDTO::valueOf)
				.collect(
						LinkedList::new, LinkedList::add,
						LinkedList::addAll);
	}

	private boolean isGame(AppId appid)
	{
		try {
			return this.library.find(appid).type().isGame();
		}
		catch (MissingFrom_appinfo_vdf missing) {
			return false;
		}
	}

	private final Library library;

	private final CollectionsData data;

	private final SystemCollectionsHome sys;

}
