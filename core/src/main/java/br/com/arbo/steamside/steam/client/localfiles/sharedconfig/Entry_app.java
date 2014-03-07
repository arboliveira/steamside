package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.util.Collection;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.types.AppId;

public class Entry_app {

	public interface Visitor {

		void each(Entry_app each);
	}

	String id;

	@NonNull
	public AppId appid() {
		String _id = id;
		if (_id == null) throw new NullPointerException();
		return new AppId(_id);
	}

	String sLastPlayed;

	public void accept(TagVisitor visitor) {
		if (tags == null) return;
		for (String tag : tags)
			visitor.each(tag);
	}

	@Nullable
	Collection<String> tags;

	String sCloudEnabled;

	public interface TagVisitor {

		void each(String tag);
	}
}
