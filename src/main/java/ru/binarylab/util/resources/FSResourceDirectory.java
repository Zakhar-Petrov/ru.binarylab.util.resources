package ru.binarylab.util.resources;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

class FSResourceDirectory extends ResourceDirectory {

	FSResourceDirectory(URL url) throws ResourceException {
		super(url);
	}

	@Override
	public Collection<ResourceEntry> getChildren() throws ResourceException {
		List<ResourceEntry> resourceEntries = new LinkedList<ResourceEntry>();
		File root = new File(toURL().getFile());
		for (File file : root.listFiles()) {
			resourceEntries.add(ResourceEntryFactory.fromFile(file));
		}
		return resourceEntries;
	}

}
