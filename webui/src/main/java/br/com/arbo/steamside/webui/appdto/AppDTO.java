package br.com.arbo.steamside.webui.appdto;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.AppName;

@JsonAutoDetect
public class AppDTO {

	public AppDTO(
			final AppId appid, final AppName name, final Size size,
			final Visible visible) {
		this.appid = appid.appid;
		this.link = "/app/" + this.appid + "/run";
		this.name = name.name;
		this.size = size.name().toLowerCase();
		this.visible = visible.name().toLowerCase();
	}

	@JsonProperty
	final String appid;
	@JsonProperty
	final String name;
	@JsonProperty
	final String link;
	@JsonProperty
	final String size;
	@JsonProperty
	final String visible;
}
