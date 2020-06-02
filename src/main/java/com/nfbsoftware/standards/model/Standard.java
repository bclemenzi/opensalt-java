package com.nfbsoftware.standards.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>A slim hierarchical representation of the full standards document.</p>
 * 
 * @author brendanclemenzi
 */
public class Standard implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String m_id;
    private String m_parentId;

    private String m_documentId;
    private String m_documentTitle;

    private String m_type;
    private String m_typeId;

    private String m_humanCodingScheme;
    private String m_listEnumeration;
    private String m_fullStatement;
    private String m_notes;
    private String m_creator;
    private String m_language;
    private String m_adoptionStatus;
    
    private int m_usageCount = 1;

    private List<Standard> m_standards = new ArrayList<Standard>();

    public String getId()
    {
        return m_id;
    }
    public void setId(String id)
    {
        m_id = id;
    }

    public String getParentId()
    {
        return m_parentId;
    }
    public void setParentId(String parentId)
    {
        m_parentId = parentId;
    }

    public String getDocumentId()
    {
        return m_documentId;
    }
    public void setDocumentId(String documentId)
    {
        m_documentId = documentId;
    }

    public String getDocumentTitle()
    {
        return m_documentTitle;
    }
    public void setDocumentTitle(String documentTitle)
    {
        m_documentTitle = documentTitle;
    }

    public String getType()
    {
        return m_type;
    }
    public void setType(String type)
    {
        m_type = type;
    }

    public String getTypeId()
    {
        return m_typeId;
    }
    public void setTypeId(String typeId)
    {
        m_typeId = typeId;
    }

    public String getHumanCodingScheme()
    {
        return m_humanCodingScheme;
    }
    public void setHumanCodingScheme(String humanCodingScheme)
    {
        m_humanCodingScheme = humanCodingScheme;
    }

    public String getListEnumeration()
    {
        return m_listEnumeration;
    }
    public void setListEnumeration(String listEnumeration)
    {
        m_listEnumeration = listEnumeration;
    }
    
    public String getFullStatement()
    {
        return m_fullStatement;
    }
    public void setFullStatement(String fullStatement)
    {
        m_fullStatement = fullStatement;
    }

    public String getNotes()
    {
        return m_notes;
    }
    public void setNotes(String notes)
    {
        m_notes = notes;
    }

    public String getCreator()
    {
        return m_creator;
    }
    public void setCreator(String creator)
    {
        m_creator = creator;
    }
    
    public String getLanguage()
    {
        return m_language;
    }
    public void setLanguage(String language)
    {
        m_language = language;
    }
    
    public String getAdoptionStatus()
    {
        return m_adoptionStatus;
    }
    public void setAdoptionStatus(String adoptionStatus)
    {
        m_adoptionStatus = adoptionStatus;
    }
    
    public int getUsageCount()
    {
        return m_usageCount;
    }
    public void setUsageCount(int usageCount)
    {
        m_usageCount = usageCount;
    }
    
    public List<Standard> getStandards()
    {
        return m_standards;
    }
    public void setStandards(List<Standard> standards)
    {
        m_standards = standards;
    }
}
