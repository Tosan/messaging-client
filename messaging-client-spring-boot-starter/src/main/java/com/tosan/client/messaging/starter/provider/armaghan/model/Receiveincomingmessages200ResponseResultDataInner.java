package com.tosan.client.messaging.starter.provider.armaghan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Receiveincomingmessages200ResponseResultDataInner
 */

@JsonTypeName("receiveincomingmessages_200_response_result_data_inner")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class Receiveincomingmessages200ResponseResultDataInner {

  private String content;

  private BigDecimal destination;

  private BigDecimal id;

  private BigDecimal insertDate;

  private BigDecimal originator;

  public Receiveincomingmessages200ResponseResultDataInner content(String content) {
    this.content = content;
    return this;
  }

  /**
   * Get content
   * @return content
   */
  
  @Schema(name = "content", example = "test message", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("content")
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Receiveincomingmessages200ResponseResultDataInner destination(BigDecimal destination) {
    this.destination = destination;
    return this;
  }

  /**
   * Get destination
   * @return destination
   */
  @Valid 
  @Schema(name = "destination", example = "50004123456789", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("destination")
  public BigDecimal getDestination() {
    return destination;
  }

  public void setDestination(BigDecimal destination) {
    this.destination = destination;
  }

  public Receiveincomingmessages200ResponseResultDataInner id(BigDecimal id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  @Valid 
  @Schema(name = "id", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public BigDecimal getId() {
    return id;
  }

  public void setId(BigDecimal id) {
    this.id = id;
  }

  public Receiveincomingmessages200ResponseResultDataInner insertDate(BigDecimal insertDate) {
    this.insertDate = insertDate;
    return this;
  }

  /**
   * Get insertDate
   * @return insertDate
   */
  @Valid 
  @Schema(name = "insertDate", example = "0", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("insertDate")
  public BigDecimal getInsertDate() {
    return insertDate;
  }

  public void setInsertDate(BigDecimal insertDate) {
    this.insertDate = insertDate;
  }

  public Receiveincomingmessages200ResponseResultDataInner originator(BigDecimal originator) {
    this.originator = originator;
    return this;
  }

  /**
   * Get originator
   * @return originator
   */
  @Valid 
  @Schema(name = "originator", example = "9112223344", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("originator")
  public BigDecimal getOriginator() {
    return originator;
  }

  public void setOriginator(BigDecimal originator) {
    this.originator = originator;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Receiveincomingmessages200ResponseResultDataInner receiveincomingmessages200ResponseResultDataInner = (Receiveincomingmessages200ResponseResultDataInner) o;
    return Objects.equals(this.content, receiveincomingmessages200ResponseResultDataInner.content) &&
        Objects.equals(this.destination, receiveincomingmessages200ResponseResultDataInner.destination) &&
        Objects.equals(this.id, receiveincomingmessages200ResponseResultDataInner.id) &&
        Objects.equals(this.insertDate, receiveincomingmessages200ResponseResultDataInner.insertDate) &&
        Objects.equals(this.originator, receiveincomingmessages200ResponseResultDataInner.originator);
  }

  @Override
  public int hashCode() {
    return Objects.hash(content, destination, id, insertDate, originator);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Receiveincomingmessages200ResponseResultDataInner {\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    destination: ").append(toIndentedString(destination)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    insertDate: ").append(toIndentedString(insertDate)).append("\n");
    sb.append("    originator: ").append(toIndentedString(originator)).append("\n");
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

