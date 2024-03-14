package com.liuxiao352.networkcapturecore.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "network_capture_log")
public class NetworkCaptureLog {

  @PrimaryKey(autoGenerate = true)
  public long id;
  private final String scheme;
  private final String host;
  private final String path;
  private final String query;
  private final String method;
  @ColumnInfo(name = "response_code")
  private final int responseCode;
  private final long duration;
  @ColumnInfo(name = "create_time")
  private final long createTime;

  public NetworkCaptureLog(String scheme, String host, String path, String query, String method,
      int responseCode, long duration, long createTime) {
    this.scheme = scheme;
    this.host = host;
    this.path = path;
    this.query = query;
    this.method = method;
    this.responseCode = responseCode;
    this.duration = duration;
    this.createTime = createTime;
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

  public long getDuration() {
    return duration;
  }

  public long getCreateTime() {
    return createTime;
  }
}
