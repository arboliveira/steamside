package br.com.arbo.steamside.xml.collections;

import java.util.LinkedList;

import br.com.arbo.steamside.collections.CollectionsQueries.WithCount;
import br.com.arbo.steamside.collections.TagsData;

public class RecentTagsXml {

	public static RecentTagsXml valueOf(TagsData tags)
	{
		RecentTagsXml xml = new RecentTagsXml();

		tags.recent().forEach(xml::add);

		return null;
	}

	void add(WithCount w)
	{
		tag.add(w.collection().name().value);
	}

	public final LinkedList<String> tag = new LinkedList<>();

}
