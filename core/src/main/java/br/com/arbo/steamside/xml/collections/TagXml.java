package br.com.arbo.steamside.xml.collections;

import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.collections.Tag;

public class TagXml {

	public static TagXml valueOf(Tag in)
	{
		TagXml xml = new TagXml();
		xml.appid = in.appid().appid;
		in.notes().map(v -> v.notes).ifPresent(v -> xml.notes = v);
		return xml;
	}

	public String appid;

	public @Nullable String notes;

}
