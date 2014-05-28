package br.com.arbo.steamside.collections;

import java.util.Optional;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.steam.client.types.AppId;

public class TagImpl implements Tag {

	public TagImpl(@NonNull AppId appid)
	{
		if (appid == null) throw new NullPointerException();
		this.appid = appid;
	}

	@Override
	@NonNull
	public AppId appid()
	{
		return appid;
	}

	@Override
	public Optional<Notes> notes()
	{
		return notes;
	}

	@NonNull
	private final AppId appid;

	private final Optional<Notes> notes = Optional.empty();

}
