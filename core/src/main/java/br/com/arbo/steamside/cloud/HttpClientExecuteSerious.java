package br.com.arbo.steamside.cloud;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;

class HttpClientExecuteSerious implements HttpClientExecute
{

	@Override
	public HttpResponse execute(HttpUriRequest post)
	{
		HttpClient client = HttpClientBuilder.create().build();

		try
		{
			return client.execute(post);
		}
		catch (UnknownHostException e)
		{
			throw new Unavailable(e);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

}