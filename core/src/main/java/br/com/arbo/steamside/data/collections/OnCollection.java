package br.com.arbo.steamside.data.collections;

import java.util.function.Consumer;

import br.com.arbo.steamside.types.AppId;

public interface OnCollection {

	void forEach(Consumer<AppId> visitor);
}