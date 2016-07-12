package pt.ual.mgi.integration.client.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-07-04T21:40:55.382+01:00")
public class UserAccount   {
  
  private String id = null;
  private String username = null;
  private String name = null;
  private String number = null;
  private String email = null;
  private String phone = null;
  private String password = null;
  private String hintQuestion = null;
  private String hintAnswer = null;

  
  /**
   **/
  public UserAccount id(String id) {
    this.id = id;
    return this;
  }
  
  @ApiModelProperty(example = "null", value = "")
  @JsonProperty("id")
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  
  /**
   **/
  public UserAccount username(String username) {
    this.username = username;
    return this;
  }
  
  @ApiModelProperty(example = "null", value = "")
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }

  
  /**
   **/
  public UserAccount name(String name) {
    this.name = name;
    return this;
  }
  
  @ApiModelProperty(example = "null", value = "")
  @JsonProperty("name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  
  /**
   **/
  public UserAccount number(String number) {
    this.number = number;
    return this;
  }
  
  @ApiModelProperty(example = "null", value = "")
  @JsonProperty("number")
  public String getNumber() {
    return number;
  }
  public void setNumber(String number) {
    this.number = number;
  }

  
  /**
   **/
  public UserAccount email(String email) {
    this.email = email;
    return this;
  }
  
  @ApiModelProperty(example = "null", value = "")
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  
  /**
   **/
  public UserAccount phone(String phone) {
    this.phone = phone;
    return this;
  }
  
  @ApiModelProperty(example = "null", value = "")
  @JsonProperty("phone")
  public String getPhone() {
    return phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
  }

  
  /**
   **/
  public UserAccount password(String password) {
    this.password = password;
    return this;
  }
  
  @ApiModelProperty(example = "null", value = "")
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  
  /**
   **/
  public UserAccount hintQuestion(String hintQuestion) {
    this.hintQuestion = hintQuestion;
    return this;
  }
  
  @ApiModelProperty(example = "null", value = "")
  @JsonProperty("hintQuestion")
  public String getHintQuestion() {
    return hintQuestion;
  }
  public void setHintQuestion(String hintQuestion) {
    this.hintQuestion = hintQuestion;
  }

  
  /**
   **/
  public UserAccount hintAnswer(String hintAnswer) {
    this.hintAnswer = hintAnswer;
    return this;
  }
  
  @ApiModelProperty(example = "null", value = "")
  @JsonProperty("hintAnswer")
  public String getHintAnswer() {
    return hintAnswer;
  }
  public void setHintAnswer(String hintAnswer) {
    this.hintAnswer = hintAnswer;
  }

  

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserAccount userAccount = (UserAccount) o;
    return Objects.equals(this.id, userAccount.id) &&
        Objects.equals(this.username, userAccount.username) &&
        Objects.equals(this.name, userAccount.name) &&
        Objects.equals(this.number, userAccount.number) &&
        Objects.equals(this.email, userAccount.email) &&
        Objects.equals(this.phone, userAccount.phone) &&
        Objects.equals(this.password, userAccount.password) &&
        Objects.equals(this.hintQuestion, userAccount.hintQuestion) &&
        Objects.equals(this.hintAnswer, userAccount.hintAnswer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, name, number, email, phone, password, hintQuestion, hintAnswer);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserAccount {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    number: ").append(toIndentedString(number)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    hintQuestion: ").append(toIndentedString(hintQuestion)).append("\n");
    sb.append("    hintAnswer: ").append(toIndentedString(hintAnswer)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

