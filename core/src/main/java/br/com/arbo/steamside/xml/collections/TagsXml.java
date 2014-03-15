package br.com.arbo.steamside.xml.collections;

import java.util.LinkedList;

import br.com.arbo.steamside.collections.Tag;

public class TagsXml {

	void add(Tag in) {
		tag.add(TagXml.valueOf(in));
	}

	public final LinkedList<TagXml> tag = new LinkedList<TagXml>();

}
