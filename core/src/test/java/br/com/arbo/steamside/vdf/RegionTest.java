package br.com.arbo.steamside.vdf;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

@Ignore
public class RegionTest {

	@Rule
	public TestName name = new TestName();
	private Vdf vdff;

	@Test
	public void nameOccursBefore_skipAndFindRegionProper() throws IOException,
			NotFound {
		final String mname = name.getMethodName();
		final Template template1 = new Template(mname + ".1apps");
		vdff = new Vdf(template1.content);
		final Region apps = vdff.root().region("apps");
		final Region app42 = apps.region("42");
		final Template template2 = new Template(mname + ".2app42expected");
		fail();
		//assertThat(app42.content(), equalTo(template2.content));
	}
}
