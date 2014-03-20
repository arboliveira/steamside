package br.com.arbo.steamside.cloud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpUtil {

	static HttpResponse execute(HttpUriRequest post)
	{
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client_execute(client, post);
		return response;
	}

	static BufferedReader newBufferedReader(HttpResponse response)
	{
		final HttpEntity entity = response.getEntity();
		final InputStream content = getContentX(entity);

		BufferedReader rd =
				new BufferedReader(
						new InputStreamReader(
								content
						)
				);
		return rd;
	}

	static UrlEncodedFormEntity newUrlEncodedFormEntity(
			List<NameValuePair> urlParameters)
	{
		try {
			return new UrlEncodedFormEntity(urlParameters, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	static void prepareRequest(HttpRequestBase post)
	{
		post.setHeader("User-Agent", USER_AGENT);
		post.setHeader(
				"Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
	}

	static StringBuffer readResponse(HttpResponse response)
	{
		try {
			return readResponseX(response);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static HttpResponse client_execute(
			HttpClient client, HttpUriRequest post)
	{
		try {
			return client.execute(post);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static InputStream getContentX(final HttpEntity entity)
	{
		try {
			return entity.getContent();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static StringBuffer readResponseX(HttpResponse response)
			throws IOException
	{
		BufferedReader rd = newBufferedReader(response);

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null)
			result.append(line);
		return result;
	}

	private static final String USER_AGENT = "Mozilla/5.0";

}
