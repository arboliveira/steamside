package br.com.arbo.steamside.collections;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.types.AppId;

public interface AppInCollection {

	@NonNull
	AppId appid();

	@Nullable
	Notes notes();
}
