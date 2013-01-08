package br.com.arbo.steamside.vdf;

public class Token {

	private final Vdf vdf;
	final int from;
	final int to;

	Token(Vdf vdf, int from, int to) {
		this.vdf = vdf;
		this.from = from;
		this.to = to;
	}

	public String text() {
		return vdf.content.substring(from + 1, to);
	}
}
