package br.com.arbo.steamside.api.app;

import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionsQueries;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.steam.client.apps.NotFound;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.AppName;

@JsonAutoDetect
public class AppDTO {

	public static AppDTO valueOf(
			final AppId appid, final Library library,
			CollectionsQueries queries)
			throws MissingFrom_appinfo_vdf, NotFound
	{
		final App app = library.find(appid);

		List<AppTagDTO> list = new LinkedList<AppTagDTO>();

		queries.tags(appid)
				.map(CollectionI::name).map(AppTagDTO::new)
				.forEach(list::add);

		return new AppDTO(appid, app.name(), list);
	}

	public AppDTO(
			final AppId appid, final AppName name, List<AppTagDTO> tags)
	{
		final String s_id = appid.appid;
		this.appid = s_id;
		this.name = name.name;
		this.link =
				String.format(
						"%s/%s/%s/%s",
						br.com.arbo.steamside.mapping.Api.api,
						br.com.arbo.steamside.mapping.App.app,
						s_id,
						br.com.arbo.steamside.mapping.App.run);
		this.image = "http://cdn.steampowered.com/v/gfx/apps/"
				+ s_id + "/header.jpg";
		this.store = "http://store.steampowered.com/app/" + s_id;
		this.tags = tags;
	}

	@JsonProperty
	final String appid;
	@JsonProperty
	final String name;
	@JsonProperty
	final String link;
	@JsonProperty
	final String image;
	@JsonProperty
	final String store;
	@JsonProperty
	final List<AppTagDTO> tags;
}
