package br.com.arbo.steamside.api.app;

import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.AppName;

@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class AppDTO {

	private static String image(final String s_id)
	{
		return "http://cdn.akamai.steamstatic.com/steam/apps/"
				+ s_id
				+ "/header.jpg";
	}

	public AppDTO(
			final AppId appid, final AppName name, List<AppTagDTO> tags,
			boolean unavailable)
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
		this.image = image(s_id);
		this.store = "http://store.steampowered.com/app/" + s_id;
		this.tags = tags;
		this.unavailable = unavailable ? "Y" : null;
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
	@JsonProperty
	final String unavailable;
}
