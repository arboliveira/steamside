package br.com.arbo.steamside.webui.wicket;

import org.apache.commons.lang3.SystemUtils;

import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.continues.Continue;
import br.com.arbo.steamside.steam.store.AppNameFactory;
import br.com.arbo.steamside.types.Category;

public class ContinueNeedsImpl implements Continue.Needs {

	private final AppNameFactory nameFactory;
	private final SharedConfigConsume sharedconfig;

	public ContinueNeedsImpl(final AppNameFactory nameFactory,
			final SharedConfigConsume sharedconfig) {
		this.nameFactory = nameFactory;
		this.sharedconfig = sharedconfig;
	}

	@Override
	public Category name() {
		return new Category(cname());
	}

	private static String cname() {
		final String username = SystemUtils.USER_NAME;
		if ("PARENT_USERNAME".equals(username)) return "PARENT_CONTINUE";
		return "KID_CONTINUE";
	}

	@Override
	public CollectionFromVdf collectionFromVdf() {
		return new CollectionFromVdf(nameFactory, sharedconfig);
	}

}
