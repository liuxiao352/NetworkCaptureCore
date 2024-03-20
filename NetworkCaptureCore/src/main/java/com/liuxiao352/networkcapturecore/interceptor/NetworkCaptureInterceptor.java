package com.liuxiao352.networkcapturecore.interceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import android.text.TextUtils;

import com.liuxiao352.networkcapturecore.database.NetworkCaptureDatabase;
import com.liuxiao352.networkcapturecore.entity.NetworkCaptureDetail;
import com.liuxiao352.networkcapturecore.entity.NetworkCaptureLog;
import com.liuxiao352.networkcapturecore.utils.NetworkCaptureTools;

import androidx.annotation.NonNull;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;

public class NetworkCaptureInterceptor implements Interceptor {

  public static final long MAX_BYTES = 1048576;

  @NonNull
  @Override
  public Response intercept(@NonNull Chain chain) throws IOException {
    Request request = chain.request();
    Response response;
    String scheme = "";
    String host = "";
    String query = "";
    String path = "";
    String responseHeader = "";
    String responseBody = "";
    String method = request.method();
    String requestHeader = "";
    String requestBody = "";
    String requestContentType = "";
    int responseCode = -1;
    long requestTime = System.currentTimeMillis();
    long responseTime = requestTime;
    long duration = 0;
    try {
      HttpUrl url = request.url();
      scheme = url.scheme();
      host = url.host();
      query = url.query();
      path = url.encodedPath();
      requestHeader = NetworkCaptureTools.toJson(request.headers().toMultimap());
      if (request.body() != null) {
        MediaType mediaType = request.body().contentType();
        if (mediaType != null) {
          requestContentType = mediaType.toString();
        }
        if (request.body().contentLength() > MAX_BYTES || request.body() instanceof MultipartBody) {
          requestBody = "Too large";
          if (request.body() instanceof MultipartBody) {
            for (MultipartBody.Part part : ((MultipartBody) request.body()).parts()) {
              Headers headers = part.headers();
              if (headers != null && headers.size() > 0) {
                requestBody = requestBody.concat("\n");
                for (int i = 0; i < headers.size(); i++) {
                  requestBody = requestBody.concat(headers.value(i));
                }
              }
            }
          }
        } else {
          Buffer buffer = new Buffer();
          request.body().writeTo(buffer);
          requestBody = buffer.readString(StandardCharsets.UTF_8);
        }
      }
      response = chain.proceed(request);
      responseTime = System.currentTimeMillis();
      duration = responseTime - requestTime;
      responseCode = response.code();
      responseHeader = NetworkCaptureTools.toJson(response.headers().toMultimap());
      if (response.body() != null) {
        if (response.body().contentLength() > MAX_BYTES) {
          responseBody = "Too large";
        } else {
          BufferedSource source = response.body().source();
          source.request(Long.MAX_VALUE);
          Buffer buffer = source.getBuffer();
          responseBody = buffer.clone().readString(StandardCharsets.UTF_8);
        }
      }
      return response;
    } catch (IOException e) {
      responseBody = e.getMessage();
      if (!TextUtils.isEmpty(responseBody)) {
        if (responseBody.contains("443")) {
          responseCode = 443;
        } else if (responseBody.contains("403")) {
          responseCode = 403;
        }
      }
      throw new IOException();
    } finally {
      NetworkCaptureLog log =
          new NetworkCaptureLog(scheme, host, path, query, method, responseCode, duration,
              requestTime);
      NetworkCaptureDetail detail =
          new NetworkCaptureDetail(scheme, host, path, query, method, responseCode, requestHeader,
              requestBody, requestContentType, responseHeader, responseBody, requestTime,
              responseTime, duration);
      NetworkCaptureDatabase.getInstance().getNetworkCaptureDao().insert(log, detail);
    }
  }
}
