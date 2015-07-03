package br.com.arbo.steamside.steam.client.localfiles.steamlocation;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.io.File;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.arbo.opersys.userhome.ProgramFiles;

public class WindowsTest
{

	@Test
	public void steamLocation__shouldBeWhereWeKnowItToBe()
	{
		File base = new File("C:\\Program Files (x86)");
		doReturn(base).when(_ProgramFiles).getProgramFiles_x86();
		assertSteamLocation(base);
	}

	@Test
	public void steamLocation__Windows32bits()
	{
		doThrow(NullPointerException.class)
			.when(_ProgramFiles).getProgramFiles_x86();
		File base = new File("C:\\Program Files");
		doReturn(base).when(_ProgramFiles).getProgramFiles();
		assertSteamLocation(base);
	}

	private void assertSteamLocation(File base)
	{
		SteamLocation newSteamLocation = new Windows(_ProgramFiles);
		assertThat(newSteamLocation.steam(), equalTo(new File(base, "Steam")));
	}

	{
		MockitoAnnotations.initMocks(this);
	}

	@Mock
	ProgramFiles _ProgramFiles;
}
