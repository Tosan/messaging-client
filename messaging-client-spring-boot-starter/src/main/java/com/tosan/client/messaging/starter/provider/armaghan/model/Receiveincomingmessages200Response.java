package com.tosan.client.messaging.starter.provider.armaghan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.tosan.client.messaging.starter.provider.armaghan.modelsabstraction.ErrorModelHolder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;

import java.util.Objects;

/**
 * Receiveincomingmessages200Response
 */

@JsonTypeName("receiveincomingmessages_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class Receiveincomingmessages200Response implements ErrorModelHolder {

  private Sendonetomanymessage200ResponseErrorModel errorModel;

  private Receiveincomingmessages200ResponseResult result;

  public Receiveincomingmessages200Response errorModel(Sendonetomanymessage200ResponseErrorModel errorModel) {
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

  public Receiveincomingmessages200Response result(Receiveincomingmessages200ResponseResult result) {
    this.result = result;
    return this;
  }

  /**
   * Get result
   * @return result
   */
  @Valid 
  @Schema(name = "result", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("result")
  public Receiveincomingmessages200ResponseResult getResult() {
    return result;
  }

  public void setResult(Receiveincomingmessages200ResponseResult result) {
    this.result = result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Receiveincomingmessages200Response receiveincomingmessages200Response = (Receiveincomingmessages200Response) o;
    return Objects.equals(this.errorModel, receiveincomingmessages200Response.errorModel) &&
        Objects.equals(this.result, receiveincomingmessages200Response.result);
  }

  @Override
  public int hashCode() {
    return Objects.hash(errorModel, result);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Receiveincomingmessages200Response {\n");
    sb.append("    errorModel: ").append(toIndentedString(errorModel)).append("\n");
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
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

