## MapleGlory

This project is based on [Kinoko](https://github.com/iw2d/kinoko), 80% of logic is done by Kinoko's developer.
Main difference between the repos is the addition of A LOT of quests -- not all conform with GMS behaviour.

## Setup

Basic configuration is available via environment variables - the names and default values of the configurable options
are defined in [ServerConstants.java](src/main/java/mapleglory/server/ServerConstants.java) and [ServerConfig.java](src/main/java/mapleglory/server/ServerConfig.java), and in `.env`.

#### Java setup

Building the project requires Java 21 and maven.

```bash
# Build jar
$ mvn clean package
```