![Java CI with Maven](https://github.com/Valkryst/VTerminal/workflows/Java%20CI%20with%20Maven/badge.svg)

This project is currently under a full rewrite. Support is still available for
the version that is currently on the `master` branch.

## Links

* [Discord Channel](https://discord.gg/TwgPHSW)
* Documentation
    * [Roguelike Development Tutorial with VTerminal](https://github.com/Valkryst/VTerminal_Tutorial/wiki)
    * [JavaDoc Documentation](https://jitpack.io/com/github/Valkryst/VTerminal/latest/javadoc/)
    * [Usage Guide](https://github.com/Valkryst/VTerminal/wiki)
* [Performance Tips](https://valkryst.com/posts/13)
* [Resolving Compatibility & Render Issues](https://valkryst.com/posts/10)

## Jar Files & Maven

The Maven dependency is hosted off of JitPack, so you will need to add JitPack as a repository before you add VTerminal as a dependency.

### Maven

JitPack ([Example](https://github.com/Valkryst/VTerminal/blob/master/pom.xml)):

    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>

Dependency:

    <dependency>
        <groupId>com.github.Valkryst</groupId>
        <artifactId>VTerminal</artifactId>
        <version>2020.12.10</version>
    </dependency>

### Jar

Jar files can be found on the [releases](https://github.com/Valkryst/VTerminal/releases) page.

Ensure that you use the Jar labelled "jar-with-dependencies" as it includes all of the required dependencies that VTerminal relies on.
