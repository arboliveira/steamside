package br.com.arbo.steamside.collections;

import java.util.Optional;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.steam.client.types.AppId;

public interface Tag {

	@NonNull
	AppId appid();

	Optional<Notes> notes();
}
