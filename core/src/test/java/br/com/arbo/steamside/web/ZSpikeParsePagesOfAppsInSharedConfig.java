package br.com.arbo.steamside.web;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import br.com.arbo.steamside.vdf.Apps.Visitor;
import br.com.arbo.steamside.vdf.SharedConfig;
import br.com.arbo.steamside.web.AppName;
import br.com.arbo.steamside.web.AppPage;

class ZSpikeParsePagesOfAppsInSharedConfig {

	public static void main(String[] args) throws IOException {
		boolean one = true && false;
		if (one)
			one();
		else
			dump();
	}

	private static void one() {
		System.out.println(toInfo("12800"));
	}

	private static void dump() throws IOException {
		new SharedConfig(
				FileUtils.readFileToString(new File(
						"etc/sharedconfig.vdf")))
				.apps().accept(new Visitor() {

					@Override
					public void each(
							String app) {
						System.out.println(
								toInfo(app)
								);
					}

				});
	}

	static String toInfo(String app) {
		return app + "]" + fetch(app).name;
	}

	private static AppName fetch(String app) {
		return new AppPage(app).name();
	}
}
