package br.com.arbo.steamside.cloud;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;

public class Dontpad {

	private static URI buildGetURI()
	{
		try {
			URIBuilder builder = new URIBuilder(url + ".body.json");
			builder.addParameter("lastUpdate", "0");
			return builder.build();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private static Dont readValueX(BufferedReader rd)
	{
		try {
			return jackson.readValue(rd, Dont.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("static-method")
	public String get()
	{
		URI uri = buildGetURI();

		HttpGet post = new HttpGet(uri);

		HttpUtil.prepareRequest(post);

		System.out.println("\nSending 'GET' request to URL : " + uri);

		HttpResponse response = HttpUtil.execute(post);

		System.out.println(
				"Response Code : "
						+ response.getStatusLine().getStatusCode()
				);

		BufferedReader rd = HttpUtil.newBufferedReader(response);

		Dont dont = readValueX(rd);
		return dont.body;
	}

	@SuppressWarnings("static-method")
	public void post(String text)
	{
		String url = "CLOUD_URL";

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("text", text));

		HttpPost post = new HttpPost(url);

		post.setEntity(HttpUtil.newUrlEncodedFormEntity(urlParameters));

		HttpUtil.prepareRequest(post);

		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + post.getEntity());

		HttpResponse response = HttpUtil.execute(post);

		System.out.println(
				"Response Code : "
						+ response.getStatusLine().getStatusCode()
				);
		StringBuffer result = HttpUtil.readResponse(response);

		System.out.println(result.toString());
	}

	private static ObjectMapper jackson = new ObjectMapper();

	private static final String url = "CLOUD_URL";
}