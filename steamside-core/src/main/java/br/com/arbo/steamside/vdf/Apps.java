package br.com.arbo.steamside.vdf;

public class Apps {

	private final Region content;

	public Apps(Region content) {
		this.content = content;
	}

	public App app(String id) {
		Region rapp =
				content.region(id);
		return new App(rapp);
	}

}
