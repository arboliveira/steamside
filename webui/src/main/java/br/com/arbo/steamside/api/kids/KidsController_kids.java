package br.com.arbo.steamside.api.kids;

import java.util.List;

public interface KidsController_kids
{

	KidDTO add(KidDTO payload);

	KidDTO delete(String id);

	List<KidDTO> jsonable();

	KidDTO update(String id, KidDTO payload);

}
