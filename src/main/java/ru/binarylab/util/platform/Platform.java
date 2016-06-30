package ru.binarylab.util.platform;

public class Platform {

	private static Platform CURRENT;

	private final OperationSystem os;
	private final Architecture architecture;

	private Platform(OperationSystem os, Architecture architecture) {
		this.os = os;
		this.architecture = architecture;
	}

	public Architecture getArchitecture() {
		return architecture;
	}

	public OperationSystem getOperationSystem() {
		return os;
	}

	public static Platform getCurrent() throws UnsupportedPlatformException {
		if (CURRENT == null) {
			CURRENT = new Platform(OperationSystem.getCurrent(), Architecture.getCurrent());
		}
		return CURRENT;
	}

}
