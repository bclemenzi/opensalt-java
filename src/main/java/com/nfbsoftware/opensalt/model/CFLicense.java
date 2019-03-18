
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
@JsonPropertyOrder({
    "identifier",
    "uri",
    "title",
    "description",
    "licenseText",
    "lastChangeDateTime"
})
public class CFLicense implements Serializable
{

    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("uri")
    private String uri;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("licenseText")
    private String licenseText;
    @JsonProperty("lastChangeDateTime")
    private String lastChangeDateTime;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -1097887794380014360L;

    @JsonProperty("identifier")
    public String getIdentifier() {
        return identifier;
    }

    @JsonProperty("identifier")
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @JsonProperty("uri")
    public String getUri() {
        return uri;
    }

    @JsonProperty("uri")
    public void setUri(String uri) {
        this.uri = uri;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("licenseText")
    public String getLicenseText() {
        return licenseText;
    }

    @JsonProperty("licenseText")
    public void setLicenseText(String licenseText) {
        this.licenseText = licenseText;
    }

    @JsonProperty("lastChangeDateTime")
    public String getLastChangeDateTime() {
        return lastChangeDateTime;
    }

    @JsonProperty("lastChangeDateTime")
    public void setLastChangeDateTime(String lastChangeDateTime) {
        this.lastChangeDateTime = lastChangeDateTime;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("identifier", identifier).append("uri", uri).append("title", title).append("description", description).append("licenseText", licenseText).append("lastChangeDateTime", lastChangeDateTime).append("additionalProperties", additionalProperties).toString();
    }

}
