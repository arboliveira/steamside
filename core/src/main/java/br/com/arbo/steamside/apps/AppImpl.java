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
	@NonNull
	public AppName name() throws MissingFrom_appinfo_vdf {
		guard_missingFrom_appinfo_vdf();
		final AppName _name = name;
		if (_name == null) throw new NullPointerException();
		return _name;
	}

	private void guard_missingFrom_appinfo_vdf() {
		if (missingFrom_appinfo_vdf != null) throw missingFrom_appinfo_vdf;
	}

	@SuppressWarnings("null")
	@Override
	@NonNull
	public String executable()
			throws NotAvailableOnThisPlatform, MissingFrom_appinfo_vdf {
		guard_notAvailableOnThisPlatform();
		guard_missingFrom_appinfo_vdf();
		if (executable == null)
			throw new NullPointerException();
		return executable;
	}

	private void guard_notAvailableOnThisPlatform() {
		if (notAvailableOnThisPlatform != null)
			throw notAvailableOnThisPlatform;
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
					this.categories, this.lastPlayed, this.cloudEnabled,
					this.notAvailableOnThisPlatform,
					this.missingFrom_appinfo_vdf);
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
		private MissingFrom_appinfo_vdf missingFrom_appinfo_vdf;
		private NotAvailableOnThisPlatform notAvailableOnThisPlatform;

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

		public void missingFrom_appinfo_vdf(MissingFrom_appinfo_vdf e) {
			this.missingFrom_appinfo_vdf = e;
		}

		public void notAvailableOnThisPlatform(NotAvailableOnThisPlatform ex) {
			this.notAvailableOnThisPlatform = ex;
		}
	}

	AppImpl(
			@NonNull final AppId appId,
			@Nullable AppName name,
			@Nullable String executable,
			@Nullable final Collection<String> categories,
			@Nullable final String lastPlayed,
			@Nullable final String cloudEnabled,
			@Nullable NotAvailableOnThisPlatform notAvailableOnThisPlatform,
			@Nullable MissingFrom_appinfo_vdf missingFrom_appinfo_vdf) {
		this.appid = appId;
		this.name = name;
		this.executable = executable;
		this.categories = categories;
		this.lastPlayed = lastPlayed;
		this.cloudEnabled = cloudEnabled;
		this.notAvailableOnThisPlatform = notAvailableOnThisPlatform;
		this.missingFrom_appinfo_vdf = missingFrom_appinfo_vdf;
	}

	@Nullable
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

	@Nullable
	private final NotAvailableOnThisPlatform notAvailableOnThisPlatform;

	@Nullable
	private final MissingFrom_appinfo_vdf missingFrom_appinfo_vdf;

}
