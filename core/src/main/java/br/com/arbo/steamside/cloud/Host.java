package br.com.arbo.steamside.cloud;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpPost;

public interface Host {

	void addURLParameters(HttpPost post, String in)
			throws UnsupportedEncodingException;

	URI buildHttpGetURI() throws URISyntaxException;

	URI buildHttpPostURI() throws URISyntaxException;

	String readGetContent(InputStream in) throws IOException;

}
