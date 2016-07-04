package ru.binarylab.util.resources;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ResourceFileExtractorTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	private File sourceDirectory;
	private File destinationDirectory;
	private File sourceTestFile;
	private File destinationTestFile;

	private ResourceFileExtractor extractor;

	@Before
	public void setUp() throws IOException {
		initSourceDirectory();
		createSourceFile();
		createDestinationDirecotry();
		initDestinationFile();
		newTestingInstance();
	}

	private void initSourceDirectory() throws IOException {
		sourceDirectory = folder.getRoot();
	}

	private void createSourceFile() throws IOException {
		sourceTestFile = folder.newFile("file.test");
	}

	private void createDestinationDirecotry() throws IOException {
		destinationDirectory = folder.newFolder("destination");
	}

	private void initDestinationFile() throws IOException {
		destinationTestFile = new File(destinationDirectory, sourceTestFile.getName());
	}

	private void newTestingInstance() throws IOException {
		ResourceFile resource = new ResourceFile(sourceTestFile.toURI().toURL());
		extractor = new ResourceFileExtractor(resource);
	}

	@Test
	public void testExtractToDirectory() throws ExtractException {
		Path destinationPath = destinationDirectory.toPath();
		extractor.extractToDirectory(destinationPath);
		assertTrue(destinationTestFile.exists());
	}

	@Test(expected = ExtractException.class)
	public void testExtractToDirectoryWithExistingFile() throws ExtractException {
		Path sourcePath = sourceDirectory.toPath();
		extractor.extractToDirectory(sourcePath);
	}

}
