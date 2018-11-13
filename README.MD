![](https://codebuild.us-east-1.amazonaws.com/badges?uuid=eyJlbmNyeXB0ZWREYXRhIjoiUXp5M3NYSlJRQlpUQ0hCdFkvUXdka0YrYnhMNzRlMWoySlVSOGM1RndVdFNHSGdQdTZiMFdjcHk5Ti9xa3daV2xLSkkwMDdxZStWMDNoN3RVc1dxN29zPSIsIml2UGFyYW1ldGVyU3BlYyI6Ik1ZK0pnK3pxcGE1d21JbFYiLCJtYXRlcmlhbFNldFNlcmlhbCI6MX0%3D&branch=master) [![Release](https://jitpack.io/v/Valkryst/VTerminal.svg)](https://jitpack.io/#Valkryst/VTerminal)


The project is currently in it's Soft release phase. During this phase, there *may* be code breaking changes if required.

## Links

* [Discord Channel](https://discord.gg/57dx68W)
* Documentation
    * [Roguelike Development Tutorial with VTerminal](https://github.com/Valkryst/VTerminal_Tutorial/wiki)
    * [JavaDoc Documentation](https://valkryst.github.io/VTerminal/)
    * [Usage Guide](https://github.com/Valkryst/VTerminal/wiki)
        * [More Examples](https://github.com/Valkryst/VTerminal/tree/master/samples) 
* [Resolving Compatibility & Render Issues](https://github.com/Valkryst/VTerminal/wiki/Resolving-Compatibility-&-Render-Issues)


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
        <version>3.6.0</version>
    </dependency>

**There is currently an issue with JitPack, preventing VTerminal from successfully building. Please use 'c17854b' as the version in your pom file.**

### Jar

Jar files can be found on the [releases](https://github.com/Valkryst/VTerminal/releases) page.

Ensure that you use the Jar labelled "jar-with-dependencies" as it includes all of the required dependencies that VTerminal relies on.

If you run into any issues, please open an issue or message me on the Discord. It's entirely possible that my build setup may be misconfigured.
