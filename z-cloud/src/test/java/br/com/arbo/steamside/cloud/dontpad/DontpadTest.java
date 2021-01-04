package br.com.arbo.steamside.cloud.dontpad;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.arbo.steamside.cloud.http.Misconfigured;

public class DontpadTest
{

	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
	}

	@Test(expected = Dontpad.AddressMissing.class)
	public void testAddressMissing() throws Throwable
	{
		Mockito.doReturn(dontpadSettings).when(dontpadSettingsFactory).read();

		Mockito.doReturn(Optional.empty()).when(dontpadSettings).address();

		Dontpad dontpad = new Dontpad(dontpadSettingsFactory);

		try
		{
			dontpad.buildHttpGetURI();

			Assert.fail();
		}
		catch (Misconfigured m)
		{
			throw m.getCause();
		}
	}

	@Mock
	DontpadSettings dontpadSettings;

	@Mock
	DontpadSettingsFactory dontpadSettingsFactory;

}
