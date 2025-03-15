package br.com.arbo.steamside.api.stats;

import br.com.arbo.steamside.steam.client.home.DumpSteamCategoriesFrom_SteamClientHome;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.DumpAppNamesGivenIds;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.DumpAppinfoVdfParse;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.DumpVdfStructureFrom_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.DumpAppNamesFrom_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.DumpVdfStructureFrom_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.DumpAppNamesFrom_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.DumpVdfStructureFrom_sharedconfig_vdf;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("stats")
public class StatsRestController {

    @RequestMapping(
            path="appinfo_vdf.txt",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String appinfo_vdf()
    {
        return dumpVdfStructureFrom_appinfo_vdf.dumpToString();
    }

    @RequestMapping(
            path="appinfo_vdf_apps.txt",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String appinfo_vdf_apps()
    {
        return dumpAppCacheParse.dumpToString();
    }

    @RequestMapping(
            path="vdfstructure_from_localconfig.txt",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String vdfstructure_from_localconfig()
    {
        return dumpVdfStructureFrom_localconfig_vdf.dumpToString();
    }

    @RequestMapping(
            path="appnames_from_localconfig.txt",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String appnames_from_localconfig()
    {
        return dumpAppNamesFrom_localconfig_vdf.dumpToString();
    }

    @RequestMapping(
            path="vdfstructure_from_sharedconfig.txt",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String vdfstructure_from_sharedconfig()
    {
        return dumpVdfStructureFrom_sharedconfig_vdf.dumpToString();
    }


    @RequestMapping(
            path="appnames_from_sharedconfig.txt",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String appnames_from_sharedconfig()
    {
        return dumpAppNamesFrom_sharedconfig_vdf.dumpToString();
    }


    @RequestMapping(
            path="steam_categories.txt",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String steam_categories()
    {
        return dumpSteamCategoriesFrom_Library.dumpToString();
    }

    @RequestMapping(
            path="appinfo_vdf_names_given_ids.txt/{ids}",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String appinfo_vdf_names_given_ids(@PathVariable String ids)
    {
        return dumpAppNamesGivenIds
                .dumpToString(StringUtils.split(ids, ','));
    }

    @Inject
    public StatsRestController(
            DumpVdfStructureFrom_appinfo_vdf dumpAppCacheContent,
            DumpAppinfoVdfParse dumpAppCacheParse,
            DumpVdfStructureFrom_localconfig_vdf dumpVdfStructureFrom_localconfig_vdf,
            DumpAppNamesFrom_localconfig_vdf dumpAppNamesFrom_localconfig_vdf,
            DumpVdfStructureFrom_sharedconfig_vdf dumpVdfStructureFrom_sharedconfig_vdf,
            DumpAppNamesFrom_sharedconfig_vdf dumpAppNamesFrom_sharedconfig_vdf,
            DumpSteamCategoriesFrom_SteamClientHome dumpSteamCategoriesFrom_Library,
			DumpAppNamesGivenIds dumpAppNamesGivenIds
    )
    {
        this.dumpVdfStructureFrom_appinfo_vdf = dumpAppCacheContent;
        this.dumpAppCacheParse = dumpAppCacheParse;
        this.dumpVdfStructureFrom_localconfig_vdf = dumpVdfStructureFrom_localconfig_vdf;
        this.dumpAppNamesFrom_localconfig_vdf = dumpAppNamesFrom_localconfig_vdf;
        this.dumpVdfStructureFrom_sharedconfig_vdf = dumpVdfStructureFrom_sharedconfig_vdf;
        this.dumpAppNamesFrom_sharedconfig_vdf = dumpAppNamesFrom_sharedconfig_vdf;
        this.dumpSteamCategoriesFrom_Library = dumpSteamCategoriesFrom_Library;
        this.dumpAppNamesGivenIds = dumpAppNamesGivenIds;
    }

    private final DumpVdfStructureFrom_appinfo_vdf dumpVdfStructureFrom_appinfo_vdf;
    private final DumpAppinfoVdfParse dumpAppCacheParse;
    private final DumpVdfStructureFrom_localconfig_vdf dumpVdfStructureFrom_localconfig_vdf;
    private final DumpAppNamesFrom_localconfig_vdf dumpAppNamesFrom_localconfig_vdf;
    private final DumpVdfStructureFrom_sharedconfig_vdf dumpVdfStructureFrom_sharedconfig_vdf;
    private final DumpAppNamesFrom_sharedconfig_vdf dumpAppNamesFrom_sharedconfig_vdf;
    private final DumpSteamCategoriesFrom_SteamClientHome dumpSteamCategoriesFrom_Library;
    private final DumpAppNamesGivenIds dumpAppNamesGivenIds;

}
