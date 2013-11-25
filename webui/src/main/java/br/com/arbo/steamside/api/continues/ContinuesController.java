package br.com.arbo.steamside.api.continues;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.json.app.AppDTO;

@Controller
@RequestMapping("continues")
public class ContinuesController {

	@RequestMapping("continues.json")
	@ResponseBody
	public List<AppDTO> continues() {
		return this.continues.continues();
	}

	@Inject
	private Continues continues;
}
