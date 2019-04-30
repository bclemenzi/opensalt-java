package com.nfbsoftware.opensalt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.github.difflib.algorithm.DiffException;
import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import com.nfbsoftware.opensalt.model.CFAssociation;
import com.nfbsoftware.opensalt.model.CFDocument;
import com.nfbsoftware.opensalt.model.CFItem;
import com.nfbsoftware.opensalt.model.CFItemTypeURI;
import com.nfbsoftware.opensalt.model.CFPackages;
import com.nfbsoftware.opensalt.model.DestinationNodeURI;
import com.nfbsoftware.opensalt.model.Documents;
import com.nfbsoftware.opensalt.model.OriginNodeURI;
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
        List<CFItem> topLevelCfItems = new ArrayList<CFItem>(); 
        
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
                            topLevelCfItems.add(tmpCFItem);
                            itemIds.add(tmpCFItem.getIdentifier());
                            
                            break;
                        }
                    }
                }
            }
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
            
            // Create a array to store the top level standards of the document
            List<Standard> topLevelStandards = new ArrayList<Standard>();
            
            // Loop the full set again to get the top level children of the document
            for(CFItem tmpCFItem : cfItems)
            {
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
                    
                    // Add the standard to the document
                    topLevelStandards.add(topLevelStandard);
                }
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
            
            if(associatedItem.getCFDocumentURI().getTitle().contains(rosettaDocumentTitle))
            {
                //System.out.println("Rossetta Document [" + associatedItem.getCFDocumentURI().getIdentifier() + "] " + associatedItem.getCFDocumentURI().getTitle());
                rosettaCFDocumentId = associatedItem.getCFDocumentURI().getIdentifier();
                
                break;
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
     * <p>Method for running to text strings through a semantic comparison engine.</p>
     * 
     * @param fromText - Beginning text
     * @param toText - Comparison text
     * @return - returns the semantic comparison text
     * 
     * @throws DiffException
     */
    private String generateSemanticComparison(String fromText, String toText) throws DiffException
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
}
