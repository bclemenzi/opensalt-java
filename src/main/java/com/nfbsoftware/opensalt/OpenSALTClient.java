package com.nfbsoftware.opensalt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.difflib.algorithm.DiffException;
import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import com.nfbsoftware.opensalt.model.CFAssociation;
import com.nfbsoftware.opensalt.model.CFDocument;
import com.nfbsoftware.opensalt.model.CFDocumentURI;
import com.nfbsoftware.opensalt.model.CFItem;
import com.nfbsoftware.opensalt.model.CFItemTypeURI;
import com.nfbsoftware.opensalt.model.CFPackages;
import com.nfbsoftware.opensalt.model.DestinationNodeURI;
import com.nfbsoftware.opensalt.model.Documents;
import com.nfbsoftware.opensalt.model.OriginNodeURI;
import com.nfbsoftware.pcg.model.ExactMatchOf;
import com.nfbsoftware.pcg.model.IsRelatedTo;
import com.nfbsoftware.pcg.model.PCGCrosswalk;
import com.nfbsoftware.standards.model.Crosswalk;
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
    
    private String m_authenticationUrl;
    private String m_grantType;
    private String m_scope;
    private String m_clientId;
    private String m_clientSecret;
    private String m_token;
    
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
     * @param authenticationUrl - The full url for the oAuth endpoint
     * @param clientId - The client id issued by the authentication source
     * @param clientSecret - The client secret issued by the authentication source
     * @param grantType - Object type we are granting
     * @param scope - Scope of the authentication
     * @throws Exception - catch all for exceptions
     */
    public void setCredentials(String authenticationUrl, String clientId, String clientSecret, String grantType, String scope) throws Exception
    {
        m_authenticationUrl = authenticationUrl;
        m_clientId = clientId;
        m_clientSecret = clientSecret;
        m_grantType = grantType;
        m_scope = scope;
        
        HttpClient httpClient = HttpClientBuilder.create().build();
        
        HttpPost postRequest = new HttpPost(m_authenticationUrl);
        
        String usernamePassword = m_clientId + ":" + m_clientSecret;
        String encodedString = Base64.getEncoder().encodeToString(usernamePassword.getBytes());
        
        postRequest.setHeader("Authorization", "Basic " + encodedString);
        postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
        
        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("grant_type", m_grantType));
        postParameters.add(new BasicNameValuePair("scope", m_scope));
        
        postRequest.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));
        
        // Get our response from the SALT server
        HttpResponse saltyResponse = httpClient.execute(postRequest);
        HttpEntity entity = saltyResponse.getEntity();
        
        // If we have an entity, convert it to Java objects
        if(entity != null) 
        {
            String responseString = EntityUtils.toString(entity);   
            
            JSONObject responseJSON = new JSONObject(responseString);
            
            m_token = StringUtils.stripToEmpty(responseJSON.getString("access_token"));
            //System.out.println("Authenticated Bearer Token: " + m_token);
        }
    }
    
    /**
     * <p>This method will use the credentials entered previously to request a new access token</p>
     * 
     * @throws Exception - catch all for exceptions
     */
    public void refreshAccessToken() throws Exception
    {
        // Only refresh the token if we have been granted one in the past.
        if(m_token != null)
        {
            // Refresh the access token
            setCredentials(m_authenticationUrl, m_clientId, m_clientSecret, m_grantType, m_scope);
        }
    }
    
    /**
     * <p>This is a request to the Service Provider to provide all of the Competency Framework Documents.</p>
     * 
     * @return A set of CFDocument objects
     * @throws Exception - catch all for exceptions
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
        
        // Add the bearer token if we have one
        if(m_token != null)
        {
            getRequest.setHeader("Authorization", "Bearer " + m_token);
        }

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
     * <p>This is a request to the Service Provider to provide all of the Competency Framework Documents.</p>
     * 
     * @param limit The 'limit' defines the download segmentation value i.e. the maximum number of records to be contained in the response.
     * @param offset The 'offset' is the number of the first record to be supplied in the segmented response message.
     * @param sort The 'sort' identifies the sort criteria to be used for the records in the response message.
     * @param orderBy This is used as part of the sorting mechanism to be use by the service provider.
     * @return A set of CFDocument objects
     * @throws Exception - catch all for exceptions
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
        
        // Add the bearer token if we have one
        if(m_token != null)
        {
            getRequest.setHeader("Authorization", "Bearer " + m_token);
        }

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
     * @return sourceId - A single CFDocument object
     * @throws Exception - catch all for exceptions
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
        
        // Add the bearer token if we have one
        if(m_token != null)
        {
            getRequest.setHeader("Authorization", "Bearer " + m_token);
        }

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
     * @return sourceId - A set of CFItem objects
     * @throws Exception - catch all for exceptions
     */
    public CFPackages getCFPackages(String sourceId) throws Exception
    {
        CFPackages cfPackages = null;
        
        HttpClient httpClient = HttpClientBuilder.create().build();
        
        // specify the host, protocol, and port
        HttpHost target = new HttpHost(m_hostDomain, m_hostPort, m_hostScheme);
        
        logger.debug("Getting CFPackages " + sourceId);
        
        // specify the get request
        HttpGet getRequest = new HttpGet("/ims/case/v1p0/CFPackages/" + sourceId);
        
        // Add the bearer token if we have one
        if(m_token != null)
        {
            getRequest.setHeader("Authorization", "Bearer " + m_token);
        }

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
            cfPackages = mapper.readValue(responseJSON.toString(), CFPackages.class);
        }
        
        return cfPackages;
    }
    
    /**
     * <p>This is a request to the service provider to provide the CFItems for the specific Competency Framework Package.</p>
     * 
     * @param sourceId The GUID that identifies the Competency Framework Document that is to be read from the service provider.
     * @return sourceId - A set of CFItem objects
     * @throws Exception - catch all for exceptions
     */
    public List<CFItem> getCFPackageItems(String sourceId) throws Exception
    {
        logger.debug("Getting getCFPackageItems " + sourceId);
        
        CFPackages cfPackages = getCFPackages(sourceId);
        
        List<CFItem> cfItems = cfPackages.getCFItems();
        
        return cfItems;
    }
    
    /**
     * <p>This is a request to the service provider to provide the top-most CFItems for the specific Competency Framework Package.</p>
     * 
     * @param sourceId The GUID that identifies the Competency Framework Document that is to be read from the service provider.
     * @return sourceId - A set of CFItem objects
     * @throws Exception - catch all for exceptions
     */
    public List<CFItem> getTopLevelCFPackageItems(String sourceId) throws Exception
    {
        logger.debug("Getting getTopLevelCFPackageItems " + sourceId);
        
        Set<String> itemIds = new HashSet<String>();

        // Create a map to store the top level standards of the document by sequence
        Map<Integer, CFItem> topLevelStandardsMap = new HashMap<Integer, CFItem>();
        
        // Get our full framework package
        CFPackages cfPackages = getCFPackages(sourceId);
        
        // Get all the items of the package
        List<CFItem> cfItems = cfPackages.getCFItems();
        
        // Loop through the items to find the top level items
        for(CFItem tmpCFItem : cfItems)
        {
            if(!itemIds.contains(tmpCFItem.getIdentifier()))
            {
                List<CFAssociation> tmpAssociations = cfPackages.getCFAssociations();
                for(CFAssociation tmpCFAssociation : tmpAssociations)
                {
                    OriginNodeURI tmpOriginNodeURI = tmpCFAssociation.getOriginNodeURI();
                    DestinationNodeURI tmpDestinationNodeURI = tmpCFAssociation.getDestinationNodeURI();
                    
                    if(tmpDestinationNodeURI != null && tmpOriginNodeURI != null)
                    {
                        if(tmpDestinationNodeURI.getIdentifier().equalsIgnoreCase(sourceId) 
                                && tmpOriginNodeURI.getIdentifier().equalsIgnoreCase(tmpCFItem.getIdentifier()))
                        {
                            Integer sequenceNumber = tmpCFAssociation.getSequenceNumber();
                            if(sequenceNumber == null)
                            {
                                sequenceNumber = new Integer(topLevelStandardsMap.size());
                            }
                            
                            topLevelStandardsMap.put(sequenceNumber, tmpCFItem);
                            itemIds.add(tmpCFItem.getIdentifier());
                            
                            break;
                        }
                    }
                }
            }
        }
        
        // Sort the standards:
        SortedSet<Integer> sortedTopLevelStandards = new TreeSet<Integer>(topLevelStandardsMap.keySet());
        
        List<CFItem> topLevelCfItems = new ArrayList<CFItem>(); 
        for(Integer key : sortedTopLevelStandards)
        {
            topLevelCfItems.add(topLevelStandardsMap.get(key));
        }
        
        return topLevelCfItems;
    }
    
    /**
     * <p>This is a request to the service provider to provide the CFAssociations for the specific Competency Framework Package.</p>
     * 
     * @param sourceId The GUID that identifies the Competency Framework Document that is to be read from the service provider.
     * @return sourceId - A set of CFItem objects
     * @throws Exception - catch all for exceptions
     */
    public List<CFAssociation> getCFPackageAssociations(String sourceId) throws Exception
    {
        logger.debug("Getting getCFPackageAssociations " + sourceId);
        
        CFPackages cfPackages = getCFPackages(sourceId);
        
        List<CFAssociation> cfAssociations = cfPackages.getCFAssociations();
        
        return cfAssociations;
    }
    
    /**
     * <p>This is a request to the Service Provider to provide the specified Competency Framework Item.</p>
     * 
     * @param sourceId The GUID that identifies the Competency Framework Item that is to be read from the service provider.
     * @return sourceId - A single CFDocument object
     * @throws Exception - catch all for exceptions
     */
    public CFItem getCFItem(String sourceId) throws Exception
    {
        CFItem cfItem = null;
        
        HttpClient httpClient = HttpClientBuilder.create().build();
        
        // specify the host, protocol, and port
        HttpHost target = new HttpHost(m_hostDomain, m_hostPort, m_hostScheme);
        
        logger.debug("Getting getCFItem " + sourceId);
        
        // specify the get request
        HttpGet getRequest = new HttpGet("/ims/case/v1p0/CFItems/" + sourceId);
        
        // Add the bearer token if we have one
        if(m_token != null)
        {
            getRequest.setHeader("Authorization", "Bearer " + m_token);
        }

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
            cfItem = mapper.readValue(responseJSON.toString(),  CFItem.class);
        }
        
        return cfItem;
    }
    
    /**
     * <p>This is a request to the Service Provider to provide the specified Competency Framework Item.</p>
     * 
     * @param sourceId The GUID that identifies the Competency Framework Item that is to be read from the service provider.
     * @return sourceId - A single CFDocument object
     * @throws Exception - catch all for exceptions
     */
    public CFItem getCFItemParent(String sourceId) throws Exception
    {
        CFItem cfItemParent = null;
        
        CFItem childItem = getCFItem(sourceId);
        
        if(childItem != null)
        {
            List<CFAssociation> associations = getCFItemAssociations(sourceId);
            
            if(associations != null)
            {
                for(CFAssociation tmpCFAssociation : associations)
                {
                    if(tmpCFAssociation.getAssociationType().equalsIgnoreCase("isChildOf"))
                    {
                        DestinationNodeURI destinationNodeURI = tmpCFAssociation.getDestinationNodeURI();
                        
                        if(destinationNodeURI != null)
                        {
                            String parentItemId = destinationNodeURI.getIdentifier();
                            
                            if(!parentItemId.equalsIgnoreCase(sourceId))
                            {
                                cfItemParent = getCFItem(parentItemId);
                                
                                break;
                            }
                        }
                    }
                }
            }
        }
        
        return cfItemParent;
    }
    
    /**
     * <p>This is a request to the Service Provider to provide the all of the Competency Associations for the specified CFItem.</p>
     * 
     * @param sourceId The GUID that identifies the Competency Framework Document that is to be read from the service provider.
     * @return sourceId - A set of CFAssociation objects
     * @throws Exception - catch all for exceptions
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
        
        // Add the bearer token if we have one
        if(m_token != null)
        {
            getRequest.setHeader("Authorization", "Bearer " + m_token);
        }

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
     * 
     * @param crosswalkClient - The PCG Crosswalk client that performs the crosswalks
     * @param sourceId - The GUID that either identifies the CFDocument identifier .OR. a CFItem identifier
     * @return A full hierarchical set of standards objects
     * @throws Exception - catch all for exceptions
     */
    public Standard getFullHierarchicalStandard(CrosswalkClient crosswalkClient, String sourceId) throws Exception
    {
    	Standard fullStandardDocument = null;
    	
        CFDocument tmpCFDocument = getCFDocument(sourceId);
        
        if(tmpCFDocument.getIdentifier() != null)
        {
        	fullStandardDocument = crosswalkClient.getEvotextHierarchyFramework(sourceId);
        }
        else
        {
        	// Since we didn't find a framework document, check to see if the sourceId is a CFItem
            CFItem tempCFItem = getCFItem(sourceId);
            
            if(tempCFItem != null)
            {
            	Standard tmpStandardDocument = crosswalkClient.getEvotextHierarchyFramework(tempCFItem.getCFDocumentURI().getIdentifier());
            	
            	fullStandardDocument = checkTheChildren(sourceId, tmpStandardDocument, fullStandardDocument);
            }
        }
    	
    	return fullStandardDocument;
    }
    
    /**
     * <p>=Returns a full hierarchical representation of the standards document</p>
     * 
     * @param sourceId - The GUID that either identifies the CFDocument identifier .OR. a CFItem identifier
     * @return A full hierarchical set of standards objects
     * @throws Exception - catch all for exceptions
     */
    public Standard getFullHierarchicalStandard(String sourceId) throws Exception
    {
        Standard standardDocument = null;
        
        CFDocument tmpCFDocument = getCFDocument(sourceId);
        
        if(tmpCFDocument.getIdentifier() != null)
        {
            standardDocument = new Standard();
            standardDocument.setId(tmpCFDocument.getIdentifier());
            standardDocument.setDocumentId(tmpCFDocument.getIdentifier());
            standardDocument.setDocumentTitle(StringUtils.stripToEmpty(tmpCFDocument.getTitle()));
            standardDocument.setFullStatement(StringUtils.stripToEmpty(tmpCFDocument.getTitle()));
            standardDocument.setNotes(StringUtils.stripToEmpty(tmpCFDocument.getNotes()));
            standardDocument.setCreator(StringUtils.stripToEmpty(tmpCFDocument.getCreator()));
            standardDocument.setAdoptionStatus(StringUtils.stripToEmpty(tmpCFDocument.getAdoptionStatus()));
            standardDocument.setLanguage(StringUtils.stripToEmpty(tmpCFDocument.getLanguage()));
            
            // Set a hash map to store all the document elements
            Map<String, CFItem> cfItemsMap = new HashMap<String, CFItem>();
            
            // Get ALL the levels of the document and store them in the hash map
            CFPackages cfPackages = getCFPackages(sourceId);
            
            List<CFItem> cfItems = cfPackages.getCFItems();
            for(CFItem tmpCFItem : cfItems)
            {
                cfItemsMap.put(tmpCFItem.getIdentifier(), tmpCFItem);
            }
            
            // Create a map to store the top level standards of the document by sequence
            Map<Integer, Standard> topLevelStandardsMap = new HashMap<Integer, Standard>();
            
            // Loop the full set again to get the top level children of the document
            for(CFItem tmpCFItem : cfItems)
            {
                Integer sequenceNumber = null;
                boolean isTopLevelItem = false;
                List<CFAssociation> tmpAssociations = cfPackages.getCFAssociations();
                for(CFAssociation tmpCFAssociation : tmpAssociations)
                {
                    OriginNodeURI tmpOriginNodeURI = tmpCFAssociation.getOriginNodeURI();
                    DestinationNodeURI tmpDestinationNodeURI = tmpCFAssociation.getDestinationNodeURI();
                    
                    if(tmpDestinationNodeURI != null && tmpOriginNodeURI != null)
                    {
                        if(tmpDestinationNodeURI.getIdentifier().equalsIgnoreCase(sourceId) 
                                && tmpOriginNodeURI.getIdentifier().equalsIgnoreCase(tmpCFItem.getIdentifier()))
                        {
                            isTopLevelItem = true;
                            
                            sequenceNumber = tmpCFAssociation.getSequenceNumber();
                            break;
                        }
                    }
                }
                
                if(isTopLevelItem)
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
                    
                    // Check sequence number
                    if(sequenceNumber == null)
                    {
                        sequenceNumber = new Integer(topLevelStandardsMap.size());
                    }
                    
                    // Add the standard to the document
                    topLevelStandardsMap.put(sequenceNumber, topLevelStandard);
                }
            }
            
            // Sort the standards:
            SortedSet<Integer> sortedTopLevelStandards = new TreeSet<Integer>(topLevelStandardsMap.keySet());
            
            // Create a array to store the top level standards of the document
            List<Standard> topLevelStandards = new ArrayList<Standard>();
            for(Integer key : sortedTopLevelStandards)
            {
                topLevelStandards.add(topLevelStandardsMap.get(key));
            }
            
            // Add the top level 
            standardDocument.setStandards(topLevelStandards);
        }
        else
        {
            // Since we didn't find a framework document, check to see if the sourceId is a CFItem
            CFItem tempCFItem = getCFItem(sourceId);
            
            if(tempCFItem != null)
            {
                // We have an item, so lets get it's parent document
                tmpCFDocument = getCFDocument(tempCFItem.getCFDocumentURI().getIdentifier());
                
                // Set a hash map to store all the document elements
                Map<String, CFItem> cfItemsMap = new HashMap<String, CFItem>();
                
                // Get ALL the levels of the document and store them in the hash map
                CFPackages cfPackages = getCFPackages(tmpCFDocument.getIdentifier());
                List<CFItem> cfItems = cfPackages.getCFItems();
                for(CFItem tmpCFItem : cfItems)
                {
                    cfItemsMap.put(tmpCFItem.getIdentifier(), tmpCFItem);
                }
                
                standardDocument = new Standard();
                standardDocument.setId(tempCFItem.getIdentifier());
                standardDocument.setDocumentId(tmpCFDocument.getIdentifier());
                standardDocument.setDocumentTitle(StringUtils.stripToEmpty(tmpCFDocument.getTitle()));
                standardDocument.setFullStatement(StringUtils.stripToEmpty(tempCFItem.getFullStatement()));
                standardDocument.setNotes(StringUtils.stripToEmpty(tempCFItem.getNotes()));
                standardDocument.setCreator(StringUtils.stripToEmpty(tmpCFDocument.getCreator()));
                standardDocument.setAdoptionStatus(StringUtils.stripToEmpty(tmpCFDocument.getAdoptionStatus()));
                standardDocument.setLanguage(StringUtils.stripToEmpty(tmpCFDocument.getLanguage()));
                standardDocument.setHumanCodingScheme(StringUtils.stripToEmpty(tempCFItem.getHumanCodingScheme()));
                standardDocument.setListEnumeration(StringUtils.stripToEmpty(tempCFItem.getListEnumeration()));
                standardDocument.setType(StringUtils.stripToEmpty(tempCFItem.getCFItemType()));

                // Get children of the item
                getChildAssociations(standardDocument, cfItemsMap, standardDocument);
            }
        }
        
        return standardDocument;
    }

    private void getChildAssociations(Standard standardDocument, Map<String, CFItem> cfItemsMap, Standard parentStandard) throws Exception
    {
        List<CFAssociation> tmpAssociations = getCFItemAssociations(parentStandard.getId());
        
        // Create a map to store the top level standards of the document by sequence
        Map<Integer, Standard> topLevelStandardsMap = new HashMap<Integer, Standard>();
        
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
                        
                        Integer sequenceNumber = tmpCFAssociation.getSequenceNumber();
                        if(sequenceNumber == null)
                        {
                            sequenceNumber = new Integer(topLevelStandardsMap.size());
                        }
                        
                        // Add child to the tree map
                        topLevelStandardsMap.put(sequenceNumber, childStandard);
                    }
                }
            }
        }
        
        // Sort the standards:
        SortedSet<Integer> sortedTopLevelStandards = new TreeSet<Integer>(topLevelStandardsMap.keySet());
        
        // Create a array to store the top level standards of the document
        for(Integer key : sortedTopLevelStandards)
        {
            // Add the child to the parent standard
            parentStandard.getStandards().add(topLevelStandardsMap.get(key));
        }
    }
    
    /**
     * <p>Returns a crosswalk object that contains the semantic comparison between the two CFItems.</p>
     * 
     * @param rosettaDocumentTitle - The full or partial name of the CFDocument being uses as a "rosetta stone" between two CFItems contained in separate CFDocuments
     * @param fromCFItemId - The GUID that identifies the CFItem in CFDocument one.
     * @param toCFItemId - The GUID that identifies the CFItem in CFDocument two.
     * @return A crosswalk object that contains the semantic comparison between the two CFItems using markdown style
     * @throws Exception - catch all for exceptions
     */
    public Crosswalk getCFItemCrosswalkByDocumentTitle(String rosettaDocumentTitle, String fromCFItemId, String toCFItemId) throws Exception
    {
        String rosettaCFDocumentId = "";
        
        List<CFAssociation> fromCFAssociations = getCFItemAssociations(fromCFItemId);

        // Loop through associations to find associations to the rosetta document.
        for(CFAssociation tmpCFAssociation : fromCFAssociations)
        {
            String associatedItemId = tmpCFAssociation.getDestinationNodeURI().getIdentifier();
            
            CFItem associatedItem = getCFItem(associatedItemId);
            
            if(associatedItem.getCFDocumentURI().getTitle().contains(rosettaDocumentTitle))
            {
                rosettaCFDocumentId = associatedItem.getCFDocumentURI().getIdentifier();
                
                break;
            }
        }
        
        if(!StringUtils.isEmpty(rosettaCFDocumentId))
        {
            return getCFItemCrosswalk(rosettaCFDocumentId, fromCFItemId, toCFItemId);
        }
        
        return null;
    }
    
    /**
     * <p>Returns a crosswalk object that contains the semantic comparison between the two CFItems.</p>
     * 
     * @param rosettaCFDocumentId - The GUID that identifies the CFDocument being uses as a "rosetta stone" between two CFItems contained in separate CFDocuments
     * @param fromCFItemId - The GUID that identifies the CFItem in CFDocument one.
     * @param toCFItemId - The GUID that identifies the CFItem in CFDocument two.
     * @return A crosswalk object that contains the semantic comparison between the two CFItems using markdown style
     * @throws Exception - catch all for exceptions
     */
    public Crosswalk getCFItemCrosswalk(String rosettaCFDocumentId, String fromCFItemId, String toCFItemId) throws Exception
    {
        Crosswalk tmpCrosswalk = new Crosswalk();
        
        // Get our rosetta stone document
        CFDocument rosettaDocument = getCFDocument(rosettaCFDocumentId);
        tmpCrosswalk.setCfDocumentId(rosettaCFDocumentId);
        tmpCrosswalk.setCfDocument(rosettaDocument);
        
        // Get our FROM CFItem
        CFItem fromCFItem = getCFItem(fromCFItemId);
        tmpCrosswalk.setFromCFItemId(fromCFItem.getIdentifier());
        tmpCrosswalk.setFromCFItem(fromCFItem);
        
        // Get our TO CFItem
        CFItem toCFItem = getCFItem(toCFItemId);
        tmpCrosswalk.setToCFItemId(toCFItem.getIdentifier());
        tmpCrosswalk.setToCFItem(toCFItem);
        
        // Collection of items in the rosetta document that are not children of itself
        List<CFItem> rosettaItemAssociations = new ArrayList<CFItem>();
        
        // Get all associations for the first item
        List<CFAssociation> fromCFAssociations = getCFItemAssociations(fromCFItem.getIdentifier());

        // Loop through associations to find associations to the rosetta document.
        for(CFAssociation tmpCFAssociation : fromCFAssociations)
        {
            // Find associations to rosetta document
            if(!tmpCFAssociation.getAssociationType().equalsIgnoreCase("isChildOf"))
            {
                String associatedItemId = tmpCFAssociation.getDestinationNodeURI().getIdentifier();
                
                CFItem associatedItem = getCFItem(associatedItemId);
                
                if(associatedItem.getCFDocumentURI().getIdentifier().equalsIgnoreCase(rosettaDocument.getIdentifier()))
                {
                    rosettaItemAssociations.add(associatedItem);
                    
                    tmpCrosswalk.getAssociationTypes().add(tmpCFAssociation.getAssociationType());
                    tmpCrosswalk.setFromItemAssociationOfDocument(tmpCFAssociation.getAssociationType());
                    
                    break;
                }
            }
        }
        
        // Loop though the rosetta items to find associations with the toCFItem
        for(CFItem tmpRosettaCFItem : rosettaItemAssociations)
        {
            List<CFAssociation> toCFAssociations = getCFItemAssociations(tmpRosettaCFItem.getIdentifier());
            
            // Loop through associations to find associations to the rosetta document.
            for(CFAssociation tmpCFAssociation : toCFAssociations)
            {
                // Find associations to rosetta document
                if(!tmpCFAssociation.getAssociationType().equalsIgnoreCase("isChildOf"))
                {
                    OriginNodeURI tmpOriginNodeURI = tmpCFAssociation.getOriginNodeURI();
                    
                    if(tmpOriginNodeURI != null)
                    {
                        if(tmpOriginNodeURI.getIdentifier().equalsIgnoreCase(toCFItem.getIdentifier()))
                        {
                            // Found the crosswalk element
                            tmpCrosswalk.getAssociationTypes().add(tmpCFAssociation.getAssociationType());
                            tmpCrosswalk.setDocumentAssociationOfToItem(tmpCFAssociation.getAssociationType());
                            
                            break;
                        }
                    }
                }
            }
        }
        
        // Perform the semantic comparison of text
        String fromText = fromCFItem.getFullStatement();
        String toText = toCFItem.getFullStatement();
        
        // Get the semantic comparison
        String semanticComparison = generateSemanticComparison(fromText, toText);
        
        tmpCrosswalk.setSemanticComparison(semanticComparison);
        
        return tmpCrosswalk;
    }
    
    /**
     * <p>Returns a crosswalk list that contains the semantic comparisons of a CFItem in a another CFDocument</p>
     * 
     * @param rosettaDocumentTitle - The full or partial name of the CFDocument being uses as a "rosetta stone" between two CFItems contained in separate CFDocuments
     * @param fromCFItemId - The GUID that identifies the CFItem we're looking to associate with.
     * @param toCFDocumentId - The GUID that identifies the CFDocument we are looking for associations in.
     * @return A crosswalk object that contains the semantic comparison between the two CFItems using markdown style
     * @throws Exception - catch all for exceptions
     */
    public List<Crosswalk> getCFItemCrosswalksByDocumentTitle(String rosettaDocumentTitle, String fromCFItemId, String toCFDocumentId) throws Exception
    {
        String rosettaCFDocumentId = "";
        
        List<CFAssociation> fromCFAssociations = getCFItemAssociations(fromCFItemId);

        // Loop through associations to find associations to the rosetta document.
        for(CFAssociation tmpCFAssociation : fromCFAssociations)
        {
            String associatedItemId = tmpCFAssociation.getDestinationNodeURI().getIdentifier();
            
            CFItem associatedItem = getCFItem(associatedItemId);
            
            CFDocumentURI associatedCFDocumentURI = associatedItem.getCFDocumentURI();
            
            if(associatedCFDocumentURI != null)
            {
                if(associatedCFDocumentURI.getTitle().contains(rosettaDocumentTitle))
                {
                    //System.out.println("Rossetta Document [" + associatedItem.getCFDocumentURI().getIdentifier() + "] " + associatedItem.getCFDocumentURI().getTitle());
                    rosettaCFDocumentId = associatedItem.getCFDocumentURI().getIdentifier();
                    
                    break;
                }
            }
        }
        
        if(!StringUtils.isEmpty(rosettaCFDocumentId))
        {
            return getCFItemCrosswalks(rosettaCFDocumentId, fromCFItemId, toCFDocumentId);
        }
        
        return new ArrayList<Crosswalk>();
    }
    
    /**
     * <p>Returns a crosswalk list that contains the semantic comparisons of a CFItem in a another CFDocument</p>
     * 
     * @param rosettaCFDocumentId - The GUID that identifies the CFDocument being uses as a "rosetta stone" between two CFItems contained in separate CFDocuments
     * @param fromCFItemId - The GUID that identifies the CFItem we're looking to associate with.
     * @param toCFDocumentId - The GUID that identifies the CFDocument we are looking for associations in.
     * @return A crosswalk object that contains the semantic comparison between the two CFItems using markdown style
     * @throws Exception - catch all for exceptions
     */
    public List<Crosswalk> getCFItemCrosswalks(String rosettaCFDocumentId, String fromCFItemId, String toCFDocumentId) throws Exception
    {
        Set<String> toItemIds = new HashSet<String>();
        List<Crosswalk> tmpCrosswalkList = new ArrayList<Crosswalk>();
        
        // Get our rosetta stone document
        CFDocument rosettaDocument = getCFDocument(rosettaCFDocumentId);
        //System.out.println("rosettaDocument: " + rosettaDocument.getTitle());
        
        // Get our FROM CFItem
        CFItem fromCFItem = getCFItem(fromCFItemId);
        
        // Collection of items in the rosetta document that are not children of itself
        List<CFItem> rosettaItemAssociations = new ArrayList<CFItem>();
        
        // Get all associations for the first item
        List<CFAssociation> fromCFAssociations = getCFItemAssociations(fromCFItem.getIdentifier());

        // Loop through associations to find associations to the rosetta document.
        for(CFAssociation tmpCFAssociation : fromCFAssociations)
        {
            // Find associations to rosetta document
            if(!tmpCFAssociation.getAssociationType().equalsIgnoreCase("isChildOf"))
            {
                String associatedItemId = tmpCFAssociation.getDestinationNodeURI().getIdentifier();
                //System.out.println("associatedItemId: " + associatedItemId + "  " + tmpCFAssociation.getDestinationNodeURI().getTitle());
                
                CFItem associatedItem = getCFItem(associatedItemId);
                
                if(associatedItem.getCFDocumentURI().getIdentifier().equalsIgnoreCase(rosettaDocument.getIdentifier()))
                {
                    rosettaItemAssociations.add(associatedItem);
                    
                    break;
                }
            }
        }
        
        // Loop though the rosetta items to find associations with the toCFItem
        for(CFItem tmpRosettaCFItem : rosettaItemAssociations)
        {
            List<CFAssociation> toCFAssociations = getCFItemAssociations(tmpRosettaCFItem.getIdentifier());
            
            // Loop through associations to find associations to the rosetta document.
            for(CFAssociation tmpCFAssociation : toCFAssociations)
            {
                // Find associations to rosetta document
                if(!tmpCFAssociation.getAssociationType().equalsIgnoreCase("isChildOf"))
                {
                    OriginNodeURI tmpOriginNodeURI = tmpCFAssociation.getOriginNodeURI();
                    
                    if(tmpOriginNodeURI != null)
                    {
                        // If the association is from the TO document, create the crosswalk.
                        if(tmpCFAssociation.getCFDocumentURI().getIdentifier().equalsIgnoreCase(toCFDocumentId))
                        {
                            CFItem originItem = getCFItem(tmpOriginNodeURI.getIdentifier());
                            //System.out.println("originItem [" + originItem.getIdentifier() + "]: " + originItem.getHumanCodingScheme() + "  (" + originItem.getCFDocumentURI().getTitle() + ")");
                            
                            if(!toItemIds.contains(originItem.getIdentifier()))
                            {
                                //System.out.println("toItemIds associatedItemId document: " + originItem.getCFDocumentURI().getTitle() + "  " + originItem.getCFDocumentURI().getIdentifier());
                                //System.out.println("toItemIds ItemId: " + originItem.getIdentifier() + "  " + originItem.getHumanCodingScheme());

                                Crosswalk tmpCrosswalk = new Crosswalk();
                                tmpCrosswalk.setCfDocumentId(rosettaCFDocumentId);
                                tmpCrosswalk.setCfDocument(rosettaDocument);
                                tmpCrosswalk.setFromCFItemId(fromCFItem.getIdentifier());
                                tmpCrosswalk.setFromCFItem(fromCFItem);
                                tmpCrosswalk.setToCFItemId(originItem.getIdentifier());
                                tmpCrosswalk.setToCFItem(originItem);
                                
                                // Found the crosswalk element
                                tmpCrosswalk.getAssociationTypes().add(tmpCFAssociation.getAssociationType());
                                tmpCrosswalk.setDocumentAssociationOfToItem(tmpCFAssociation.getAssociationType());
                                
                                // Perform the semantic comparison of text
                                String fromText = StringUtils.trimToEmpty(fromCFItem.getFullStatement());
                                String toText = StringUtils.trimToEmpty(originItem.getFullStatement());
                                
                                // Get the semantic comparison
                                String semanticComparison = generateSemanticComparison(fromText, toText);
                                
                                tmpCrosswalk.setSemanticComparison(semanticComparison);
                                
                                // Add the crosswalk to the result list.
                                tmpCrosswalkList.add(tmpCrosswalk);
                                
                                // Add this id to the list of used items
                                toItemIds.add(originItem.getIdentifier());
                            }
                        }
                    }
                }
            }
        }
        
        return tmpCrosswalkList;
    }
    
    /**
     * <p>Returns a crosswalk list that contains the semantic comparisons of a CFItem in a another CFDocument</p>
     * 
     * @param crosswalkClient - The PCG Crosswalk client that performs the crosswalks
     * @param fromCFItemId - The CFItem in a CFDocument.
     * @param targetCFDocumentId - The GUID that identifies the target CFDocument.
     * 
     * @return A crosswalk object that contains the semantic comparison between the two CFItems using markdown style
     * @throws Exception - catch all for exceptions
     */
    public List<Crosswalk> getCFItemCrosswalks(CrosswalkClient crosswalkClient, String fromCFItemId, String targetCFDocumentId) throws Exception
    {
    	CFItem fromCFItem = getCFItem(fromCFItemId);
    	
    	return getCFItemCrosswalks(crosswalkClient, fromCFItem, targetCFDocumentId);
    }
    
    /**
     * <p>Returns a crosswalk list that contains the semantic comparisons of a CFItem in a another CFDocument</p>
     * 
     * @param crosswalkClient - The PCG Crosswalk client that performs the crosswalks
     * @param fromCFItem - The GUID that identifies the CFItem in a CFDocument.
     * @param targetCFDocumentId - The GUID that identifies the target CFDocument.
     * 
     * @return A crosswalk object that contains the semantic comparison between the two CFItems using markdown style
     * @throws Exception - catch all for exceptions
     */
    public List<Crosswalk> getCFItemCrosswalks(CrosswalkClient crosswalkClient, CFItem fromCFItem, String targetCFDocumentId) throws Exception
    {
        List<Crosswalk> tmpCrosswalkList = new ArrayList<Crosswalk>();
        
        // Get our target document
        CFDocument targetDocument = getCFDocument(targetCFDocumentId);
        
        // Call our to PCG to get crosswalks
        PCGCrosswalk tmpPCGCrosswalk = crosswalkClient.crosswalkByIdentifier(fromCFItem.getIdentifier(), targetCFDocumentId);
        
        if(tmpPCGCrosswalk != null)
        {
            for(ExactMatchOf tmpExactMatchOf : tmpPCGCrosswalk.getExactMatchOf())
            {
                Crosswalk tmpCrosswalk = new Crosswalk();
                
                // Set our target document
                tmpCrosswalk.setCfDocumentId(targetCFDocumentId);
                tmpCrosswalk.setCfDocument(targetDocument);
                
                // Set our from item
                tmpCrosswalk.setFromCFItemId(fromCFItem.getIdentifier());
                tmpCrosswalk.setFromCFItem(fromCFItem);
                
                // Get the item we crosswalked to
                CFItem toCFItem = getCFItem(tmpExactMatchOf.getIdentifier());
                
                tmpCrosswalk.setToCFItemId(toCFItem.getIdentifier());
                tmpCrosswalk.setToCFItem(toCFItem);
                
                // Perform the semantic comparison of text
                String fromText = fromCFItem.getFullStatement();
                String toText = toCFItem.getFullStatement();
                
                tmpCrosswalk.getAssociationTypes().add("exactMatchOf");
                tmpCrosswalk.setDocumentAssociationOfToItem("exactMatchOf");
                
                // Get the semantic comparison
                String semanticComparison = generateSemanticComparison(fromText, toText);
                
                tmpCrosswalk.setSemanticComparison(semanticComparison);
                
                tmpCrosswalkList.add(tmpCrosswalk);
            }
            
            for(IsRelatedTo tmpIsRelatedTo : tmpPCGCrosswalk.getIsRelatedTo())
            {
                Crosswalk tmpCrosswalk = new Crosswalk();
                
                // Set our target document
                tmpCrosswalk.setCfDocumentId(targetCFDocumentId);
                tmpCrosswalk.setCfDocument(targetDocument);
                
                // Set our from item
                tmpCrosswalk.setFromCFItemId(fromCFItem.getIdentifier());
                tmpCrosswalk.setFromCFItem(fromCFItem);
                
                // Get the item we crosswalked to
                CFItem toCFItem = getCFItem(tmpIsRelatedTo.getIdentifier());
                
                tmpCrosswalk.setToCFItemId(toCFItem.getIdentifier());
                tmpCrosswalk.setToCFItem(toCFItem);
                
                // Perform the semantic comparison of text
                String fromText = fromCFItem.getFullStatement();
                String toText = toCFItem.getFullStatement();
                
                tmpCrosswalk.getAssociationTypes().add("isRelatedTo");
                tmpCrosswalk.setDocumentAssociationOfToItem("isRelatedTo");
                
                // Get the semantic comparison
                String semanticComparison = generateSemanticComparison(fromText, toText);
                
                tmpCrosswalk.setSemanticComparison(semanticComparison);
                
                tmpCrosswalkList.add(tmpCrosswalk);
            }
        }
        else
        {
            logger.error("No PCG crosswalk data found, null pointer, for target document: " + targetDocument.getTitle() + " (" + targetDocument.getIdentifier() + ")");
        }
        
        return tmpCrosswalkList;
    }
    
    /**
     * <p>Returns a crosswalk list that contains the semantic comparisons of a CFItem in a another CFDocument</p>
     * 
     * @param crosswalkClient - The PCG Crosswalk client that performs the crosswalks
     * @param fromCFItemIds - List of GUIDs that identify the CFItems in a CFDocument.
     * @param targetCFDocumentId - The GUID that identifies the target CFDocument.
     * 
     * @return A crosswalk object that contains the semantic comparison between the two CFItems using markdown style
     * @throws Exception - catch all for exceptions
     */
    public Map<String, List<Crosswalk>> getCFItemCrosswalksByIds(CrosswalkClient crosswalkClient, List<String> fromCFItemIds, String targetCFDocumentId) throws Exception
    {
    	List<CFItem> fromCFItems = new ArrayList<CFItem>();
    	
    	return getCFItemCrosswalks(crosswalkClient, fromCFItems, targetCFDocumentId);
    }
    
    /**
     * <p>Returns a crosswalk list that contains the semantic comparisons of a CFItem in a another CFDocument</p>
     * 
     * @param crosswalkClient - The PCG Crosswalk client that performs the crosswalks
     * @param fromCFItems - List of CFItems in a CFDocument.
     * @param targetCFDocumentId - The GUID that identifies the target CFDocument.
     * 
     * @return A crosswalk object that contains the semantic comparison between the two CFItems using markdown style
     * @throws Exception - catch all for exceptions
     */
    public Map<String, List<Crosswalk>> getCFItemCrosswalks(CrosswalkClient crosswalkClient, List<CFItem> fromCFItems, String targetCFDocumentId) throws Exception
    {
    	Map<String, List<Crosswalk>> tmpCrosswalkMap = new HashMap<String, List<Crosswalk>>();
        
        // Get our target document
        CFDocument targetDocument = getCFDocument(targetCFDocumentId);
        
        // Create fromItemMap
        List<String> fromCFItemIds = new ArrayList<String>();
        Map<String, CFItem> fromItemMap = new HashMap<String, CFItem>();
        
        for(CFItem tmpCFItem : fromCFItems)
        {
        	fromCFItemIds.add(tmpCFItem.getIdentifier());
        	fromItemMap.put(tmpCFItem.getIdentifier(), tmpCFItem);
        }
        
        // Get all the crosswalks
        Map<String, PCGCrosswalk> tmpPCGCrosswalks = crosswalkClient.crosswalkByIdentifiers(fromCFItemIds, targetCFDocumentId);
        
        for(String fromIdKey : tmpPCGCrosswalks.keySet())
        {
        	List<Crosswalk> tmpCrosswalkList = new ArrayList<Crosswalk>();
        	
	        // Get our FROM CFItem within our cached map
	        CFItem fromCFItem = fromItemMap.get(fromIdKey);
	        
	        // Call our to PCG to get crosswalks
	        PCGCrosswalk tmpPCGCrosswalk = tmpPCGCrosswalks.get(fromIdKey);
	        
	        if(tmpPCGCrosswalk != null)
	        {
	            for(ExactMatchOf tmpExactMatchOf : tmpPCGCrosswalk.getExactMatchOf())
	            {
	                Crosswalk tmpCrosswalk = new Crosswalk();
	                
	                // Set our target document
	                tmpCrosswalk.setCfDocumentId(targetCFDocumentId);
	                tmpCrosswalk.setCfDocument(targetDocument);
	                
	                // Set our from item
	                tmpCrosswalk.setFromCFItemId(fromCFItem.getIdentifier());
	                tmpCrosswalk.setFromCFItem(fromCFItem);
	                
	                // Get the item we crosswalked to
	                CFItem toCFItem = getCFItem(tmpExactMatchOf.getIdentifier());
	                
	                tmpCrosswalk.setToCFItemId(toCFItem.getIdentifier());
	                tmpCrosswalk.setToCFItem(toCFItem);
	                
	                // Perform the semantic comparison of text
	                String fromText = fromCFItem.getFullStatement();
	                String toText = toCFItem.getFullStatement();
	                
	                tmpCrosswalk.getAssociationTypes().add("exactMatchOf");
	                tmpCrosswalk.setDocumentAssociationOfToItem("exactMatchOf");
	                
	                // Get the semantic comparison
	                String semanticComparison = generateSemanticComparison(fromText, toText);
	                
	                tmpCrosswalk.setSemanticComparison(semanticComparison);
	                
	                tmpCrosswalkList.add(tmpCrosswalk);
	            }
	            
	            for(IsRelatedTo tmpIsRelatedTo : tmpPCGCrosswalk.getIsRelatedTo())
	            {
	                Crosswalk tmpCrosswalk = new Crosswalk();
	                
	                // Set our target document
	                tmpCrosswalk.setCfDocumentId(targetCFDocumentId);
	                tmpCrosswalk.setCfDocument(targetDocument);
	                
	                // Set our from item
	                tmpCrosswalk.setFromCFItemId(fromCFItem.getIdentifier());
	                tmpCrosswalk.setFromCFItem(fromCFItem);
	                
	                // Get the item we crosswalked to
	                CFItem toCFItem = getCFItem(tmpIsRelatedTo.getIdentifier());
	                
	                tmpCrosswalk.setToCFItemId(toCFItem.getIdentifier());
	                tmpCrosswalk.setToCFItem(toCFItem);
	                
	                // Perform the semantic comparison of text
	                String fromText = fromCFItem.getFullStatement();
	                String toText = toCFItem.getFullStatement();
	                
	                tmpCrosswalk.getAssociationTypes().add("isRelatedTo");
	                tmpCrosswalk.setDocumentAssociationOfToItem("isRelatedTo");
	                
	                // Get the semantic comparison
	                String semanticComparison = generateSemanticComparison(fromText, toText);
	                
	                tmpCrosswalk.setSemanticComparison(semanticComparison);
	                
	                tmpCrosswalkList.add(tmpCrosswalk);
	            }
	        }
	        else
	        {
	            logger.error("No PCG crosswalk data found, null pointer, for target document: " + targetDocument.getTitle() + " (" + targetDocument.getIdentifier() + ")");
	        }
	        
	        // Add the overall crosswalk to the map
	        tmpCrosswalkMap.put(fromIdKey, tmpCrosswalkList);
        }
        
        return tmpCrosswalkMap;
    }
    
    /**
     * 
     * @param cfItemId - The GUID that identifies the CFItem in a CFDocument.
     * @return A string representing the breadcrumb trail.
     * @throws Exception - catch all for exceptions
     */
    public String getItemBreadcrumbTrail(String cfItemId) throws Exception
    {
        StringBuffer breadcrumbTrail = new StringBuffer();
        
        List<String> breadcrumbItems = new ArrayList<String>();
        
        // Populate our breadcrumb array
        getParentBreadcrumbs(cfItemId, breadcrumbItems);
        
        // Look through the array backwards to create the trail
        for (int i = breadcrumbItems.size(); i > 0; i--)
        {
            String tempItem = breadcrumbItems.get(i-1);
            
            if(!StringUtils.isEmpty(tempItem))
            {
                breadcrumbTrail.append(tempItem);
                
                if(i != 1)
                {
                    breadcrumbTrail.append(" > ");
                }
            }
        }
        
        return breadcrumbTrail.toString();
    }
    
    /**
     * 
     * @param cfItemId - The GUID that identifies the CFItem in a CFDocument.
     * @return A CFItem of the ultimate parent
     * @throws Exception - catch all for exceptions
     */
    public CFItem getUltimateParentItem(String cfItemId) throws Exception
    {
        List<CFItem> parentItems = new ArrayList<CFItem>();
        
        // Populate our breadcrumb array
        getParentItems(cfItemId, parentItems);
        
        if(parentItems.size() > 0)
        {
            return parentItems.get(parentItems.size()-1);
        }
        else
        {
            return null;
        }
    }
    
    /**
     * 
     * @param cfItemId - The GUID that identifies the CFItem in a CFDocument.
     * @param breadcrumbItems A list representing the breadcrumb trail.
     * @throws Exception - catch all for exceptions
     */
    private void getParentItems(String cfItemId, List<CFItem> parentItems) throws Exception
    {
        CFItem parentItem = getCFItemParent(cfItemId);
        
        if(parentItem != null)
        {
            parentItems.add(parentItem);
            
            // Loop through to the next parent up the tree
            getParentItems(parentItem.getIdentifier(), parentItems);
        }
    }

    /**
     * 
     * @param cfItemId - The GUID that identifies the CFItem in a CFDocument.
     * @param breadcrumbItems A list representing the breadcrumb trail.
     * @throws Exception - catch all for exceptions
     */
    private void getParentBreadcrumbs(String cfItemId, List<String> breadcrumbItems) throws Exception
    {
        CFItem parentItem = getCFItemParent(cfItemId);
        
        if(parentItem != null)
        {
            String tmpHumanCodingScheme = StringUtils.stripToEmpty(parentItem.getHumanCodingScheme());
            String tmpFullStatement = StringUtils.stripToEmpty(parentItem.getFullStatement());
            
            if(!StringUtils.isEmpty(tmpHumanCodingScheme))
            {
                tmpFullStatement = tmpHumanCodingScheme;
            }
            
            breadcrumbItems.add(StringUtils.stripToEmpty(tmpFullStatement));
            
            // Loop through to the next parent up the tree
            getParentBreadcrumbs(parentItem.getIdentifier(), breadcrumbItems);
        }
    }

    /**
     * <p>Method for running to text strings through a semantic comparison engine.</p>
     * 
     * @param fromText - Beginning text
     * @param toText - Comparison text
     * @return - returns the semantic comparison text
     * 
     * @throws DiffException - catch all for exceptions
     */
    public String generateSemanticComparison(String fromText, String toText) throws DiffException
    {
        String semanticComparison = "";
        
        DiffRowGenerator generator = DiffRowGenerator.create()
                .showInlineDiffs(true)
                .mergeOriginalRevised(true)
                .inlineDiffByWord(true)
                .oldTag(f -> f?"<span class=\"openSaltOldTag\">":"</span>")
                .newTag(f -> f?"<span class=\"openSaltNewTag\">":"</span>")
                .build();
        
        List<DiffRow> rows = generator.generateDiffRows(
                Arrays.asList(fromText),
                Arrays.asList(toText));
        
        //System.out.println(rows.get(0).getOldLine());
        semanticComparison = rows.get(0).getOldLine();
        
        return semanticComparison;
    }
	
    /**
     * 
     * @param sourceId
     * @param standardDocument
     * @param fullStandardDocument
     * @return - returns Standard opbject
     */
	private Standard checkTheChildren(String sourceId, Standard standardDocument, Standard fullStandardDocument)
	{
		for(Standard tmpStandard : standardDocument.getStandards())
		{
			System.out.println(tmpStandard.getHumanCodingScheme() + " - " + tmpStandard.getFullStatement());
			
			if(tmpStandard.getId().equalsIgnoreCase(sourceId))
			{
				fullStandardDocument = tmpStandard;
			}
			else
			{
				fullStandardDocument = checkTheChildren(sourceId, tmpStandard, fullStandardDocument);
			}
			
			if(fullStandardDocument != null)
			{
				break;
			}
		}
		
		return fullStandardDocument;
	}
}
