![Java CI with Maven](https://github.com/Valkryst/VTerminal/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master)

This project does not require frequent updates as it is a look and feel for Swing, which is a stable framework. You can assume it works unless otherwise stated.

## Table of Contents

* [Links](https://github.com/Valkryst/VTerminal#links)
* [Installation](https://github.com/Valkryst/VTerminal#installation)
	* [Gradle](https://github.com/Valkryst/VTerminal#-gradle)
	* [Maven](https://github.com/Valkryst/VTerminal#-maven)
	* [sbt](https://github.com/Valkryst/VTerminal#-scala-sbt)
* [Media (Screenshots & Videos)](https://github.com/Valkryst/VTerminal#media)
* [Supported Components](https://github.com/Valkryst/VTerminal#supported-components)
* [Enhanced Components](https://github.com/Valkryst/VTerminal#enhanced-components)
* [Credits & Inspiration](https://github.com/Valkryst/VTerminal#credits--inspiration)

## Links

* [Discussions/Forum](https://github.com/Valkryst/VTerminal/discussions)
* Documentation
	* [Usage Guide](https://github.com/Valkryst/VTerminal/wiki)
* Related Projects
    * [VTerminalPaletteEditor](https://github.com/Valkryst/VTerminalPaletteEditor), which can be used to create or edit custom palettes for use with VTerminal. 
    * [VTerminalTestGUI](https://github.com/Valkryst/VTerminalTestGUI), which I use to visually test VTerminal.
    *  Chris Reuter's [demo](https://codeberg.org/suetanvil/vterm-demo-jruby), showing how VTerminal can be used with JRuby.
* [Performance Tips](https://www.valkryst.com/posts/13)
* [Resolving Compatibility & Render Issues](https://www.valkryst.com/posts/10)

## Installation

VTerminal is hosted on the [JitPack package repository](https://jitpack.io/#Valkryst/VTerminal)
which supports Gradle, Maven, and sbt.

### ![Gradle](https://i.imgur.com/qtc6bXq.png?1) Gradle

Add JitPack to your `build.gradle` at the end of repositories.

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Add VTerminal as a dependency.

```
dependencies {
	implementation 'com.github.Valkryst:VTerminal:2025.10.1'
}
```

### ![Maven](https://i.imgur.com/2TZzobp.png?1) Maven

Add JitPack as a repository.

``` xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
Add VTerminal as a dependency.

```xml
<dependency>
    <groupId>com.github.Valkryst</groupId>
    <artifactId>VTerminal</artifactId>
    <version>2025.10.1</version>
</dependency>
```

### ![Scala SBT](https://i.imgur.com/Nqv3mVd.png?1) Scala SBT

Add JitPack as a resolver.

```
resolvers += "jitpack" at "https://jitpack.io"
```

Add VTerminal as a dependency.

```
libraryDependencies += "com.github.Valkryst" % "VTerminal" % "2025.10.1"
```

## Media

Please note that all screenshots and videos _will_ look worse than if you were to run the example GUI yourself. This is primarily due to compression.

### Screenshots

* [JButton](https://i.imgur.com/cPxSsMt.png)
* [JCheckBox](https://i.imgur.com/wgAhykm.png)
* [JLabel](https://i.imgur.com/xLgL7ei.png)
* [JLayeredPane](https://i.imgur.com/eXLpSAU.png)
* [JPasswordField](https://i.imgur.com/pJ1krjB.png)
* [JProgressBar](https://i.imgur.com/YBuP0L7.png)
* [JRadioButton](https://i.imgur.com/Tt4UlLv.png)
* [JScrollPane](https://i.imgur.com/x0mFMsq.png)
* [VEditorPane](https://i.imgur.com/xTdco5g.png)
* [VPanel](https://i.imgur.com/9JOYN1e.png)
	* [With a blur shader.](https://i.imgur.com/0DK4WQj.png)
* [VTextArea](https://i.imgur.com/ZX9DkA7.png)
* [VTextField](https://i.imgur.com/FAKN1yP.png)
* [VTextPane](https://i.imgur.com/sxNXgjV.png)

### Videos

* [Click-through of the example GUI](https://youtu.be/dH1yRMyEL0s) used for
  testing VTerminal. This covers most, if not all the supported components.

## Supported Components

If there is a component that you'd like VTerminal to support, or if you're
experiencing any issues with the supported components, then please feel free to
[open an issue](https://github.com/Valkryst/VTerminal/issues/new).

* [JButton](https://docs.oracle.com/en/java/javase/15/docs/api/java.desktop/javax/swing/JButton.html)
* [JCheckBox](https://docs.oracle.com/en/java/javase/15/docs/api/java.desktop/javax/swing/JCheckBox.html)
* [JEditorPane](https://docs.oracle.com/en/java/javase/15/docs/api/java.desktop/javax/swing/JEditorPane.html)
* [JLabel](https://docs.oracle.com/en/java/javase/15/docs/api/java.desktop/javax/swing/JLabel.html)
* [JPanel](https://docs.oracle.com/en/java/javase/15/docs/api/java.desktop/javax/swing/JPanel.html)
* [JPasswordField](https://docs.oracle.com/en/java/javase/15/docs/api/java.desktop/javax/swing/JPasswordField.html)
* [JProgressBar](https://docs.oracle.com/en/java/javase/15/docs/api/java.desktop/javax/swing/JProgressBar.html)
* [JRadioButton](https://docs.oracle.com/en/java/javase/15/docs/api/java.desktop/javax/swing/JRadioButton.html)
* [JScrollPane](https://docs.oracle.com/en/java/javase/15/docs/api/java.desktop/javax/swing/JScrollPane.html)
* [JTextArea](https://docs.oracle.com/en/java/javase/15/docs/api/java.desktop/javax/swing/JTextArea.html)
* [JTextField](https://docs.oracle.com/en/java/javase/15/docs/api/java.desktop/javax/swing/JTextField.html)
* [JTextPane](https://docs.oracle.com/en/java/javase/15/docs/api/java.desktop/javax/swing/JTextPane.html)
* [JToolTip](https://docs.oracle.com/en/java/javase/15/docs/api/java.desktop/javax/swing/JToolTip.html)
* [VFrame](https://github.com/Valkryst/VTerminal/tree/master/src/main/java/com/valkryst/VTerminal/component/VFrame.java)
	* A subclass of _JFrame_ which allows for easier en/disabling of full screen
	  mode and which has a number of convenience methods.
* [VPanel](https://github.com/Valkryst/VTerminal/tree/master/src/main/java/com/valkryst/VTerminal/component/VPanel.java)
	* A subclass of _JPanel_ which allows for displaying a 2D grid of characters
	  in which each cell can display a unique fore/background color and
	  [code point](https://en.wikipedia.org/wiki/Code_point) (Unicode character).
	  
## Enhanced Components

Unless otherwise specified, the following components have an overridden
`paintComponent` method which ensures the component is rendered at a higher
quality than its default counterpart.

The following image shows a `JEditorPane` and a `VEditorPane` side-by-side.

![](https://i.imgur.com/6XfbVar.png)

Unfortunately, I was unable to find a fool-proof way of ensuring that all
components render with high-quality [RenderingHints](https://docs.oracle.com/en/java/javase/15/docs/api/java.desktop/java/awt/RenderingHints.html).
If a method is ever discovered, then these classes _may_ be depreciated and/or
removed from this repository.

* [VEditorPane](https://github.com/Valkryst/VTerminal/tree/master/src/main/java/com/valkryst/VTerminal/component/VEditorPane.java)
* [VPasswordField](https://github.com/Valkryst/VTerminal/tree/master/src/main/java/com/valkryst/VTerminal/component/VPasswordField.java)
* [VTextArea](https://github.com/Valkryst/VTerminal/tree/master/src/main/java/com/valkryst/VTerminal/component/VTextArea.java)
* [VTextField](https://github.com/Valkryst/VTerminal/tree/master/src/main/java/com/valkryst/VTerminal/component/VTextField.java)
* [VTextPane](https://github.com/Valkryst/VTerminal/tree/master/src/main/java/com/valkryst/VTerminal/component/VTextPane.java)
	  
## Credits & Inspiration

* Architecture
  * Robert Eckstein's ["Java SE Application Design With MVC"](https://www.oracle.com/technical-resources/articles/javase/application-design-with-mvc.html).
  * Juri Strumpflohner's ["Logical separation with MVC"](https://juristr.com/blog/2008/03/logical-separation-with-mvc/), a follow-up to Eckstein's article.
* [Filthy Rich Clients: Developing Animated and Graphical Effects for Desktop Java™ Applications](https://www.amazon.ca/Filthy-Rich-Clients-Developing-Applications/dp/0132413930/)
* Trystan's [AsciiPanel](https://github.com/trystan/AsciiPanel).
* [VFont](https://github.com/Valkryst/VTerminal/tree/master/src/main/java/com/valkryst/VTerminal/font/VFont.java)
	* The [FontGenerator](https://github.com/ZenDarva/AsylumRLE/blob/FpsRefactor/src/main/java/xyz/theasylum/zendarva/rle/FontGenerator.java) from ZenDarva's [AsylumRLE](https://github.com/ZenDarva/AsylumRLE).
	* The [FontRenderer](https://github.com/sunenielsen/tribaltrouble/blob/master/tools/classes/com/oddlabs/fontutil/FontRenderer.java) from Oddlabs' [Tribal Trouble](https://github.com/sunenielsen/tribaltrouble).
