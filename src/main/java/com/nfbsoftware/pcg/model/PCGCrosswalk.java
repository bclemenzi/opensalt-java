package com.nfbsoftware.pcg.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "exactMatchOf", "isRelatedTo" })
public class PCGCrosswalk
{

    @JsonProperty("exactMatchOf")
    private List<ExactMatchOf> exactMatchOf = null;
    @JsonProperty("isRelatedTo")
    private List<IsRelatedTo> isRelatedTo = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("exactMatchOf")
    public List<ExactMatchOf> getExactMatchOf()
    {
        return exactMatchOf;
    }

    @JsonProperty("exactMatchOf")
    public void setExactMatchOf(List<ExactMatchOf> exactMatchOf)
    {
        this.exactMatchOf = exactMatchOf;
    }

    @JsonProperty("isRelatedTo")
    public List<IsRelatedTo> getIsRelatedTo()
    {
        return isRelatedTo;
    }

    @JsonProperty("isRelatedTo")
    public void setIsRelatedTo(List<IsRelatedTo> isRelatedTo)
    {
        this.isRelatedTo = isRelatedTo;
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
