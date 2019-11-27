package br.com.arbo.steamside.api.app;

import java.util.List;

import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.AppName;

public class AppCardDTOBuilder
{

	public AppCardDTOBuilder appid(AppId appid)
	{
		this.appid = appid;
		return this;
	}

	public AppCardDTO build()
	{
		AppCardDTO a = new AppCardDTO();
		a.appid = appid.appid();
		a.image = image();
		a.link = run_api_link();
		a.name = name.name();
		a.store = store();
		a.tags = tagsDTO;
		if (_unavailable) a.unavailable = "Y";
		return a;
	}

	public AppCardDTOBuilder name(AppName name)
	{
		this.name = name;
		return this;
	}

	public AppCardDTOBuilder tags(List<AppCardTagDTO> tagsDTO)
	{
		this.tagsDTO = tagsDTO;
		return this;
	}

	public AppCardDTOBuilder unavailable(boolean unavailable)
	{
		this._unavailable = unavailable;
		return this;
	}

	String image()
	{
		return Image.image(appid);
	}

	String run_api_link()
	{
		return String.format(
			"%s/%s/%s/%s",
			br.com.arbo.steamside.mapping.Api.api,
			br.com.arbo.steamside.mapping.App.app,
			appid,
			br.com.arbo.steamside.mapping.App.run);
	}

	String store()
	{
		return "http://store.steampowered.com/app/" + appid;
	}

	AppId appid;
	AppName name;
	List<AppCardTagDTO> tagsDTO;
	boolean _unavailable;

}
