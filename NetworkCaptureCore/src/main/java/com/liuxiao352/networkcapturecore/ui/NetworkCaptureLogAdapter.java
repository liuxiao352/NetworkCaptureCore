package com.liuxiao352.networkcapturecore.ui;

import java.util.Locale;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.liuxiao352.networkcapturecore.databinding.ItemNetworkCaptureLogBinding;
import com.liuxiao352.networkcapturecore.entity.NetworkCaptureLog;
import com.liuxiao352.networkcapturecore.utils.NetworkCaptureTools;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class NetworkCaptureLogAdapter extends
    PagedListAdapter<NetworkCaptureLog, NetworkCaptureLogAdapter.NetworkCaptureViewHolder> {

  protected NetworkCaptureLogAdapter() {
    super(new DiffUtil.ItemCallback<NetworkCaptureLog>() {
      @Override
      public boolean areItemsTheSame(@NonNull NetworkCaptureLog oldItem,
          @NonNull NetworkCaptureLog newItem) {
        return oldItem.getId() == newItem.getId();
      }

      @Override
      public boolean areContentsTheSame(@NonNull NetworkCaptureLog oldItem,
          @NonNull NetworkCaptureLog newItem) {
        return true;
      }
    });
  }

  @NonNull
  @Override
  public NetworkCaptureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    ItemNetworkCaptureLogBinding binding =
        ItemNetworkCaptureLogBinding.inflate(inflater, parent, false);
    return new NetworkCaptureViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull NetworkCaptureViewHolder holder, int position) {
    NetworkCaptureLog log = getItem(position);
    if (log == null) {
      return;
    }
    holder.binding.tvSchemeHost.setText(TextUtils.concat(log.getScheme(), "://", log.getHost()));
    holder.binding.tvCreateTime.setText(NetworkCaptureTools.formatTime(log.getCreateTime()));
    StringBuilder pathQuery = new StringBuilder();
    pathQuery.append(log.getPath());
    if (!TextUtils.isEmpty(log.getQuery())) {
      pathQuery.append("?").append(log.getQuery());
    }
    holder.binding.tvPathQuery.setText(pathQuery);
    holder.binding.tvMethod.setText(log.getMethod().toUpperCase(Locale.US));
    holder.binding.tvResponseCode.setTextColor(
        log.getResponseCode() == 200 ? 0xff009688 : 0xffff0000);
    holder.binding.tvResponseCode.setText(String.valueOf(log.getResponseCode()));
    holder.binding.tvDuration.setText(String.format("%sms", log.getDuration()));
  }

  final class NetworkCaptureViewHolder extends RecyclerView.ViewHolder {
    private final ItemNetworkCaptureLogBinding binding;

    public NetworkCaptureViewHolder(@NonNull ItemNetworkCaptureLogBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
      itemView.setOnClickListener(v -> {
        NetworkCaptureLog captureLog = getItem(getAdapterPosition());
        if (captureLog != null) {
          NetworkCaptureDetailActivity.start(v.getContext(), captureLog.getId());
        }
      });
    }
  }
}
