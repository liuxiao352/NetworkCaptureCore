package com.liuxiao352.networkcapturecore.ui;

import com.liuxiao352.networkcapturecore.databinding.ItemNetworkCaptureLogBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NetworkCaptureViewHolder extends RecyclerView.ViewHolder {

  public final ItemNetworkCaptureLogBinding binding;

  public NetworkCaptureViewHolder(@NonNull ItemNetworkCaptureLogBinding binding) {
    super(binding.getRoot());
    this.binding = binding;
  }
}