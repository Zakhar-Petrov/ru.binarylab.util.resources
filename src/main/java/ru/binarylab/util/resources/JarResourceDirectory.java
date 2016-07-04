package ru.binarylab.util.resources;

import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

class JarResourceDirectory extends ResourceDirectory {

	private String rootUrlString;

	private Map<String, List<ResourceEntry>> entriesMap;

	JarResourceDirectory(URL url) throws ResourceException {
		super(url);
	}

	@Override
	public Collection<ResourceEntry> getChildren() throws ResourceException {
		if (entriesMap == null) {
			initialize();
		}
		return entriesMap.get(toURL().toString());
	}

	protected void initialize() throws ResourceException {
		entriesMap = new HashMap<String, List<ResourceEntry>>();

		JarURLConnection connection = ResourceUtils.getJarConnection(toURL());
		JarFile jarFile = ResourceUtils.getJarFile(connection);
		rootUrlString = getRootUrlStringFromConnection(connection);

		Enumeration<JarEntry> jarEntries = jarFile.entries();
		while (jarEntries.hasMoreElements()) {
			JarEntry jarEntry = jarEntries.nextElement();
			addResource(jarEntry.getName());

		}
	}

	private void addResource(String resourcePath) throws ResourceException {
		String resourceDirectoryPath = getDirectoryPath(resourcePath);

		if (resourceDirectoryPath.equals(resourcePath)) {
			return;
		}

		String resourceDirectoryPathUrlString = rootUrlString + resourceDirectoryPath;
		List<ResourceEntry> resources = getResources(resourceDirectoryPathUrlString);

		String resourcePathUrlString = rootUrlString + resourcePath;
		try {
			resources.add(ResourceEntryFactory.fromURL(new URL(resourcePathUrlString)));
		} catch (MalformedURLException e) {
			throw new ResourceException("Can't recognize url: " + resourcePathUrlString, e);
		}
	}

	private List<ResourceEntry> getResources(String key) {
		List<ResourceEntry> resources = entriesMap.get(key);
		if (resources == null) {
			resources = new LinkedList<ResourceEntry>();
			entriesMap.put(key, resources);
		}
		return resources;
	}

	private String getRootUrlStringFromConnection(JarURLConnection connection) {
		return "jar:" + connection.getJarFileURL().toString() + "!/";
	}

	private String getDirectoryPath(String path) {
		int lastSlashIndex = path.lastIndexOf("/");
		String directoryPath = "";
		if (lastSlashIndex > -1) {
			directoryPath = path.substring(0, lastSlashIndex + 1);
		}
		return directoryPath;
	}

}
