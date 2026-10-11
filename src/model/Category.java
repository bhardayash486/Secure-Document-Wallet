package model;

public class Category
{
    private int categoryId;
    private String categoryName;

    // Default Constructor.
    public Category()
    {
    }

    // Parameterize Constructor.
    public Category(int categoryId, String categoryName)
    {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
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
}