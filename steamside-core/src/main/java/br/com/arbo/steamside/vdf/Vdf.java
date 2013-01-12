package br.com.arbo.steamside.vdf;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import br.com.arbo.steamside.vdf.Region.KeyValueVisitor;

public class Vdf {

	final String content;
	private final Region root;

	public Vdf(String content) {
		this.content = content;
		this.root = new Region(new RootReaderFactory(this));
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

	public static void main(String[] args) throws IOException {
		final String text = FileUtils.readFileToString(
				new File("etc/sharedconfig.vdf"));
		Vdf vdf = new Vdf(text);
		vdf.root().accept(new Dump());
	}

	static final class Dump implements
			KeyValueVisitor {

		@Override
		public void onSubRegion(String k, Region r) {
			System.out.println(k + ": REGION");
			r.accept(new Dump());
		}

		@Override
		public void onKeyValue(String k, String v) {
			System.out.println(k + ": " + v);
		}
	}

}
