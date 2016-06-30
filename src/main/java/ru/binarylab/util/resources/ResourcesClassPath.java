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

	public List<ResourceEntry> getRsources(String path) throws IOException {
		URL parent = classLoader.getResource(path);
		File file = new File(parent.getFile());
		file.isDirectory();
		file.isFile();
		List<ResourceEntry> resources = new LinkedList<ResourceEntry>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(path)));
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				resources.add(new ResourceEntry(new URL(parent.toString() + "/" + line)));
			}
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// nothing
			}
		}
		return resources;
	}

}
