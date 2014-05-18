package br.com.arbo.steamside.steam.client.localfiles.steamlocation;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

import br.com.arbo.org.apache.commons.lang3.ProgramFiles;

public class WindowsTest {

	@SuppressWarnings("static-method")
	@Test
	public void steamLocation__shouldBeWhereWeKnowItToBe() {
		String base = "C:\\Program Files";
		File known = new File("C:\\Program Files", "Steam");

		ProgramFiles _ProgramFiles =
				SteamLocationTestFixture.mockProgramFiles(base);
		SteamLocation newSteamLocation = new Windows(_ProgramFiles);
		assertThat(newSteamLocation.steam(), equalTo(known));
	}
}
