
package com.nfbsoftware.opensalt.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
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
    "CFItems"
})
public class CFPackages implements Serializable
{
    @JsonProperty("CFItems")
    private List<CFItem> cFItems = null;
    @JsonProperty("CFAssociations")
    private List<CFAssociation> cFAssociations = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 8230413264084869585L;

    @JsonProperty("CFItems")
    public List<CFItem> getCFItems() {
        return cFItems;
    }

    @JsonProperty("CFItems")
    public void setCFItems(List<CFItem> cFItems) {
        this.cFItems = cFItems;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
    
    @JsonProperty("CFAssociations")
    public List<CFAssociation> getCFAssociations() {
        return cFAssociations;
    }

    @JsonProperty("CFAssociations")
    public void setCFAssociations(List<CFAssociation> cFAssociations) {
        this.cFAssociations = cFAssociations;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("cFItems", cFItems).append("additionalProperties", additionalProperties).toString();
    }

}
