package br.com.arbo.steamside.data;

import java.util.ArrayList;

public class InMemorySteamsideDataBox {

	public void addListener(Listener listener) {
		listeners.add(listener);
	}

	public SteamsideData getData() {
		return data;
	}

	public void setData(SteamsideData data) {
		this.data = data;
		listeners.stream().forEach(l -> l.onChange(data));
	}

	public interface Listener {

		void onChange(SteamsideData data);

	}

	private SteamsideData data;

	private final ArrayList<Listener> listeners = new ArrayList<>(1);
}
