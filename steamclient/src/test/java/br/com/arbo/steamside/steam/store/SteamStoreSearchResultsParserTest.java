package br.com.arbo.steamside.steam.store;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class SteamStoreSearchResultsParserTest
{

	@SuppressWarnings("static-method")
	@Test
	public void aHappyDay()
	{
		String term = "residue";

		String content =
			"<div class=\"search_results_count\">15 results match your search.</div>\r\n"
				+ "			<script>\r\n"
				+ "		g_strUnfilteredURL = 'https://store.steampowered.com/search/?term="
				+ term + "&ignore_preferences=1';\r\n" + "	</script>"
				+ "		<div id=\"search_resultsRows\">\r\n"
				+ "<!-- List Items -->\r\n"
				+ "		<a href=\"https://store.steampowered.com/app/265790/Residue_Final_Cut/?snr=1_7_7_151_150_1\""
				+ "  data-ds-appid=\"265790\"  >\r\n"
				+ "            <div class=\"responsive_search_name_combined\">\r\n"
				+ "                <div class=\"col search_name ellipsis\">\r\n"
				+ "         <span class=\"title\">Residue: Final Cut</span>\r\n"
				+ "                    <p>";

		String expected = "265790:Residue: Final Cut";

		assertParse(expected, content);
	}

	@SuppressWarnings("static-method")
	@Test
	public void excluded()
	{
		String term = "controversial";

		String content =
			"<div id=\"search_results_filtered_warning\" class=\"search_results_filtered_warning collapsed important\">\r\n"
				+ "			<div>102 results match your search. 2 titles "
				+ "(including <a href=\"https://store.steampowered.com/app/618010/Volleyball_Heaven/?snr=1_7_7_151\">"
				+ "Volleyball Heaven</a>) have been excluded based on your preferences.</div>\r\n"
				+ "		</div>\r\n"
				+ "			<script>\r\n"
				+ "		g_strUnfilteredURL = 'https://store.steampowered.com/search/?term="
				+ term + "&ignore_preferences=1';\r\n" + "	</script>"
				+ "		<div id=\"search_resultsRows\">\r\n"
				+ "<!-- List Items -->\r\n"
				+ "		<a href=\"https://store.steampowered.com/app/1184770/The_Political_Process/?snr=1_7_7_151_150_1\""
				+ "  data-ds-appid=\"1184770\" >\r\n"
				+ "            <div class=\"responsive_search_name_combined\">\r\n"
				+ "                <div class=\"col search_name ellipsis\">\r\n"
				+ "                    <span class=\"title\">The Political Process</span>\r\n"
				+ "                    <p>";

		String expected = "1184770:The Political Process";

		assertParse(expected, content);
	}

	private static void assertParse(String expected, String content)
	{
		ArrayList<App> actual = new ArrayList<>();

		SteamStoreSearchResultsParser.parse(content, actual::add);

		Assert.assertEquals(expected, actual.get(0).toString());
	}

}
