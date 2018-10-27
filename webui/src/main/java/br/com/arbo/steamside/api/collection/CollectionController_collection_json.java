package br.com.arbo.steamside.api.collection;

import java.util.List;
import java.util.stream.Stream;

import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.api.app.AppDTO_json;
import br.com.arbo.steamside.api.app.Limit;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.collections.system.SystemCollectionsHome;
import br.com.arbo.steamside.steam.client.apps.home.AppCriteria;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.types.CollectionName;

class CollectionController_collection_json
{

	List<AppDTO> jsonable()
	{
		Stream< ? extends Tag> appsOf =
			sys.appsOf(
				new CollectionName(name),
				new AppCriteria().gamesOnly(gamesOnly));
		return inventory_json.jsonable(appsOf);
	}

	CollectionController_collection_json(
		String name, Limit limit, SystemCollectionsHome sys,
		SteamClientHome steamClientHome,
		TagsQueries queries, boolean gamesOnly)
	{
		this.name = name;
		this.sys = sys;
		this.gamesOnly = gamesOnly;
		this.inventory_json = new AppDTO_json(queries, steamClientHome, limit);
	}

	private final boolean gamesOnly;
	private final AppDTO_json inventory_json;
	private final String name;
	private final SystemCollectionsHome sys;

}
