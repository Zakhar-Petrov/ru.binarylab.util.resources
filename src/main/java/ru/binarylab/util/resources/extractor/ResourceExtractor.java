package ru.binarylab.util.resources.extractor;

import java.nio.file.Path;
import java.util.Collection;

import ru.binarylab.util.resources.ResourceDirectory;
import ru.binarylab.util.resources.ResourceEntry;
import ru.binarylab.util.resources.ResourceException;
import ru.binarylab.util.resources.ResourceFile;

public class ResourceExtractor {

	private final ResourceEntry resourceEntry;

	public ResourceExtractor(ResourceEntry resourceEntry) {
		this.resourceEntry = resourceEntry;
	}

	public void extractToDirectory(Path destinationPath) throws ExtractException {
		if (resourceEntry instanceof ResourceFile) {
			extractFile((ResourceFile) resourceEntry, destinationPath);
		} else if (resourceEntry instanceof ResourceDirectory) {
			extractDirectory((ResourceDirectory) resourceEntry, destinationPath);
		} else {
			throw new IllegalStateException(
					"Can't extract such type of resource: " + resourceEntry.getClass().getSimpleName());
		}
	}

	private void extractFile(ResourceFile resourceFile, Path destinationPath) throws ExtractException {
		new ResourceFileExtractor(resourceFile).extractToDirectory(destinationPath);
	}

	private void extractDirectory(ResourceDirectory resourceDirectory, Path destinationPath) throws ExtractException {
		Collection<ResourceEntry> resourceEntries;
		try {
			resourceEntries = resourceDirectory.getChildren();
		} catch (ResourceException e) {
			throw new ExtractException("Can't get resurce directory structure", e);
		}
		for (ResourceEntry resourceEntry : resourceEntries) {
			if (resourceEntry instanceof ResourceFile) {
				ResourceFile resourceFile = (ResourceFile) resourceEntry;
				extractFile(resourceFile, destinationPath);
			}
		}
	}

}
