
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
    "identifier",
    "uri",
    "category",
    "description",
    "CFItemURI",
    "weight",
    "position",
    "rubricId",
    "lastChangeDateTime",
    "CFRubricCriterionLevels"
})
public class CFRubricCriterium implements Serializable
{

    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("uri")
    private String uri;
    @JsonProperty("category")
    private String category;
    @JsonProperty("description")
    private String description;
    @JsonProperty("CFItemURI")
    private String cFItemURI;
    @JsonProperty("weight")
    private Integer weight;
    @JsonProperty("position")
    private Integer position;
    @JsonProperty("rubricId")
    private String rubricId;
    @JsonProperty("lastChangeDateTime")
    private String lastChangeDateTime;
    @JsonProperty("CFRubricCriterionLevels")
    private List<CFRubricCriterionLevel> cFRubricCriterionLevels = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -2059653626114914189L;

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

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("CFItemURI")
    public String getCFItemURI() {
        return cFItemURI;
    }

    @JsonProperty("CFItemURI")
    public void setCFItemURI(String cFItemURI) {
        this.cFItemURI = cFItemURI;
    }

    @JsonProperty("weight")
    public Integer getWeight() {
        return weight;
    }

    @JsonProperty("weight")
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @JsonProperty("position")
    public Integer getPosition() {
        return position;
    }

    @JsonProperty("position")
    public void setPosition(Integer position) {
        this.position = position;
    }

    @JsonProperty("rubricId")
    public String getRubricId() {
        return rubricId;
    }

    @JsonProperty("rubricId")
    public void setRubricId(String rubricId) {
        this.rubricId = rubricId;
    }

    @JsonProperty("lastChangeDateTime")
    public String getLastChangeDateTime() {
        return lastChangeDateTime;
    }

    @JsonProperty("lastChangeDateTime")
    public void setLastChangeDateTime(String lastChangeDateTime) {
        this.lastChangeDateTime = lastChangeDateTime;
    }

    @JsonProperty("CFRubricCriterionLevels")
    public List<CFRubricCriterionLevel> getCFRubricCriterionLevels() {
        return cFRubricCriterionLevels;
    }

    @JsonProperty("CFRubricCriterionLevels")
    public void setCFRubricCriterionLevels(List<CFRubricCriterionLevel> cFRubricCriterionLevels) {
        this.cFRubricCriterionLevels = cFRubricCriterionLevels;
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
        return new ToStringBuilder(this).append("identifier", identifier).append("uri", uri).append("category", category).append("description", description).append("cFItemURI", cFItemURI).append("weight", weight).append("position", position).append("rubricId", rubricId).append("lastChangeDateTime", lastChangeDateTime).append("cFRubricCriterionLevels", cFRubricCriterionLevels).append("additionalProperties", additionalProperties).toString();
    }

}
