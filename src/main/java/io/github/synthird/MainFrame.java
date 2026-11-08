package io.github.synthird;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.util.SystemFileChooser;
import com.formdev.flatlaf.util.SystemFileChooser.FileNameExtensionFilter;
import com.formdev.flatlaf.util.SystemInfo;

public class MainFrame extends JFrame {
	String originalWindowTitle = "Textfield resizes window";
	JTextField changeWindowTitle;

	BufferedImage transparentIcon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	String appPath = System.getProperty("jpackage.app-path");

	// Default window size
	int widthSize = 435,
			heightSize = 283;

	public MainFrame() {
		// File chooser
		SystemFileChooser fileChooser = new SystemFileChooser();
		fileChooser.setDialogTitle("Set icon");
		fileChooser.putPlatformProperty(SystemFileChooser.WINDOWS_FILE_NAME_LABEL, "Set icon to:");
		fileChooser.setApproveButtonText("Choose icon");
		fileChooser.setFileFilter(new FileNameExtensionFilter("png or jpg", "png", "jpg"));

		// Width panel
		JPanel widthPanel = setUpPanel(0);

		JSpinner widthField = setUpSpinner(widthPanel, widthSize);
		widthField.addChangeListener(e -> {
			widthSize = (int) widthField.getValue();
		});

		setUpLabel("px (Width)", widthPanel).addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent _) {
				focusTextfield(widthField);
			}
		});

		// Height panel
		JPanel heightPanel = setUpPanel(1);
	
		JSpinner heightField = setUpSpinner(heightPanel, heightSize);
		heightField.addChangeListener(e -> {
			heightSize = (int) heightField.getValue();
		});
	
		setUpLabel("px (Height)", heightPanel).addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent _) {
				focusTextfield(heightField);
			}
		});

		if (SystemInfo.isGNOME) {
			// Don't create the change icon buttons in Linux GNOME desktop environment
			for (JPanel panel : setRestUILevels()) {
				panel.setLocation(panel.getX(), panel.getY() - 35);
			}
		} else {
			// Add the change icon buttons
			JPanel changeIconPanel = setUpPanel(2);

			// Change icon
			setUpButton("Set custom window icon", changeIconPanel).addActionListener(e -> {
				// Choose a custom icon
				setWindowsIcon(transparentIcon);
				int hasChosenIcon = fileChooser.showOpenDialog(this);

				if (hasChosenIcon == SystemFileChooser.APPROVE_OPTION) {
					setIconImage(new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath()).getImage());
				} else {
					setWindowsIcon(null);
				}
			});

			// Reset icon
			setUpButton("Remove custom window icon", changeIconPanel).addActionListener(e -> {
				removeOrSetIconToDefault();
			});

			// Set up the rest of the UI and the window icon
			setRestUILevels();
			removeOrSetIconToDefault();
		}

		// Window setup
		setTitle(originalWindowTitle);
		changeWindowSize();
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		// Change widthField and heightField when the window is resized by mouse
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent _) {
				widthField.setValue(getWidth());
				heightField.setValue(getHeight());
			}
		});
	}

	private ArrayList<JPanel> setRestUILevels() {
		ArrayList<JPanel> panels = new ArrayList<>();

		// Change window title label
		JPanel changeWindowTitleLabelPanel = setUpPanel(3);

		setUpLabel("Change window title", changeWindowTitleLabelPanel).addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent _) {
				focusTextfield(changeWindowTitle);
			}
		});

		panels.add(changeWindowTitleLabelPanel);

		// Change window title textfield
		JPanel changeWindowTitlePanel = setUpPanel(4);
		changeWindowTitlePanel.setLocation(changeWindowTitlePanel.getX(), changeWindowTitlePanel.getY() - 15);

		changeWindowTitle = new JTextField(25);
		changeWindowTitle.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent _) {
				String changeWindowTitleText = changeWindowTitle.getText();

				if (changeWindowTitleText.isEmpty()) {
					setTitle(originalWindowTitle);
				} else {
					setTitle(changeWindowTitleText);
				}
			}
		});

		changeWindowTitlePanel.add(changeWindowTitle);
		panels.add(changeWindowTitlePanel);

		// Resizable checkbox
		JCheckBox resizable = setUpCheckBox("Resize with mouse and maximize/restore button");
		resizable.addActionListener(e -> {
			setResizable(!isResizable());
		});

		JPanel resizablePanel = setUpPanel(5);
		resizablePanel.setLocation(resizablePanel.getX(), resizablePanel.getY() - 20);
		resizablePanel.add(resizable);
		panels.add(resizablePanel);

		// Toggle between light and dark mode
		JCheckBox darkMode = setUpCheckBox("Dark mode");
		darkMode.addActionListener(e -> {
			if (FlatLaf.isLafDark()) {
				FlatLightLaf.setup();
			} else {
				FlatDarkLaf.setup();
			}

			FlatLaf.updateUI();
		});

		JPanel darkModePanel = setUpPanel(6);
		darkModePanel.setLocation(darkModePanel.getX(), darkModePanel.getY() - 28);
		darkModePanel.add(darkMode);
		panels.add(darkModePanel);

		// Resize & exit buttons
		JPanel bottomButtonsPanel = setUpPanel(7);
		bottomButtonsPanel.setLocation(bottomButtonsPanel.getX(), bottomButtonsPanel.getY() - 35);

		// Resize button
		setUpButton("Resize", bottomButtonsPanel).addActionListener(e -> {
			changeWindowSize();
		});

		// Exit button
		setUpButton("Exit", bottomButtonsPanel).addActionListener(e -> {
			System.exit(0);
		});

		panels.add(bottomButtonsPanel);
		return panels;
	}

	private void changeWindowSize() {
		setSize(widthSize, heightSize);
	}

	private void removeOrSetIconToDefault() {
		if (appPath != null) {
			Path installedDir = Paths.get(appPath).getParent().getParent(),
					iconPath = installedDir.resolve("lib").resolve("app").resolve("icon.png");
			File iconFile = iconPath.toFile();

			if (iconFile.exists()) {
				Image imageIcon = new ImageIcon(iconFile.getAbsolutePath()).getImage();
				setIconImage(imageIcon);
			} else {
				setIconImage(null);
			}
		} else {
			setIconImage(null);
		}
	}

	private void setWindowsIcon(Image image) {
		if (SystemInfo.isWindows) {
			if (getIconImage() == null || getIconImage() == transparentIcon) {
				setIconImage(image);
			}
		}
	}

	private void focusTextfield(JTextField textField) {
		textField.requestFocusInWindow();
	}

	private void focusTextfield(JSpinner textField) {
		textField.requestFocusInWindow();
	}

	// Setting up GUIs that have the same properties

	private JSpinner setUpSpinner(JPanel panel, int initialValue) {
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(initialValue, 0, Integer.MAX_VALUE, 1));
		spinner.setPreferredSize(new Dimension(75, 26));
		spinner.getEditor().getComponent(0).addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					changeWindowSize();
				}
			}
		});

		panel.add(spinner);
		return spinner;
	}

	private JCheckBox setUpCheckBox(String text) {
		JCheckBox checkbox = new JCheckBox(text, true);
		checkbox.setOpaque(false);
		return checkbox;
	}

	private JLabel setUpLabel(String text, JPanel panel) {
		JLabel label = new JLabel(text);
		panel.add(label);
		return label;
	}

	private JButton setUpButton(String text, JPanel buttonPanel) {
		JButton button = new JButton(text);
		buttonPanel.add(button);
		return button;
	}

	private JPanel setUpPanel(int level) {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBounds(0, 35 * level, 510, 35);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		add(panel);
		return panel;
	}
}
