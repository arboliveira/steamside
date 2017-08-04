package br.com.arbo.steamside.cloud;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;

public class CloudUploadSerious implements CloudUpload
{

	@Override
	public HttpResponse upload(HttpPost post) throws Unavailable
	{
		HttpClientExecute exec = new HttpClientExecuteSerious();

		HttpResponse response = exec.execute(post);

		return response;
	}

}
