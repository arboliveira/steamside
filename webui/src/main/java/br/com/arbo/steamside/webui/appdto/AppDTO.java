package br.com.arbo.steamside.webui.appdto;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.AppName;

@JsonAutoDetect
public class AppDTO {

	public AppDTO(
            final AppId appid, final AppName name) {
        final String s_id = appid.appid;
        this.appid = s_id;
        this.name = name.name;
        this.link = "/app/" + s_id + "/run";
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
