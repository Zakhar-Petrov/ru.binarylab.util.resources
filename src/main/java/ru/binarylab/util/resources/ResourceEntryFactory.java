package ru.binarylab.util.resources;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class ResourceEntryFactory {

	private ResourceEntryFactory() {
	}

	public static ResourceEntry fromURL(URL url) throws ResourceException {
		if (ResourceUtils.isDirectory(url)) {
			if (ResourceUtils.isJarURL(url)) {
				return new JarResourceDirectory(url);
			} else if (ResourceUtils.isFileURL(url)) {
				return new FSResourceDirectory(url);
			} else {
				throw new IllegalArgumentException("Unsupported protocol: " + url.getProtocol());
			}
		} else {
			return new ResourceFile(url);
		}
	}

	public static ResourceEntry fromFile(File file) throws ResourceException {
		URL url;
		try {
			url = file.toURI().toURL();
		} catch (MalformedURLException e) {
			throw new ResourceException("Can't convert File to URL", e);
		}
		if (file.isDirectory()) {
			return new FSResourceDirectory(url);
		} else {
			return new ResourceFile(url);
		}
	}

}
