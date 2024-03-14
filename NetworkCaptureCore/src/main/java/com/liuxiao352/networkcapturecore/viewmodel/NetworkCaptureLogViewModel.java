package com.liuxiao352.networkcapturecore.viewmodel;

import com.liuxiao352.networkcapturecore.database.NetworkCaptureDatabase;
import com.liuxiao352.networkcapturecore.entity.NetworkCaptureLog;
import com.liuxiao352.networkcapturecore.utils.NetworkCaptureTools;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class NetworkCaptureLogViewModel extends ViewModel {

  private final LiveData<PagedList<NetworkCaptureLog>> networkCaptureLogListLiveData;

  public NetworkCaptureLogViewModel() {
    DataSource.Factory<Integer, NetworkCaptureLog> query =
        NetworkCaptureDatabase.getInstance().getNetworkCaptureDao().getNetworkCaptureLogList();
    PagedList.Config config =
        new PagedList.Config.Builder()
            .setPageSize(15)
            .setInitialLoadSizeHint(30)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build();
    networkCaptureLogListLiveData = new LivePagedListBuilder<>(query, config).build();
  }

  public final LiveData<PagedList<NetworkCaptureLog>> getNetworkCaptureLogListLiveData() {
    return networkCaptureLogListLiveData;
  }


  public void removeAll() {
    NetworkCaptureTools.execute(
        () -> NetworkCaptureDatabase.getInstance().getNetworkCaptureDao().removeAll());
  }
}
