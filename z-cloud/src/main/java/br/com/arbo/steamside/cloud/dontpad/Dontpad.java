package br.com.arbo.steamside.cloud.dontpad;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.arbo.steamside.cloud.http.Host;
import br.com.arbo.steamside.cloud.http.Misconfigured;

public class Dontpad implements Host
{

	private static UrlEncodedFormEntity newUrlEncodedFormEntity(
		List<NameValuePair> urlParameters)
		throws UnsupportedEncodingException
	{
		return new UrlEncodedFormEntity(urlParameters, "UTF-8");
	}

	private static Dont readValueX(InputStream in) throws IOException
	{
		return jackson.readValue(in, Dont.class);
	}

	@Inject
	public Dontpad(DontpadSettingsFactory dontpadSettingsFactory)
	{
		this.settingsFactory = dontpadSettingsFactory;
	}

	@Override
	public void addURLParameters(HttpPost post, String text)
		throws UnsupportedEncodingException
	{
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("text", text));
		post.setEntity(newUrlEncodedFormEntity(urlParameters));
	}

	@Override
	public URI buildHttpGetURI() throws Misconfigured
	{
		try
		{
			return new URIBuilder(url() + ".body.json")
				.addParameter("lastUpdate", "0")
				.build();
		}
		catch (URISyntaxException e)
		{
			throw new Misconfigured(e);
		}
	}

	@Override
	public URI buildHttpPostURI() throws Misconfigured
	{
		try
		{
			return new URIBuilder(url()).build();
		}
		catch (URISyntaxException e)
		{
			throw new Misconfigured(e);
		}
	}

	@Override
	public String readGetContent(InputStream in) throws IOException
	{
		Dont dont = readValueX(in);
		return dont.body;
	}

	private String url() throws Misconfigured
	{
		return settingsFactory.read().address().orElseThrow(this::misconfigured)
			.url();
	}

	private Misconfigured misconfigured()
	{
		return new Misconfigured(new AddressMissing());
	}

	public static class AddressMissing extends RuntimeException
	{
		//
	}

	private static ObjectMapper jackson = new ObjectMapper();

	private final DontpadSettingsFactory settingsFactory;

}