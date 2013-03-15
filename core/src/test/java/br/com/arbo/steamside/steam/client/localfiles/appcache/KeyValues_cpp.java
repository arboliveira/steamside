package br.com.arbo.steamside.steam.client.localfiles.appcache;

import static br.com.arbo.steamside.steam.client.localfiles.appcache.KeyValues_h.TYPE_NONE;
import static br.com.arbo.steamside.steam.client.localfiles.appcache.KeyValues_h.TYPE_NUMTYPES;
import static br.com.arbo.steamside.steam.client.localfiles.appcache.KeyValues_h.TYPE_STRING;

public class KeyValues_cpp {

	private String name;
	private String value;

	public void readAsBinary(final ByteBufferX buffer) {

		while (true) {
			final byte type = buffer.getUnsignedChar();
			if (type == TYPE_NUMTYPES) {
				buffer.getUnsignedChar(); // another 8?!?
				break;
			}
			this.name = buffer.read__null_terminated_string();
			switch (type) {
				case TYPE_NONE:
					final KeyValues_cpp subregion = new KeyValues_cpp();
					subregion.readAsBinary(buffer);
					break;
				case TYPE_STRING:
					this.value = buffer.read__null_terminated_string();
					System.out.println(name + "=" + value);
					break;
				default:
					throw new IllegalStateException(String.valueOf(type));
			}
		}

	}
}
