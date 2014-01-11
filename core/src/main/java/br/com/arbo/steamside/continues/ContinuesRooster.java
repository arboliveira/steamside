package br.com.arbo.steamside.continues;

import br.com.arbo.steamside.apps.Apps.AppVisitor;

public interface ContinuesRooster {

	void accept(final AppVisitor visitor);

}
