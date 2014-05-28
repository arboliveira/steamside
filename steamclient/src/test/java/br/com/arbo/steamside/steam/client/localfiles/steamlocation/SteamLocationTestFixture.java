package br.com.arbo.steamside.steam.client.localfiles.steamlocation;

import java.io.File;

import org.mockito.Mockito;

import br.com.arbo.opersys.userhome.ProgramFiles;
import br.com.arbo.opersys.userhome.UserHome;

class SteamLocationTestFixture {

	static ProgramFiles mockProgramFiles(final String base)
	{
		final ProgramFiles mock = Mockito.mock(ProgramFiles.class);
		Mockito.when(mock.getProgramFiles()).thenReturn(new File(base));
		return mock;
	}

	static UserHome mockUserHome(final String base)
	{
		final UserHome mock = Mockito.mock(UserHome.class);
		Mockito.when(mock.getUserHome()).thenReturn(new File(base));
		return mock;
	}

}