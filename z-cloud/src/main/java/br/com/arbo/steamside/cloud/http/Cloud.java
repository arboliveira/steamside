package br.com.arbo.steamside.cloud.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

public class Cloud
{

	private static InputStream getContent(HttpEntity entity)
	{
		try
		{
			return entity.getContent();
		}
		catch (IOException e)
		{
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

	public String download() throws Misconfigured, Unavailable
	{
		URI uri = buildHttpGetURI();
		HttpGet get = new HttpGet(uri);
		prepareRequest(get);

		log.info("Sending 'GET' request to URL : " + uri);

		HttpClientExecute exec = new HttpClientExecuteSerious();

		HttpResponse response = exec.execute(get);

		log.info(
			"Response Code : "
				+ response.getStatusLine().getStatusCode());

		final HttpEntity entity = response.getEntity();
		final InputStream in = getContent(entity);
		String content = readGetContent(in);
		return content;
	}

	public void upload(String in) throws Unavailable
	{
		URI uri = buildHttpPostURI();
		HttpPost post = new HttpPost(uri);
		prepareRequest(post);
		addURLParameters(in, post);

		log.info("\nSending 'POST' request to URL : " + uri);
		log.info("Post parameters : " + post.getEntity());

		HttpResponse response = cloudUpload.upload(post);

		log.info(
			"Response Code : "
				+ response.getStatusLine().getStatusCode());
	}

	private void addURLParameters(String in, HttpPost post)
	{
		try
		{
			host.addURLParameters(post, in);

		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException(e);
		}
	}

	private URI buildHttpGetURI() throws Misconfigured
	{
		return host.buildHttpGetURI();
	}

	private URI buildHttpPostURI() throws Misconfigured
	{
		return host.buildHttpPostURI();
	}

	private String readGetContent(InputStream in)
	{
		try
		{
			return host.readGetContent(in);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private static final String USER_AGENT = "Mozilla/5.0";

	@Inject
	public Cloud(Host host, CloudUpload cloudUpload)
	{
		this.host = host;
		this.cloudUpload = cloudUpload;
	}

	private final CloudUpload cloudUpload;

	private final Host host;

	private final Log log = LogFactory.getLog(this.getClass());
}
