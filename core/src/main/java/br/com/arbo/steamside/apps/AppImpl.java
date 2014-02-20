package br.com.arbo.steamside.apps;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.AppName;
import br.com.arbo.steamside.types.Category;

public class AppImpl implements App {

	@Override
	public boolean isInCategory(final Category category) {
		final Collection<String> c = categories;
		if (c == null) return false;
		return c.contains(category.category);
	}

	@SuppressWarnings("null")
	@Override
	@NonNull
	public AppId appid() {
		return appid;
	}

	public static class Builder {

		private final Collection<String> categories = new HashSet<String>();

		public AppImpl make() {
			return new AppImpl(newAppId(), this.name, this.executable,
					this.categories, this.lastPlayed, this.cloudEnabled);
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
		private AppName name;
		private String executable;
		private boolean missingFrom_appinfo_vdf;
		private boolean notAvailableOnThisPlatform;

		public Builder lastPlayed(final String v) {
			this.lastPlayed = v;
			return this;
		}

		public void cloudEnabled(final String v) {
			this.cloudEnabled = v;
		}

		public Builder appid(@NonNull final String k) {
			this.appid = new AppId(k);
			return this;
		}

		public Builder name(AppName name) {
			this.name = name;
			return this;
		}

		public Builder executable(String executable) {
			this.executable = executable;
			return this;
		}

		public void addCategory(String category) {
			this.categories.add(category);
		}

		public void missingFrom_appinfo_vdf() {
			this.missingFrom_appinfo_vdf = true;
		}

		public void notAvailableOnThisPlatform() {
			this.notAvailableOnThisPlatform = true;
		}
	}

	AppImpl(
			@NonNull final AppId appId,
			@Nullable AppName name,
			@Nullable String executable,
			@Nullable final Collection<String> categories,
			@Nullable final String lastPlayed,
			@Nullable final String cloudEnabled) {
		this.appid = appId;
		this.name = name;
		this.executable = executable;
		this.categories = categories;
		this.lastPlayed = lastPlayed;
		this.cloudEnabled = cloudEnabled;
	}

	@Override
	@Nullable
	public String lastPlayed() {
		return lastPlayed;
	}

	@Override
	@NonNull
	public String lastPlayedOrCry() throws NeverPlayed {
		final String v = lastPlayed;
		if (v == null) throw new NeverPlayed();
		return v;
	}

	public String cloudEnabled() {
		return cloudEnabled;
	}

	@Override
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

	@NonNull
	private final AppName name;

	@Nullable
	private final String executable;

	@Nullable
	private final String cloudEnabled;

	@Nullable
	private final Collection<String> categories;

	@NonNull
	private final AppId appid;

	@Nullable
	private final String lastPlayed;

	@SuppressWarnings("null")
	@Override
	@NonNull
	public AppName name() {
		return name;
	}

	@SuppressWarnings("null")
	@Override
	@NonNull
	public String executable() throws NotAvailableOnThisPlatform {
		if (executable == null) throw new NotAvailableOnThisPlatform();
		return executable;
	}

}
