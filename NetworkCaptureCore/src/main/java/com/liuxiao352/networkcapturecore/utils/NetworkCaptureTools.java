package com.liuxiao352.networkcapturecore.utils;

import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.liuxiao352.networkcapturecore.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NetworkCaptureTools {

  @SuppressLint("StaticFieldLeak")
  private static Context context;

  private static Executor singleThreadExecutor;

  private static Gson gson;

  private static DateFormat timeDataFormat;

  public static void init(Context context) {
    NetworkCaptureTools.context = (Application) context;
    singleThreadExecutor = Executors.newSingleThreadExecutor();
    gson = new GsonBuilder().setPrettyPrinting()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    timeDataFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.US);
  }

  public static Context getContext() {
    return context;
  }

  public static String toJson(Object src) {
    if (src == null) {
      return "";
    }
    try {
      return gson.toJson(src);
    } catch (Exception e) {
      return "";
    }
  }

  public static String setPrettyPrinting(String json) {
    if (TextUtils.isEmpty(json)) {
      return "";
    }
    try {
      return toJson(new JsonParser().parse(json));
    } catch (Exception e) {
      return json;
    }
  }

  public static List<String> getJsonList(String json) {
    List<String> list = new ArrayList<>();
    if (TextUtils.isEmpty(json)) {
      return list;
    }
    json = setPrettyPrinting(json);
    String[] split = json.split("\n");
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < split.length; i++) {
      boolean warp = i != 0 && i != split.length - 1 && i % 5 == 0;
      if (warp) {
        list.add(sb.toString());
        sb = new StringBuilder();
      }
      sb.append(split[i]);
      if (!warp) {
        sb.append("\n");
      }
    }
    if (!TextUtils.isEmpty(sb.toString())) {
      list.add(sb.toString());
    }
    return list;
  }

  @Nullable
  public static <T> T fromJson(String json, Type typeOfT) {
    if (TextUtils.isEmpty(json)) {
      return null;
    }
    try {
      return gson.fromJson(json, typeOfT);
    } catch (Exception e) {
      return null;
    }
  }

  public static Executor getThreadExecutor() {
    return singleThreadExecutor;
  }

  public static void execute(Runnable runnable) {
    singleThreadExecutor.execute(runnable);
  }

  public static String formatTime(long time) {
    return timeDataFormat.format(time);
  }

  public static CharSequence formatHeader(String header) {
    if (TextUtils.isEmpty(header)) {
      return "";
    }
    Map<String, List<String>> map = fromJson(header, new TypeToken<Map<String, List<String>>>() {
    }.getType());
    if (map != null) {
      SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
      boolean wrap = false;
      for (String name : map.keySet()) {
        if (wrap) {
          spannableStringBuilder.append("\n");
        }
        wrap = true;
        spannableStringBuilder.append(name).append(": ");
        List<String> values = map.get(name);
        if (values != null) {
          String value = values.toString().replaceAll("\\]", "").replaceAll("\\[", "");
          spannableStringBuilder.append(value);
          spannableStringBuilder.setSpan(new ClickableSpan() {
                                           @Override
                                           public void onClick(@NonNull View widget) {
                                             if (setClipText(value)) {
                                               Snackbar.make(widget, context.getString(R.string.nc_copy_success),
                                                   Snackbar.LENGTH_SHORT).show();
                                             }
                                           }
                                         }, spannableStringBuilder.length() - value.length(), spannableStringBuilder.length(),
              Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
      }
      return spannableStringBuilder;
    }
    return "";
  }

  public static String decodeString(String src) {
    if (TextUtils.isEmpty(src)) {
      return "";
    }
    try {
      return URLDecoder.decode(src, "utf-8");
    } catch (Exception e) {
      return "";
    }
  }

  public static boolean setClipText(String text) {
    if (TextUtils.isEmpty(text)) {
      return false;
    }
    try {
      ClipboardManager cm =
          (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
      ClipData clip = ClipData.newPlainText("", text);
      cm.setPrimaryClip(clip);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
