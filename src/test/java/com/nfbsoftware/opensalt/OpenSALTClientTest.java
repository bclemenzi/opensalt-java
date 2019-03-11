package com.nfbsoftware.opensalt;

import java.util.List;

import com.nfbsoftware.opensalt.model.CFDocument;

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
            
            assertTrue(true);
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
            
            List<CFDocument> documentList = client.getCFDocuments(1, 0, "title", "desc");
            
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
            
            assertTrue(true);
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
            
            assertTrue(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            assertTrue(false);
        }
        
        System.out.println("====> Finished OpenSALTClientTest.testGetCFDocument");
    }
}
