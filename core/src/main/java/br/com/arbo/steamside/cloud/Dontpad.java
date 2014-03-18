package br.com.arbo.steamside.cloud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class Dontpad {

	private static HttpResponse client_execute(HttpClient client, HttpPost post) {
		try {
			return client.execute(post);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static UrlEncodedFormEntity newUrlEncodedFormEntity(
			List<NameValuePair> urlParameters)
	{
		try {
			return new UrlEncodedFormEntity(urlParameters, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private static StringBuffer readResponse(HttpResponse response) {
		try {
			return readResponseX(response);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static StringBuffer readResponseX(HttpResponse response)
			throws IOException {
		BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null)
			result.append(line);
		return result;
	}

	@SuppressWarnings("static-method")
	public void post(String text) {

		String url = "CLOUD_URL";

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("text", text));

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);

		// add header
		post.setHeader("User-Agent", USER_AGENT);

		post.setEntity(newUrlEncodedFormEntity(urlParameters));

		post.setHeader(
				"Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8"
				);

		HttpResponse response = client_execute(client, post);
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + post.getEntity());
		System.out.println("Response Code : " +
				response.getStatusLine().getStatusCode());

		StringBuffer result = readResponse(response);

		System.out.println(result.toString());

	}

	private static final String USER_AGENT = "Mozilla/5.0";
}