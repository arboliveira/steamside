package br.com.arbo.steamside.vdf;

class Region {

	private final Vdf vdf;
	private final Token head;

	Region(Vdf vdf, int from) {
		this.vdf = vdf;
		this.head = vdf.seek(from);
	}

	Region region(String name) {
		final int body = head.to + 1;
		int pos = body;
		while (true) {
			Token next = vdf.seek(pos);
			if (next.text().equals(name))
				return new Region(vdf, next.from);
			pos = next.to + 1;
		}
	}

	interface KeyValueVisitor {

		void visit(KeyValue kv);
	}

	void accept(KeyValueVisitor visitor) {
		final int body = head.to + 1;
		int pos = body;
		while (true) {
			Token key = vdf.seek(pos);
			Token value = vdf.seek(key.to + 1);
			visitor.visit(new KeyValue(key, value));
		}
	}
}
