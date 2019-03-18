
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
    "CFDocument",
    "CFItems",
    "CFAssociations",
    "CFDefinitions",
    "CFRubrics"
})
public class CFDocuments implements Serializable
{

    @JsonProperty("CFDocument")
    private CFDocument cFDocument;
    @JsonProperty("CFItems")
    private List<CFItem> cFItems = null;
    @JsonProperty("CFAssociations")
    private List<CFAssociation> cFAssociations = null;
    @JsonProperty("CFDefinitions")
    private CFDefinitions cFDefinitions;
    @JsonProperty("CFRubrics")
    private List<CFRubric> cFRubrics = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 3066464536032954483L;

    @JsonProperty("CFDocument")
    public CFDocument getCFDocument() {
        return cFDocument;
    }

    @JsonProperty("CFDocument")
    public void setCFDocument(CFDocument cFDocument) {
        this.cFDocument = cFDocument;
    }

    @JsonProperty("CFItems")
    public List<CFItem> getCFItems() {
        return cFItems;
    }

    @JsonProperty("CFItems")
    public void setCFItems(List<CFItem> cFItems) {
        this.cFItems = cFItems;
    }

    @JsonProperty("CFAssociations")
    public List<CFAssociation> getCFAssociations() {
        return cFAssociations;
    }

    @JsonProperty("CFAssociations")
    public void setCFAssociations(List<CFAssociation> cFAssociations) {
        this.cFAssociations = cFAssociations;
    }

    @JsonProperty("CFDefinitions")
    public CFDefinitions getCFDefinitions() {
        return cFDefinitions;
    }

    @JsonProperty("CFDefinitions")
    public void setCFDefinitions(CFDefinitions cFDefinitions) {
        this.cFDefinitions = cFDefinitions;
    }

    @JsonProperty("CFRubrics")
    public List<CFRubric> getCFRubrics() {
        return cFRubrics;
    }

    @JsonProperty("CFRubrics")
    public void setCFRubrics(List<CFRubric> cFRubrics) {
        this.cFRubrics = cFRubrics;
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
        return new ToStringBuilder(this).append("cFDocument", cFDocument).append("cFItems", cFItems).append("cFAssociations", cFAssociations).append("cFDefinitions", cFDefinitions).append("cFRubrics", cFRubrics).append("additionalProperties", additionalProperties).toString();
    }

}
