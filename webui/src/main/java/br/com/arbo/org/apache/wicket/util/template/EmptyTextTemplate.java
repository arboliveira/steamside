package br.com.arbo.org.apache.wicket.util.template;

import java.util.Map;

import org.apache.wicket.util.template.TextTemplate;

public class EmptyTextTemplate extends TextTemplate {

	@Override
	public String getString() {
		return "";
	}

	@Override
	public TextTemplate interpolate(final Map<String, ? > variables) {
		return this;
	}
}