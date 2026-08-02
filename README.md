# Textfield resizes window

Resize a GUI window using textfields/textboxes.

![Textfield resizes window dark mode.](https://github.com/user-attachments/assets/a6373bcb-b61e-4f85-ba77-33a3fe15c9d0)

## Third-party libraries used

- [FlatLaf](https://formdev.com/flatlaf/)
- [Packr](https://github.com/libgdx/packr)

## Download/Installation

You can download the latest version in the [releases page](https://github.com/Synthird/textfield-resizes-window/releases/latest).

### Opening the exe (Windows only)

1. Download the exe file in the [releases page](https://github.com/Synthird/textfield-resizes-window/releases/latest).
2. Run the exe file and proceed with installation.

### Opening the AppImage (Linux only)

1. Download and extract the tar folder called ```linux-textfield-resizes-window```.
2. Open the properties of the ```textfield-resizes-window``` AppImage in the extracted folder to make sure it's allowed to run as a program.
3. Run the AppImage once it's allowed to do so.

### Opening the jar file (Any operating system)

1. You need a JDK or JRE installed on your device to open the jar file. Here are a couple ones to choose:
    
    - [Amazon Correto](https://aws.amazon.com/corretto/)
    - [Azul Zulu](https://www.azul.com/downloads/?package=jdk#zulu)
    - [Eclipse Adoptium Temurin](https://adoptium.net/)
    - [IBM Semeru Runtimes](https://developer.ibm.com/languages/java/semeru-runtimes/)
    - [Microsoft's openJDK](https://www.microsoft.com/openjdk)
    - [Oracle GraalVM](https://www.graalvm.org/downloads/)
    - [Oracle Java SE](https://www.oracle.com/java/technologies/downloads/)
    - [Red Hat's openJDK](https://developers.redhat.com/products/openjdk/download)
    - [SapMachine](https://sapmachine.io/)

2. Once you installed a JDK or JRE, download and unzip the zip folder called ```textfield-resizes-window.zip```.
3. Open the jar file in the unzipped folder.

## Running the source code

If you want to download and run the source code of this repository, you should download [FlatLaf](https://www.formdev.com/flatlaf/). (**Reccommended:** Use v3.7 or up.)

1. After downloading the source code, create a new folder called ```lib``` in the root directory of it.
2. Download the [FlatLaf jar file library](https://central.sonatype.com/artifact/com.formdev/flatlaf/overview).
3. Place the library into the lib folder.

![The FlatLaf library in the lib folder.](https://github.com/user-attachments/assets/f721eda5-e0a4-4d9b-b3ed-556846c81b10)

4. Go into your downloaded ```textfield-resizes-window``` folder in the terminal.
5. Run in the terminal:

```bash
java -cp lib/*.jar src/*.java
```

## License

This repository contains the MIT license. You must give credit if you are going to use its source code.
