package br.com.arbo.steamside.api.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.eclipse.jdt.annotation.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.arbo.steamside.apps.NotFound;
import br.com.arbo.steamside.rungame.RunGame;
import br.com.arbo.steamside.rungame.Timeout;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.types.AppId;

@Controller
@RequestMapping(br.com.arbo.steamside.mapping.App.app)
public class AppController {

	@SuppressWarnings("static-method")
	@RequestMapping("{appid}/tag/{collection}")
	public void tag(
			@NonNull @PathVariable final String appid,
			@NonNull @PathVariable final String collection
			) throws Exception {

		final String x = appid + " --> " + collection;
		System.out.println(x);
		sendPost(x);

	}

	@SuppressWarnings("static-method")
	@RequestMapping("{appid}/" + br.com.arbo.steamside.mapping.App.run)
	public void run(
			@NonNull @PathVariable final String appid
			) throws NotAvailableOnThisPlatform, Timeout, NotFound {

		rungame.askSteamToRunGameAndWaitUntilItsUp(new AppId(appid));
		letLoadingAnimationRunForJustALittleLonger();

	}

	private static void letLoadingAnimationRunForJustALittleLonger() {
		try {
			Thread.sleep(4000);
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Inject
	private RunGame rungame;

	private final String USER_AGENT = "Mozilla/5.0";

	private void sendPost(String text) throws Exception {

		String url = "CLOUD_URL";

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);

		// add header
		post.setHeader("User-Agent", USER_AGENT);

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("text", text));

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		post.setHeader(
				"Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8"
				);

		HttpResponse response = client.execute(post);
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + post.getEntity());
		System.out.println("Response Code : " +
				response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null)
			result.append(line);

		System.out.println(result.toString());

	}

}
