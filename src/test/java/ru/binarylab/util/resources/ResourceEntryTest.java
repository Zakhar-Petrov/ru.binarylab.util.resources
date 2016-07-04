package ru.binarylab.util.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ResourceEntryTest {

	private static final String EXISTING_FILE_NAME = "existing.file";
	private static final String NOT_EXISTING_FILE_NAME = "not_existing.file";

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	private URL existingFileUrl;
	private ResourceFile existingResource;
	private URL notExistingFileUrl;
	private ResourceFile notExistingResource;

	@Before
	public void createResourceFile() throws MalformedURLException, IOException {
		existingFileUrl = folder.newFile(EXISTING_FILE_NAME).toURI().toURL();
		existingResource = new ResourceFile(existingFileUrl);
		notExistingFileUrl = new File(folder.getRoot(), NOT_EXISTING_FILE_NAME).toURI().toURL();
		notExistingResource = new ResourceFile(notExistingFileUrl);
	}

	@Test
	public void testGetName() {
		assertEquals("name", EXISTING_FILE_NAME, existingResource.getName());
		assertEquals("name", NOT_EXISTING_FILE_NAME, notExistingResource.getName());
	}

	@Test
	public void testToURL() {
		assertEquals("URL", existingFileUrl, existingResource.toURL());
		assertEquals("URL", notExistingFileUrl, notExistingResource.toURL());
	}

	@Test
	public void testOpenStream() throws IOException {
		InputStream inputStream = existingResource.openStream();
		assertNotNull("inputStream", inputStream);
	}

	@Test(expected = IOException.class)
	public void testOpenStreamWithNotExistingResource() throws IOException {
		InputStream inputStream = notExistingResource.openStream();
		assertNotNull("inputStream", inputStream);
	}

}
