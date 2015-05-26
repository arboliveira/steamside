package br.com.arbo.steamside.app.instance;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ExtractUsernameTest {

	@Test
	public void happyDay()
	{
		String json =
			"{\n"
				+ "  \"userName\" : \"kid\"\n"
				+ "}";
		String username = ExtractUsername.from(json);
		assertThat(username, equalTo("kid"));
	}
}
