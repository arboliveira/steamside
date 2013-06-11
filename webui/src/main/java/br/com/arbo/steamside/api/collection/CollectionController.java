package br.com.arbo.steamside.api.collection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.json.appcollection.AppCollectionDTO;

@Controller
@RequestMapping("collection")
public class CollectionController {

	/*    
	   CollectionService service;
	    
	   @Autowired
	   public CollectionController(CollectionService service) {
	       this.service = service;
	   }
	   */

	@SuppressWarnings("static-method")
	@RequestMapping("{id}")
	@ResponseBody
	public AppCollectionDTO getById(@PathVariable final String id) {
		//        return service.getById(id);
		return new AppCollectionDTO();
	}

}
