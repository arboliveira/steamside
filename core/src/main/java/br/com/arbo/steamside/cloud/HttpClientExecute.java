package br.com.arbo.steamside.cloud;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

interface HttpClientExecute
{

	HttpResponse execute(HttpUriRequest post) throws Unavailable;

}