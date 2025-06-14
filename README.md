# opensalt-java
A simplified Java library for working with the Competencies and Academic Standards Exchange (CASE) OpenAPI  services.

# OpenSALT Java Library

## What is OpenSalt?

[OpenSalt](https://opensalt.net/api/doc/) provides an OpenAPI that allows a user to download various [Academic Standards](https://opensalt.net/cfdoc/) and competency frameworks.

Features
--------

  * Follows the IMS Global CASE v1.0 OpenAPI
  * Fully constructed and inflated document trees
  * Standards crosswalking with semantic comparisons
  * oAuth support to work with IMS Global's [CASE Network](https://casenetwork.imsglobal.org/cfdoc/)
  * Support for [Public Consulting Group's Crosswalk Services](https://api-stg.opensalt.net/api/doc)
  * Published on Maven Central Repository

Getting started
---------------
Including the Java library in your project

The easiest way to incorporate the library into your Java project is to use Maven. Simply add a new dependency to your `pom.xml`:

```xml
<dependency>
   <groupId>com.nfbsoftware</groupId>
   <artifactId>opensalt-java</artifactId>
   <version>1.0.44</version>
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
**Authenticated Client Option**

```java
// Set the location of our CASE server
String hostDomain = "casenetwork.imsglobal.org";
String hostScheme = "https";
int hostPort = 443;

// Set the server's location and credentials
String fullAuthenticationUrl = "https://oauth2-case.imsglobal.org/oauth2server/clienttoken";
String clientId = "your client id here";
String clientSecret = "your client secret here";
String grantType = "client_credentials";
String scope = "http://purl.imsglobal.org/casenetwork/case/v1p0/scope/core.readonly http://purl.imsglobal.org/casenetwork/case/v1p0/scope/all.readonly";

// Init our client object with the host and client information
OpenSALTClient openSaltClient = new OpenSALTClient(hostDomain, hostPort, hostScheme);

// Set your account credentials to acquire an access token.
client.setCredentials(fullAuthenticationUrl, clientId, clientSecret, grantType, scope);
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
CFPackages cfPackages = client.getCFPackages("c5fb0812-d7cb-11e8-824f-0242ac160002");

// Get the items for that package
List<CFItem> cfItems = cfPackages.getCFItems();

for(CFItem tmpCFItem : cfItems)
{
    if(tmpCFItem.getCFItemType() == null)
    {
        System.out.println("Identifier: " + tmpCFItem.getIdentifier() + " [" + tmpCFItem.getCFItemType() + "] " + tmpCFItem.getFullStatement());
    }
}
```

**Get an entire framework of package items**

```java	
// Get an entire framework package
List<CFItem> cfItems = client.getCFPackageItems("c5fb0812-d7cb-11e8-824f-0242ac160002");
            
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

**Get a partial hierarchical standard document starting from a child level.  It is recommended that you cache the results as this call can take a long time.**

```java	
// Get a partial hierarchical standard document by passing in a CFItem identifier
Standard partialStandardsDocument = client.getFullHierarchicalStandard("5ad17357-f280-11e8-9cff-0242ac140002");

if(partialStandardsDocument != null)
{
    System.out.println(partialStandardsDocument.getDocumentTitle());
}
```

**Crosswalk two standards with a semantic comparison output**

```java	
// CFDocument used as the bridge between two standards in separate documents.
String rosettaDocumentId = "c5fb0812-d7cb-11e8-824f-0242ac160002";

// Standard (CFItem) from document one
String fromItemId = "5acff4b8-f280-11e8-9cff-0242ac140002";

// Standard (CFItem) from document two
String toItemId = "5ca43735-f280-11e8-9cff-0242ac140002";
            
// Get the crosswalk between two standards
Crosswalk tmpCrosswalk = client.getCFItemCrosswalk(rosettaDocumentId, fromItemId, toItemId);

if(tmpCrosswalk != null)
{
    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = mapper.writeValueAsString(tmpCrosswalk);
    
    System.out.println(jsonInString);
}
```

**Crosswalk a single standard against an entire document with a semantic comparison output**

```java	
// CFDocument used as the bridge between two standards in separate documents.
// CFDocument = PCG Compendium for Mathematics
String rosettaDocumentId = "c5fb0812-d7cb-11e8-824f-0242ac160002";

// Standard (CFItem) from Tennessee Academic Standards: Mathematics - 1.OA.A.1
String fromItemId = "5ca44a05-f280-11e8-9cff-0242ac140002";

// Cross walked against another states CFDcoument
// CFDocument = Pennsylvania Core Math Standards
String toDcoumentId = "4d6d3d12-f280-11e8-9cff-0242ac140002";

// Pass in a CFItem ID to get the subset of the document
List<Crosswalk> tmpCrosswalks = client.getCFItemCrosswalks(rosettaDocumentId, fromItemId, toDcoumentId);

int counter = 1;
for(Crosswalk tmpCrosswalk : tmpCrosswalks)
{
    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = mapper.writeValueAsString(tmpCrosswalk);
    
    System.out.println("Crosswalk " + counter + ": " + jsonInString);
    
    counter++;
}
```


**PCG Crosswalk a single standard against an entire document with a semantic comparison output**

```java	
// Init the IMS Global client 
OpenSALTClient openSaltClient = new OpenSALTClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
openSaltClient.setCredentials(AUTHENTICATION_URL, CLIENT_ID, CLIENT_SECRET, GRANT_TYPE, SCOPE);
    
// Init the PCG Crosswalk Service client         
CrosswalkClient crosswalkClient = new CrosswalkClient(PCG_HOST_DOMAIN, PCG_HOST_PORT, PCG_HOST_SCHEME);
crosswalkClient.setCredentials(PCG_AUTHENTICATION_URL, PCG_CLIENT_ID, PCG_CLIENT_SECRET, PCG_GRANT_TYPE, PCG_SCOPE);

// Pennsylvania Core English Language Arts CFItem id we'd like to crosswalk from
String fromItemId = "3feec684-d7cc-11e8-824f-0242ac160002";

// Tennessee Academic Standards: English Language Arts
String toDocumentId = "c607fa0c-d7cb-11e8-824f-0242ac160002";

PCGCrosswalk tmpPCGCrosswalk = client.crosswalkByIdentifier(fromItemId, toDocumentId);

if(tmpPCGCrosswalk != null)
{
	for(ExactMatchOf tmpExactMatchOf : tmpPCGCrosswalk.getExactMatchOf())
	{
	    System.out.println("ExactMatchOf: " + tmpExactMatchOf.getFullStatement());
	}
	
	for(IsRelatedTo tmpIsRelatedTo : tmpPCGCrosswalk.getIsRelatedTo())
	{
	    System.out.println("IsRelatedTo: " + tmpIsRelatedTo.getFullStatement());
	}
}
```

**PCG Crosswalk a List of standards against an entire document with a semantic comparison output**

```java	
// Init the IMS Global client 
OpenSALTClient openSaltClient = new OpenSALTClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
openSaltClient.setCredentials(AUTHENTICATION_URL, CLIENT_ID, CLIENT_SECRET, GRANT_TYPE, SCOPE);
    
// Init the PCG Crosswalk Service client         
CrosswalkClient client = new CrosswalkClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
client.setCredentials(AUTHENTICATION_URL, CLIENT_ID, CLIENT_SECRET, GRANT_TYPE, SCOPE);

// Tennessee Academic Standards: English Language Arts
String toDocumentId = "c607fa0c-d7cb-11e8-824f-0242ac160002";

// List of Common Core standard identifiers
List<String> identifiers = new ArrayList<String>();
identifiers.add("6b341e9e-d7cc-11e8-824f-0242ac160002");
identifiers.add("6b370e84-d7cc-11e8-824f-0242ac160002");
identifiers.add("6b38c894-d7cc-11e8-824f-0242ac160002");

// Pass in a List identifiers to crosswalk against the TO document framework
Map<String, PCGCrosswalk> tmpCrosswalks = client.crosswalkByIdentifiers(identifiers, "c607fa0c-d7cb-11e8-824f-0242ac160002");

int counter = 0;
for(String identifierId : tmpPCGCrosswalks.keySet())
{
	PCGCrosswalk tmpPCGCrosswalk = tmpPCGCrosswalks.get(identifierId);
	
	if(tmpPCGCrosswalk != null)
	{
        for(ExactMatchOf tmpExactMatchOf : tmpPCGCrosswalk.getExactMatchOf())
        {
            System.out.println(counter + ": ExactMatchOf: " + tmpExactMatchOf.getFullStatement());
        }
        
        for(IsRelatedTo tmpIsRelatedTo : tmpPCGCrosswalk.getIsRelatedTo())
        {
            System.out.println(counter + ": IsRelatedTo: " + tmpIsRelatedTo.getFullStatement());
        }
        
        counter++;
	}
}
```