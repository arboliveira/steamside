package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.util.Collection;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.steam.client.types.AppId;

public class Entry_app {

	public void accept(TagVisitor visitor)
	{
		final Collection<String> t = tags;
		if (t == null) return;
		for (String tag : t)
			visitor.each(tag);
	}

	@NonNull
	public AppId appid()
	{
		String _id = id;
		if (_id == null) throw new NullPointerException();
		return new AppId(_id);
	}

	public interface TagVisitor {

		void each(String tag);
	}

	String id;

	String sCloudEnabled;

	String sLastPlayed;

	@Nullable
	Collection<String> tags;
}
