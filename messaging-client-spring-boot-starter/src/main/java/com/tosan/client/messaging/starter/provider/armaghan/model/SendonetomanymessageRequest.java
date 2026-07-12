package com.tosan.client.messaging.starter.provider.armaghan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.tosan.client.messaging.starter.provider.armaghan.modelsabstraction.ClientConfigurableWithOriginator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SendonetomanymessageRequest
 */

@JsonTypeName("sendonetomanymessage_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class SendonetomanymessageRequest implements ClientConfigurableWithOriginator {

  private String content;

  @Valid
  private List<String> destinations = new ArrayList<>();

  private String originator;

  private String password;

  private String username;

  public SendonetomanymessageRequest content(String content) {
    this.content = content;
    return this;
  }

  /**
   * Get content
   * @return content
   */
  
  @Schema(name = "content", example = "{{sample_text}}", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("content")
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public SendonetomanymessageRequest destinations(List<String> destinations) {
    this.destinations = destinations;
    return this;
  }

  public SendonetomanymessageRequest addDestinationsItem(String destinationsItem) {
    if (this.destinations == null) {
      this.destinations = new ArrayList<>();
    }
    this.destinations.add(destinationsItem);
    return this;
  }

  /**
   * Get destinations
   * @return destinations
   */
  
  @Schema(name = "destinations", example = "[\"{{sample_destination}}\"]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("destinations")
  public List<String> getDestinations() {
    return destinations;
  }

  public void setDestinations(List<String> destinations) {
    this.destinations = destinations;
  }

  public SendonetomanymessageRequest originator(String originator) {
    this.originator = originator;
    return this;
  }

  /**
   * Get originator
   * @return originator
   */
  
  @Schema(name = "originator", example = "{{originator}}", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("originator")
  public String getOriginator() {
    return originator;
  }

  public void setOriginator(String originator) {
    this.originator = originator;
  }

  public SendonetomanymessageRequest password(String password) {
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

  public SendonetomanymessageRequest username(String username) {
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
    SendonetomanymessageRequest sendonetomanymessageRequest = (SendonetomanymessageRequest) o;
    return Objects.equals(this.content, sendonetomanymessageRequest.content) &&
        Objects.equals(this.destinations, sendonetomanymessageRequest.destinations) &&
        Objects.equals(this.originator, sendonetomanymessageRequest.originator) &&
        Objects.equals(this.password, sendonetomanymessageRequest.password) &&
        Objects.equals(this.username, sendonetomanymessageRequest.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(content, destinations, originator, password, username);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SendonetomanymessageRequest {\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    destinations: ").append(toIndentedString(destinations)).append("\n");
    sb.append("    originator: ").append(toIndentedString(originator)).append("\n");
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

