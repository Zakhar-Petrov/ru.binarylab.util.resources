package ru.binarylab.util.resources;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ResourceUtils {

	private ResourceUtils() {
	}

	public static boolean isDirectory(URL url) throws ResourceException {
		if (isJarURL(url)) {
			JarURLConnection connection = getJarConnection(url);

			JarEntry jarEntry = getJarEntry(connection);

			if (jarEntry == null) {
				return true;
			}

			if (jarEntry.isDirectory()) {
				return true;
			}

			JarFile jarFile = getJarFile(connection);

			try {
				return jarFile.getInputStream(jarEntry) == null;
			} catch (IOException e) {
				throw new ResourceException("Resource not found", e);
			}
		} else if (isFileURL(url)) {
			File filePath = new File(url.getFile());
			return filePath.isDirectory();
		}

		throw new IllegalArgumentException("Unsupported protocol: " + url.getProtocol());
	}

	public static JarURLConnection getJarConnection(URL url) throws ResourceException {
		try {
			return (JarURLConnection) url.openConnection();
		} catch (IOException e) {
			throw new ResourceException("Resource not found", e);
		}
	}

	public static JarEntry getJarEntry(JarURLConnection connection) throws ResourceException {
		try {
			return connection.getJarEntry();
		} catch (IOException e) {
			throw new ResourceException("Resource not found", e);
		}
	}

	public static JarFile getJarFile(JarURLConnection connection) throws ResourceException {
		try {
			return connection.getJarFile();
		} catch (IOException e) {
			throw new ResourceException("Resource not found", e);
		}
	}

	public static boolean isJarURL(URL url) {
		return url.getProtocol().equals("jar");
	}

	public static boolean isFileURL(URL url) {
		return url.getProtocol().equals("file");
	}

}
