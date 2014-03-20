package br.com.arbo.steamside.cloud;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXB;

import br.com.arbo.steamside.xml.SteamsideXml;

public class LoadDontpad {

	private static byte[] getBytes(String xml)
	{
		try {
			return xml.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private static SteamsideXml unmarshal(final InputStream stream)
	{
		return JAXB.unmarshal(stream, SteamsideXml.class);
	}

	@SuppressWarnings("static-method")
	public SteamsideXml load()
	{
		String xml = new Dontpad().get();
		InputStream stream = new ByteArrayInputStream(getBytes(xml));
		try {
			try {
				return unmarshal(stream);
			} finally {
				stream.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
