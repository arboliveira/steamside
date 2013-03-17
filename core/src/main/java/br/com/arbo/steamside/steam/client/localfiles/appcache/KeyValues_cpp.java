package br.com.arbo.steamside.steam.client.localfiles.appcache;

import static br.com.arbo.steamside.steam.client.localfiles.appcache.KeyValues_h.TYPE_NONE;
import static br.com.arbo.steamside.steam.client.localfiles.appcache.KeyValues_h.TYPE_NUMTYPES;
import static br.com.arbo.steamside.steam.client.localfiles.appcache.KeyValues_h.TYPE_STRING;
import br.com.arbo.steamside.vdf.KeyValueVisitor;
import br.com.arbo.steamside.vdf.KeyValueVisitor.Finished;
import br.com.arbo.steamside.vdf.Region;

public class KeyValues_cpp {

	public static void readAsBinary(
			final ByteBufferX buffer,
			final KeyValueVisitor visitor) {
		while (true) {
			final byte type = buffer.getUnsignedChar();
			if (type == TYPE_NUMTYPES) break;
			try {
				readNameValue(buffer, visitor, type);
			} catch (final Finished e) {
				break;
			}
		}

	}

	private static void readNameValue(final ByteBufferX buffer,
			final KeyValueVisitor visitor, final byte type) throws Finished {
		class RegionImpl implements Region {

			@Override
			public void accept(final KeyValueVisitor visitor) {
				readAsBinary(buffer, visitor);
			}

		}

		final String name = buffer.read__null_terminated_string();
		switch (type) {
			case TYPE_NONE:
				visitor.onSubRegion(name, new RegionImpl());
				break;
			case TYPE_STRING:
				final String value = buffer.read__null_terminated_string();
				visitor.onKeyValue(name, value);
				break;
			default:
				throw new IllegalStateException(String.valueOf(type));
		}
	}
}
