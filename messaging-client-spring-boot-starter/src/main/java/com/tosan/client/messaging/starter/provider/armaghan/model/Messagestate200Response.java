package com.tosan.client.messaging.starter.provider.armaghan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.tosan.client.messaging.starter.provider.armaghan.modelsabstraction.ErrorModelHolder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Messagestate200Response
 */

@JsonTypeName("messagestate_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class Messagestate200Response implements ErrorModelHolder {

  private Sendonetomanymessage200ResponseErrorModel errorModel;

  @Valid
  private List<@Valid Messagestate200ResponseStatesInner> states = new ArrayList<>();

  public Messagestate200Response errorModel(Sendonetomanymessage200ResponseErrorModel errorModel) {
    this.errorModel = errorModel;
    return this;
  }

  /**
   * Get errorModel
   * @return errorModel
   */
  @Valid 
  @Schema(name = "errorModel", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("errorModel")
  public Sendonetomanymessage200ResponseErrorModel getErrorModel() {
    return errorModel;
  }

  public void setErrorModel(Sendonetomanymessage200ResponseErrorModel errorModel) {
    this.errorModel = errorModel;
  }

  public Messagestate200Response states(List<@Valid Messagestate200ResponseStatesInner> states) {
    this.states = states;
    return this;
  }

  public Messagestate200Response addStatesItem(Messagestate200ResponseStatesInner statesItem) {
    if (this.states == null) {
      this.states = new ArrayList<>();
    }
    this.states.add(statesItem);
    return this;
  }

  /**
   * Get states
   * @return states
   */
  @Valid 
  @Schema(name = "states", example = "[{\"id\":123456789123456780,\"state\":1}]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("states")
  public List<@Valid Messagestate200ResponseStatesInner> getStates() {
    return states;
  }

  public void setStates(List<@Valid Messagestate200ResponseStatesInner> states) {
    this.states = states;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Messagestate200Response messagestate200Response = (Messagestate200Response) o;
    return Objects.equals(this.errorModel, messagestate200Response.errorModel) &&
        Objects.equals(this.states, messagestate200Response.states);
  }

  @Override
  public int hashCode() {
    return Objects.hash(errorModel, states);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Messagestate200Response {\n");
    sb.append("    errorModel: ").append(toIndentedString(errorModel)).append("\n");
    sb.append("    states: ").append(toIndentedString(states)).append("\n");
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

