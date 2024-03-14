package com.liuxiao352.networkcapturecore.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.liuxiao352.networkcapturecore.databinding.ItemNetworkCaptureDetailJsonTextBinding;
import com.liuxiao352.networkcapturecore.databinding.ItemNetworkCaptureDetailKvBinding;
import com.liuxiao352.networkcapturecore.entity.KeyValue;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NetworkCaptureDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  public static final int KEY_VALUE = 1;
  public static final int JSON_TEXT = 2;
  private final List<Object> list = new ArrayList<>();
  private OnClickJsonTextListener mOnClickJsonTextListener;

  @Override
  public int getItemViewType(int position) {
    Object item = list.get(position);
    if (item instanceof KeyValue) {
      return KEY_VALUE;
    }
    return JSON_TEXT;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    if (viewType == KEY_VALUE) {
      ItemNetworkCaptureDetailKvBinding kvBinding =
          ItemNetworkCaptureDetailKvBinding.inflate(inflater, parent, false);
      return new KeyValueViewHolder(kvBinding);
    }
    ItemNetworkCaptureDetailJsonTextBinding jsonTextBinding =
        ItemNetworkCaptureDetailJsonTextBinding.inflate(inflater, parent, false);
    return new JsonTextViewHolder(jsonTextBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    int viewType = getItemViewType(position);
    if (viewType == KEY_VALUE) {
      KeyValue item = (KeyValue) list.get(position);
      KeyValueViewHolder viewHolder = (KeyValueViewHolder) holder;
      viewHolder.binding.tvTitle.setText(item.getKey());
      if (TextUtils.isEmpty(item.getValue())) {
        viewHolder.binding.tvContent.setVisibility(View.GONE);
        viewHolder.binding.divider.setVisibility(View.GONE);
      } else {
        viewHolder.binding.tvContent.setVisibility(View.VISIBLE);
        viewHolder.binding.divider.setVisibility(View.VISIBLE);
        viewHolder.binding.tvContent.setText(item.getValue());
      }
    } else {
      String jsonText = (String) list.get(position);
      JsonTextViewHolder viewHolder = (JsonTextViewHolder) holder;
      viewHolder.binding.tvJsonText.setText(jsonText);
    }
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  @SuppressLint("NotifyDataSetChanged")
  public void setList(List<Object> list) {
    this.list.clear();
    if (list != null) {
      this.list.addAll(list);
    }
    notifyDataSetChanged();
  }

  static class KeyValueViewHolder extends RecyclerView.ViewHolder {
    final ItemNetworkCaptureDetailKvBinding binding;

    public KeyValueViewHolder(@NonNull ItemNetworkCaptureDetailKvBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
      this.binding.tvContent.setMovementMethod(LinkMovementMethod.getInstance());
    }
  }

  class JsonTextViewHolder extends RecyclerView.ViewHolder {
    final ItemNetworkCaptureDetailJsonTextBinding binding;

    public JsonTextViewHolder(@NonNull ItemNetworkCaptureDetailJsonTextBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
      itemView.setOnClickListener(v -> {
        if (mOnClickJsonTextListener != null) {
          mOnClickJsonTextListener.onClickJsonText();
        }
      });
    }
  }

  public void setOnClickJsonTextListener(OnClickJsonTextListener listener) {
    mOnClickJsonTextListener = listener;
  }

  public interface OnClickJsonTextListener {
    void onClickJsonText();
  }
}
