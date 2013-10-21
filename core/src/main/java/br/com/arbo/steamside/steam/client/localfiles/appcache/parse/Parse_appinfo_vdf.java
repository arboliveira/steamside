package br.com.arbo.steamside.steam.client.localfiles.appcache.parse;

import br.com.arbo.steamside.steam.client.localfiles.appcache.Content_appinfo_vdf;

public class Parse_appinfo_vdf {

	public void parse() {
		vdf.accept(new ContentVisitor(parsevisitor));
	}

	public Parse_appinfo_vdf(
			final Content_appinfo_vdf vdf,
			final ParseVisitor visitor) {
		this.vdf = vdf;
		this.parsevisitor = visitor;
	}

	private final Content_appinfo_vdf vdf;
	private final ParseVisitor parsevisitor;

}
