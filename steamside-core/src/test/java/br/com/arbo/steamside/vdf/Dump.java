package br.com.arbo.steamside.vdf;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

final class Dump implements
		KeyValueVisitor {

	public static void main(String[] args) throws IOException {
		final String text = FileUtils.readFileToString(
				new File("etc/sharedconfig.vdf"));
		Vdf vdf = new Vdf(text);
		vdf.root().accept(new Dump());
	}

	@Override
	public void onSubRegion(String k, Region r) {
		System.out.println(k + ": REGION");
		r.accept(this);
	}

	@Override
	public void onKeyValue(String k, String v) throws Finished {
		System.out.println(k + ": " + v);
	}
}