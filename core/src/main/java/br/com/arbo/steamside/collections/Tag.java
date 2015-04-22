package br.com.arbo.steamside.collections;

import java.util.Optional;

import br.com.arbo.steamside.steam.client.types.AppId;

public interface Tag {

	AppId appid();

	Optional<Notes> notes();
}
