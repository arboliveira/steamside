package br.com.arbo.steamside.collections;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.types.AppId;

public class AppInCollectionImpl implements AppInCollection {

	@NonNull
	private final AppId appid;

	@Nullable
	private Notes notes;

	public AppInCollectionImpl(@NonNull AppId appid) {
		this.appid = appid;
	}

	@Override
	@NonNull
	public AppId appid() {
		return appid;
	}

	@Override
	@Nullable
	public Notes notes() {
		return notes;
	}

}
