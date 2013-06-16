package br.com.arbo.steamside.data.collections;

import br.com.arbo.steamside.apps.Apps.AppIdVisitor;

public interface OnCollection {

	void accept(AppIdVisitor visitor);
}