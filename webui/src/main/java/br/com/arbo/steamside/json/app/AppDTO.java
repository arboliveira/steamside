package br.com.arbo.steamside.json.app;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.I_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.NotFound;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.AppName;

@JsonAutoDetect
public class AppDTO {

	public static AppDTO valueOf(
			final AppId appid, final I_appinfo_vdf appinfo)
			throws NotFound {
		final AppName appname = nameOf(appid, appinfo);
		final AppDTO dto = new AppDTO(appid, appname);
		return dto;
	}

	private static AppName nameOf(
			final AppId appid, final I_appinfo_vdf appinfo)
			throws NotFound {
		return appinfo.get(appid).name();
	}

	public AppDTO(
			final AppId appid, final AppName name) {
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
}
