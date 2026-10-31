package io.github.synthird;

import com.formdev.flatlaf.FlatDarkLaf;

public class App {
	public static void main(String[] args) {
		FlatDarkLaf.setup();
		new MainFrame();
	}
}