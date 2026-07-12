package com.tosan.client.messaging.starter.provider.armaghan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.tosan.client.messaging.starter.provider.armaghan.modelsabstraction.ErrorModelHolder;
import com.tosan.client.messaging.starter.provider.armaghan.modelsabstraction.ResponseWithReferences;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Sendonetomanymessage200Response
 */

@JsonTypeName("sendonetomanymessage_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class Sendonetomanymessage200Response implements ErrorModelHolder , ResponseWithReferences {

  private Sendonetomanymessage200ResponseErrorModel errorModel;

  @Valid
  private List<BigDecimal> references = new ArrayList<>();

  public Sendonetomanymessage200Response errorModel(Sendonetomanymessage200ResponseErrorModel errorModel) {
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

  public Sendonetomanymessage200Response references(List<BigDecimal> references) {
    this.references = references;
    return this;
  }

  public Sendonetomanymessage200Response addReferencesItem(BigDecimal referencesItem) {
    if (this.references == null) {
      this.references = new ArrayList<>();
    }
    this.references.add(referencesItem);
    return this;
  }

  /**
   * Get references
   * @return references
   */
  @Valid 
  @Schema(name = "references", example = "[987654321123456800]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("references")
  public List<BigDecimal> getReferences() {
    return references;
  }

  public void setReferences(List<BigDecimal> references) {
    this.references = references;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Sendonetomanymessage200Response sendonetomanymessage200Response = (Sendonetomanymessage200Response) o;
    return Objects.equals(this.errorModel, sendonetomanymessage200Response.errorModel) &&
        Objects.equals(this.references, sendonetomanymessage200Response.references);
  }

  @Override
  public int hashCode() {
    return Objects.hash(errorModel, references);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Sendonetomanymessage200Response {\n");
    sb.append("    errorModel: ").append(toIndentedString(errorModel)).append("\n");
    sb.append("    references: ").append(toIndentedString(references)).append("\n");
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

