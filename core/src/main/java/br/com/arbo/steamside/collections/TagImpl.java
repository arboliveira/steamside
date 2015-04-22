package br.com.arbo.steamside.collections;

import java.util.Optional;

import br.com.arbo.steamside.steam.client.types.AppId;

public class TagImpl implements Tag {

	public TagImpl(AppId appid)
	{
		this.appid = appid;
	}

	@Override
	public AppId appid()
	{
		return appid;
	}

	@Override
	public Optional<Notes> notes()
	{
		return notes;
	}

	private final AppId appid;

	private final Optional<Notes> notes = Optional.empty();

}
