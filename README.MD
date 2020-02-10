![](https://codebuild.us-east-1.amazonaws.com/badges?uuid=eyJlbmNyeXB0ZWREYXRhIjoiUXp5M3NYSlJRQlpUQ0hCdFkvUXdka0YrYnhMNzRlMWoySlVSOGM1RndVdFNHSGdQdTZiMFdjcHk5Ti9xa3daV2xLSkkwMDdxZStWMDNoN3RVc1dxN29zPSIsIml2UGFyYW1ldGVyU3BlYyI6Ik1ZK0pnK3pxcGE1d21JbFYiLCJtYXRlcmlhbFNldFNlcmlhbCI6MX0%3D&branch=master) [![Release](https://jitpack.io/v/Valkryst/VTerminal.svg)](https://jitpack.io/#Valkryst/VTerminal)[![Total alerts](https://img.shields.io/lgtm/alerts/g/Valkryst/VTerminal.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/Valkryst/VTerminal/alerts/)[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/Valkryst/VTerminal.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/Valkryst/VTerminal/context:java)


The project is currently in it's Soft release phase. During this phase, there *may* be code breaking changes if required.

## Links

* [Discord Channel](https://discord.gg/TwgPHSW)
* Documentation
    * [Roguelike Development Tutorial with VTerminal](https://github.com/Valkryst/VTerminal_Tutorial/wiki)
    * [JavaDoc Documentation](https://jitpack.io/com/github/Valkryst/VTerminal/latest/javadoc/)
    * [Usage Guide](https://github.com/Valkryst/VTerminal/wiki)
* [Performance Tips](https://valkryst.com/Sections/VTerminal/Articles/Performance%20Tips/index.html)
* [Resolving Compatibility & Render Issues](https://valkryst.com/Sections/VTerminal/Articles/Resolving%20Compatibility%20&%20Render%20Issues/index.html)


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
        <version>2020.02.09-break-alpha</version>
    </dependency>

### Jar

Jar files can be found on the [releases](https://github.com/Valkryst/VTerminal/releases) page.

Ensure that you use the Jar labelled "jar-with-dependencies" as it includes all of the required dependencies that VTerminal relies on.
