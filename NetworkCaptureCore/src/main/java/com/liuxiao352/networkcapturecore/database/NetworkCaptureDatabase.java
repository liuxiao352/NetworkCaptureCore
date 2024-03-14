package com.liuxiao352.networkcapturecore.database;

import com.liuxiao352.networkcapturecore.entity.NetworkCaptureDetail;
import com.liuxiao352.networkcapturecore.entity.NetworkCaptureLog;
import com.liuxiao352.networkcapturecore.utils.NetworkCaptureTools;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = { NetworkCaptureLog.class,
    NetworkCaptureDetail.class }, version = 1, exportSchema = false)
public abstract class NetworkCaptureDatabase extends RoomDatabase {

  private static volatile NetworkCaptureDatabase instance;

  public static NetworkCaptureDatabase getInstance() {
    if (instance == null) {
      synchronized (NetworkCaptureDatabase.class) {
        if (instance == null) {
          instance =
              Room.databaseBuilder(NetworkCaptureTools.getContext(), NetworkCaptureDatabase.class,
                      "network_capture").setQueryExecutor(NetworkCaptureTools.getThreadExecutor())
                  .fallbackToDestructiveMigration().build();
        }
      }
    }
    return instance;
  }

  public abstract NetworkCaptureDao getNetworkCaptureDao();
}
