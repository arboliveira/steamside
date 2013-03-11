package br.com.arbo.org.apache.wicket.markup.html.pages;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.resource.IResourceStream;

import br.com.arbo.org.apache.wicket.util.template.EmptyTextTemplate;

public class EmptyPage extends WebPage
		implements IMarkupResourceStreamProvider {

	protected EmptyPage() {
		super();
	}

	protected EmptyPage(final PageParameters parameters) {
		super(parameters);
	}

	@Override
	public IResourceStream getMarkupResourceStream(
			final MarkupContainer container, final Class< ? > containerClass) {
		return new EmptyTextTemplate();
	}

}
