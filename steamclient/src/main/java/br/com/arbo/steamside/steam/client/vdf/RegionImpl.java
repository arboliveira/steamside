package br.com.arbo.steamside.steam.client.vdf;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;

import br.com.arbo.steamside.steam.client.vdf.KeyValueVisitor.Finished;

public class RegionImpl implements Region {

	RegionImpl(final Reader reader) {
		this.tokenizer = StreamTokenizerBuilder.build(reader);
	}

	@Override
	public void accept(final KeyValueVisitor visitor)
	{
		try {
			while (true)
				advance(visitor);
		} catch (final Finished ok) {
			//
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Region region(final String name) throws NotFound
	{
		class Find implements KeyValueVisitor {

			@Override
			public void onKeyValue(final String k, final String v)
					throws Finished
			{
				// Do nothing
			}

			@Override
			public void onSubRegion(final String k, final Region r)
					throws Finished
			{
				if (k.equalsIgnoreCase(name)) {
					found = r;
					throw new Finished();
				}
				skipPastEndOfRegion();
			}

			Region found;

		}

		final Find find = new Find();
		accept(find);
		if (find.found != null) return find.found;
		throw NotFound.name(name);
	}

	void skipPastEndOfRegion()
	{
		class DoNothing implements KeyValueVisitor {

			@Override
			public void onKeyValue(final String k, final String v)
					throws Finished
			{
				// 
			}

			@Override
			public void onSubRegion(final String k, final Region r)
					throws Finished
			{
				skipPastEndOfRegion();
			}

		}
		accept(new DoNothing());
	}

	private void advance(final KeyValueVisitor visitor)
			throws IOException, Finished
	{
		nextToken();
		final String key = tokenizer.sval;

		if (key == null) finish();

		nextToken();
		final String value = tokenizer.sval;

		if (value != null) {
			visitor.onKeyValue(key, value);
			return;
		}

		if (tokenizer.ttype != '{') throw new IllegalStateException();
		visitor.onSubRegion(key, RegionImpl.this);
	}

	private void finish() throws Finished
	{
		final int ttype = tokenizer.ttype;
		if (ttype == '}' || ttype == StreamTokenizer.TT_EOF)
			throw new Finished();
		throw new IllegalStateException();
	}

	private void nextToken() throws IOException
	{
		tokenizer.nextToken();
	}

	final StreamTokenizer tokenizer;
}
