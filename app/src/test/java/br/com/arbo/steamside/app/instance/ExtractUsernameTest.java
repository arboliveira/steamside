package br.com.arbo.steamside.app.instance;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ExtractUsernameTest extends ExtractUsername {

	@SuppressWarnings("static-method")
	@Test
	public void happyday() {
		final String json = "{\n" +
				"  \"username\" : \"kid\"\n" +
				"}";
		final String username = ExtractUsername.from(json);
		assertThat(username, equalTo("kid"));
	}
}
