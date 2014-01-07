package br.com.arbo.steamside.json.appcollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.arbo.steamside.json.app.AppDTO;

public class AppCollectionDTO {

	public AppCollectionDTO(final Collection<AppDTO> apps) {
		this.apps = new ArrayList<AppDTO>(apps);
	}

	public List<AppDTO> apps;

}
