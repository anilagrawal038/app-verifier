package com.san;

import java.io.File;

public class Test {

	static String[] folders = new String[] { "User1", "User2" };

	public static void main1(String[] args) {
		for (String path : folders) {
			File f = new File("test\\" + path);
			f.mkdir();
		}
	}

	public static void main2(String[] args) {
		File f = new File("Solutions\\Question\\Question1");
		for (File file : f.listFiles()) {
			System.out.println("Renaming : " + file.getName());
			file.renameTo(new File("Solutions\\Question\\Question1\\" + (file.getName().substring(0, (file.getName().length() - 7)) + ".tar")));
		}
	}

	public static void main(String[] args) {
		File f = new File("test");
		for (File file : f.listFiles()) {
			System.out.println("Adding Executable for : " + file.getName());
			new File(file.getAbsolutePath() + "\\Executable").mkdir();
		}
	}

}
