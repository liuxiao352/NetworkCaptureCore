package com.liuxiao352.networkcapturecore.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

import com.liuxiao352.networkcapturecore.R;
import com.liuxiao352.networkcapturecore.ui.NetworkCaptureLogActivity;

public class ShortcutHelper {
  private final Context context;

  public ShortcutHelper(Context context) {
    this.context = context;
  }

  public void createShortcuts() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
      return;
    }
    ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
        !shortcutManager.isRequestPinShortcutSupported()) {
      return;
    }

    List<ShortcutInfo> shortcuts = new ArrayList<>();
    Intent intent = new Intent(context, NetworkCaptureLogActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    intent.setAction("test");
    ShortcutInfo shortcutInfo =
        new ShortcutInfo.Builder(context, "sdk_shortcut_network_capture").setShortLabel(
                context.getString(R.string.nc_app_name))
            .setLongLabel(context.getString(R.string.nc_app_name))
            .setIcon(Icon.createWithResource(context, R.mipmap.np_logo)).setIntent(intent).build();
    shortcuts.add(shortcutInfo);
    shortcutManager.setDynamicShortcuts(shortcuts);
  }
}
