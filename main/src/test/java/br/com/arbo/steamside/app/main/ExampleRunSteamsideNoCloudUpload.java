package br.com.arbo.steamside.app.main;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.app.context.SourcesFactory;
import br.com.arbo.steamside.app.context.SpringApplicationFactory;
import br.com.arbo.steamside.app.launch.SourcesCustomizer;
import br.com.arbo.steamside.cloud.CopySteamsideXmlToCloud;

class ExampleRunSteamsideNoCloudUpload
{

	public static void main(final String[] args)
	{
		SpringApplicationFactory.run(
			SourcesFactory.newInstance().sources(NoCloudUpload.class),
			args);
	}

	static class NoCloudUpload implements SourcesCustomizer
	{

		@Override
		public void customize(Sources s)
		{
			s.replaceWithImplementor(
				CopySteamsideXmlToCloud.class, UploadToSysout.class);
		}

	}

}
