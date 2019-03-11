package com.nfbsoftware.opensalt;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nfbsoftware.opensalt.model.CFDocument;
import com.nfbsoftware.opensalt.model.CFDocuments;

/**
 * This is a Java utility class that is used to communicate with the Academic Benchmarks' RESTful API.
 * 
 * @author brendanclemenzi
 */
public class OpenSALTClient
{
    private static final Log logger = LogFactory.getLog(OpenSALTClient.class);

    private String m_hostDomain;
    private String m_hostScheme;
    
    private int m_hostPort;
    
    /**
     * 
     * @param hostDomain - Base domain for the API host server
     * @param hostPort - The port number running the API
     * @param hostScheme - The http protocol used for the API
     */
    public OpenSALTClient(String hostDomain, int hostPort, String hostScheme)
    {
        m_hostDomain= hostDomain;
        m_hostPort = hostPort;
        m_hostScheme = hostScheme;
    }
    
    /**
     * 
     * @return A set of CFDocument objects
     * @throws Exception
     */
    public List<CFDocument> getAllCFDocuments() throws Exception
    {
        List<CFDocument> cfDocumentList = new ArrayList<CFDocument>();
        
        HttpClient httpClient = HttpClientBuilder.create().build();
        
        // specify the host, protocol, and port
        HttpHost target = new HttpHost(m_hostDomain, m_hostPort, m_hostScheme);
        
        logger.debug("Getting All CFDocuments");
        
        // specify the get request
        HttpGet getRequest = new HttpGet("/ims/case/v1p0/CFDocuments");

        // Get our response from the SALT server
        HttpResponse saltyResponse = httpClient.execute(target, getRequest);
        HttpEntity entity = saltyResponse.getEntity();
        
        // If we have an entity, convert it to Java objects
        if(entity != null) 
        {
            String responseString = EntityUtils.toString(entity);   
            
            JSONObject responseJSON = new JSONObject(responseString);
            System.out.println(responseJSON.toString());
            
            ObjectMapper mapper = new ObjectMapper();
            CFDocuments objectValue = mapper.readValue(responseJSON.toString(),  CFDocuments.class);
            
            cfDocumentList = objectValue.getCFDocuments();
        }
        
        return cfDocumentList;
    }
    
    /**
     * 
     * @param limit The 'limit' defines the download segmentation value i.e. the maximum number of records to be contained in the response.
     * @param offset The 'offset' is the number of the first record to be supplied in the segmented response message.
     * @param sort The 'sort' identifies the sort criteria to be used for the records in the response message.
     * @param orderBy This is used as part of the sorting mechanism to be use by the service provider.
     * @return
     * @throws Exception
     */
    public List<CFDocument> getCFDocuments(int limit, int offset, String sort, String orderBy) throws Exception
    {
        List<CFDocument> cfDocumentList = new ArrayList<CFDocument>();
        
        // Set a few defaults if needed:
        sort = StringUtils.defaultIfEmpty(sort, "title");
        orderBy = StringUtils.defaultIfEmpty(orderBy, "asc");
        
        // Make sure we don't go over the maximum
        limit = (limit > 100) ? 100 : limit;
        
        logger.debug("Getting CFDocuments:  Limit=" + limit  + " Offset=" + offset + " Sort=" + sort + " Order By=" + orderBy);
        
        HttpClient httpClient = HttpClientBuilder.create().build();
        
        // specify the host, protocol, and port
        HttpHost target = new HttpHost(m_hostDomain, m_hostPort, m_hostScheme);
        
        // specify the get request
        HttpGet getRequest = new HttpGet("/ims/case/v1p0/CFDocuments?limit=" + limit + "&offset=" + offset + "&sort=" + sort + "&orderBy=" + orderBy);

        // Get our response from the SALT server
        HttpResponse saltyResponse = httpClient.execute(target, getRequest);
        HttpEntity entity = saltyResponse.getEntity();
        
        // If we have an entity, convert it to Java objects
        if(entity != null) 
        {
            String responseString = EntityUtils.toString(entity);   
            
            JSONObject responseJSON = new JSONObject(responseString);
            System.out.println(responseJSON.toString());
            
            ObjectMapper mapper = new ObjectMapper();
            CFDocuments objectValue = mapper.readValue(responseJSON.toString(),  CFDocuments.class);
            
            cfDocumentList = objectValue.getCFDocuments();
        }
        
        return cfDocumentList;
    }
    
    /**
     * 
     * @param sourceId The GUID that identifies the Competency Framework Document that is to be read from the service provider.
     * @return
     * @throws Exception
     */
    public CFDocument getCFDocument(String sourceId) throws Exception
    {
        CFDocument cfDocument = null;
        
        HttpClient httpClient = HttpClientBuilder.create().build();
        
        // specify the host, protocol, and port
        HttpHost target = new HttpHost(m_hostDomain, m_hostPort, m_hostScheme);
        
        logger.debug("Getting CFDocument " + sourceId);
        
        // specify the get request
        HttpGet getRequest = new HttpGet("/ims/case/v1p0/CFDocuments/" + sourceId);

        // Get our response from the SALT server
        HttpResponse saltyResponse = httpClient.execute(target, getRequest);
        HttpEntity entity = saltyResponse.getEntity();
        
        // If we have an entity, convert it to Java objects
        if(entity != null) 
        {
            String responseString = EntityUtils.toString(entity);   
            
            JSONObject responseJSON = new JSONObject(responseString);
            System.out.println(responseJSON.toString());
            
            ObjectMapper mapper = new ObjectMapper();
            cfDocument = mapper.readValue(responseJSON.toString(),  CFDocument.class);
        }
        
        return cfDocument;
    }
}
