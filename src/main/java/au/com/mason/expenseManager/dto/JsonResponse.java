package au.com.mason.expenseManager.dto;

public class JsonResponse
{
  private String status = "";
  private String errorMessage = "";
  private String id;
  
  public JsonResponse(String status, String errorMessage, String id)
  {
    this.status = status;
    this.errorMessage = errorMessage;
    this.id = id;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public String getErrorMessage()
  {
    return this.errorMessage;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
}

