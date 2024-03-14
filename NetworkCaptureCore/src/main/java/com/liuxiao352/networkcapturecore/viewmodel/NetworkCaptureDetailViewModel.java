package com.liuxiao352.networkcapturecore.viewmodel;

import com.liuxiao352.networkcapturecore.database.NetworkCaptureDatabase;
import com.liuxiao352.networkcapturecore.entity.NetworkCaptureDetail;
import com.liuxiao352.networkcapturecore.utils.NetworkCaptureTools;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NetworkCaptureDetailViewModel extends ViewModel {

  private MutableLiveData<NetworkCaptureDetail> networkCaptureDetailLiveData;

  public final LiveData<NetworkCaptureDetail> getNetworkCaptureDetailLiveData() {
    if (networkCaptureDetailLiveData == null) {
      networkCaptureDetailLiveData = new MutableLiveData<>();
    }
    return networkCaptureDetailLiveData;
  }

  public void fetchNetworkCaptureDetail(long id) {
    if (networkCaptureDetailLiveData == null) {
      networkCaptureDetailLiveData = new MutableLiveData<>();
    }
    NetworkCaptureTools.execute(() -> {
      NetworkCaptureDetail networkCaptureDetail =
          NetworkCaptureDatabase.getInstance().getNetworkCaptureDao().getNetworkCaptureDetail(id);
      networkCaptureDetailLiveData.postValue(networkCaptureDetail);
    });
  }
}
