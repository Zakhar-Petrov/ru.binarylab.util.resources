package ru.binarylab.util.resources;

import java.io.File;
import java.net.URL;

public abstract class AbstractResourceEntry implements ResourceEntry {

	private final URL url;

	protected AbstractResourceEntry(URL url) {
		this.url = url;
	}

	public String getName() {
		return new File(url.getPath()).getName();
	}

	public URL toURL() {
		return url;
	}

	@Override
	public String toString() {
		return url.getPath();
	}

}
