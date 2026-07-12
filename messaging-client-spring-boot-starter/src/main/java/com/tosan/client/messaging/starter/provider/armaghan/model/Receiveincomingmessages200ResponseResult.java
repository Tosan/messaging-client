package com.tosan.client.messaging.starter.provider.armaghan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Receiveincomingmessages200ResponseResult
 */

@JsonTypeName("receiveincomingmessages_200_response_result")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class Receiveincomingmessages200ResponseResult {

  @Valid
  private List<@Valid Receiveincomingmessages200ResponseResultDataInner> data = new ArrayList<>();

  private BigDecimal recordsFiltered;

  private BigDecimal recordsTotal;

  public Receiveincomingmessages200ResponseResult data(List<@Valid Receiveincomingmessages200ResponseResultDataInner> data) {
    this.data = data;
    return this;
  }

  public Receiveincomingmessages200ResponseResult addDataItem(Receiveincomingmessages200ResponseResultDataInner dataItem) {
    if (this.data == null) {
      this.data = new ArrayList<>();
    }
    this.data.add(dataItem);
    return this;
  }

  /**
   * Get data
   * @return data
   */
  @Valid 
  @Schema(name = "data", example = "[{\"content\":\"test message\",\"destination\":50004123456789,\"id\":1,\"insertDate\":0,\"originator\":9112223344}]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("data")
  public List<@Valid Receiveincomingmessages200ResponseResultDataInner> getData() {
    return data;
  }

  public void setData(List<@Valid Receiveincomingmessages200ResponseResultDataInner> data) {
    this.data = data;
  }

  public Receiveincomingmessages200ResponseResult recordsFiltered(BigDecimal recordsFiltered) {
    this.recordsFiltered = recordsFiltered;
    return this;
  }

  /**
   * Get recordsFiltered
   * @return recordsFiltered
   */
  @Valid 
  @Schema(name = "recordsFiltered", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("recordsFiltered")
  public BigDecimal getRecordsFiltered() {
    return recordsFiltered;
  }

  public void setRecordsFiltered(BigDecimal recordsFiltered) {
    this.recordsFiltered = recordsFiltered;
  }

  public Receiveincomingmessages200ResponseResult recordsTotal(BigDecimal recordsTotal) {
    this.recordsTotal = recordsTotal;
    return this;
  }

  /**
   * Get recordsTotal
   * @return recordsTotal
   */
  @Valid 
  @Schema(name = "recordsTotal", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("recordsTotal")
  public BigDecimal getRecordsTotal() {
    return recordsTotal;
  }

  public void setRecordsTotal(BigDecimal recordsTotal) {
    this.recordsTotal = recordsTotal;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Receiveincomingmessages200ResponseResult receiveincomingmessages200ResponseResult = (Receiveincomingmessages200ResponseResult) o;
    return Objects.equals(this.data, receiveincomingmessages200ResponseResult.data) &&
        Objects.equals(this.recordsFiltered, receiveincomingmessages200ResponseResult.recordsFiltered) &&
        Objects.equals(this.recordsTotal, receiveincomingmessages200ResponseResult.recordsTotal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, recordsFiltered, recordsTotal);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Receiveincomingmessages200ResponseResult {\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    recordsFiltered: ").append(toIndentedString(recordsFiltered)).append("\n");
    sb.append("    recordsTotal: ").append(toIndentedString(recordsTotal)).append("\n");
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

