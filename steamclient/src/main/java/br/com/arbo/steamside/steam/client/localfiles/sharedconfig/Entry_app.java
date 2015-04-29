package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.steam.client.types.AppId;

public class Entry_app {

	public void accept(TagVisitor visitor)
	{
		Optional.ofNullable(tags).ifPresent(t -> {
			for (String tag : t)
				visitor.each(tag);
		} );
	}

	@NonNull
	public AppId appid()
	{
		return new AppId(Objects.requireNonNull(id));
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
