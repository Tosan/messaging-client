package com.tosan.client.messaging.starter.provider.armaghan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.tosan.client.messaging.starter.provider.armaghan.modelsabstraction.ClientConfigurable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * MessagestateRequest
 */

@JsonTypeName("messagestate_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class MessagestateRequest implements ClientConfigurable {

  @Valid
  private List<BigDecimal> ids = new ArrayList<>();

  private String password;

  private String username;

  public MessagestateRequest ids(List<BigDecimal> ids) {
    this.ids = ids;
    return this;
  }

  public MessagestateRequest addIdsItem(BigDecimal idsItem) {
    if (this.ids == null) {
      this.ids = new ArrayList<>();
    }
    this.ids.add(idsItem);
    return this;
  }

  /**
   * Get ids
   * @return ids
   */
  @Valid 
  @Schema(name = "ids", example = "[123456789123456780]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ids")
  public List<BigDecimal> getIds() {
    return ids;
  }

  public void setIds(List<BigDecimal> ids) {
    this.ids = ids;
  }

  public MessagestateRequest password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Get password
   * @return password
   */
  
  @Schema(name = "password", example = "{{password}}", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public MessagestateRequest username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Get username
   * @return username
   */
  
  @Schema(name = "username", example = "{{username}}", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MessagestateRequest messagestateRequest = (MessagestateRequest) o;
    return Objects.equals(this.ids, messagestateRequest.ids) &&
        Objects.equals(this.password, messagestateRequest.password) &&
        Objects.equals(this.username, messagestateRequest.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ids, password, username);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MessagestateRequest {\n");
    sb.append("    ids: ").append(toIndentedString(ids)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

