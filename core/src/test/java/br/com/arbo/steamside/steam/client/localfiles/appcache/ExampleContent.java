package br.com.arbo.steamside.steam.client.localfiles.appcache;

import static br.com.arbo.steamside.steam.client.localfiles.appcache.KeyValues_h.KEYVALUES_TOKEN_SIZE;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
		buffer = new ByteBufferX(
				ch.map(MapMode.READ_ONLY, 0L, ch.size()),
				KEYVALUES_TOKEN_SIZE);
		go3();
	}

	private void go3() throws UnsupportedEncodingException {
		buffer.read__uint32_t(); // 0x07564426
		buffer.read__uint32_t(); // enum EUniverse
		while (true) {
			final int app_id = buffer.read__uint32_t();

			if (app_id == 0) break;

			final int data_size = buffer.read__uint32_t();

			final String padded =
					StringUtils.leftPad(
							String.valueOf(app_id),
							6);
			System.out.println(padded + ":" + data_size);

			final int p1 = buffer.position();

			buffer.read__uint32_t(); // unknown1
			buffer.read__uint32_t(); // last_updated
			buffer.read__uint64_t(); // unknown2;
			for (int i = 1; i <= 20; i++)
				buffer.read__uint8_t(); // unknown3[20];
			buffer.read__uint32_t(); // change_number;

			final int p2 = buffer.position();
			final int pdif = p2 - p1;
			final int remaining = data_size - pdif;

			while (true) {
				final byte section_number = buffer.read__uint8_t(); // enum EAppInfoSection
				if (section_number == 0) break;

				System.out.println("section number>>>>" + section_number);

				buffer.getUnsignedChar(); // 0x00

				final String mystery = buffer.read__null_terminated_string(); // 0x35 0x00 ..... appid! null terminated string?
				System.out.println("mystery>>>" + mystery);

				final KeyValues_cpp keyvalues = new KeyValues_cpp();
				keyvalues.readAsBinary(buffer);

				if (false) {
					/*
					final byte[] section_data = new byte[remaining];
					buffer.get(section_data);
					final String s = new String(section_data, "Cp1252");
					System.out.println(s);
					*/
				}
			}
		}
	}

	static private final char BYTE_0 = 0;
	static private final char BYTE_8 = 8;
	static private final String ZONE_SEPARATOR =
			new String(new char[] { BYTE_0, BYTE_8, BYTE_8 });

	private ByteBufferX buffer;
}
