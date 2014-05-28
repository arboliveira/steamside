package br.com.arbo.opersys.username;

import org.apache.commons.lang3.SystemUtils;

public class FromJava implements User {

	@Override
	public String username() {
		return SystemUtils.USER_NAME;
	}

}
