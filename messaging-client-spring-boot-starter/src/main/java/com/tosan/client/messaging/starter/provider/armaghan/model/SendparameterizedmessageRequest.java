package com.tosan.client.messaging.starter.provider.armaghan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.tosan.client.messaging.starter.provider.armaghan.modelsabstraction.ClientConfigurable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SendparameterizedmessageRequest
 */

@JsonTypeName("sendparameterizedmessage_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class SendparameterizedmessageRequest implements ClientConfigurable {

  @Valid
  private List<String> destinations = new ArrayList<>();

  @Valid
  private List<String> parameters = new ArrayList<>();

  private String password;

  private String template;

  private String username;

  public SendparameterizedmessageRequest destinations(List<String> destinations) {
    this.destinations = destinations;
    return this;
  }

  public SendparameterizedmessageRequest addDestinationsItem(String destinationsItem) {
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

  public SendparameterizedmessageRequest parameters(List<String> parameters) {
    this.parameters = parameters;
    return this;
  }

  public SendparameterizedmessageRequest addParametersItem(String parametersItem) {
    if (this.parameters == null) {
      this.parameters = new ArrayList<>();
    }
    this.parameters.add(parametersItem);
    return this;
  }

  /**
   * Get parameters
   * @return parameters
   */
  
  @Schema(name = "parameters", example = "[\"{{sample_text}}\"]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("parameters")
  public List<String> getParameters() {
    return parameters;
  }

  public void setParameters(List<String> parameters) {
    this.parameters = parameters;
  }

  public SendparameterizedmessageRequest password(String password) {
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

  public SendparameterizedmessageRequest template(String template) {
    this.template = template;
    return this;
  }

  /**
   * Get template
   * @return template
   */
  
  @Schema(name = "template", example = "", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("template")
  public String getTemplate() {
    return template;
  }

  public void setTemplate(String template) {
    this.template = template;
  }

  public SendparameterizedmessageRequest username(String username) {
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
    SendparameterizedmessageRequest sendparameterizedmessageRequest = (SendparameterizedmessageRequest) o;
    return Objects.equals(this.destinations, sendparameterizedmessageRequest.destinations) &&
        Objects.equals(this.parameters, sendparameterizedmessageRequest.parameters) &&
        Objects.equals(this.password, sendparameterizedmessageRequest.password) &&
        Objects.equals(this.template, sendparameterizedmessageRequest.template) &&
        Objects.equals(this.username, sendparameterizedmessageRequest.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(destinations, parameters, password, template, username);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SendparameterizedmessageRequest {\n");
    sb.append("    destinations: ").append(toIndentedString(destinations)).append("\n");
    sb.append("    parameters: ").append(toIndentedString(parameters)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    template: ").append(toIndentedString(template)).append("\n");
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

