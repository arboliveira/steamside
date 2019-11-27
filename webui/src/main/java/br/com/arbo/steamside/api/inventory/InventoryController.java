package br.com.arbo.steamside.api.inventory;

import java.util.List;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.arbo.steamside.api.app.AppCardDTO;
import br.com.arbo.steamside.api.app.AppDTO_json;
import br.com.arbo.steamside.api.collection.CollectionDTO;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.collections.system.Everything;
import br.com.arbo.steamside.collections.system.Uncollected;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.platform.PlatformFactory;

@RestController
@RequestMapping("inventory")
public class InventoryController
{

	@RequestMapping(value = "owned.json")
	public List<AppCardDTO> owned()
	{
		return jsonable(owned.apps());
	}

	@RequestMapping(value = "owned-count.json")
	public CollectionDTO owned_count()
	{
		return CollectionDTO.valueOf(owned.withCount());
	}

	@RequestMapping(value = "tagless.json")
	public List<AppCardDTO> tagless()
	{
		return jsonable(tagless.apps());
	}

	@RequestMapping(value = "tagless-count.json")
	public CollectionDTO tagless_count()
	{
		return CollectionDTO.valueOf(tagless.withCount());
	}

	@Inject
	public InventoryController(
		SteamClientHome steamClientHome, PlatformFactory platformFactory,
		TagsQueries tagsQueries,
		br.com.arbo.steamside.api.app.AppSettings apiAppSettings)
	{
		this.steamClientHome = steamClientHome;
		this.platformFactory = platformFactory;
		this.tagsQueries = tagsQueries;
		this.apiAppSettings = apiAppSettings;
		this.tagless = new Uncollected(steamClientHome, tagsQueries);
		this.owned = new Everything(steamClientHome);
	}

	private List<AppCardDTO> jsonable(Stream< ? extends Tag> tags)
	{
		return new AppDTO_json(
			tagsQueries, steamClientHome, platformFactory, apiAppSettings.limit())
				.jsonable(tags);
	}

	private final br.com.arbo.steamside.api.app.AppSettings apiAppSettings;

	private final Everything owned;
	private final PlatformFactory platformFactory;
	private final SteamClientHome steamClientHome;
	private final Uncollected tagless;
	private final TagsQueries tagsQueries;

}
