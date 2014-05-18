package br.com.arbo.steamside.steam.client.localfiles.steamlocation;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

import br.com.arbo.org.apache.commons.lang3.UserHome;

public class MacOSXTest {

	@SuppressWarnings("static-method")
	@Test
	public void steamLocation__shouldBeWhereWeKnowItToBe() {
		String base = "/Users/gaben";
		String known = "/Users/gaben/Library/Application Support/Steam";

		UserHome userhome = SteamLocationTestFixture.mockUserHome(base);
		SteamLocation location = new MacOSX(userhome);
		assertThat(location.steam(), equalTo(new File(known)));
	}
}
