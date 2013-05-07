package br.com.arbo.steamside.app.instance;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ExtractUsernameTest {

	@SuppressWarnings("static-method")
	@Test
	public void happyDay() {
		final String json = "{\n" +
				"  \"username\" : \"kid\"\n" +
				"}";
		final String username = ExtractUsername.from(json);
		assertThat(username, equalTo("kid"));
	}
}
