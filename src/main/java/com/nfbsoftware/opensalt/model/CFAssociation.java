
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
    "associationType",
    "CFDocumentURI",
    "sequenceNumber",
    "uri",
    "originNodeURI",
    "destinationNodeURI",
    "CFAssociationGroupingURI",
    "lastChangeDateTime"
})
public class CFAssociation implements Serializable
{

    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("associationType")
    private String associationType;
    @JsonProperty("sequenceNumber")
    private Integer sequenceNumber;
    @JsonProperty("uri")
    private String uri;
    @JsonProperty("originNodeURI")
    private OriginNodeURI originNodeURI;
    @JsonProperty("destinationNodeURI")
    private DestinationNodeURI destinationNodeURI;
    @JsonProperty("CFAssociationGroupingURI")
    private CFAssociationGroupingURI cFAssociationGroupingURI;
    @JsonProperty("lastChangeDateTime")
    private String lastChangeDateTime;
    @JsonProperty("CFDocumentURI")
    private CFDocumentURI cFDocumentURI;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -296322173638264412L;

    @JsonProperty("identifier")
    public String getIdentifier() {
        return identifier;
    }

    @JsonProperty("identifier")
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @JsonProperty("associationType")
    public String getAssociationType() {
        return associationType;
    }

    @JsonProperty("associationType")
    public void setAssociationType(String associationType) {
        this.associationType = associationType;
    }

    @JsonProperty("CFDocumentURI")
    public CFDocumentURI getCFDocumentURI() {
        return cFDocumentURI;
    }

    @JsonProperty("CFDocumentURI")
    public void setCFDocumentURI(CFDocumentURI cFDocumentURI) {
        this.cFDocumentURI = cFDocumentURI;
    }

    @JsonProperty("sequenceNumber")
    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    @JsonProperty("sequenceNumber")
    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    @JsonProperty("uri")
    public String getUri() {
        return uri;
    }

    @JsonProperty("uri")
    public void setUri(String uri) {
        this.uri = uri;
    }

    @JsonProperty("originNodeURI")
    public OriginNodeURI getOriginNodeURI() {
        return originNodeURI;
    }

    @JsonProperty("originNodeURI")
    public void setOriginNodeURI(OriginNodeURI originNodeURI) {
        this.originNodeURI = originNodeURI;
    }

    @JsonProperty("destinationNodeURI")
    public DestinationNodeURI getDestinationNodeURI() {
        return destinationNodeURI;
    }

    @JsonProperty("destinationNodeURI")
    public void setDestinationNodeURI(DestinationNodeURI destinationNodeURI) {
        this.destinationNodeURI = destinationNodeURI;
    }

    @JsonProperty("CFAssociationGroupingURI")
    public CFAssociationGroupingURI getCFAssociationGroupingURI() {
        return cFAssociationGroupingURI;
    }

    @JsonProperty("CFAssociationGroupingURI")
    public void setCFAssociationGroupingURI(CFAssociationGroupingURI cFAssociationGroupingURI) {
        this.cFAssociationGroupingURI = cFAssociationGroupingURI;
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
        return new ToStringBuilder(this).append("identifier", identifier).append("associationType", associationType).append("cFDocumentURI", cFDocumentURI).append("sequenceNumber", sequenceNumber).append("uri", uri).append("originNodeURI", originNodeURI).append("destinationNodeURI", destinationNodeURI).append("cFAssociationGroupingURI", cFAssociationGroupingURI).append("lastChangeDateTime", lastChangeDateTime).append("additionalProperties", additionalProperties).toString();
    }

}
