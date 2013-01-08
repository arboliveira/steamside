package br.com.arbo.steamside.vdf;

public class Vdf {

	final String content;
	private final Region root;

	public Vdf(String content) {
		this.content = content;
		this.root = new Region(this, 0);
	}

	public Region region(String name) {
		return root.region(name);
	}

	public String content() {
		return content;
	}

	public Region root() {
		return root;
	}

	Token seek(int from) {
		int open = nextq(from);
		int close = nextq(open + 1);
		return new Token(this, open, close);
	}

	private int nextq(int from) {
		int open = content.indexOf(q, from);
		if (open == -1) throw new TokenNotFound();
		return open;
	}

	private static final char q = '"';
}
