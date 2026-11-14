package io.github.synthird;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.util.SystemInfo;

import javax.swing.JFrame;

public class App {
	public static void main(String[] args) {
		if (SystemInfo.isLinux) {
			// Enable window decorations in Linux
			JFrame.setDefaultLookAndFeelDecorated(true);
		}

		FlatDarkLaf.setup();
		new MainFrame();
	}
}