
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
    "CFConcepts",
    "CFSubjects",
    "CFLicenses",
    "CFItemTypes",
    "CFAssociationGroupings"
})
public class CFDefinitions implements Serializable
{

    @JsonProperty("CFConcepts")
    private List<CFConcept> cFConcepts = null;
    @JsonProperty("CFSubjects")
    private List<CFSubject> cFSubjects = null;
    @JsonProperty("CFLicenses")
    private List<CFLicense> cFLicenses = null;
    @JsonProperty("CFItemTypes")
    private List<CFItemType> cFItemTypes = null;
    @JsonProperty("CFAssociationGroupings")
    private List<CFAssociationGrouping> cFAssociationGroupings = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -5791479087575449053L;

    @JsonProperty("CFConcepts")
    public List<CFConcept> getCFConcepts() {
        return cFConcepts;
    }

    @JsonProperty("CFConcepts")
    public void setCFConcepts(List<CFConcept> cFConcepts) {
        this.cFConcepts = cFConcepts;
    }

    @JsonProperty("CFSubjects")
    public List<CFSubject> getCFSubjects() {
        return cFSubjects;
    }

    @JsonProperty("CFSubjects")
    public void setCFSubjects(List<CFSubject> cFSubjects) {
        this.cFSubjects = cFSubjects;
    }

    @JsonProperty("CFLicenses")
    public List<CFLicense> getCFLicenses() {
        return cFLicenses;
    }

    @JsonProperty("CFLicenses")
    public void setCFLicenses(List<CFLicense> cFLicenses) {
        this.cFLicenses = cFLicenses;
    }

    @JsonProperty("CFItemTypes")
    public List<CFItemType> getCFItemTypes() {
        return cFItemTypes;
    }

    @JsonProperty("CFItemTypes")
    public void setCFItemTypes(List<CFItemType> cFItemTypes) {
        this.cFItemTypes = cFItemTypes;
    }

    @JsonProperty("CFAssociationGroupings")
    public List<CFAssociationGrouping> getCFAssociationGroupings() {
        return cFAssociationGroupings;
    }

    @JsonProperty("CFAssociationGroupings")
    public void setCFAssociationGroupings(List<CFAssociationGrouping> cFAssociationGroupings) {
        this.cFAssociationGroupings = cFAssociationGroupings;
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
        return new ToStringBuilder(this).append("cFConcepts", cFConcepts).append("cFSubjects", cFSubjects).append("cFLicenses", cFLicenses).append("cFItemTypes", cFItemTypes).append("cFAssociationGroupings", cFAssociationGroupings).append("additionalProperties", additionalProperties).toString();
    }

}
