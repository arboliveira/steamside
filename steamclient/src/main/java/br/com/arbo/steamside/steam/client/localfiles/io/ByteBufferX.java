package br.com.arbo.steamside.steam.client.localfiles.io;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteBufferX {

	public String read__null_terminated_string() {
		int i = 0;
		while (true) {
			string_buffer[i] = buffer.get();
			if (string_buffer[i] == 0) break;
			i++;
		}
		return newString(i);
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

	public long read__int64_t() {
		return buffer.getLong();
	}

	public int position() {
		return buffer.position();
	}

	public final void position(int newPosition) {
		buffer.position(newPosition);
	}

	private String newString(final int i) {
		try {
			return new String(string_buffer, 0, i, "UTF8");
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public ByteBufferX(final ByteBuffer buffer, final int string_buffer_size) {
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		this.buffer = buffer;
		this.string_buffer = new byte[string_buffer_size];
	}

	private final ByteBuffer buffer;
	private final byte[] string_buffer;

}
