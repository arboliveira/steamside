package br.com.arbo.steamside.steam.client.localfiles.digest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.Data_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Data_sharedconfig_vdf;

public class Spread {

	private final Future<Data_appinfo_vdf> f_appinfo;
	private final Future<Data_localconfig_vdf> f_localconfig;
	private final Future<Data_sharedconfig_vdf> f_sharedconfig;

	public Spread(Future<Data_appinfo_vdf> f_appinfo,
			Future<Data_localconfig_vdf> f_localconfig,
			Future<Data_sharedconfig_vdf> f_sharedconfig) {
		this.f_appinfo = f_appinfo;
		this.f_localconfig = f_localconfig;
		this.f_sharedconfig = f_sharedconfig;
	}

	public Combine converge() throws InterruptedException, ExecutionException {
		return new Combine(
				f_appinfo.get(), f_localconfig.get(), f_sharedconfig.get());
	}

}
