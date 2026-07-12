package com.tosan.client.messaging.starter.provider.armaghan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Sendonetomanymessage200ResponseErrorModel
 */

@JsonTypeName("sendonetomanymessage_200_response_errorModel")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class Sendonetomanymessage200ResponseErrorModel {

  private BigDecimal errorCode;

  private Object message = null;

  private Object timestamp = null;

  public Sendonetomanymessage200ResponseErrorModel errorCode(BigDecimal errorCode) {
    this.errorCode = errorCode;
    return this;
  }

  /**
   * Get errorCode
   * @return errorCode
   */
  @Valid 
  @Schema(name = "errorCode", example = "0", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("errorCode")
  public BigDecimal getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(BigDecimal errorCode) {
    this.errorCode = errorCode;
  }

  public Sendonetomanymessage200ResponseErrorModel message(Object message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
   */
  
  @Schema(name = "message", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("message")
  public Object getMessage() {
    return message;
  }

  public void setMessage(Object message) {
    this.message = message;
  }

  public Sendonetomanymessage200ResponseErrorModel timestamp(Object timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Get timestamp
   * @return timestamp
   */
  
  @Schema(name = "timestamp", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("timestamp")
  public Object getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Object timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Sendonetomanymessage200ResponseErrorModel sendonetomanymessage200ResponseErrorModel = (Sendonetomanymessage200ResponseErrorModel) o;
    return Objects.equals(this.errorCode, sendonetomanymessage200ResponseErrorModel.errorCode) &&
        Objects.equals(this.message, sendonetomanymessage200ResponseErrorModel.message) &&
        Objects.equals(this.timestamp, sendonetomanymessage200ResponseErrorModel.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(errorCode, message, timestamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Sendonetomanymessage200ResponseErrorModel {\n");
    sb.append("    errorCode: ").append(toIndentedString(errorCode)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
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

