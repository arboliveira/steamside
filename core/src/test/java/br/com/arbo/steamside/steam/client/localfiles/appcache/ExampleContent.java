package br.com.arbo.steamside.steam.client.localfiles.appcache;

import static br.com.arbo.steamside.steam.client.localfiles.appcache.KeyValues_h.KEYVALUES_TOKEN_SIZE;
import static br.com.arbo.steamside.steam.client.localfiles.appcache.KeyValues_h.TYPE_NONE;
import static br.com.arbo.steamside.steam.client.localfiles.appcache.KeyValues_h.TYPE_NUMTYPES;
import static br.com.arbo.steamside.steam.client.localfiles.appcache.KeyValues_h.TYPE_STRING;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import org.apache.commons.lang3.StringUtils;

public class ExampleContent {

	public static void main(final String[] args) throws IOException {
		new ExampleContent().go();
	}

	private void go() throws IOException {
		final FileInputStream f =
				new FileInputStream(File_appinfo_vdf.appinfo_vdf());
		try {
			go2(f);
		} finally {
			f.close();
		}
	}

	private void go2(final FileInputStream f) throws IOException {
		final FileChannel ch = f.getChannel();
		buffer = ch.map(MapMode.READ_ONLY, 0L, ch.size());
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		go3();
	}

	private void go3() throws UnsupportedEncodingException {
		read__uint32_t(); // 0x07564426
		read__uint32_t(); // enum EUniverse
		while (true) {
			final int app_id = read__uint32_t();

			if (app_id == 0) break;

			final int data_size = read__uint32_t();

			final String padded =
					StringUtils.leftPad(
							String.valueOf(app_id),
							6);
			System.out.println(padded + ":" + data_size);

			final int p1 = buffer.position();

			read__uint32_t(); // unknown1
			read__uint32_t(); // last_updated
			read__uint64_t(); // unknown2;
			for (int i = 1; i <= 20; i++)
				read__uint8_t(); // unknown3[20];
			read__uint32_t(); // change_number;

			final int p2 = buffer.position();
			final int pdif = p2 - p1;
			final int remaining = data_size - pdif;

			while (true) {
				final byte section_number = read__uint8_t(); // enum EAppInfoSection
				if (section_number == 0) break;

				System.out.println("section number>>>>" + section_number);

				buffer.get(); // 0x00

				final String mystery = read__null_terminated_string(); // 0x35 0x00 ..... appid! null terminated string?
				System.out.println("mystery>>>" + mystery);
				while (true) {
					final byte type = buffer.get();
					if (type == TYPE_NUMTYPES) {
						buffer.get(); // another 8?!?
						break;
					}
					final String k = read__null_terminated_string();
					switch (type) {
					case TYPE_NONE:
						////// new KeyValues();
						break;
					case TYPE_STRING: {
						final String v = read__null_terminated_string();
						System.out.println(k + "=" + v);
						break;
					}
					default:
						throw new IllegalStateException();
					}
				}

				if (false) {
					final byte[] section_data = new byte[remaining];
					buffer.get(section_data);
					final String s = new String(section_data, "Cp1252");
					System.out.println(s);
				}
			}
		}
	}

	private String read__null_terminated_string()
			throws UnsupportedEncodingException {
		int i = 0;
		while (true) {
			stringbuffer[i] = buffer.get();
			if (stringbuffer[i] == 0) break;
			i++;
		}
		final String sname = new String(stringbuffer, 0, i, "Cp1252");
		return sname;
	}

	private byte read__uint8_t() {
		return buffer.get();
	}

	private long read__uint64_t() {
		return buffer.getLong();
	}

	private void skip(final int i) {
		buffer.position(buffer.position() + i);
	}

	private int read__uint32_t() {
		return buffer.getInt();
	}

	static private final char BYTE_0 = 0;
	static private final char BYTE_8 = 8;
	static private final String ZONE_SEPARATOR =
			new String(new char[] { BYTE_0, BYTE_8, BYTE_8 });

	private MappedByteBuffer buffer;

	private final byte[] stringbuffer = new byte[KEYVALUES_TOKEN_SIZE];
}
