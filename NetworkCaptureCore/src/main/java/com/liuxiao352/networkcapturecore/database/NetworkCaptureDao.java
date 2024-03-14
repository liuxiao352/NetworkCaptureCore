package com.liuxiao352.networkcapturecore.database;


import com.liuxiao352.networkcapturecore.entity.NetworkCaptureDetail;
import com.liuxiao352.networkcapturecore.entity.NetworkCaptureLog;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public interface NetworkCaptureDao {

  @Query("select * from network_capture_log order by create_time DESC")
  DataSource.Factory<Integer, NetworkCaptureLog> getNetworkCaptureLogList();


  @Query("select * from network_capture_detail where id= :id")
  NetworkCaptureDetail getNetworkCaptureDetail(long id);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  long insertNetworkCaptureLog(NetworkCaptureLog log);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  long insertNetworkCaptureDetail(NetworkCaptureDetail detail);

  @Query("delete from network_capture_log")
  void removeAllNetworkCaptureLog();

  @Query("delete from network_capture_detail")
  void removeAllNetworkCaptureDetail();


  @Transaction
  default void insert(NetworkCaptureLog log, NetworkCaptureDetail detail) {
    detail.id = insertNetworkCaptureLog(log);
    insertNetworkCaptureDetail(detail);
  }

  @Transaction
  default void removeAll() {
    removeAllNetworkCaptureLog();
    removeAllNetworkCaptureDetail();
  }
}
