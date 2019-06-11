package com.nfbsoftware.pcg.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "fullStatement", "humanCodingScheme", "uri", "identifier" })
public class IsRelatedTo
{

    @JsonProperty("fullStatement")
    private String fullStatement;
    @JsonProperty("humanCodingScheme")
    private String humanCodingScheme;
    @JsonProperty("uri")
    private String uri;
    @JsonProperty("identifier")
    private String identifier;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("fullStatement")
    public String getFullStatement()
    {
        return fullStatement;
    }

    @JsonProperty("fullStatement")
    public void setFullStatement(String fullStatement)
    {
        this.fullStatement = fullStatement;
    }

    @JsonProperty("humanCodingScheme")
    public String getHumanCodingScheme()
    {
        return humanCodingScheme;
    }

    @JsonProperty("humanCodingScheme")
    public void setHumanCodingScheme(String humanCodingScheme)
    {
        this.humanCodingScheme = humanCodingScheme;
    }

    @JsonProperty("uri")
    public String getUri()
    {
        return uri;
    }

    @JsonProperty("uri")
    public void setUri(String uri)
    {
        this.uri = uri;
    }

    @JsonProperty("identifier")
    public String getIdentifier()
    {
        return identifier;
    }

    @JsonProperty("identifier")
    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
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
}
