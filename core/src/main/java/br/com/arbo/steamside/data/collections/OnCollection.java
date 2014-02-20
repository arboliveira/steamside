package br.com.arbo.steamside.data.collections;

import br.com.arbo.steamside.types.AppId;

public interface OnCollection {

	void accept(AppId.Visitor visitor);
}