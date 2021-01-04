package br.com.arbo.steamside.xml.collections;

import br.com.arbo.steamside.collections.TagsQueries;

public class RecentTagsXmlFactory
{

	public static RecentTagsXml valueOf(TagsQueries tags)
	{
		RecentTagsXml xml = new RecentTagsXml();
	
		tags.recent().forEach(xml::add);
	
		return xml;
	}

}
