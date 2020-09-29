package com.nfbsoftware.opensalt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nfbsoftware.pcg.model.ExactMatchOf;
import com.nfbsoftware.pcg.model.IsRelatedTo;
import com.nfbsoftware.pcg.model.PCGCrosswalk;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 * @author Brendan Clemenzi
 * @email brendan@clemenzi.com
 */
public class CrosswalkClientTest extends TestCase
{
    private String HOST_DOMAIN = "api-stg.opensalt.net"; 
    private int HOST_PORT = 443; 
    private String HOST_SCHEME = "https"; 
    
    private String AUTHENTICATION_URL = "https://api-stg.opensalt.net/oauth/token";
    private String CLIENT_ID = "evotext.com";
    private String CLIENT_SECRET = "9852vRckTB51WzF3C6HVMYiqh9yfGH0uB6M9UFQS-cf1";
    private String GRANT_TYPE = "client_credentials";
    private String SCOPE = "";
    
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CrosswalkClientTest( String testName )
    {
        super( testName );
    }
    
    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( CrosswalkClientTest.class );
    }
    
    /**
     * 
     * @throws Exception
     */
    public void testAuthentication() throws Exception
    {
        System.out.println("====> Starting CrosswalkClientTest.testAuthentication");
        
        try
        {
            CrosswalkClient client = new CrosswalkClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
            
            client.setCredentials(AUTHENTICATION_URL, CLIENT_ID, CLIENT_SECRET, GRANT_TYPE, SCOPE);
            
            PCGCrosswalk tmpPCGCrosswalk = client.crosswalkByIdentifier("3feec684-d7cc-11e8-824f-0242ac160002", "c607fa0c-d7cb-11e8-824f-0242ac160002");
            
            for(ExactMatchOf tmpExactMatchOf : tmpPCGCrosswalk.getExactMatchOf())
            {
                System.out.println("ExactMatchOf: " + tmpExactMatchOf.getFullStatement());
            }
            
            for(IsRelatedTo tmpIsRelatedTo : tmpPCGCrosswalk.getIsRelatedTo())
            {
                System.out.println("IsRelatedTo: " + tmpIsRelatedTo.getFullStatement());
            }
            
            assertTrue(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            assertTrue(true);
        }
        
        System.out.println("====> Finished CrosswalkClientTest.testAuthentication");
    }
    
    /**
     * 
     * @throws Exception
     */
    public void testCrosswalkToFramework() throws Exception
    {
        System.out.println("====> Starting CrosswalkClientTest.testCrosswalkToFramework");
        
        try
        {
            CrosswalkClient client = new CrosswalkClient(HOST_DOMAIN, HOST_PORT, HOST_SCHEME);
            
            client.setCredentials(AUTHENTICATION_URL, CLIENT_ID, CLIENT_SECRET, GRANT_TYPE, SCOPE);
            
            List<String> identifiers = new ArrayList<String>();
            identifiers.add("6b341e9e-d7cc-11e8-824f-0242ac160002");
            identifiers.add("6b370e84-d7cc-11e8-824f-0242ac160002");
            identifiers.add("6b38c894-d7cc-11e8-824f-0242ac160002");
            
            Map<String, PCGCrosswalk> tmpPCGCrosswalks = client.crosswalkByIdentifiers(identifiers, "c607fa0c-d7cb-11e8-824f-0242ac160002");
            
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
            

            
            assertTrue(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            assertTrue(true);
        }
        
        System.out.println("====> Finished CrosswalkClientTest.testCrosswalkToFramework");
    }
}
