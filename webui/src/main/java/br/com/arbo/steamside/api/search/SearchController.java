package br.com.arbo.steamside.api.search;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.api.continues.Continues;

@Controller
@RequestMapping("search")
public class SearchController {

	@RequestMapping(value = "search.json", params = { "query", "recent=true" })
	@ResponseBody
	public List<AppDTO> recent() {
		return this.continues.continues();
	}

	@SuppressWarnings("static-method")
	@RequestMapping(value = "search.json", params = "query")
	@ResponseBody
	public List<AppDTO> search(
			@RequestParam final String query) {
		if (query == null) throw new NullPointerException();
		return Search.search(query);
	}

	@Inject
	private Continues continues;

}
