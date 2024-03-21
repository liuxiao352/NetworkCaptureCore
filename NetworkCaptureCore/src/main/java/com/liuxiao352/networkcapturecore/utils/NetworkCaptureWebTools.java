package com.liuxiao352.networkcapturecore.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *  @Nullable
 *   public static String getVConsoleJs() {
 *     try {
 *       Class<?> clazz =
 *           Class.forName("com.liuxiao352.networkcapturecore.utils.NetworkCaptureWebTools");
 *       return Objects.requireNonNull(clazz.getMethod("getVConsoleJs").invoke(null)).toString();
 *     } catch (Exception e) {
 *       return null;
 *     }
 *   }
 */
public class NetworkCaptureWebTools {

  private static String vConsoleJs;

  public static String getVConsoleJs() {
    if (vConsoleJs == null) {
      try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
          NetworkCaptureTools.getContext().getAssets().open("vconsole.js")))) {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
          sb.append(line);
        }
        vConsoleJs = sb.toString();
      } catch (Exception ignored) {
      }
    }
    return vConsoleJs;
  }
}
