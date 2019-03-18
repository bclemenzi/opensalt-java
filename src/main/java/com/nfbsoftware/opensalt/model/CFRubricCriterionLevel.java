
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
    "description",
    "quality",
    "score",
    "feedback",
    "position",
    "rubricCriterionId",
    "lastChangeDateTime"
})
public class CFRubricCriterionLevel implements Serializable
{

    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("uri")
    private String uri;
    @JsonProperty("description")
    private String description;
    @JsonProperty("quality")
    private String quality;
    @JsonProperty("score")
    private Integer score;
    @JsonProperty("feedback")
    private String feedback;
    @JsonProperty("position")
    private Integer position;
    @JsonProperty("rubricCriterionId")
    private String rubricCriterionId;
    @JsonProperty("lastChangeDateTime")
    private String lastChangeDateTime;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -397566292928936499L;

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

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("quality")
    public String getQuality() {
        return quality;
    }

    @JsonProperty("quality")
    public void setQuality(String quality) {
        this.quality = quality;
    }

    @JsonProperty("score")
    public Integer getScore() {
        return score;
    }

    @JsonProperty("score")
    public void setScore(Integer score) {
        this.score = score;
    }

    @JsonProperty("feedback")
    public String getFeedback() {
        return feedback;
    }

    @JsonProperty("feedback")
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @JsonProperty("position")
    public Integer getPosition() {
        return position;
    }

    @JsonProperty("position")
    public void setPosition(Integer position) {
        this.position = position;
    }

    @JsonProperty("rubricCriterionId")
    public String getRubricCriterionId() {
        return rubricCriterionId;
    }

    @JsonProperty("rubricCriterionId")
    public void setRubricCriterionId(String rubricCriterionId) {
        this.rubricCriterionId = rubricCriterionId;
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
        return new ToStringBuilder(this).append("identifier", identifier).append("uri", uri).append("description", description).append("quality", quality).append("score", score).append("feedback", feedback).append("position", position).append("rubricCriterionId", rubricCriterionId).append("lastChangeDateTime", lastChangeDateTime).append("additionalProperties", additionalProperties).toString();
    }

}
