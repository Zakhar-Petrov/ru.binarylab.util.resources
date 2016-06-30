package ru.binarylab.util.platform;

public enum OperationSystem {

	WINDOWS("dll"), LINUX("so");

	private final String libExtension;

	public static OperationSystem getCurrent() throws UnsupportedPlatformException {
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.equals("linux")) {
			return LINUX;
		} else if (osName.startsWith("win")) {
			return WINDOWS;
		} else {
			throw new UnsupportedPlatformException("Unsupported os: " + osName);
		}
	}

	private OperationSystem(String libExtension) {
		this.libExtension = libExtension;
	}

	public String getLibExtension() {
		return libExtension;
	}

}
