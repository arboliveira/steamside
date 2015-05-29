package br.com.arbo.steamside.api.collection;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionImpl;
import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.CombineCollections;
import br.com.arbo.steamside.collections.CombineCollections.Operation;
import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.collections.TagsQueries.WithCount;
import br.com.arbo.steamside.collections.system.SystemCollectionsHome;
import br.com.arbo.steamside.data.copy.CopyAllSteamCategories;
import br.com.arbo.steamside.settings.Settings;
import br.com.arbo.steamside.steam.client.apps.AppCriteria;
import br.com.arbo.steamside.steam.client.library.Library;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

@RestController
@RequestMapping("collection")
public class CollectionController {

	private static LinkedList<CollectionDTO> jsonify(
		Stream< ? extends WithCount> all)
	{
		return all
			.map(CollectionDTO::valueOf)
			.collect(
				LinkedList::new, LinkedList::add,
				LinkedList::addAll);
	}

	@Inject
	public CollectionController(
		Library library, CollectionsData collections,
		TagsData tags, Settings settings,
		br.com.arbo.steamside.api.app.AppSettings apiAppSettings)
	{
		this.library = library;
		this.collections = collections;
		this.tags = tags;
		this.settings = settings;
		this.apiAppSettings = apiAppSettings;
		this.sys = new SystemCollectionsHome(library, tags);
	}

	@RequestMapping(value = "{name}/add/{appid}")
	public void add(
		@PathVariable @NonNull String name,
		@PathVariable @NonNull String appid)
	{
		tags.tagRemember(new CollectionName(name), new AppId(appid));
	}

	@RequestMapping(value = "{name}/combine/{collection}/into/{combined}/copy")
	public void combine_into_copy(
		@PathVariable @NonNull String name,
		@PathVariable @NonNull String collection,
		@PathVariable @NonNull String combined)
	{
		new CombineCollections(
			name, collection, combined, Operation.Copy, tags)
				.combine();
	}

	@RequestMapping(value = "{name}/combine/{collection}/into/{combined}/move")
	public void combine_into_move(
		@PathVariable @NonNull String name,
		@PathVariable @NonNull String collection,
		@PathVariable @NonNull String combined)
	{
		new CombineCollections(
			name, collection, combined, Operation.Move, tags)
				.combine();
	}

	@RequestMapping(value = "copy-all-steam-categories")
	public void copyAllSteamCategories()
	{
		new CopyAllSteamCategories(tags, library).execute();
	}

	@RequestMapping(value = "{name}/create")
	public void create(@PathVariable @NonNull String name)
	{
		collections.add(
			new CollectionImpl(
				new CollectionName(name),
				CollectionI.IsSystem.NO));
	}

	@RequestMapping(value = "collection.json", params = "name")
	public List<AppDTO> json(@RequestParam String name)
	{
		return new CollectionController_collection_json(
			name, apiAppSettings.limit(), sys, library, tags,
			settings.gamesOnly()).jsonable();
	}

	@RequestMapping(value = "collections.json")
	public List<CollectionDTO> jsonCollections()
	{
		AppCriteria criteria = new AppCriteria() {

			{
				this.gamesOnly = settings.gamesOnly();
			}
		};
		return jsonify(sys.allWithCount(criteria));
	}

	@RequestMapping(value = "tag-suggestions.json")
	public List<CollectionDTO> jsonTagSuggestions()
	{
		return jsonify(tags.recent());
	}

	@RequestMapping(value = "{name}/remove/{appid}")
	public void remove(
		@PathVariable @NonNull String name,
		@PathVariable @NonNull String appid)
	{
		tags.untag(new CollectionName(name), new AppId(appid));
	}

	private final br.com.arbo.steamside.api.app.AppSettings apiAppSettings;

	final Settings settings;

	private final Library library;

	private final CollectionsData collections;

	private final TagsData tags;

	private final SystemCollectionsHome sys;

}
