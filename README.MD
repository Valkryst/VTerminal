![Java CI with Maven](https://github.com/Valkryst/VTerminal/workflows/Java%20CI%20with%20Maven/badge.svg)

This project is currently under a full rewrite. Support is still available for
the version that is currently on the `master` branch.

## Table of Contents

* [Links](https://github.com/Valkryst/VTerminal#links)
* [Importing the Library](https://github.com/Valkryst/VTerminal#importing-the-library)
	* [Gradle](https://github.com/Valkryst/VTerminal#gradle)
	* [Maven](https://github.com/Valkryst/VTerminal#maven)
	* [Jar](https://github.com/Valkryst/VTerminal#jar)


## Links

* [Discord Channel](https://discord.gg/TwgPHSW)
* Documentation
    * [Roguelike Development Tutorial with VTerminal](https://github.com/Valkryst/VTerminal_Tutorial/wiki)
    * [JavaDoc Documentation](https://jitpack.io/com/github/Valkryst/VTerminal/latest/javadoc/)
    * [Usage Guide](https://github.com/Valkryst/VTerminal/wiki)
* [Performance Tips](https://valkryst.com/posts/13)
* [Resolving Compatibility & Render Issues](https://valkryst.com/posts/10)


## Importing the Library

VTerminal is hosted on the [JitPack package repository](https://jitpack.io/#Valkryst/VTerminal)
which supports both Gradle and Maven.

### Gradle

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
	implementation 'com.github.Valkryst:VTerminal:2020.12.28'
}
```

### Maven

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
    <version>2020.12.28</version>
</dependency>
```

### Jar

You can find the Jar file on the  [releases](https://github.com/Valkryst/VTerminal/releases)
page. Please ensure to use the `jar-with-dependencies` Jar.