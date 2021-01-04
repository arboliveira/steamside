package br.com.arbo.steamside.cloud.http;

import java.util.Optional;

import br.com.arbo.steamside.cloud.CloudSettingsFactory.Missing;
import br.com.arbo.steamside.cloud.http.LoadCloud.Disabled;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.xml.SteamsideXml_To_InMemorySteamsideData;

public class FromCloud {

	private Optional<SteamsideData> fromCloud()
	{
		try
		{
			return Optional.of(SteamsideXml_To_InMemorySteamsideData.toSteamsideData(loadCloud.load()));
			// TODO Success? Enqueue save to file
		}
		catch (Disabled e)
		{
			// Oh well, you know what you're doing, probably
			return Optional.empty();
		}
		catch (Unavailable e)
		{
			// TODO Send Warning to User Alert Bus: can't sync to the cloud
			e.printStackTrace();
			return Optional.empty();
		}
		catch (Missing e)
		{
			// TODO Send Suggestion to User Alert Bus: configure sync?
			e.printStackTrace();
			return Optional.empty();
		}
		catch (Misconfigured e)
		{
			// TODO Send Suggestion to User Alert Bus: fix sync configuration?
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	private LoadCloud loadCloud;
}
