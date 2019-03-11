package com.nfbsoftware.opensalt.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"CFDocuments"})
public class CFDocuments implements Serializable
{
    private final static long serialVersionUID = -8665078274939844832L;
    
    @JsonProperty("CFDocuments")
    private List<CFDocument> cFDocuments = null;
    
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    
    @JsonProperty("CFDocuments")
    public List<CFDocument> getCFDocuments() 
    {
        return cFDocuments;
    }

    @JsonProperty("CFDocuments")
    public void setCFDocuments(List<CFDocument> cFDocuments) 
    {
        this.cFDocuments = cFDocuments;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() 
    {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) 
    {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() 
    {
        return new ToStringBuilder(this).append("cFDocuments", cFDocuments).append("additionalProperties", additionalProperties).toString();
    }
}
