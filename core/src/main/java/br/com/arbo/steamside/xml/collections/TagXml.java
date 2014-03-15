package br.com.arbo.steamside.xml.collections;

import br.com.arbo.steamside.collections.Tag;

public class TagXml {

	public static TagXml valueOf(Tag in) {
		TagXml xml = new TagXml();
		xml.appid = in.appid().appid;
		xml.notes = in.notes().map(v -> v.notes).orElse(null);
		return xml;
	}

	public String appid;

	public String notes;

}
