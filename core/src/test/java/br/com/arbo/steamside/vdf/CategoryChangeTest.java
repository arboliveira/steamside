package br.com.arbo.steamside.vdf;

import static br.com.arbo.org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

@Ignore
@SuppressWarnings("static-method")
public class CategoryChangeTest {

	@Rule
	public TestName name = new TestName();
	private TemplatePair templates;
	private VdfImpl vdff;

	@Before
	public void loadVdfFragment() throws IOException, NotFound {
		templates = new TemplatePair(name.getMethodName());
		vdff = new VdfImpl(templates.before);
		final AppCategoryChange app =
				new AppCategoryChange(vdff.root().region("42"));
		app.category("Expected");
	}

	@After
	public void assertTemplate() {
		assertThat(vdff.content(), equalTo(templates.expected));
	}

	@Test
	public void singleTag_tagGetsChanged() {
		//
	}

	@Test
	public void favoriteTagOnly_becomesTwoTags() {
		fail("Not yet implemented");
	}

	@Test
	public void favoriteThenCategory_onlyCategoryGetsChanged() {
		fail("Not yet implemented");
	}

	@Test
	public void categoryThenFavorite_onlyCategoryGetsChanged() {
		fail("Not yet implemented");
	}

	@Test
	public void tagless_becomesSingleTag() {
		fail("Not yet implemented");
	}

}