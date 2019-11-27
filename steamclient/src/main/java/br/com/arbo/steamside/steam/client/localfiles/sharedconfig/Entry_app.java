package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.steam.client.types.AppId;

public class Entry_app
{

	public void accept(CategoryVisitor visitor)
	{
		Optional.ofNullable(categories)
			.ifPresent(t -> visitCategories(visitor, t));
	}

	@NonNull
	public AppId appid()
	{
		return new AppId(Objects.requireNonNull(id));
	}

	@Nullable
	Collection<String> categories;

	String id;

	String sCloudEnabled;

	String sLastPlayed;

	private static void visitCategories(CategoryVisitor visitor,
		Collection<String> categories)
	{
		for (String category : categories)
			visitor.each(category);
	}

	public interface CategoryVisitor
	{

		void each(String category);
	}
}
