package ru.binarylab.util.resources.extractor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import ru.binarylab.util.resources.ResourceFile;

public class ResourceFileExtractor {

	private final ResourceFile resource;

	public ResourceFileExtractor(ResourceFile resource) {
		this.resource = resource;
	}

	public void extractToDirectory(Path destinationPath) throws ExtractException {
		File destinationDirectory = destinationPath.toFile();
		createDirectoryIfNotExists(destinationDirectory);

		File destinationFile = new File(destinationDirectory, resource.getName());
		if (isFileExist(destinationFile) && theSameFile(destinationFile)) {
			return;
		}

		extractAs(destinationFile);
	}

	private boolean theSameFile(File file) throws ExtractException {
		InputStream inputStreamA;
		try {
			inputStreamA = resource.openStream();
		} catch (IOException e) {
			throw new ExtractException("Can't read file from url:" + resource.toURL(), e);
		}

		try {
			InputStream inputStreamB;
			try {
				inputStreamB = new FileInputStream(file);
			} catch (IOException e) {
				throw new ExtractException("Can't read existing file:" + file.getAbsolutePath(), e);
			}

			try {
				try {
					return contentEquals(inputStreamA, inputStreamB);
				} catch (IOException e) {
					throw new ExtractException("Can't read file", e);
				}
			} finally {
				try {
					inputStreamB.close();
				} catch (IOException e) {
					// Do nothing
				}
			}
		} finally {
			try {
				inputStreamA.close();
			} catch (IOException e) {
				// Do nothing
			}
		}
	}

	private static boolean contentEquals(InputStream input1, InputStream input2) throws IOException {
		input1 = wrapWithBufferedInputStream(input1);
		input2 = wrapWithBufferedInputStream(input2);

		int b1;
		int b2;
		do {
			b1 = input1.read();
			b2 = input2.read();
			if (b1 != b2) {
				return false;
			}
		} while (b1 != -1);

		return true;
	}

	private static BufferedInputStream wrapWithBufferedInputStream(InputStream input) {
		if ((input instanceof BufferedInputStream)) {
			return (BufferedInputStream) input;
		}
		return new BufferedInputStream(input);
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
