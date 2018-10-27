package br.com.arbo.steamside.steam.client.localfiles.vdf;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import org.apache.commons.io.IOUtils;
import org.hamcrest.core.IsNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RegionImplTest
{

	@Test(expected = NotFound.class)
	public void missingName__throwsException() throws NotFound
	{
		subject.region("Bogus");
	}

	@Test
	public void nestedSubregion__isFound() throws NotFound
	{
		Region region1 = subject.region("UserLocalConfigStore");
		Region region2 = region1.region("Software");
		Assert.assertThat(region2, IsNull.notNullValue());
	}

	@Before
	public void setUp() throws IOException
	{
		this.subject = new RegionImpl(new StringReader(readTemplate()));
	}

	private String readTemplate() throws IOException
	{
		Class< ? extends RegionImplTest> c = this.getClass();
		String template = c.getSimpleName() + ".localconfig.vdf";
		try (InputStream res = c.getResourceAsStream(template))
		{
			return IOUtils.toString(res, "UTF-8");
		}
	}

	private RegionImpl subject;

}
