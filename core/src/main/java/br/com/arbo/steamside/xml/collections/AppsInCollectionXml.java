package br.com.arbo.steamside.xml.collections;

import java.util.LinkedList;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.Apps.AppIdVisitor;
import br.com.arbo.steamside.types.AppId;

public class AppsInCollectionXml {

	public final LinkedList<AppInCollectionXml> app = new LinkedList<AppInCollectionXml>();

	public void add(@NonNull final AppId appid) {
		@SuppressWarnings("null")
		@NonNull
		final String incoming = appid.appid;
		for (final AppInCollectionXml each : app)
			if (each.appid.equals(incoming)) return;
		final AppInCollectionXml anew = newAppInCollectionXml(incoming);
		app.add(anew);
	}

	private static AppInCollectionXml newAppInCollectionXml(
			final String incoming) {
		final AppInCollectionXml a = new AppInCollectionXml();
		a.appid = incoming;
		return a;
	}

	@SuppressWarnings("null")
	public void accept(final AppIdVisitor visitor) {
		for (final AppInCollectionXml each : app)
			visitor.each(new AppId(each.appid));
	}
}
