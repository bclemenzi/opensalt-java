
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
    "CFDocumentURI",
    "fullStatement",
    "alternativeLabel",
    "CFItemType",
    "uri",
    "humanCodingScheme",
    "listEnumeration",
    "abbreviatedStatement",
    "conceptKeywords",
    "conceptKeywordsURI",
    "notes",
    "language",
    "educationLevel",
    "CFItemTypeURI",
    "licenseURI",
    "statusStartDate",
    "statusEndDate",
    "lastChangeDateTime",
    "CFAssociations"
})
public class CFItem implements Serializable
{

    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("fullStatement")
    private String fullStatement;
    @JsonProperty("alternativeLabel")
    private String alternativeLabel;
    @JsonProperty("CFItemType")
    private String cFItemType;
    @JsonProperty("uri")
    private String uri;
    @JsonProperty("humanCodingScheme")
    private String humanCodingScheme;
    @JsonProperty("listEnumeration")
    private String listEnumeration;
    @JsonProperty("abbreviatedStatement")
    private String abbreviatedStatement;
    @JsonProperty("conceptKeywords")
    private List<String> conceptKeywords = null;
    @JsonProperty("conceptKeywordsURI")
    private ConceptKeywordsURI conceptKeywordsURI;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("language")
    private String language;
    @JsonProperty("educationLevel")
    private List<String> educationLevel = null;
    @JsonProperty("CFItemTypeURI")
    private CFItemTypeURI cFItemTypeURI;
    @JsonProperty("licenseURI")
    private LicenseURI_ licenseURI;
    @JsonProperty("statusStartDate")
    private String statusStartDate;
    @JsonProperty("statusEndDate")
    private String statusEndDate;
    @JsonProperty("lastChangeDateTime")
    private String lastChangeDateTime;
    @JsonProperty("CFDocumentURI")
    private CFDocumentURI cFDocumentURI;
    
    @JsonProperty("CFAssociations")
    private List<CFAssociation> cFAssociations = null;
    
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -2438652573241550328L;

    @JsonProperty("identifier")
    public String getIdentifier() {
        return identifier;
    }

    @JsonProperty("identifier")
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @JsonProperty("CFDocumentURI")
    public CFDocumentURI getCFDocumentURI() {
        return cFDocumentURI;
    }

    @JsonProperty("CFDocumentURI")
    public void setCFDocumentURI(CFDocumentURI cFDocumentURI) {
        this.cFDocumentURI = cFDocumentURI;
    }

    @JsonProperty("fullStatement")
    public String getFullStatement() {
        return fullStatement;
    }

    @JsonProperty("fullStatement")
    public void setFullStatement(String fullStatement) {
        this.fullStatement = fullStatement;
    }

    @JsonProperty("alternativeLabel")
    public String getAlternativeLabel() {
        return alternativeLabel;
    }

    @JsonProperty("alternativeLabel")
    public void setAlternativeLabel(String alternativeLabel) {
        this.alternativeLabel = alternativeLabel;
    }

    @JsonProperty("CFItemType")
    public String getCFItemType() {
        return cFItemType;
    }

    @JsonProperty("CFItemType")
    public void setCFItemType(String cFItemType) {
        this.cFItemType = cFItemType;
    }

    @JsonProperty("uri")
    public String getUri() {
        return uri;
    }

    @JsonProperty("uri")
    public void setUri(String uri) {
        this.uri = uri;
    }

    @JsonProperty("humanCodingScheme")
    public String getHumanCodingScheme() {
        return humanCodingScheme;
    }

    @JsonProperty("humanCodingScheme")
    public void setHumanCodingScheme(String humanCodingScheme) {
        this.humanCodingScheme = humanCodingScheme;
    }

    @JsonProperty("listEnumeration")
    public String getListEnumeration() {
        return listEnumeration;
    }

    @JsonProperty("listEnumeration")
    public void setListEnumeration(String listEnumeration) {
        this.listEnumeration = listEnumeration;
    }

    @JsonProperty("abbreviatedStatement")
    public String getAbbreviatedStatement() {
        return abbreviatedStatement;
    }

    @JsonProperty("abbreviatedStatement")
    public void setAbbreviatedStatement(String abbreviatedStatement) {
        this.abbreviatedStatement = abbreviatedStatement;
    }

    @JsonProperty("conceptKeywords")
    public List<String> getConceptKeywords() {
        return conceptKeywords;
    }

    @JsonProperty("conceptKeywords")
    public void setConceptKeywords(List<String> conceptKeywords) {
        this.conceptKeywords = conceptKeywords;
    }

    @JsonProperty("conceptKeywordsURI")
    public ConceptKeywordsURI getConceptKeywordsURI() {
        return conceptKeywordsURI;
    }

    @JsonProperty("conceptKeywordsURI")
    public void setConceptKeywordsURI(ConceptKeywordsURI conceptKeywordsURI) {
        this.conceptKeywordsURI = conceptKeywordsURI;
    }

    @JsonProperty("notes")
    public String getNotes() {
        return notes;
    }

    @JsonProperty("notes")
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    @JsonProperty("educationLevel")
    public List<String> getEducationLevel() {
        return educationLevel;
    }

    @JsonProperty("educationLevel")
    public void setEducationLevel(List<String> educationLevel) {
        this.educationLevel = educationLevel;
    }

    @JsonProperty("CFItemTypeURI")
    public CFItemTypeURI getCFItemTypeURI() {
        return cFItemTypeURI;
    }

    @JsonProperty("CFItemTypeURI")
    public void setCFItemTypeURI(CFItemTypeURI cFItemTypeURI) {
        this.cFItemTypeURI = cFItemTypeURI;
    }

    @JsonProperty("licenseURI")
    public LicenseURI_ getLicenseURI() {
        return licenseURI;
    }

    @JsonProperty("licenseURI")
    public void setLicenseURI(LicenseURI_ licenseURI) {
        this.licenseURI = licenseURI;
    }

    @JsonProperty("statusStartDate")
    public String getStatusStartDate() {
        return statusStartDate;
    }

    @JsonProperty("statusStartDate")
    public void setStatusStartDate(String statusStartDate) {
        this.statusStartDate = statusStartDate;
    }

    @JsonProperty("statusEndDate")
    public String getStatusEndDate() {
        return statusEndDate;
    }

    @JsonProperty("statusEndDate")
    public void setStatusEndDate(String statusEndDate) {
        this.statusEndDate = statusEndDate;
    }

    @JsonProperty("lastChangeDateTime")
    public String getLastChangeDateTime() {
        return lastChangeDateTime;
    }

    @JsonProperty("lastChangeDateTime")
    public void setLastChangeDateTime(String lastChangeDateTime) {
        this.lastChangeDateTime = lastChangeDateTime;
    }
    
    @JsonProperty("CFAssociations")
    public List<CFAssociation> getCFAssociations() {
        return cFAssociations;
    }

    @JsonProperty("CFAssociations")
    public void setCFAssociations(List<CFAssociation> cFAssociations) {
        this.cFAssociations = cFAssociations;
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
        return new ToStringBuilder(this).append("identifier", identifier).append("cFDocumentURI", cFDocumentURI).append("fullStatement", fullStatement).append("alternativeLabel", alternativeLabel).append("cFItemType", cFItemType).append("uri", uri).append("humanCodingScheme", humanCodingScheme).append("listEnumeration", listEnumeration).append("abbreviatedStatement", abbreviatedStatement).append("conceptKeywords", conceptKeywords).append("conceptKeywordsURI", conceptKeywordsURI).append("notes", notes).append("language", language).append("educationLevel", educationLevel).append("cFItemTypeURI", cFItemTypeURI).append("licenseURI", licenseURI).append("statusStartDate", statusStartDate).append("statusEndDate", statusEndDate).append("lastChangeDateTime", lastChangeDateTime).append("additionalProperties", additionalProperties).toString();
    }

}
