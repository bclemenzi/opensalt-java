package com.nfbsoftware.standards.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nfbsoftware.opensalt.model.CFDocument;
import com.nfbsoftware.opensalt.model.CFItem;

/**
 * 
 * @author brendanclemenzi
 */
public class Crosswalk implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String m_cfDocumentId;
    private String m_fromCFItemId;
    private String m_toCFItemId;
    
    private String m_semanticComparison;
    
    private CFDocument m_cfDocument;
    private CFItem m_fromCFItem;
    private CFItem m_toCFItem;
    
    private List<String> m_associationTypes = new ArrayList<String>();
    
    private String m_fromItemAssociationOfDocument;
    private String m_documentAssociationOfToItem;
    
    public String getCfDocumentId()
    {
        return m_cfDocumentId;
    }
    public void setCfDocumentId(String cfDocumentId)
    {
        m_cfDocumentId = cfDocumentId;
    }
    
    public List<String> getAssociationTypes()
    {
        return m_associationTypes;
    }
    public void setAssociationTypes(List<String> associationTypes)
    {
        m_associationTypes = associationTypes;
    }
    
    public String getFromItemAssociationOfDocument()
    {
        return m_fromItemAssociationOfDocument;
    }
    public void setFromItemAssociationOfDocument(String fromItemAssociationOfDocument)
    {
        m_fromItemAssociationOfDocument = fromItemAssociationOfDocument;
    }
    
    public String getDocumentAssociationOfToItem()
    {
        return m_documentAssociationOfToItem;
    }
    public void setDocumentAssociationOfToItem(String documentAssociationOfToItem)
    {
        m_documentAssociationOfToItem = documentAssociationOfToItem;
    }
    
    public String getSemanticComparison()
    {
        return m_semanticComparison;
    }
    public void setSemanticComparison(String semanticComparison)
    {
        m_semanticComparison = semanticComparison;
    }
    
    @JsonIgnore
    public CFDocument getCfDocument()
    {
        return m_cfDocument;
    }
    public void setCfDocument(CFDocument cfDocument)
    {
        m_cfDocument = cfDocument;
    }
    
    public String getFromCFItemId()
    {
        return m_fromCFItemId;
    }
    public void setFromCFItemId(String fromCFItemId)
    {
        m_fromCFItemId = fromCFItemId;
    }
    
    public String getToCFItemId()
    {
        return m_toCFItemId;
    }
    public void setToCFItemId(String toCFItemId)
    {
        m_toCFItemId = toCFItemId;
    }
    
    @JsonIgnore
    public CFItem getFromCFItem()
    {
        return m_fromCFItem;
    }
    public void setFromCFItem(CFItem fromCFItem)
    {
        m_fromCFItem = fromCFItem;
    }
    
    @JsonIgnore
    public CFItem getToCFItem()
    {
        return m_toCFItem;
    }
    public void setToCFItem(CFItem toCFItem)
    {
        m_toCFItem = toCFItem;
    }
}
