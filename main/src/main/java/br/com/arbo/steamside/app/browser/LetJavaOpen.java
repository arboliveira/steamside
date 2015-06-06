package br.com.arbo.steamside.app.browser;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import br.com.arbo.steamside.mapping.Api;
import br.com.arbo.steamside.mapping.Session;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;

public class LetJavaOpen implements WebBrowser
{

	private static void browse(URI address) throws IOException
	{
		Desktop.getDesktop().browse(address);
	}

	private static String inPublic(String name)
	{
		return "/public/" + name;
	}

	private static String replaceAddress(String template, String var,
		String replacement)
	{
		String searchString =
			StringUtils.substringBetween(
				template, "var " + var + " = \"", "\";");
		String replaced =
			StringUtils.replaceOnce(template, searchString, replacement);
		return replaced;
	}

	private static String replaceAddresses(String screen, int port)
	{
		String r = screen;
		r = replaceAddress(r, "urlSession", urlSession(port));
		r = replaceAddress(r, "urlLanding", urlLanding(port));
		return r;
	}

	private static String urlBase(int port)
	{
		return "http://localhost:" + port;
	}

	private static String urlLanding(int port)
	{
		return urlBase(port);
	}

	private static String urlSession(int port)
	{
		return "http://localhost:" + port
			+ "/" + Api.api
			+ "/" + Session.session
			+ "/" + Session.session_json;
	}

	@Inject
	public LetJavaOpen(Dir_userid dir_userid)
	{
		this.dir_userid = dir_userid;
	}

	@Override
	public void landing(final int port)
	{
		try
		{
			browse(new URI(urlLanding(port)));
		}
		catch (URISyntaxException | IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public void loading(int port)
	{
		try
		{
			File loading_try = loading_try(port);
			browse(loading_try.toURI());
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private File fileInLoadingDir(String name)
	{
		File userid = dir_userid.userid();
		File userid_Steamside_loading =
			new File(userid, "/Steamside/loading");
		userid_Steamside_loading.mkdirs();
		return new File(userid_Steamside_loading, name);
	}

	private File hydrateLoadingScreenHtml(int port) throws IOException
	{
		String name = LOADING_SCREEN_HTML;
		String screen = stringFromResourceInPublic(name);
		String replaced = replaceAddresses(screen, port);
		return writeToLoadingDir(name, replaced);
	}

	private void hydrateSteamsideLogoImage() throws IOException
	{
		String name = LOGO_IMAGE;
		File file = fileInLoadingDir(name);

		try (InputStream input = resourceInPublic(name))
		{
			Files.copy(
				input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
	}

	private File loading_try(int port) throws IOException
	{
		hydrateSteamsideLogoImage();
		return hydrateLoadingScreenHtml(port);
	}

	private InputStream resourceInPublic(String name)
	{
		return this.getClass().getResourceAsStream(inPublic(name));
	}

	private String stringFromResourceInPublic(String name) throws IOException
	{
		return IOUtils.toString(
			this.getClass().getResource(inPublic(name)), "UTF-8");
	}

	private File writeToLoadingDir(String name, String replaced)
		throws IOException
	{
		File file = fileInLoadingDir(name);
		FileUtils.write(file, replaced);
		return file;
	}

	private final Dir_userid dir_userid;
	private static final String LOADING_SCREEN_HTML = "LoadingScreen.html";

	private static final String LOGO_IMAGE = "steamside.png";
}
