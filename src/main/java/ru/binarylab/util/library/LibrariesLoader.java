package ru.binarylab.util.library;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import ru.binarylab.util.platform.Platform;

public class LibrariesLoader {

	private final Path path;

	private final FileFilter librariesFilter = new FileFilter() {
		public boolean accept(File pathname) {
			return isLibrary(pathname);
		}
	};

	public LibrariesLoader(Path path) {
		this.path = path;
	}

	public void load() {
		File file = path.toFile();
		if (file.isDirectory()) {
			tryLoadLibrariesInDirectory(file);
		} else if (isLibrary(file)) {
			System.load(file.getAbsolutePath());
		}
	}

	private void tryLoadLibrariesInDirectory(File directory) {
		File[] files = directory.listFiles(librariesFilter);
		tryLoadLibraries(Arrays.asList(files));
	}

	private void tryLoadLibraries(Collection<File> libs) {
		for (File lib : libs) {
			try {
				System.load(lib.getAbsolutePath());
			} catch (UnsatisfiedLinkError e) {
				LinkedList<File> copy = new LinkedList<File>(libs);
				copy.remove(lib);
				tryLoadLibraries(copy);
				System.load(lib.getAbsolutePath());
			}
		}
	}

	private static boolean isLibrary(File file) {
		String extension = Platform.getCurrent().getOperationSystem().getLibExtension();
		return file.isFile() && file.getName().contains(extension);
	}

}
