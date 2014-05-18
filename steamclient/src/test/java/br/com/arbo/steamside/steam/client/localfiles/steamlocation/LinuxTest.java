package br.com.arbo.steamside.steam.client.localfiles.steamlocation;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

import br.com.arbo.org.apache.commons.lang3.UserHome;

public class LinuxTest {

	@SuppressWarnings("static-method")
	@Test
	public void steamLocation__shouldBeWhereWeKnowItToBe() {
		String base = "/home/gaben";
		String known = "/home/gaben/.steam/steam";

		UserHome userhome = SteamLocationTestFixture.mockUserHome(base);
		SteamLocation location = new Linux(userhome);
		assertThat(location.steam(), equalTo(new File(known)));
	}
}
