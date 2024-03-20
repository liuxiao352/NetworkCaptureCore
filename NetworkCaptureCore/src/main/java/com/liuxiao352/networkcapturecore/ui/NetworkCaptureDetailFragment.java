package com.liuxiao352.networkcapturecore.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.liuxiao352.networkcapturecore.R;
import com.liuxiao352.networkcapturecore.databinding.FragmentNetworkCaptureDetailBinding;
import com.liuxiao352.networkcapturecore.entity.KeyValue;
import com.liuxiao352.networkcapturecore.utils.NetworkCaptureTools;
import com.liuxiao352.networkcapturecore.viewmodel.NetworkCaptureDetailViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

public class NetworkCaptureDetailFragment extends Fragment {

  public static final int REQUEST = 1;
  public static final int RESPONSE = 2;
  private static final String ARGS_TYPE = "type";
  private int type = REQUEST;
  private NetworkCaptureDetailViewModel viewModel;
  private NetworkCaptureDetailAdapter adapter;
  private String responseBodyJson;

  public static NetworkCaptureDetailFragment newInstance(int type) {
    Bundle args = new Bundle();
    args.putInt(ARGS_TYPE, type);
    NetworkCaptureDetailFragment fragment = new NetworkCaptureDetailFragment();
    fragment.setArguments(args);
    return fragment;
  }

  private FragmentNetworkCaptureDetailBinding binding;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      type = getArguments().getInt(ARGS_TYPE);
    }
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentNetworkCaptureDetailBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = new ViewModelProvider(requireActivity()).get(NetworkCaptureDetailViewModel.class);
    adapter = new NetworkCaptureDetailAdapter();
    initRecyclerView();
    initObserves();
  }

  private void initRecyclerView() {
    binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
    binding.recyclerView.setAdapter(adapter);
    adapter.setOnClickJsonTextListener(() -> {
      if (NetworkCaptureTools.setClipText(responseBodyJson)) {
        Snackbar.make(binding.getRoot(), getString(R.string.nc_copy_success), Snackbar.LENGTH_SHORT)
            .show();
      }
    });
  }

  private void initObserves() {
    viewModel.getNetworkCaptureDetailLiveData().observe(getViewLifecycleOwner(), detail -> {
      List<Object> list = new ArrayList<>();
      if (type == REQUEST) {
        StringBuilder url = new StringBuilder(detail.getScheme());
        url.append("://").append(detail.getHost()).append(detail.getPath());
        if (!TextUtils.isEmpty(detail.getQuery())) {
          url.append("?").append(detail.getQuery());
        }
        SpannableString urlSpannable = new SpannableString(url);
        urlSpannable.setSpan(new ClickableSpan() {
          @Override
          public void onClick(@NonNull View widget) {
            if (NetworkCaptureTools.setClipText(url.toString())) {
              Snackbar.make(widget, getString(R.string.nc_copy_success), Snackbar.LENGTH_SHORT)
                  .show();
            }
          }
        }, 0, url.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        list.add(new KeyValue(getString(R.string.nc_url), urlSpannable));
        String method = detail.getMethod().toUpperCase(Locale.US);
        list.add(new KeyValue(getString(R.string.nc_method), method));
        CharSequence header = NetworkCaptureTools.formatHeader(detail.getRequestHeader());
        list.add(new KeyValue(getString(R.string.nc_headers),
            TextUtils.isEmpty(header) ? getString(R.string.nc_none) : header));
        String requestBody = NetworkCaptureTools.decodeString(detail.getRequestBody());
        String contentType =
            TextUtils.isEmpty(detail.getRequestContentType()) ? "" : detail.getRequestContentType();
        list.add(new KeyValue(getString(R.string.nc_content_type),
            TextUtils.isEmpty(contentType) ? getString(R.string.nc_none) :
                TextUtils.isEmpty(contentType) ? getString(R.string.nc_none) : contentType));
        list.add(new KeyValue(getString(R.string.nc_body),
            TextUtils.isEmpty(requestBody) ? getString(R.string.nc_none) : requestBody));
      } else {
        String responseCode = String.valueOf(detail.getResponseCode());
        SpannableString responseCodeSpannable = new SpannableString(responseCode);
        responseCodeSpannable.setSpan(
            new ForegroundColorSpan(detail.getResponseCode() == 200 ? 0xff009688 : 0xffff0000), 0,
            responseCode.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        list.add(new KeyValue(getString(R.string.nc_response_code), responseCodeSpannable));
        String responseTime = NetworkCaptureTools.formatTime(detail.getResponseTime());
        list.add(new KeyValue(getString(R.string.nc_response_time), responseTime));
        CharSequence header = NetworkCaptureTools.formatHeader(detail.getResponseHeader());
        list.add(new KeyValue(getString(R.string.nc_headers),
            TextUtils.isEmpty(header) ? getString(R.string.nc_none) : header));
        responseBodyJson = detail.getResponseBody();
        List<String> jsonList = NetworkCaptureTools.getJsonList(responseBodyJson);
        list.add(new KeyValue(getString(R.string.nc_body),
            jsonList.isEmpty() ? getString(R.string.nc_none) : ""));
        list.addAll(jsonList);
      }
      adapter.setList(list);
    });
  }
}
