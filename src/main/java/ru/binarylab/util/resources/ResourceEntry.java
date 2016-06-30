package ru.binarylab.util.resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ResourceEntry {

	private final URL url;

	public ResourceEntry(URL url) {
		this.url = url;
	}

	public String getName() {
		return new File(url.getPath()).getName();
	}

	public URL toURL() {
		return url;
	}

	public InputStream openStream() throws IOException {
		return url.openStream();
	}

	@Override
	public String toString() {
		return url.getPath();
	}

}
