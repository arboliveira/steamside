package br.com.arbo.steamside.steam.store;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.AppName;

public class SteamStoreSearchResultsParser
{

	static void parse(String content,
		SteamStoreSearchResultVisitor visitor)
	{
		Pattern pattern = SteamStoreSearchResultsParser.pattern();
		Matcher matcher = pattern.matcher(
			StringUtils.substringAfter(
				content, "<!-- List Items -->"));
		while (matcher.find())
		{
			visitor.each(extractOne(matcher));
		}
	}

	static App extractOne(Matcher matcher)
	{
		String appid = matcher.group(1);
		String name = StringEscapeUtils.unescapeHtml4(matcher.group(2));
		return new App(new AppId(appid), new AppName(name));
	}

	static Pattern pattern()
	{
		String aopen = Pattern.quote(
			"<a href=\"https://store.steampowered.com/app/");
		String appidgroup = "\\d+";
		String anything = ".*?";
		String nameopen = Pattern.quote("<span class=\"title\">");
		String namegroup = ".+?";
		String nameclose = Pattern.quote("</span>");

		String regex = aopen
			+ "(" + appidgroup + ")"
			+ anything + nameopen
			+ "(" + namegroup + ")"
			+ nameclose;

		return Pattern.compile(regex, Pattern.DOTALL);
	}

}
