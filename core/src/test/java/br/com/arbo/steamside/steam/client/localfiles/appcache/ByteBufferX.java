package br.com.arbo.steamside.steam.client.localfiles.appcache;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteBufferX {

	public String read__null_terminated_string() {
		int i = 0;
		while (true) {
			stringbuffer[i] = buffer.get();
			if (stringbuffer[i] == 0) break;
			i++;
		}
		final String sname = newString(i);
		return sname;
	}

	private String newString(final int i) {
		try {
			return new String(stringbuffer, 0, i, "UTF8");
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public byte getUnsignedChar() {
		return buffer.get();
	}

	public byte read__uint8_t() {
		return buffer.get();
	}

	public int read__uint32_t() {
		return buffer.getInt();
	}

	public long read__uint64_t() {
		return buffer.getLong();
	}

	public void skip(final int i) {
		buffer.position(buffer.position() + i);
	}

	public int position() {
		return buffer.position();
	}

	public ByteBufferX(final ByteBuffer buffer, final int stringbufferSize) {
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		this.buffer = buffer;
		stringbuffer = new byte[stringbufferSize];
	}

	private final ByteBuffer buffer;
	private final byte[] stringbuffer;

}
