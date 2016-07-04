package ru.binarylab.util.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class ResourcesClassPath {

	private final ClassLoader classLoader;

	public ResourcesClassPath(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public List<ResourceEntry> getRsources(String path) throws ResourceException {
		URL parent = classLoader.getResource(path);
		if (ResourceUtils.isJarURL(parent)) {

		} else {
			File pathFile = new File(parent.getFile());
			if (pathFile.isDirectory()) {

			}
		}

		List<ResourceEntry> resources = new LinkedList<ResourceEntry>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(path)));
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				ResourceEntry resource = ResourceEntryFactory.fromURL(new URL(parent.toString() + "/" + line));
				resources.add(resource);
			}
		} catch (IOException e) {
			throw new ResourceException("Can't read resources", e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// nothing
			}
		}
		return resources;
	}

	public ResourceEntry getResource(String path) throws ResourceException {
		URL url = classLoader.getResource(path);
		return ResourceEntryFactory.fromURL(url);
	}

}
