package br.com.arbo.steamside.api.search;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.json.app.AppDTO;
import br.com.arbo.steamside.json.appcollection.AppCollectionDTO;

@Controller
@RequestMapping("search")
public class SearchController {

	@SuppressWarnings("static-method")
	@RequestMapping(value = "search.json", params = Params.PARAM_query)
	@ResponseBody
	public List<AppDTO> search(@RequestParam final String query) {
		final AppCollectionDTO dto = Search.search(query);
		return dto.apps;
	}

}
