package com.nfbsoftware.opensalt;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nfbsoftware.opensalt.model.CFDocument;
import com.nfbsoftware.opensalt.model.CFItem;
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
public class CaseNetworkClientTest extends TestCase
{
    private String HOST_DOMAIN = "casenetwork.imsglobal.org"; 
    private int HOST_PORT = 443; 
    private String HOST_SCHEME = "https"; 
    
    private String AUTHENTICATION_URL = "https://oauth2-case.imsglobal.org/oauth2server/clienttoken";
    private String CLIENT_ID = "your client id";
    private String CLIENT_SECRET = "your client secret";
    private String GRANT_TYPE = "client_credentials";
    private String SCOPE = "http://purl.imsglobal.org/casenetwork/case/v1p0/scope/core.readonly http://purl.imsglobal.org/casenetwork/case/v1p0/scope/all.readonly";
    
    private String PCG_HOST_DOMAIN = "api-stg.opensalt.net"; 
    private int PCG_HOST_PORT = 443; 
    private String PCG_HOST_SCHEME = "https"; 
    
    private String PCG_AUTHENTICATION_URL = "https://api-stg.opensalt.net/oauth/token";
    private String PCG_CLIENT_ID = "your client id";
    private String PCG_CLIENT_SECRET = "your client secret";
    private String PCG_GRANT_TYPE = "client_credentials";
    private String PCG_SCOPE = "";
    
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CaseNetworkClientTest( String testName )
    {
        super( testName );
    }
    
    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( CaseNetworkClientTest.class );
    }
    
    /**
     * 
     * @throws Exception
     */
    public void testAuthentication() throws Exception
    {
        System.out.println("====> Starting CaseNetworkClientTest.testAuthentication");
        
        try
        {
            OpenSALTClient client = new OpenSALTClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
            
            client.setCredentials(AUTHENTICATION_URL, CLIENT_ID, CLIENT_SECRET, GRANT_TYPE, SCOPE);
            
            assertTrue(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            assertTrue(true);
        }
        
        System.out.println("====> Finished CaseNetworkClientTest.testGetAllCFDocuments");
    }
    
    /**
     * 
     * @throws Exception
     */
    public void testGetAllCFDocuments() throws Exception
    {
        System.out.println("====> Starting CaseNetworkClientTest.testGetAllCFDocuments");
        
        try
        {
            OpenSALTClient client = new OpenSALTClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
            client.setCredentials(AUTHENTICATION_URL, CLIENT_ID, CLIENT_SECRET, GRANT_TYPE, SCOPE);
            
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
            
            assertTrue(true);
        }
        
        System.out.println("====> Finished CaseNetworkClientTest.testGetAllCFDocuments");
    }
    
    /**
     * 
     * @throws Exception
     */
    public void testGetCFDocuments() throws Exception
    {
        System.out.println("====> Starting CaseNetworkClientTest.testGetCFDocuments");
        
        try
        {
            OpenSALTClient client = new OpenSALTClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
            client.setCredentials(AUTHENTICATION_URL, CLIENT_ID, CLIENT_SECRET, GRANT_TYPE, SCOPE);
            
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
            
            assertTrue(true);
        }
        
        System.out.println("====> Finished CaseNetworkClientTest.testGetCFDocuments");
    }
    
    /**
     * 
     * @throws Exception
     */
    public void testGetCFDocument() throws Exception
    {
        System.out.println("====> Starting CaseNetworkClientTest.testGetCFDocument");
        
        try
        {
            OpenSALTClient client = new OpenSALTClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
            client.setCredentials(AUTHENTICATION_URL, CLIENT_ID, CLIENT_SECRET, GRANT_TYPE, SCOPE);
            
            CFDocument tmpCFDocument = client.getCFDocument("c664d506-d7cb-11e8-824f-0242ac160002");
            
            System.out.println("Identifier: " + tmpCFDocument.getIdentifier());
            System.out.println("Title: " + tmpCFDocument.getTitle());
            System.out.println("Description: " + tmpCFDocument.getDescription());
            System.out.println("Notes: " + tmpCFDocument.getNotes());
            System.out.println("Creator: " + tmpCFDocument.getCreator());
            System.out.println("Adoption Status: " + tmpCFDocument.getAdoptionStatus());
            System.out.println("");
            
            assertTrue(tmpCFDocument != null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            assertTrue(true);
        }
        
        System.out.println("====> Finished CaseNetworkClientTest.testGetCFDocument");
    }
    
    /**
     * 
     * @throws Exception
     */
    public void testItemToDocumentCrosswalks() throws Exception
    {
        System.out.println("====> Starting CaseNetworkClientTest.testItemToDocumentCrosswalks");
        
        try
        {
            OpenSALTClient openSaltClient = new OpenSALTClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
            openSaltClient.setCredentials(AUTHENTICATION_URL, CLIENT_ID, CLIENT_SECRET, GRANT_TYPE, SCOPE);
            
            CrosswalkClient crosswalkClient = new CrosswalkClient(PCG_HOST_DOMAIN, PCG_HOST_PORT, PCG_HOST_SCHEME);
            crosswalkClient.setCredentials(PCG_AUTHENTICATION_URL, PCG_CLIENT_ID, PCG_CLIENT_SECRET, PCG_GRANT_TYPE, PCG_SCOPE);
            
            Long startTime = (new Date()).getTime();
            System.out.println(startTime);
            
            // Pennsylvania Core English Language Arts CFItem id we'd like to crosswalk from
            String fromItemId = "3feec684-d7cc-11e8-824f-0242ac160002";
            
            // Tennessee Academic Standards: English Language Arts
            String toDcoumentId = "c607fa0c-d7cb-11e8-824f-0242ac160002";
            
            // Pass in a CFItem ID to get the subset of the document
            List<Crosswalk> tmpCrosswalks = openSaltClient.getCFItemCrosswalks(crosswalkClient, fromItemId, toDcoumentId);
            
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
            
            assertTrue(true);
        }
        
        System.out.println("====> Finished CaseNetworkClientTest.testItemToDocumentCrosswalks");
    }
    
    
    /**
     * 
     * @throws Exception
     */
    public void testFullHierarchicalStandard() throws Exception
    {
        System.out.println("====> Starting CaseNetworkClientTest.testFullHierarchicalStandard");
        
        try
        {
            OpenSALTClient openSaltClient = new OpenSALTClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
            openSaltClient.setCredentials(AUTHENTICATION_URL, CLIENT_ID, CLIENT_SECRET, GRANT_TYPE, SCOPE);
            
            CrosswalkClient crosswalkClient = new CrosswalkClient(PCG_HOST_DOMAIN, PCG_HOST_PORT, PCG_HOST_SCHEME);
            crosswalkClient.setCredentials(PCG_AUTHENTICATION_URL, PCG_CLIENT_ID, PCG_CLIENT_SECRET, PCG_GRANT_TYPE, PCG_SCOPE);
            
            Long startTime = (new Date()).getTime();
            System.out.println(startTime);
            
            // Pennsylvania Core English Language Arts CFItem id we'd like to crosswalk from
            //String sourceId = "3feec684-d7cc-11e8-824f-0242ac160002";
            
            // California Common Core State Standards - Mathematics - Algebra I
            //String sourceId = "7bb994e2-d7cc-11e8-824f-0242ac160002";
            
            // Texas Test
            String sourceId = "c627f1db-d7cb-11e8-824f-0242ac160002";
            
            // Tennessee Academic Standards: English Language Arts
            //String sourceId = "c607fa0c-d7cb-11e8-824f-0242ac160002";
            
            // Pass in a CFItem ID to get the subset of the document
            Standard tmpStandard = openSaltClient.getFullHierarchicalStandard(crosswalkClient, sourceId);
            
            if(tmpStandard != null)
            {
            	ObjectMapper mapper = new ObjectMapper();
                String jsonInString = mapper.writeValueAsString(tmpStandard);
                
                System.out.println("tmpStandard: " + jsonInString);
            }
            
            Long finishTime = (new Date()).getTime();
            System.out.println(finishTime);
            
            System.out.println("Test TAT: " + (finishTime - startTime));
            
            assertTrue(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            assertTrue(true);
        }
        
        System.out.println("====> Finished CaseNetworkClientTest.testFullHierarchicalStandard");
    }
    
    /**
     * 
     * @throws Exception
     */
    public void testGetTopLevelCFPackageItems() throws Exception
    {
        System.out.println("====> Starting CaseNetworkClientTest.testGetTopLevelCFPackageItems");
        
        try
        {
            OpenSALTClient openSaltClient = new OpenSALTClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
            openSaltClient.setCredentials(AUTHENTICATION_URL, CLIENT_ID, CLIENT_SECRET, GRANT_TYPE, SCOPE);
            
            // Arizona
            //List<CFItem> cfItems = openSaltClient.getTopLevelCFPackageItems("c6498415-d7cb-11e8-824f-0242ac160002");
            
            // Texas  
            //List<CFItem> cfItems = openSaltClient.getTopLevelCFPackageItems("bc997e24-7f3b-5df0-a0cd-3a8ac9cf0e2e");
            
            // TN  
            List<CFItem> cfItems = openSaltClient.getTopLevelCFPackageItems("33a85513-4cab-402a-a7be-f3aeee56eebc");
            
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
            
            assertTrue(true);
        }
        
        System.out.println("====> Finished CaseNetworkClientTest.testGetTopLevelCFPackageItems");
    }
}
