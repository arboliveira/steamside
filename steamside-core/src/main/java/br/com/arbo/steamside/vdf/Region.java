package br.com.arbo.steamside.vdf;

import java.io.StreamTokenizer;
import java.io.StringReader;

class Region {

	private final Vdf vdf;
	private final Token head;
	private final int from;

	Region(Vdf vdf, int from) {
		this.vdf = vdf;
		this.from = from;
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

		void onKeyValue(String k, String v);

		void onSubRegion(String k, Region r);
	}

	/*
	void accept(KeyValueVisitor visitor) {
		final int body = head.to + 1;
		int pos = body;
		while (true) {
			Token key = vdf.seek(pos);
			Token value = vdf.seek(key.to + 1);
			visitor.visit(new KeyValue(key, value));
		}
	}
	*/

	void accept(KeyValueVisitor visitor) {
		StreamTokenizer parser = StreamTokenizerBuilder.build(new StringReader(
				vdf.content.substring(this.from)));

		// Read node name
		parser.nextToken();
		String name = parser.sval;

		parser.nextToken();
		String value = parser.sval;

		// Check if next token is value or open of branch
		if (value != null)
		{
			// If token is value, read value
			visitor.onKeyValue(name, value);

		} else if (parser.ttype == '{')
		{
			// If token is open branch, read child nodes

			Region sub = new Region(vdf, parser.lineno());
			visitor.onSubRegion(name, sub);

			while (parser.nextToken() != '}')
			{
				parser.pushBack(); // token is meaningful, becomes available again for parsing

				if (!branchNode.addNode(Node.parse(parser)))
				{
					throw new InvalidFileException(
							"Could not read node from file.");
				}
			}
			node = branchNode;
		}
	}
}
