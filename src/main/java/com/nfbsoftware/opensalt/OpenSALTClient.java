package com.nfbsoftware.opensalt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.nfbsoftware.opensalt.model.CFAssociation;
import com.nfbsoftware.opensalt.model.CFDocument;
import com.nfbsoftware.opensalt.model.CFItem;
import com.nfbsoftware.opensalt.model.CFItemTypeURI;
import com.nfbsoftware.opensalt.model.CFPackages;
import com.nfbsoftware.opensalt.model.Documents;
import com.nfbsoftware.opensalt.model.OriginNodeURI;
import com.nfbsoftware.standards.model.Standard;

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
     * <p>This is a request to the Service Provider to provide all of the Competency Framework Documents./p>
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
            //System.out.println(responseJSON.toString());
            
            ObjectMapper mapper = new ObjectMapper();
            Documents objectValue = mapper.readValue(responseJSON.toString(),  Documents.class);
            
            cfDocumentList = objectValue.getCFDocuments();
        }
        
        return cfDocumentList;
    }
    
    /**
     * <p>This is a request to the Service Provider to provide all of the Competency Framework Documents./p>
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
            //System.out.println(responseJSON.toString());
            
            ObjectMapper mapper = new ObjectMapper();
            Documents objectValue = mapper.readValue(responseJSON.toString(),  Documents.class);
            
            cfDocumentList = objectValue.getCFDocuments();
        }
        
        return cfDocumentList;
    }
    
    /**
     * <p>This is a request to the service provider to provide the information for the specific Competency Framework Document.</p>
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
            //System.out.println(responseJSON.toString());
            
            ObjectMapper mapper = new ObjectMapper();
            cfDocument = mapper.readValue(responseJSON.toString(),  CFDocument.class);
        }
        
        return cfDocument;
    }
    
    /**
     * <p>This is a request to the service provider to provide the information for the specific Competency Framework Package.</p>
     * 
     * @param sourceId The GUID that identifies the Competency Framework Document that is to be read from the service provider.
     * @return
     * @throws Exception
     */
    public List<CFItem> getCFPackages(String sourceId) throws Exception
    {
        List<CFItem> cfItems = null;
        
        HttpClient httpClient = HttpClientBuilder.create().build();
        
        // specify the host, protocol, and port
        HttpHost target = new HttpHost(m_hostDomain, m_hostPort, m_hostScheme);
        
        logger.debug("Getting CFPackages " + sourceId);
        
        // specify the get request
        HttpGet getRequest = new HttpGet("/ims/case/v1p0/CFPackages/" + sourceId);

        // Get our response from the SALT server
        HttpResponse saltyResponse = httpClient.execute(target, getRequest);
        HttpEntity entity = saltyResponse.getEntity();
        
        // If we have an entity, convert it to Java objects
        if(entity != null) 
        {
            String responseString = EntityUtils.toString(entity);   
            
            JSONObject responseJSON = new JSONObject(responseString);
            //System.out.println(responseJSON.toString());
            
            ObjectMapper mapper = new ObjectMapper();
            CFPackages tmpCFPackages = mapper.readValue(responseJSON.toString(), CFPackages.class);
            
            cfItems = tmpCFPackages.getCFItems();
        }
        
        return cfItems;
    }
    
    /**
     * <p>This is a request to the Service Provider to provide the all of the Competency Associations for the specified CFItem.</p>
     * 
     * @param sourceId The GUID that identifies the Competency Framework Document that is to be read from the service provider.
     * @return
     * @throws Exception
     */
    public List<CFAssociation> getCFItemAssociations(String sourceId) throws Exception
    {
        List<CFAssociation> cfAssociations = null;
        
        HttpClient httpClient = HttpClientBuilder.create().build();
        
        // specify the host, protocol, and port
        HttpHost target = new HttpHost(m_hostDomain, m_hostPort, m_hostScheme);
        
        logger.debug("Getting CFDocument " + sourceId);
        
        // specify the get request
        HttpGet getRequest = new HttpGet("/ims/case/v1p0/CFItemAssociations/" + sourceId);

        // Get our response from the SALT server
        HttpResponse saltyResponse = httpClient.execute(target, getRequest);
        HttpEntity entity = saltyResponse.getEntity();
        
        // If we have an entity, convert it to Java objects
        if(entity != null) 
        {
            String responseString = EntityUtils.toString(entity);   
            
            JSONObject responseJSON = new JSONObject(responseString);
            //System.out.println(responseJSON.toString());
            
            ObjectMapper mapper = new ObjectMapper();
            CFItem tmpCFItem = mapper.readValue(responseJSON.toString(), CFItem.class);
            
            cfAssociations = tmpCFItem.getCFAssociations();
        }
        
        return cfAssociations;
    }
    
    /**
     * <p>=Returns a full hierarchical representation of the standards document</p>
     * 
     * @param sourceId The GUID that identifies the Competency Framework Document that is to be read from the service provider.
     * @return
     */
    public Standard getFullHierarchicalStandard(String documentSourceId) throws Exception
    {
        Standard standardDocument = null;
        
        CFDocument tmpCFDocument = getCFDocument(documentSourceId);
        
        if(tmpCFDocument != null)
        {
            standardDocument = new Standard();
            standardDocument.setId(tmpCFDocument.getIdentifier());
            standardDocument.setDocumentTitle(StringUtils.stripToEmpty(tmpCFDocument.getTitle()));
            standardDocument.setFullStatement(StringUtils.stripToEmpty(tmpCFDocument.getTitle()));
            standardDocument.setNotes(StringUtils.stripToEmpty(tmpCFDocument.getNotes()));
            standardDocument.setCreator(StringUtils.stripToEmpty(tmpCFDocument.getCreator()));
            standardDocument.setAdoptionStatus(StringUtils.stripToEmpty(tmpCFDocument.getAdoptionStatus()));
            standardDocument.setLanguage(StringUtils.stripToEmpty(tmpCFDocument.getLanguage()));
            
            // Set a hash map to store all the document elements
            Map<String, CFItem> cfItemsMap = new HashMap<String, CFItem>();
            
            // Get ALL the levels of the document and store them in the hash map
            List<CFItem> cfItems = getCFPackages(documentSourceId);
            for(CFItem tmpCFItem : cfItems)
            {
                cfItemsMap.put(tmpCFItem.getIdentifier(), tmpCFItem);
            }
            
            // Create a array to store the top level standards of the document
            List<Standard> topLevelStandards = new ArrayList<Standard>();
            
            // Loop the full set again to get the top level children of the document
            for(CFItem tmpCFItem : cfItems)
            {
                if(tmpCFItem.getCFItemType() == null)
                {
                    // Create new standard object
                    Standard topLevelStandard = new Standard();
                    topLevelStandard.setId(tmpCFItem.getIdentifier());
                    topLevelStandard.setFullStatement(StringUtils.stripToEmpty(tmpCFItem.getFullStatement()));
                    topLevelStandard.setNotes(StringUtils.stripToEmpty(tmpCFItem.getNotes()));
                    topLevelStandard.setHumanCodingScheme(StringUtils.stripToEmpty(tmpCFItem.getHumanCodingScheme()));
                    topLevelStandard.setListEnumeration(StringUtils.stripToEmpty(tmpCFItem.getListEnumeration()));
                    topLevelStandard.setType(StringUtils.stripToEmpty(tmpCFItem.getCFItemType()));
                    
                    CFItemTypeURI tmpCFItemTypeURI = tmpCFItem.getCFItemTypeURI();
                    if(tmpCFItemTypeURI != null)
                    {
                        topLevelStandard.setTypeId(StringUtils.stripToEmpty(tmpCFItemTypeURI.getIdentifier()));
                    }
                    
                    // Set Document level values
                    topLevelStandard.setDocumentId(standardDocument.getId());
                    topLevelStandard.setDocumentTitle(standardDocument.getDocumentTitle());
                    topLevelStandard.setCreator(standardDocument.getCreator());
                    topLevelStandard.setAdoptionStatus(standardDocument.getAdoptionStatus());
                    topLevelStandard.setLanguage(standardDocument.getLanguage());
                    
                    // Get children of the item
                    getChildAssociations(standardDocument, cfItemsMap, topLevelStandard);
                    
                    // Add the standard to the document
                    topLevelStandards.add(topLevelStandard);
                }
            }
            
            // Add the top level 
            standardDocument.setStandards(topLevelStandards);
        }
        
        return standardDocument;
    }

    private void getChildAssociations(Standard standardDocument, Map<String, CFItem> cfItemsMap, Standard parentStandard) throws Exception
    {
        List<CFAssociation> tmpAssociations = getCFItemAssociations(parentStandard.getId());
        
        for(CFAssociation tmpCFAssociation : tmpAssociations)
        {
            if(tmpCFAssociation.getAssociationType().equalsIgnoreCase("isChildOf"))
            {
                OriginNodeURI tmpOriginNodeURI = tmpCFAssociation.getOriginNodeURI();
                
                if(tmpOriginNodeURI != null)
                {
                    // If the is NOT equal to the item we are using in the association
                    if(!tmpOriginNodeURI.getIdentifier().equalsIgnoreCase(parentStandard.getId()))
                    {
                        // Get the full item for this association from the document map
                        CFItem childCFItem = cfItemsMap.get(tmpOriginNodeURI.getIdentifier());
                        
                        // Create new standard object
                        Standard childStandard = new Standard();
                        childStandard.setId(childCFItem.getIdentifier());
                        childStandard.setParentId(parentStandard.getId());
                        childStandard.setFullStatement(StringUtils.stripToEmpty(childCFItem.getFullStatement()));
                        childStandard.setNotes(StringUtils.stripToEmpty(childCFItem.getNotes()));
                        childStandard.setHumanCodingScheme(StringUtils.stripToEmpty(childCFItem.getHumanCodingScheme()));
                        childStandard.setListEnumeration(StringUtils.stripToEmpty(childCFItem.getListEnumeration()));
                        childStandard.setType(StringUtils.stripToEmpty(childCFItem.getCFItemType()));
                        
                        CFItemTypeURI childCFItemTypeURI = childCFItem.getCFItemTypeURI();
                        if(childCFItemTypeURI != null)
                        {
                            childStandard.setTypeId(StringUtils.stripToEmpty(childCFItemTypeURI.getIdentifier()));
                        }
                        
                        // Set Document level values
                        childStandard.setDocumentId(standardDocument.getId());
                        childStandard.setDocumentTitle(standardDocument.getDocumentTitle());
                        childStandard.setCreator(standardDocument.getCreator());
                        childStandard.setAdoptionStatus(standardDocument.getAdoptionStatus());
                        childStandard.setLanguage(standardDocument.getLanguage());
                        
                        // Check for child documents
                        getChildAssociations(standardDocument, cfItemsMap, childStandard);
                        
                        // Add the child to the parent standard
                        parentStandard.getStandards().add(childStandard);
                    }
                }
            }
        }
    }
}
