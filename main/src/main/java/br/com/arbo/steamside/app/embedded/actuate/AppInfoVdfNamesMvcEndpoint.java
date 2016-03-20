package br.com.arbo.steamside.app.embedded.actuate;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.steam.client.localfiles.appcache.DumpAppNamesGivenIds;

@Component
public class AppInfoVdfNamesMvcEndpoint extends EndpointMvcAdapter
{

	@RequestMapping(value = "/names", method = RequestMethod.GET)
	@ResponseBody
	public String filter(@RequestParam("ids") String ids)
	{
		return dumpAppNamesGivenIds
			.dumpToString(StringUtils.split(ids, ','));
	}

	@Inject
	public AppInfoVdfNamesMvcEndpoint(
		AppInfoVdfNamesEndpoint delegate,
		DumpAppNamesGivenIds dumpAppNamesGivenIds)
	{
		super(delegate);
		this.dumpAppNamesGivenIds = dumpAppNamesGivenIds;
	}

	private final DumpAppNamesGivenIds dumpAppNamesGivenIds;

}
