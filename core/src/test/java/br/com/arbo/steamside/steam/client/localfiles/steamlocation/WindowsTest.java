package br.com.arbo.steamside.steam.client.localfiles.steamlocation;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

public class WindowsTest {

	@SuppressWarnings("static-method")
	@Test
	@Ignore("must mock System.getEnv :-(")
	public void steamLocation__shouldBeWhereWeKnowItToBe() {
		String base = "C:\\Program Files";
		String known = "C:\\Program Files\\Steam";
		SteamLocation newSteamLocation = new Windows();
		assertThat(newSteamLocation.steam(), equalTo(new File(known)));
	}
}
