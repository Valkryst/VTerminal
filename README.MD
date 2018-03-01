[![MIT License](https://img.shields.io/badge/license-MIT_License-green.svg)](https://github.com/Valkryst/VTerminal/blob/master/LICENSE.md) ![](https://travis-ci.org/Valkryst/VTerminal.svg?branch=master) [![codecov](https://codecov.io/gh/Valkryst/VTerminal/branch/master/graph/badge.svg)](https://codecov.io/gh/Valkryst/VTerminal) [![Release](https://jitpack.io/v/Valkryst/VTerminal.svg)](https://jitpack.io/#Valkryst/VTerminal)

VPanel simulates a Unicode terminal display. It supports arbitrary fore/background colors,  terminal sizes, and allows
the use of any font.

The project is currently in it's BETA phase. During this phase, the API is being heavily reworked and refined. Code breaking changes will be committed.

## Links

* [JavaDoc Documentation](https://valkryst.github.io/VTerminal/)
* [Usage Guide](https://github.com/Valkryst/VTerminal/wiki)
    * [More Examples](https://github.com/Valkryst/VTerminal/tree/master/test/com/valkryst/VTerminal/samples)
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
        <version>2.20.0-BETA</version>
    </dependency>
    
You may also need to add VRadio as a dependency:

    <dependency>
	    <groupId>com.github.Valkryst</groupId>
	    <artifactId>VRadio</artifactId>
	    <version>1.2.0</version>
	</dependency>

### Jar

Jar files can be found on the [releases](https://github.com/Valkryst/VTerminal/releases) page.
