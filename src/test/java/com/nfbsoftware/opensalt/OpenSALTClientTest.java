package com.nfbsoftware.opensalt;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nfbsoftware.opensalt.model.CFAssociation;
import com.nfbsoftware.opensalt.model.CFDocument;
import com.nfbsoftware.opensalt.model.CFItem;
import com.nfbsoftware.opensalt.model.CFPackages;
import com.nfbsoftware.opensalt.model.OriginNodeURI;
import com.nfbsoftware.standards.model.Crosswalk;
import com.nfbsoftware.standards.model.Standard;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 * @author Brendan Clemenzi
 * @email brendan@clemenzi.com
 */
public class OpenSALTClientTest extends TestCase
{
    private String HOST_DOMAIN = "opensalt.net"; 
    private int HOST_PORT = 443; 
    private String HOST_SCHEME = "https"; 
    
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public OpenSALTClientTest( String testName )
    {
        super( testName );
    }
    
    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( OpenSALTClientTest.class );
    }
    
    /**
     * 
     * @throws Exception
     */
    public void testGetAllCFDocuments() throws Exception
    {
        System.out.println("====> Starting OpenSALTClientTest.testGetAllCFDocuments");
        
        try
        {
            OpenSALTClient client = new OpenSALTClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
            
            List<CFDocument> documentList = client.getAllCFDocuments();
            
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
            
            assertTrue(documentList.size() > 0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            assertTrue(false);
        }
        
        System.out.println("====> Finished OpenSALTClientTest.testGetAllCFDocuments");
    }
    
    /**
     * 
     * @throws Exception
     */
    public void testGetCFDocuments() throws Exception
    {
        System.out.println("====> Starting OpenSALTClientTest.testGetCFDocuments");
        
        try
        {
            OpenSALTClient client = new OpenSALTClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
            
            List<CFDocument> documentList = client.getCFDocuments(100, 0, "title", "desc");
            
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
            
            assertTrue(documentList.size() > 0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            assertTrue(false);
        }
        
        System.out.println("====> Finished OpenSALTClientTest.testGetCFDocuments");
    }
    
    /**
     * 
     * @throws Exception
     */
    public void testGetCFDocument() throws Exception
    {
        System.out.println("====> Starting OpenSALTClientTest.testGetCFDocument");
        
        try
        {
            OpenSALTClient client = new OpenSALTClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
            
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
            
            assertTrue(tmpCFDocument != null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            assertTrue(false);
        }
        
        System.out.println("====> Finished OpenSALTClientTest.testGetCFDocument");
    }
    
    /**
     * 
     * @throws Exception
     */
    public void testGetCFPackages() throws Exception
    {
        System.out.println("====> Starting OpenSALTClientTest.testGetCFPackages");
        
        try
        {
            OpenSALTClient client = new OpenSALTClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
            
            CFPackages cfPackages = client.getCFPackages("c5fb0812-d7cb-11e8-824f-0242ac160002");
            List<CFItem> cfItems = cfPackages.getCFItems();
            
            for(CFItem tmpCFItem : cfItems)
            {
                if(tmpCFItem.getCFItemType() == null)
                {
                    System.out.println("Identifier: " + tmpCFItem.getIdentifier() + " [" + tmpCFItem.getCFItemType() + "] " + tmpCFItem.getFullStatement());
                }
            }
            
            assertTrue(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            assertTrue(false);
        }
        
        System.out.println("====> Finished OpenSALTClientTest.testGetCFPackage");
    }
    
    /**
     * 
     * @throws Exception
     */
    public void testGetTopLevelCFPackageItems() throws Exception
    {
        System.out.println("====> Starting OpenSALTClientTest.testGetTopLevelCFPackageItems");
        
        try
        {
            OpenSALTClient client = new OpenSALTClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
            
            List<CFItem> cfItems = client.getTopLevelCFPackageItems("c607fa0c-d7cb-11e8-824f-0242ac160002");
            
            for(CFItem tmpCFItem : cfItems)
            {
                if(tmpCFItem.getCFItemType() == null)
                {
                    System.out.println("Identifier: " + tmpCFItem.getIdentifier() + " - " + tmpCFItem.getFullStatement());
                }
            }
            
            assertTrue(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            assertTrue(false);
        }
        
        System.out.println("====> Finished OpenSALTClientTest.testGetTopLevelCFPackageItems");
    }
    
    /**
     * 
     * @throws Exception
     */
    public void testGetCFItem() throws Exception
    {
        System.out.println("====> Starting OpenSALTClientTest.testGetCFItem");
        
        try
        {
            OpenSALTClient client = new OpenSALTClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
            
            CFItem tmpCFItem = client.getCFItem("5ad1a3fe-f280-11e8-9cff-0242ac140002");
            
            if(tmpCFItem != null)
            {
                System.out.println("Identifier: " + tmpCFItem.getIdentifier());
                System.out.println("HumanCodingScheme: " + tmpCFItem.getHumanCodingScheme());
                System.out.println("FullStatement: " + tmpCFItem.getFullStatement());
                System.out.println("Notes: " + tmpCFItem.getNotes());
            }
            
            assertTrue(tmpCFItem != null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            assertTrue(false);
        }
        
        System.out.println("====> Finished OpenSALTClientTest.testGetCFItem");
    }
    
    /**
     * 
     * @throws Exception
     */
    public void testGetCFItemBreadcrumb() throws Exception
    {
        System.out.println("====> Starting OpenSALTClientTest.testGetCFItemBreadcrumb");
        
        try
        {
            OpenSALTClient client = new OpenSALTClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
            
            String breadcrumbTrail = client.getItemBreadcrumbTrail("5ad1a3fe-f280-11e8-9cff-0242ac140002");
            
            System.out.println("breadcrumbTrail:");
            System.out.println(breadcrumbTrail);
            
            assertTrue(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            assertTrue(false);
        }
        
        System.out.println("====> Finished OpenSALTClientTest.testGetCFItemBreadcrumb");
    }
    
    /**
     * 
     * @throws Exception
     */
    public void testGetCFItemAssociations() throws Exception
    {
        System.out.println("====> Starting OpenSALTClientTest.testGetCFItemAssociations");
        
        try
        {
            OpenSALTClient client = new OpenSALTClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
            
            List<CFAssociation> tmpAssociations = client.getCFItemAssociations("18eff32c-d7cc-11e8-824f-0242ac160002");
            
            for(CFAssociation tmpCFAssociation : tmpAssociations)
            {
                if(tmpCFAssociation.getAssociationType().equalsIgnoreCase("isChildOf"))
                {
                    OriginNodeURI tmpOriginNodeURI = tmpCFAssociation.getOriginNodeURI();
                    
                    if(tmpOriginNodeURI != null)
                    {
                        System.out.println("Identifier: " + tmpOriginNodeURI.getIdentifier() + " - " + tmpOriginNodeURI.getTitle());
                    }
                }
                
            }
            
            assertTrue(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            assertTrue(false);
        }
        
        System.out.println("====> Finished OpenSALTClientTest.testGetCFItemAssociations");
    }
    
    /**
     * 
     * @throws Exception
     */
    public void testBuildHierarchicalJSON() throws Exception
    {
        System.out.println("====> Starting OpenSALTClientTest.testBuildHierarchicalJSON");
        
        try
        {
            OpenSALTClient client = new OpenSALTClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
            
            System.out.println((new Date()).getTime());
            
            //Standard fullStandardsDocument = client.getFullHierarchicalStandard("4d6e94e4-f280-11e8-9cff-0242ac140002");
            Standard fullStandardsDocument = null;  //client.getFullHierarchicalStandard("4d6e94e4-f280-11e8-9cff-0242ac140002");
            
            if(fullStandardsDocument != null)
            {
                System.out.println(fullStandardsDocument.getDocumentTitle());
                
                ObjectMapper mapper = new ObjectMapper();
                String jsonInString = mapper.writeValueAsString(fullStandardsDocument);
                
                System.out.println(jsonInString);
            }
            
            System.out.println((new Date()).getTime());
            
            assertTrue(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            assertTrue(false);
        }
        
        System.out.println("====> Finished OpenSALTClientTest.testBuildHierarchicalJSON");
    }
    
    /**
     * 
     * @throws Exception
     */
    public void testBuildHierarchicalJSONForCFItem() throws Exception
    {
        System.out.println("====> Starting OpenSALTClientTest.testBuildHierarchicalJSONForCFItem");
        
        try
        {
            OpenSALTClient client = new OpenSALTClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
            
            System.out.println((new Date()).getTime());
            
            // Pass in a CFItem ID to get the subset of the document
            Standard fullStandardsDocument = null; //client.getFullHierarchicalStandard("5ad17357-f280-11e8-9cff-0242ac140002");
            
            if(fullStandardsDocument != null)
            {
                System.out.println(fullStandardsDocument.getDocumentTitle());
                
                ObjectMapper mapper = new ObjectMapper();
                String jsonInString = mapper.writeValueAsString(fullStandardsDocument);
                
                System.out.println(jsonInString);
            }
            
            System.out.println((new Date()).getTime());
            
            assertTrue(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            assertTrue(false);
        }
        
        System.out.println("====> Finished OpenSALTClientTest.testBuildHierarchicalJSONForCFItem");
    }
    
    /**
     * 
     * @throws Exception
     */
    public void testItemToItemCrosswalk() throws Exception
    {
        System.out.println("====> Starting OpenSALTClientTest.testItemToItemCrosswalk");
        
        try
        {
            OpenSALTClient client = new OpenSALTClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
            
            Long startTime = (new Date()).getTime();
            System.out.println(startTime);
            
            String rosettaDocumentId = "c5fb0812-d7cb-11e8-824f-0242ac160002";
            String fromItemId = "5acff4b8-f280-11e8-9cff-0242ac140002";
            String toItemId = "5ca43735-f280-11e8-9cff-0242ac140002";
            
            // Pass in a CFItem ID to get the subset of the document
            Crosswalk tmpCrosswalk = client.getCFItemCrosswalk(rosettaDocumentId, fromItemId, toItemId);
            
            if(tmpCrosswalk != null)
            {
                ObjectMapper mapper = new ObjectMapper();
                String jsonInString = mapper.writeValueAsString(tmpCrosswalk);
                
                System.out.println(jsonInString);
            }
            
            Long finishTime = (new Date()).getTime();
            System.out.println(finishTime);
            
            System.out.println("Test TAT: " + (finishTime - startTime));
            
            assertTrue(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            assertTrue(false);
        }
        
        System.out.println("====> Finished OpenSALTClientTest.testItemToItemCrosswalk");
    }
    
    /**
     * 
     * @throws Exception
     */
    public void testItemToItemCrosswalkByDocumentTitle() throws Exception
    {
        System.out.println("====> Starting OpenSALTClientTest.testItemToItemCrosswalkByDocumentTitle");
        
        try
        {
            OpenSALTClient client = new OpenSALTClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
            
            Long startTime = (new Date()).getTime();
            System.out.println(startTime);
            
            String rosettaDocumentTitle = "PCG Compendium for";
            String fromItemId = "5acff4b8-f280-11e8-9cff-0242ac140002";
            String toItemId = "5ca43735-f280-11e8-9cff-0242ac140002";
            
            // Pass in a CFItem ID to get the subset of the document
            Crosswalk tmpCrosswalk = client.getCFItemCrosswalkByDocumentTitle(rosettaDocumentTitle, fromItemId, toItemId);
            
            if(tmpCrosswalk != null)
            {
                ObjectMapper mapper = new ObjectMapper();
                String jsonInString = mapper.writeValueAsString(tmpCrosswalk);
                
                System.out.println(jsonInString);
            }
            
            Long finishTime = (new Date()).getTime();
            System.out.println(finishTime);
            
            System.out.println("Test TAT: " + (finishTime - startTime));
            
            assertTrue(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            assertTrue(false);
        }
        
        System.out.println("====> Finished OpenSALTClientTest.testItemToItemCrosswalkByDocumentTitle");
    }
    
    /**
     * 
     * @throws Exception
     */
    public void testItemToDocumentCrosswalks() throws Exception
    {
        System.out.println("====> Starting OpenSALTClientTest.testItemToDocumentCrosswalks");
        
        try
        {
            OpenSALTClient client = new OpenSALTClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
            
            Long startTime = (new Date()).getTime();
            System.out.println(startTime);
            
            // PCG Compendium for Mathematics
            String rosettaDocumentId = "c5fb0812-d7cb-11e8-824f-0242ac160002";
            
            // 
            String fromItemId = "5ca44a05-f280-11e8-9cff-0242ac140002";
            
            // PCG - Pennsylvania Core Math Standards
            String toDcoumentId = "4d6d3d12-f280-11e8-9cff-0242ac140002";
            
            // Pass in a CFItem ID to get the subset of the document
            List<Crosswalk> tmpCrosswalks = client.getCFItemCrosswalks(rosettaDocumentId, fromItemId, toDcoumentId);
            
            int counter = 1;
            for(Crosswalk tmpCrosswalk : tmpCrosswalks)
            {
                ObjectMapper mapper = new ObjectMapper();
                String jsonInString = mapper.writeValueAsString(tmpCrosswalk);
                
                System.out.println("Crosswalk " + counter + ": " + jsonInString);
                
                System.out.println(tmpCrosswalk.getSemanticComparison());
                
                counter++;
            }
            
            Long finishTime = (new Date()).getTime();
            System.out.println(finishTime);
            
            System.out.println("Test TAT: " + (finishTime - startTime));
            
            assertTrue(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            assertTrue(false);
        }
        
        System.out.println("====> Finished OpenSALTClientTest.testItemToDocumentCrosswalks");
    }
    
    /**
     * 
     * @throws Exception
     */
    public void testItemToDocumentCrosswalksByDocumentTitle() throws Exception
    {
        System.out.println("====> Starting OpenSALTClientTest.testItemToDocumentCrosswalksByDocumentTitle");
        
        try
        {
            OpenSALTClient client = new OpenSALTClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
            
            Long startTime = (new Date()).getTime();
            System.out.println(startTime);
            
            String rosettaDocumentTitle = "PCG Compendium for";
            String fromItemId = "5ca44a05-f280-11e8-9cff-0242ac140002";
            
            // PCG - Pennsylvania Core Math Standards
            String toDcoumentId = "4d6d3d12-f280-11e8-9cff-0242ac140002";
            
            // Pass in a CFItem ID to get the subset of the document
            List<Crosswalk> tmpCrosswalks = client.getCFItemCrosswalksByDocumentTitle(rosettaDocumentTitle, fromItemId, toDcoumentId);
            
            int counter = 1;
            for(Crosswalk tmpCrosswalk : tmpCrosswalks)
            {
                ObjectMapper mapper = new ObjectMapper();
                String jsonInString = mapper.writeValueAsString(tmpCrosswalk);
                
                System.out.println("Crosswalk " + counter + ": " + jsonInString);
                
                counter++;
            }
            
            Long finishTime = (new Date()).getTime();
            System.out.println(finishTime);
            
            System.out.println("Test TAT: " + (finishTime - startTime));
            
            assertTrue(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            assertTrue(false);
        }
        
        System.out.println("====> Finished OpenSALTClientTest.testItemToDocumentCrosswalksByDocumentTitle");
    }
}
