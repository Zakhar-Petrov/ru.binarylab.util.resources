package ru.binarylab.util.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ResourceFile extends AbstractResourceEntry {

	ResourceFile(URL url) {
		super(url);
	}

	public InputStream openStream() throws IOException {
		return toURL().openStream();
	}

}
