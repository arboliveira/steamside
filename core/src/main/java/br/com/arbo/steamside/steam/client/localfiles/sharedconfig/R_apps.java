package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import br.com.arbo.steamside.types.AppId;

public interface R_apps {

	void accept(AppId.Visitor visitor);

	void accept(Entry_app.Visitor visitor);

}
