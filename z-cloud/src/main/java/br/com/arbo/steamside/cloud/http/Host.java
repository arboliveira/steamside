package br.com.arbo.steamside.cloud.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import org.apache.http.client.methods.HttpPost;

public interface Host
{

	void addURLParameters(HttpPost post, String in)
		throws UnsupportedEncodingException;

	URI buildHttpGetURI() throws Misconfigured;

	URI buildHttpPostURI() throws Misconfigured;

	String readGetContent(InputStream in) throws IOException;

}
