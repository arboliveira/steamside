package br.com.arbo.steamside.api.continues;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.arbo.steamside.api.app.AppCardDTO;

@RestController
@RequestMapping("continues")
public class ContinuesController {

	@RequestMapping("continues.json")
	public List<AppCardDTO> continues()
	{
		return this.continues.continues();
	}

	@Inject
	private Continues continues;
}
