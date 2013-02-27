package br.com.arbo.steamside.webui.appdto;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import br.com.arbo.steamside.steam.store.AppName;
import br.com.arbo.steamside.types.AppId;

@JsonAutoDetect
public class AppDTO {

	public AppDTO(final AppId appid, final AppName name) {
		this.appid = appid.appid;
		this.link = "/app/" + this.appid + "/run";
		this.name = name.name;
	}

	@JsonProperty
	final String appid;
	@JsonProperty
	final String name;
	@JsonProperty
	final String link;
}
