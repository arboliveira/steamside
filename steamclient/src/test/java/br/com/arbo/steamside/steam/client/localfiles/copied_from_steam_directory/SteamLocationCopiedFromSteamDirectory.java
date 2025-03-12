package br.com.arbo.steamside.steam.client.localfiles.copied_from_steam_directory;

import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocation;

import java.io.File;

public class SteamLocationCopiedFromSteamDirectory implements SteamLocation {

    @Override
    public File steam() {
        return new File(this.getClass().getResource("").getFile());
    }
}
