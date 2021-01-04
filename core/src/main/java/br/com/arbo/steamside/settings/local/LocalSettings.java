package br.com.arbo.steamside.settings.local;

import java.nio.file.Path;
import java.util.Optional;

public interface LocalSettings
{

	boolean cloudEnabled();

	Optional<Path> cloudSyncedLocation();

}
