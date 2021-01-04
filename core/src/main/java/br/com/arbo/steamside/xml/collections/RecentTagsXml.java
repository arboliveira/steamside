package br.com.arbo.steamside.xml.collections;

import java.util.LinkedList;

import br.com.arbo.steamside.collections.InMemoryTagsHome;
import br.com.arbo.steamside.collections.TagsQueries.WithCount;
import br.com.arbo.steamside.types.CollectionName;

public class RecentTagsXml
{

	public void toTagsHome(InMemoryTagsHome tags)
	{
		new ToTagsHome(tags).convert();
	}

	void add(WithCount w)
	{
		tag.add(w.collection().value);
	}

	class ToTagsHome
	{

		public ToTagsHome(InMemoryTagsHome tags)
		{
			this.tags = tags;
		}

		void convert()
		{
			tag.stream().forEach(this::addTag);
		}

		private void addTag(String tagname)
		{
			tags.rememberRecentTag(new CollectionName(tagname));
		}

		private final InMemoryTagsHome tags;

	}

	public final LinkedList<String> tag = new LinkedList<>();

}
