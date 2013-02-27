package br.com.arbo.steamside.vdf;

import br.com.arbo.steamside.types.AppId;

public interface Apps {

	App app(AppId appid) throws NotFound;

	void accept(final AppIdVisitor visitor);

	public interface AppIdVisitor {

		void each(AppId appid);
	}

}
