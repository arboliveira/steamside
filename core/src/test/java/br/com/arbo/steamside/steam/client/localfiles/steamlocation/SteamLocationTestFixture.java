package br.com.arbo.steamside.steam.client.localfiles.steamlocation;

import java.io.File;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;

import br.com.arbo.org.apache.commons.lang3.ProgramFiles;
import br.com.arbo.org.apache.commons.lang3.UserHome;

class SteamLocationTestFixture {

	static UserHome mockUserHome(final String base) {
		final Mockery m = new JUnit4Mockery();
		final UserHome mock = m.mock(UserHome.class);
		m.checking(/* @formatter:off */new Expectations() { {	/* @formatter:on */
				allowing(mock).getUserHome();
				will(returnValue(new File(base)));
			}
		});
		return mock;
	}

	static ProgramFiles mockProgramFiles(final String base) {
		final Mockery m = new JUnit4Mockery();
		final ProgramFiles mock = m.mock(ProgramFiles.class);
		m.checking(/* @formatter:off */new Expectations() { {	/* @formatter:on */
				allowing(mock).getProgramFiles();
				will(returnValue(new File(base)));
			}
		});
		return mock;
	}

}