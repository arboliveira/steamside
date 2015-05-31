package br.com.arbo.steamside.xml.autosave;

import br.com.arbo.steamside.data.ObservableSteamsideData;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.settings.file.SaveFile;

public class AutoSave
{

	public static SteamsideData connect(SteamsideData target, SaveFile saveFile)
	{
		ObservableSteamsideData observe = new ObservableSteamsideData(target);
		ParallelSave asyncSave = new ParallelSave(saveFile);
		observe.addObserver(() -> asyncSave.submit(observe));
		return observe;
	}

}
