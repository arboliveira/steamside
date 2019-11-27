package br.com.arbo.steamside.steam.client.localfiles.vdf;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RegionImplTest
{

	@Test
	public void missingName__emptyOptional()
	{
		Assert.assertFalse(subject.region("Bogus").isPresent());
	}

	@Test
	public void nestedSubregion__isFound()
	{
		Assert.assertTrue(
			subject.region("UserLocalConfigStore")
				.flatMap(r -> r.region("Software"))
				.isPresent());
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
