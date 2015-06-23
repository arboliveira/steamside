package br.com.arbo.steamside.api.kids;

import br.com.arbo.steamside.kids.Kid;

public class KidDTO
{

	public static KidDTO valueOf(Kid kid)
	{
		KidDTO dto = new KidDTO();
		dto.name = kid.getName().name;
		dto.user = kid.getUser().username();
		dto.inventory = kid.getCollection().value;
		return dto;
	}

	public String name;
	public String user;
	public String inventory;

}
