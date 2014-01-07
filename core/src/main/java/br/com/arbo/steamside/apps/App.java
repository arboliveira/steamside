package br.com.arbo.steamside.apps;

import java.util.Collection;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.Category;

public class App {

	public boolean isInCategory(final Category category) {
		final Collection<String> c = categories;
		if (c == null) return false;
		return c.contains(category.category);
	}

	public AppId appid() {
		return appid;
	}

	public static class Builder {

		private Collection<String> categories;

		public App make() {
			return new App(newAppId(), this.categories,
					this.lastPlayed, this.cloudEnabled);
		}

		@NonNull
		private AppId newAppId() {
			final AppId _appid = appid;
			if (_appid != null) return _appid;
			throw new NullPointerException();
		}

		private AppId appid;
		private String lastPlayed;
		private String cloudEnabled;

		public Builder lastPlayed(final String v) {
			this.lastPlayed = v;
			return this;
		}

		public void cloudEnabled(final String v) {
			this.cloudEnabled = v;
		}

		public void categories(final Collection<String> v) {
			this.categories = v;
		}

		public Builder appid(@NonNull final String k) {
			this.appid = new AppId(k);
			return this;
		}
	}

	App(
			@NonNull final AppId appId,
			@Nullable final Collection<String> categories,
			@Nullable final String lastPlayed,
			@Nullable final String cloudEnabled) {
		this.appid = appId;
		this.categories = categories;
		this.lastPlayed = lastPlayed;
		this.cloudEnabled = cloudEnabled;
	}

	private final AppId appid;
	private final String lastPlayed;

	@Nullable
	public String lastPlayed() {
		return lastPlayed;
	}

	@NonNull
	public String lastPlayedOrCry() {
		final String v = lastPlayed;
		if (v == null) throw new NeverPlayed();
		return v;
	}

	public String cloudEnabled() {
		return cloudEnabled;
	}

	private final String cloudEnabled;

	@Nullable
	private final Collection<String> categories;

	public void accept(final Category.Visitor visitor) {
		final Collection<String> c = categories;
		if (c == null) return;
		for (final String one : c)
			visitor.visit(new Category(one));
	}

	@Override
	public String toString() {
		return appid.toString();
	}
}
