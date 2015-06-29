package br.com.arbo.steamside.api.kids;

import br.com.arbo.opersys.username.Username;
import br.com.arbo.steamside.kids.Kid;
import br.com.arbo.steamside.kids.KidImpl;
import br.com.arbo.steamside.kids.KidName;
import br.com.arbo.steamside.types.CollectionName;

public class KidDTO
{

	public static KidDTO valueOf(Kid kid)
	{
		KidDTO dto = new KidDTO();
		dto.name = kid.getName().name;
		dto.user = kid.getUser().username();
		dto.inventory = kid.getCollection().value;
		dto.id = dto.name;
		return dto;
	}

	public Kid toKid()
	{
		return new KidImpl(
			new KidName(name),
			new Username(user),
			requireInventory());
	}

	private CollectionName requireInventory()
	{
		try
		{
			return new CollectionName(inventory);
		}
		catch (NullPointerException ex)
		{
			throw new RuntimeException(
				"Please select the games your kid can play");
		}
	}

	public String id;
	public String name;
	public String user;
	public String inventory;

}
