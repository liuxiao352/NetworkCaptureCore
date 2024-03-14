package com.liuxiao352.networkcapturecore.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;


import com.liuxiao352.networkcapturecore.R;
import com.liuxiao352.networkcapturecore.databinding.ActiviyNetworkCaptureLogBinding;
import com.liuxiao352.networkcapturecore.viewmodel.NetworkCaptureLogViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

public class NetworkCaptureLogActivity extends AppCompatActivity {

  private ActiviyNetworkCaptureLogBinding binding;
  private NetworkCaptureLogViewModel viewModel;
  private NetworkCaptureLogAdapter adapter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActiviyNetworkCaptureLogBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    viewModel = new ViewModelProvider(this).get(NetworkCaptureLogViewModel.class);
    initToolbar();
    initRecyclerView();
    initObserves();
  }

  private void initToolbar() {
    binding.toolbar.inflateMenu(R.menu.menu_nc_log);
    binding.toolbar.setOnMenuItemClickListener(item -> {
      if (item.getItemId() == R.id.clear_network_capture_log) {
        viewModel.removeAll();
        return true;
      }
      return false;
    });
  }

  private void initRecyclerView() {
    DividerItemDecoration dividerItemDecoration =
        new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
    Drawable listDividerDrawable = ContextCompat.getDrawable(this, R.drawable.nc_list_divider);
    if (listDividerDrawable != null) {
      dividerItemDecoration.setDrawable(listDividerDrawable);
    }
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    adapter = new NetworkCaptureLogAdapter();
    binding.recyclerView.setLayoutManager(layoutManager);
    binding.recyclerView.addItemDecoration(dividerItemDecoration);
    binding.recyclerView.setAdapter(adapter);
  }

  private void initObserves() {
    viewModel.getNetworkCaptureLogListLiveData().observe(this, list -> {
      adapter.submitList(list, () -> {
        binding.recyclerView.scrollToPosition(0);
      });
    });
  }
}
