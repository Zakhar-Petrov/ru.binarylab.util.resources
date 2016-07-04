package ru.binarylab.util.resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ResourceFileExtractor {

	private final ResourceFile resource;

	public ResourceFileExtractor(ResourceFile resource) {
		this.resource = resource;
	}

	public void extractToDirectory(Path destinationPath) throws ExtractException {
		File destinationDirectory = destinationPath.toFile();
		createDirectoryIfNotExists(destinationDirectory);

		File destinationFile = new File(destinationDirectory, resource.getName());
		if (isFileExist(destinationFile)) {
			throw new ExtractException("File already exists: " + destinationFile.getAbsolutePath());
		}

		extractAs(destinationFile);
	}

	private static void createDirectoryIfNotExists(File directory) throws ExtractException {
		if (!isDirectoryExist(directory)) {
			if (!directory.mkdirs()) {
				throw new ExtractException("Can't create directory" + directory.getAbsolutePath());
			}
		}
	}

	private static boolean isDirectoryExist(File directory) {
		return directory.exists() && directory.isDirectory();
	}

	private static boolean isFileExist(File file) {
		return file.exists() && file.isFile();
	}

	private void extractAs(File dstFile) throws ExtractException {
		InputStream input;

		try {
			input = resource.openStream();
		} catch (IOException e) {
			throw new ExtractException("Can't read file from url:" + resource.toURL(), e);
		}

		try {
			CopyOption[] options = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING };
			Files.copy(input, dstFile.toPath(), options);
		} catch (Exception ex) {
			tryRemoveFileIfExists(dstFile);
			throw new ExtractException("Can't copy file to:" + dstFile.toPath(), ex);
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				// Do nothing
			}
		}
	}

	private void tryRemoveFileIfExists(File file) {
		try {
			Files.deleteIfExists(file.toPath());
		} catch (Exception e) {
			// Do nothing
		}
	}

}
