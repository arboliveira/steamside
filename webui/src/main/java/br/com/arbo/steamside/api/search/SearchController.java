package br.com.arbo.steamside.api.search;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.api.app.AppApi;
import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.api.app.AppDTOFactory;
import br.com.arbo.steamside.api.app.AppTagDTO;
import br.com.arbo.steamside.api.continues.Continues;
import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.steam.client.types.AppId;

@Controller
@RequestMapping("search")
public class SearchController {

	@RequestMapping(value = "search.json", params = { "query", "recent=true" })
	@ResponseBody
	public List<AppDTO> recent()
	{
		return this.continues.continues();
	}

	@SuppressWarnings("static-method")
	@RequestMapping(value = "search.json", params = "query")
	@ResponseBody
	public List<AppDTO> search(
			@RequestParam final String query)
	{
		if (query == null) throw new NullPointerException();

		List<AppApi> apps = Search.search(query);
		List<AppDTO> dtos = new ArrayList<>(apps.size());
		for (AppApi appApi : apps)
			dtos.add(toDto(appApi));

		return dtos;
	}

	private AppDTO toDto(AppApi appApi)
	{
		TagsQueries queries = tags;

		List<AppTagDTO> tagsDTO =
				AppDTOFactory.tags_jsonable(
						new AppId(appApi.appid()), queries);

		return new AppDTO(appApi, tagsDTO);
	}

	@Inject
	private Continues continues;

	@Inject
	private TagsData tags;

}
