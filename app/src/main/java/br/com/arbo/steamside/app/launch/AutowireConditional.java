package br.com.arbo.steamside.app.launch;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import br.com.arbo.steamside.data.autowire.Autowire;
import br.com.arbo.steamside.data.autowire.AutowireSteamsideData;
import br.com.arbo.steamside.data.load.InitialLoad;
import br.com.arbo.steamside.xml.autosave.ParallelSave;

@EnableAutoConfiguration
@ConditionalOnBean(AutowireSteamsideData.class)
public class AutowireConditional {

	@Inject
	public AutowireConditional(
		InitialLoad initialLoad,
		AutowireSteamsideData data,
		ParallelSave parallelSave)
	{
		this.autowire = new Autowire(initialLoad, data, parallelSave);
	}

	final Autowire autowire;

}
