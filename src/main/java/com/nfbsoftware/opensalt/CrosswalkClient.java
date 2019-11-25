package com.nfbsoftware.opensalt;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nfbsoftware.pcg.model.PCGCrosswalk;

/**
 * This is a Java utility class that is used to communicate with the Academic Benchmarks' RESTful API.
 * 
 * @author brendanclemenzi
 */
public class CrosswalkClient
{
    private static final Log logger = LogFactory.getLog(CrosswalkClient.class);

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
    public CrosswalkClient(String hostDomain, int hostPort, String hostScheme)
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
     * <p>Gets item matches in the target framework of an item identified by identifier.</p>
     * 
     * @param identifier - The identifier of the item to match
     * @param target - The identifier of the target framework
     * @return An instance of PCGCrosswalk
     * @throws Exception - catch all for exceptions
     */
    public PCGCrosswalk crosswalkByIdentifier(String identifier, String target) throws Exception
    {
        PCGCrosswalk tmpPCGCrosswalk = null;
        
        HttpClient httpClient = HttpClientBuilder.create().build();
        
        // specify the host, protocol, and port
        HttpHost targetHost = new HttpHost(m_hostDomain, m_hostPort, m_hostScheme);
        
        logger.debug("Getting crosswalk by identifier");
        
        // specify the get request
        HttpGet getRequest = new HttpGet("/api/v1/crosswalk/by-identifier/" + identifier + "/" + target);
        
        // Add the bearer token if we have one
        if(m_token != null)
        {
            getRequest.setHeader("Authorization", "Bearer " + m_token);
        }

        // Get our response from the SALT server
        HttpResponse saltyResponse = httpClient.execute(targetHost, getRequest);
        HttpEntity entity = saltyResponse.getEntity();
        
        // If we have an entity, convert it to Java objects
        if(entity != null) 
        {
            String responseString = EntityUtils.toString(entity);   
            
            JSONObject responseJSON = new JSONObject(responseString);
            //System.out.println(responseJSON.toString());
            logger.debug("Crosswalk/by-identifier Response (" + identifier + " to " + target + "): " + responseJSON.toString());
            
            ObjectMapper mapper = new ObjectMapper();
            tmpPCGCrosswalk = mapper.readValue(responseJSON.toString(),  PCGCrosswalk.class);
        }
        
        return tmpPCGCrosswalk;
    }
    
    /**
     * <p>Gets item matches in the target framework of an item with a specific Human Coding Scheme in the source framework.</p>
     * 
     * @param source - The identifier, uri, or alias of the source framework
     * @param hcs - The Human Coding Scheme of the item in the source framework to match
     * @param target - The identifier of the target framework
     * @return An instance of PCGCrosswalk
     * @throws Exception - catch all for exceptions
     */
    public PCGCrosswalk crosswalkByHumanCodingScheme(String source, String hcs, String target) throws Exception
    {
        PCGCrosswalk tmpPCGCrosswalk = null;
        
        HttpClient httpClient = HttpClientBuilder.create().build();
        
        // specify the host, protocol, and port
        HttpHost targetHost = new HttpHost(m_hostDomain, m_hostPort, m_hostScheme);
        
        logger.debug("Getting crosswalk by human coding scheme");
        
        // specify the get request
        HttpGet getRequest = new HttpGet("/api/v1/crosswalk/by-hcs/" + source + "/" + hcs + "/" + target);
        
        // Add the bearer token if we have one
        if(m_token != null)
        {
            getRequest.setHeader("Authorization", "Bearer " + m_token);
        }

        // Get our response from the SALT server
        HttpResponse saltyResponse = httpClient.execute(targetHost, getRequest);
        HttpEntity entity = saltyResponse.getEntity();
        
        // If we have an entity, convert it to Java objects
        if(entity != null) 
        {
            String responseString = EntityUtils.toString(entity);   
            
            JSONObject responseJSON = new JSONObject(responseString);
            //System.out.println(responseJSON.toString());
            logger.debug("Crosswalk/by-hcs Response (" + source + " -  " + hcs + " - " + target + "): " + responseJSON.toString());
            
            ObjectMapper mapper = new ObjectMapper();
            tmpPCGCrosswalk = mapper.readValue(responseJSON.toString(),  PCGCrosswalk.class);
        }
        
        return tmpPCGCrosswalk;
    }
    
    /**
     * <p>Gets item matches in the target framework of an item identified by identifier.</p>
     * 
     * @param identifier - The identifier of the item to match
     * @param target - The identifier of the target framework
     * @return An instance of PCGCrosswalk
     * @throws Exception - catch all for exceptions
     */
    public Map<String, PCGCrosswalk> crosswalkByIdentifiers(List<String> identifiers, String target) throws Exception
    {
    	Map<String, PCGCrosswalk> tmpPCGCrosswalks = new HashMap<String, PCGCrosswalk>();
        
        HttpClient httpClient = HttpClientBuilder.create().build();
        
        // specify the host, protocol, and port
        HttpHost targetHost = new HttpHost(m_hostDomain, m_hostPort, m_hostScheme);
        
        logger.debug("Getting crosswalk by identifiers");
        
        // specify the get request
        HttpPost postRequest = new HttpPost("/api/v1/crosswalk/to-framework/" + target);
        
        // Add the bearer token if we have one
        if(m_token != null)
        {
        	postRequest.setHeader("Authorization", "Bearer " + m_token);
        }
        
        JSONObject postBodyJSON = new JSONObject();
        postBodyJSON.append("identifiers", identifiers);
        
        // TODO Fix this garbage logic
        String tmpBody = StringUtils.replace(postBodyJSON.toString(), "[[", "[");
        tmpBody = StringUtils.replace(tmpBody, "]]", "]");
        
        HttpEntity postBody = new ByteArrayEntity(tmpBody.getBytes("UTF-8"));
        
        // Set the body of the post
        postRequest.setEntity(postBody);

        // Get our response from the SALT server
        HttpResponse saltyResponse = httpClient.execute(targetHost, postRequest);
        HttpEntity entity = saltyResponse.getEntity();
        
        // If we have an entity, convert it to Java objects
        if(entity != null) 
        {
            String responseString = EntityUtils.toString(entity);   
            
            JSONObject responseJSON = new JSONObject(responseString);
            //System.out.println(responseJSON.toString());
            logger.debug("Crosswalk/to-framework Response (" + target + "): " + responseJSON.toString());
            
            Set<String> identifierKeys = responseJSON.keySet();
            
            ObjectMapper mapper = new ObjectMapper();
            for(String key : identifierKeys)
            {
            	PCGCrosswalk tmpPCGCrosswalk = mapper.readValue(responseJSON.get(key).toString(),  PCGCrosswalk.class);
            
            	if(tmpPCGCrosswalk != null)
                {
            		tmpPCGCrosswalks.put(key, tmpPCGCrosswalk);
                }
            }
        }
        
        return tmpPCGCrosswalks;
    }
}
