# opensalt-java
A simplified Java library for working with the Competencies and Academic Standards Exchange (CASE) OpenAPI  services.

# OpenSALT Java Library

## What is OpenSalt?

[OpenSalt](https://opensalt.net/api/doc/) provides an OpenAPI that allows a user to download various [Academic Standards](https://opensalt.net/cfdoc/) and competency frameworks.

Features
--------

  * Supports IMS Global CASE v1.0
  * Fully constructed and inflated document trees
  * Published on Maven Central Repository

Getting started
---------------
Including the Java library in your project

The easiest way to incorporate the library into your Java project is to use Maven. Simply add a new dependency to your `pom.xml`:

```xml
<dependency>
   <groupId>com.nfbsoftware</groupId>
   <artifactId>opensalt-java</artifactId>
   <version>1.0.0</version>
</dependency>
```
Usage
-----
Below you will find a number of basic examples to guide you through the use of the Java library.

**Basic Client Option**

```java
// Set the location of our CASE server
String hostDomain = "opensalt.net";
String hostScheme = "https";
int hostPort = 443;

// Init our client object with the host information
OpenSALTClient openSaltClient = new OpenSALTClient(hostDomain, hostPort, hostScheme);
```
