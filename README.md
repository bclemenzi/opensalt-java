# opensalt-java
A simplified Java library for working with the Competencies and Academic Standards Exchange (CASE) OpenAPI  services.

# OpenSALT Java Library

## What is OpenSalt?

[OpenSalt](https://opensalt.net/api/doc/) provides an OpenAPI that allows a user to download various [Academic Standards](https://opensalt.net/cfdoc/) and competency frameworks.

Features
--------

  * Follows the IMS Global CASE v1.0 OpenAPI
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
   <version>1.0.4</version>
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
Library Functions
-----
The current set of functions were designed to allow a developer to mimic the functionality of the search system found online: [OpenSALT.net](https://opensalt.net/api/doc)

**List all of the CFDocuments available**

```java	
// Get a list of all the documents
List<CFDocument> documentList = client.getAllCFDocuments();

// Loop through the returned documents
int counter = 1;
for(CFDocument tmpCFDocument : documentList)
{
    System.out.println("[Doc " + counter + "] Identifier: " + tmpCFDocument.getIdentifier());
    System.out.println("[Doc " + counter + "] Title: " + tmpCFDocument.getTitle());
    System.out.println("[Doc " + counter + "] Description: " + tmpCFDocument.getDescription());
    System.out.println("[Doc " + counter + "] Notes: " + tmpCFDocument.getNotes());
    System.out.println("[Doc " + counter + "] Creator: " + tmpCFDocument.getCreator());
    System.out.println("[Doc " + counter + "] Adoption Status: " + tmpCFDocument.getAdoptionStatus());
    System.out.println("");
    
    counter++;
}
```

**List the CFDocuments available by page**

```java	
// Get a paged list (first 25 documents) of documents sorted by title
List<CFDocument> documentList = client.getCFDocuments(25, 0, "title", "desc");

// Loop through the returned documents
int counter = 1;
for(CFDocument tmpCFDocument : documentList)
{
    System.out.println("[Doc " + counter + "] Identifier: " + tmpCFDocument.getIdentifier());
    System.out.println("[Doc " + counter + "] Title: " + tmpCFDocument.getTitle());
    System.out.println("[Doc " + counter + "] Description: " + tmpCFDocument.getDescription());
    System.out.println("[Doc " + counter + "] Notes: " + tmpCFDocument.getNotes());
    System.out.println("[Doc " + counter + "] Creator: " + tmpCFDocument.getCreator());
    System.out.println("[Doc " + counter + "] Adoption Status: " + tmpCFDocument.getAdoptionStatus());
    System.out.println("");
    
    counter++;
}
```

**Get a single CFDocument**

```java	
// Get a single document by it's identifier
CFDocument tmpCFDocument = client.getCFDocument("c5fb0812-d7cb-11e8-824f-0242ac160002");

if(tmpCFDocument != null)
{
    System.out.println("Identifier: " + tmpCFDocument.getIdentifier());
    System.out.println("Title: " + tmpCFDocument.getTitle());
    System.out.println("Description: " + tmpCFDocument.getDescription());
    System.out.println("Notes: " + tmpCFDocument.getNotes());
    System.out.println("Creator: " + tmpCFDocument.getCreator());
    System.out.println("Adoption Status: " + tmpCFDocument.getAdoptionStatus());
}
```

**Get an entire framework package**

```java	
// Get an entire framework package
List<CFItem> cfItems = client.getCFPackages("c5fb0812-d7cb-11e8-824f-0242ac160002");
            
for(CFItem tmpCFItem : cfItems)
{
    if(tmpCFItem.getCFItemType() == null)
    {
        System.out.println("Identifier: " + tmpCFItem.getIdentifier() + " [" + tmpCFItem.getCFItemType() + "] " + tmpCFItem.getFullStatement());
    }
}
```

**Get an single item**

```java	
// Get an single item
CFItem tmpCFItem = client.getCFItem("5ad1a3fe-f280-11e8-9cff-0242ac140002");

if(tmpCFItem != null)
{
    System.out.println("Identifier: " + tmpCFItem.getIdentifier());
    System.out.println("HumanCodingScheme: " + tmpCFItem.getHumanCodingScheme());
    System.out.println("FullStatement: " + tmpCFItem.getFullStatement());
    System.out.println("Notes: " + tmpCFItem.getNotes());
}
```

**Get all item associations**

```java	
// Get all the associations for a given item
List<CFAssociation> tmpAssociations = client.getCFItemAssociations("18eff32c-d7cc-11e8-824f-0242ac160002");

for(CFAssociation tmpCFAssociation : tmpAssociations)
{
    System.out.println("Identifier: " + tmpCFAssociation.getIdentifier());
}
```

**Get a full hierarchical standard document.  It is recommended that you cache the results as this call can take a long time.**

```java	
// Get a full hierarchical standard document
Standard fullStandardsDocument = client.getFullHierarchicalStandard("c5fb0812-d7cb-11e8-824f-0242ac160002");

if(fullStandardsDocument != null)
{
    System.out.println(fullStandardsDocument.getDocumentTitle());
}
```