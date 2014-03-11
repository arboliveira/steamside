package br.com.arbo.steamside.xml.collections;

import br.com.arbo.steamside.collections.AppInCollection;

public class AppInCollectionXml {

	public static AppInCollectionXml valueOf(AppInCollection in) {
		AppInCollectionXml xml = new AppInCollectionXml();
		xml.appid = in.appid().appid;
		xml.notes = in.notes().notes;
		return xml;
	}

	public String appid;

	public String notes;

}
