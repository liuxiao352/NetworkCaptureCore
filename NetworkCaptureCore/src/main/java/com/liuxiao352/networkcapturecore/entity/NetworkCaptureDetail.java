package com.liuxiao352.networkcapturecore.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "network_capture_detail")
public class NetworkCaptureDetail {

  @PrimaryKey
  public long id;
  private final String scheme;
  private final String host;
  private final String path;
  private final String query;
  private final String method;

  @ColumnInfo(name = "response_code")
  private final int responseCode;

  @ColumnInfo(name = "request_header")
  private final String requestHeader;

  @ColumnInfo(name = "request_body")
  private final String requestBody;

  @ColumnInfo(name = "request_content_type")
  private final String requestContentType;

  @ColumnInfo(name = "response_header")
  private final String responseHeader;

  @ColumnInfo(name = "response_body")
  private final String responseBody;

  @ColumnInfo(name = "request_time")
  private final long requestTime;

  @ColumnInfo(name = "response_time")
  private final long responseTime;

  private final long duration;

  public NetworkCaptureDetail(String scheme, String host, String path, String query, String method,
      int responseCode, String requestHeader, String requestBody, String requestContentType,
      String responseHeader, String responseBody, long requestTime, long responseTime,
      long duration) {
    this.scheme = scheme;
    this.host = host;
    this.path = path;
    this.query = query;
    this.method = method;
    this.responseCode = responseCode;
    this.requestHeader = requestHeader;
    this.requestBody = requestBody;
    this.requestContentType = requestContentType;
    this.responseHeader = responseHeader;
    this.responseBody = responseBody;
    this.requestTime = requestTime;
    this.responseTime = responseTime;
    this.duration = duration;
  }

  public long getId() {
    return id;
  }

  public String getScheme() {
    return scheme;
  }

  public String getHost() {
    return host;
  }

  public String getPath() {
    return path;
  }

  public String getQuery() {
    return query;
  }

  public String getMethod() {
    return method;
  }

  public int getResponseCode() {
    return responseCode;
  }

  public String getRequestHeader() {
    return requestHeader;
  }

  public String getRequestBody() {
    return requestBody;
  }

  public String getRequestContentType() {
    return requestContentType;
  }

  public String getResponseHeader() {
    return responseHeader;
  }

  public String getResponseBody() {
    return responseBody;
  }

  public long getRequestTime() {
    return requestTime;
  }

  public long getResponseTime() {
    return responseTime;
  }

  public long getDuration() {
    return duration;
  }
}
