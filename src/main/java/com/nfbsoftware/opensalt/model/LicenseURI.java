package com.nfbsoftware.opensalt.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "title", "identifier", "uri" })
public class LicenseURI implements Serializable
{
    private final static long serialVersionUID = 7018538720670214680L;

    @JsonProperty("title")
    private String title;

    @JsonProperty("identifier")
    private String identifier;

    @JsonProperty("uri")
    private String uri;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("title")
    public String getTitle()
    {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title)
    {
        this.title = title;
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
        return new ToStringBuilder(this).append("title", title).append("identifier", identifier).append("uri", uri)
                .append("additionalProperties", additionalProperties).toString();
    }
}
