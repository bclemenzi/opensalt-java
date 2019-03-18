package com.nfbsoftware.opensalt.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "identifier", "uri", "creator", "title", "lastChangeDateTime", "officialSourceURL", "publisher", "description", "subject",
        "subjectURI", "language", "version", "adoptionStatus", "statusStartDate", "statusEndDate", "licenseURI", "notes", "CFPackageURI", "CFItems" })
public class CFDocument implements Serializable
{
    private final static long serialVersionUID = -5251396790181393665L;

    @JsonProperty("identifier")
    private String identifier;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("creator")
    private String creator;

    @JsonProperty("title")
    private String title;

    @JsonProperty("lastChangeDateTime")
    private String lastChangeDateTime;

    @JsonProperty("officialSourceURL")
    private String officialSourceURL;

    @JsonProperty("publisher")
    private String publisher;

    @JsonProperty("description")
    private String description;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("language")
    private String language;

    @JsonProperty("version")
    private String version;

    @JsonProperty("adoptionStatus")
    private String adoptionStatus;

    @JsonProperty("statusStartDate")
    private String statusStartDate;

    @JsonProperty("statusEndDate")
    private String statusEndDate;

    @JsonProperty("licenseURI")
    private LicenseURI licenseURI;

    @JsonProperty("CFPackageURI")
    private CFPackageURI cFPackageURI;

    @JsonProperty("subject")
    private List<String> subject = null;

    @JsonProperty("subjectURI")
    private List<SubjectURus> subjectURI = null;
    
    @JsonProperty("CFItems")
    private List<CFItem> cFItems = null;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    @JsonProperty("creator")
    public String getCreator()
    {
        return creator;
    }

    @JsonProperty("creator")
    public void setCreator(String creator)
    {
        this.creator = creator;
    }

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

    @JsonProperty("lastChangeDateTime")
    public String getLastChangeDateTime()
    {
        return lastChangeDateTime;
    }

    @JsonProperty("lastChangeDateTime")
    public void setLastChangeDateTime(String lastChangeDateTime)
    {
        this.lastChangeDateTime = lastChangeDateTime;
    }

    @JsonProperty("officialSourceURL")
    public String getOfficialSourceURL()
    {
        return officialSourceURL;
    }

    @JsonProperty("officialSourceURL")
    public void setOfficialSourceURL(String officialSourceURL)
    {
        this.officialSourceURL = officialSourceURL;
    }

    @JsonProperty("publisher")
    public String getPublisher()
    {
        return publisher;
    }

    @JsonProperty("publisher")
    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

    @JsonProperty("description")
    public String getDescription()
    {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description)
    {
        this.description = description;
    }

    @JsonProperty("subject")
    public List<String> getSubject()
    {
        return subject;
    }

    @JsonProperty("subject")
    public void setSubject(List<String> subject)
    {
        this.subject = subject;
    }

    @JsonProperty("subjectURI")
    public List<SubjectURus> getSubjectURI()
    {
        return subjectURI;
    }

    @JsonProperty("subjectURI")
    public void setSubjectURI(List<SubjectURus> subjectURI)
    {
        this.subjectURI = subjectURI;
    }

    @JsonProperty("CFItems")
    public List<CFItem> getCFItems() {
        return cFItems;
    }

    @JsonProperty("CFItems")
    public void setCFItems(List<CFItem> cFItems) {
        this.cFItems = cFItems;
    }

    @JsonProperty("language")
    public String getLanguage()
    {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(String language)
    {
        this.language = language;
    }

    @JsonProperty("version")
    public String getVersion()
    {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(String version)
    {
        this.version = version;
    }

    @JsonProperty("adoptionStatus")
    public String getAdoptionStatus()
    {
        return adoptionStatus;
    }

    @JsonProperty("adoptionStatus")
    public void setAdoptionStatus(String adoptionStatus)
    {
        this.adoptionStatus = adoptionStatus;
    }

    @JsonProperty("statusStartDate")
    public String getStatusStartDate()
    {
        return statusStartDate;
    }

    @JsonProperty("statusStartDate")
    public void setStatusStartDate(String statusStartDate)
    {
        this.statusStartDate = statusStartDate;
    }

    @JsonProperty("statusEndDate")
    public String getStatusEndDate()
    {
        return statusEndDate;
    }

    @JsonProperty("statusEndDate")
    public void setStatusEndDate(String statusEndDate)
    {
        this.statusEndDate = statusEndDate;
    }

    @JsonProperty("licenseURI")
    public LicenseURI getLicenseURI()
    {
        return licenseURI;
    }

    @JsonProperty("licenseURI")
    public void setLicenseURI(LicenseURI licenseURI)
    {
        this.licenseURI = licenseURI;
    }

    @JsonProperty("notes")
    public String getNotes()
    {
        return notes;
    }

    @JsonProperty("notes")
    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    @JsonProperty("CFPackageURI")
    public CFPackageURI getCFPackageURI()
    {
        return cFPackageURI;
    }

    @JsonProperty("CFPackageURI")
    public void setCFPackageURI(CFPackageURI cFPackageURI)
    {
        this.cFPackageURI = cFPackageURI;
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
        return new ToStringBuilder(this).append("identifier", identifier).append("cFPackageURI", cFPackageURI).append("creator", creator)
                .append("adoptionStatus", adoptionStatus).append("notes", notes).append("subject", subject)
                .append("lastChangeDateTime", lastChangeDateTime).append("language", language).append("title", title).append("uri", uri)
                .append("subjectURI", subjectURI).append("publisher", publisher).append("officialSourceURL", officialSourceURL)
                .append("additionalProperties", additionalProperties).toString();
    }

}
