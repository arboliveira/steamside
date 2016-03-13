package br.com.arbo.steamside.app.embedded.actuate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.steam.client.localfiles.appcache.DumpAppNamesGivenIds;

public class AppInfoVdfNamesMvcEndpoint extends EndpointMvcAdapter
{

	@RequestMapping(value = "/names", method = RequestMethod.GET)
	@ResponseBody
	public String filter(@RequestParam("ids") String ids)
	{
		return new DumpAppNamesGivenIds(StringUtils.split(ids, ','))
			.dumpToString();
	}

	public AppInfoVdfNamesMvcEndpoint(
		Endpoint< ? > delegate)
	{
		super(delegate);
	}

}
