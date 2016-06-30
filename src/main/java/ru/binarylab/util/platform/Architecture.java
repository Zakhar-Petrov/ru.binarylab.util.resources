package ru.binarylab.util.platform;

public enum Architecture {

	X86(), X86_64();

	public static Architecture getCurrent() throws UnsupportedPlatformException {
		String architecture = System.getProperty("os.arch");
		if (architecture.equals("i386") || architecture.equals("i686")) {
			return X86;
		} else if (architecture.equals("amd64") || architecture.equals("universal")) {
			return X86_64;
		} else {
			throw new UnsupportedPlatformException("Unsupported architecture: " + architecture);
		}
	}

}
