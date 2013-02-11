package br.com.arbo.steamside.webui.appdto;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonAutoDetect
public class AppDTO {

	public AppDTO(final String appid, final String name) {
		this.appid = appid;
		this.link = "app/" + appid + "/run";
		this.name = name;
	}

	@JsonProperty
	final String appid;
	@JsonProperty
	final String name;
	@JsonProperty
	final String link;
}
