package br.com.arbo.steamside.container;

import java.io.File;

public interface MonitorableFile<T> {

	File file();

	T data();

}
