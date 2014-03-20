package br.com.arbo.steamside.cloud;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

public class Cloud {

	private static HttpResponse client_execute(
			HttpClient client, HttpUriRequest post)
	{
		try {
			return client.execute(post);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static HttpResponse execute(HttpUriRequest post)
	{
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client_execute(client, post);
		return response;
	}

	private static InputStream getContent(final HttpEntity entity)
	{
		try {
			return entity.getContent();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void prepareRequest(HttpRequestBase post)
	{
		post.setHeader("User-Agent", USER_AGENT);
		post.setHeader(
				"Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
	}

	@Inject
	public Cloud(Host host) {
		this.host = host;
	}

	public String download() {
		URI uri = buildHttpGetURI();
		HttpGet get = new HttpGet(uri);
		prepareRequest(get);

		System.out.println("\nSending 'GET' request to URL : " + uri);

		HttpResponse response = execute(get);

		System.out.println(
				"Response Code : "
						+ response.getStatusLine().getStatusCode()
				);

		final HttpEntity entity = response.getEntity();
		final InputStream in = getContent(entity);
		String content = readGetContent(in);
		return content;
	}

	public void upload(String in) {
		URI uri = buildHttpPostURI();
		HttpPost post = new HttpPost(uri);
		prepareRequest(post);
		addURLParameters(in, post);

		System.out.println("\nSending 'POST' request to URL : " + uri);
		System.out.println("Post parameters : " + post.getEntity());

		HttpResponse response = execute(post);

		System.out.println(
				"Response Code : "
						+ response.getStatusLine().getStatusCode()
				);
	}

	private void addURLParameters(String in, HttpPost post) {
		try {
			host.addURLParameters(post, in);

		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private URI buildHttpGetURI() {
		try {
			return host.buildHttpGetURI();
		}
		catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private URI buildHttpPostURI() {
		try {
			return host.buildHttpPostURI();
		}
		catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private String readGetContent(InputStream in) {
		try {
			return host.readGetContent(in);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private final Host host;

	private static final String USER_AGENT = "Mozilla/5.0";

}
