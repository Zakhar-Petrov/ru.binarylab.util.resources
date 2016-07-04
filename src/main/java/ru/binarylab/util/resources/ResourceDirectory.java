package ru.binarylab.util.resources;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

public abstract class ResourceDirectory extends AbstractResourceEntry {

	ResourceDirectory(URL url) throws ResourceException {
		super(normilizeURL(url));
		checkDirectoryURL();
	}

	private static URL normilizeURL(URL url) throws ResourceException {
		if (!url.getPath().endsWith("/")) {
			try {
				return new URL(url.toString() + "/");
			} catch (MalformedURLException e) {
				throw new ResourceException("Can't normilize URL: " + url.toString(), e);
			}
		}
		return url;
	}

	private void checkDirectoryURL() throws ResourceException {
		if (!ResourceUtils.isDirectory(toURL())) {
			throw new ResourceException("URL isn't directory: " + toURL().toString());
		}
	}

	public abstract Collection<ResourceEntry> getChildren() throws ResourceException;

}
