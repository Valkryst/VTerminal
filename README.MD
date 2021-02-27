![Java CI with Maven](https://github.com/Valkryst/VTerminal/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master)
[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/Valkryst/VTerminal.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/Valkryst/VTerminal/context:java)
[![Total alerts](https://img.shields.io/lgtm/alerts/g/Valkryst/VTerminal.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/Valkryst/VTerminal/alerts/)


## Table of Contents

* [Links](https://github.com/Valkryst/VTerminal#links)
* [Installation](https://github.com/Valkryst/VTerminal#installation)
	* [Gradle](https://github.com/Valkryst/VTerminal#-gradle)
	* [Maven](https://github.com/Valkryst/VTerminal#-maven)
	* [sbt](https://github.com/Valkryst/VTerminal#-scala-sbt)
* [Supported Components](https://github.com/Valkryst/VTerminal#supported-components)
* [Enhanced Components](https://github.com/Valkryst/VTerminal#enhanced-components)
* [Credits & Inspiration](https://github.com/Valkryst/VTerminal#credits--inspiration)

## Links

* [Discord Channel](https://discord.gg/TwgPHSW)
* Documentation
	* [JavaDoc Documentation](https://jitpack.io/com/github/Valkryst/VTerminal/latest/javadoc/)
	* [Usage Guide](https://github.com/Valkryst/VTerminal/wiki)
* [Performance Tips](https://valkryst.com/posts/13)
* [Resolving Compatibility & Render Issues](https://valkryst.com/posts/10)

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
	implementation 'com.github.Valkryst:VTerminal:2021.02.26-hotfix'
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
    <version>2021.02.26-hotfix</version>
</dependency>
```

### ![Scala SBT](https://i.imgur.com/Nqv3mVd.png?1) Scala SBT

Add JitPack as a resolver.

```
resolvers += "jitpack" at "https://jitpack.io"
```

Add VTerminal as a dependency.

```
libraryDependencies += "com.github.Valkryst" % "VTerminal" % "2021.02.26-hotfix"
```

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