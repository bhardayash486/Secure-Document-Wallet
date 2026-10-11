package model;

import java.sql.Timestamp;

public class Document
{
    private int docId,userId,categoryId;
    private String docName,description,filePath,fileType,categoryName;
    private long fileSize;
    private Timestamp uploadDate;

    // Default Constructor.
    public Document()
    {
    }

    // Parameterize Constructor.
    public Document(int docId,int userId,int categoryId,String docName,String description,String filePath,String fileType,long fileSize,Timestamp uploadDate)
    {
        this.docId = docId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.docName = docName;
        this.description = description;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.uploadDate = uploadDate;
    }

    // Getter and Setter Methods for Document Id.
    public int getDocumentId()
    {
        return docId;
    }
    public void setDocumentId(int docId)
    {
        this.docId = docId;
    }

    // Getter and Setter Methods for User Id.
    public int getUserId()
    {
        return userId;
    }
    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    // Getter and Setter Methods for Category Id.
    public int getCategoryId()
    {
        return categoryId;
    }
    public void setCategoryId(int categoryId)
    {
        this.categoryId = categoryId;
    }

    // Getter and Setter Methods for Category Name.
    public String getCategoryName()
    {
        return categoryName;
    }
    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    // Getter and Setter Methods for Document Name.
    public String getDocumentName()
    {
        return docName;
    }
    public void setDocumentName(String docName)
    {
        this.docName = docName;
    }

    // Getter and Setter Methods for Document Description.
    public String getDocDescription()
    {
        return description;
    }
    public void setDocDescription(String description)
    {
        this.description = description;
    }

    // Getter and Setter Methods for Document File Path.
    public String getDocFilePath()
    {
        return filePath;
    }
    public void setDocFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    // Getter and Setter Methods for Document File Type.
    public String getDocFileType()
    {
        return fileType;
    }
    public void setDocFileType(String fileType)
    {
        this.fileType = fileType;
    }

    // Getter and Setter Methods for Document File Size.
    public long getDocFileSize()
    {
        return fileSize;
    }
    public void setDocFileSize(long fileSize)
    {
        this.fileSize = fileSize;
    }

    // Getter and Setter Methods for Document File Upload Date.
    public Timestamp getDocUploadDate()
    {
        return uploadDate;
    }
    public void setDocUploadDate(Timestamp uploadDate)
    {
        this.uploadDate = uploadDate;
    }
}