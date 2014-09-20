package br.com.arbo.steamside.collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class InMemoryTagsHomeTest {

	@Test
	public void testOnDeleteCollectionWithTags()
	{
		AppId app = new AppId("142857");
		CollectionName a = new CollectionName("A");
		tags.tagRemember(a, app);

		assert_allWithTags_count(1);
		assert_tags_count(app, 1);
		assert_recent_count(1);

		CollectionI aA = collections.find(a);
		collections.delete(aA);

		assertNotFound(a);
		assert_allWithTags_count(0);
		assert_tags_count(app, 0);
		assert_recent_count(0);
	}

	private void assert_allWithTags_count(long n)
	{
		assertThat(tags.allWithTags().count(), equalTo(n));
	}

	private void assert_recent_count(long n)
	{
		assertThat(tags.recent().count(), equalTo(n));
	}

	private void assert_tags_count(AppId app, long n)
	{
		assertThat(tags.tags(app).count(), equalTo(n));
	}

	private void assertNotFound(final CollectionName a)
	{
		try
		{
			collections.find(a);
		}
		catch (NotFound e)
		{
			/* ok */
		}
	}

	private final InMemoryCollectionsHome collections =
			new InMemoryCollectionsHome();

	private final InMemoryTagsHome tags =
			new InMemoryTagsHome(collections);

}
