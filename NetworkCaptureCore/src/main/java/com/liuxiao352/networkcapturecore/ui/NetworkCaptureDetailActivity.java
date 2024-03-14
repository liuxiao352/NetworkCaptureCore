package com.liuxiao352.networkcapturecore.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayoutMediator;
import com.liuxiao352.networkcapturecore.databinding.ActiviyNetworkCaptureDetailBinding;
import com.liuxiao352.networkcapturecore.viewmodel.NetworkCaptureDetailViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class NetworkCaptureDetailActivity extends AppCompatActivity {

  private static final String EXTRA_ID = "id";
  private static final String[] TABS = { "请求", "响应" };
  private ActiviyNetworkCaptureDetailBinding binding;

  public static void start(Context context, long id) {
    Intent starter = new Intent(context, NetworkCaptureDetailActivity.class);
    starter.putExtra(EXTRA_ID, id);
    context.startActivity(starter);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    long id = getIntent().getLongExtra(EXTRA_ID, -1);
    if (id < 0) {
      Toast.makeText(this, "查询的id不存在", Toast.LENGTH_SHORT).show();
      finish();
      return;
    }
    new ViewModelProvider(this).get(NetworkCaptureDetailViewModel.class)
        .fetchNetworkCaptureDetail(id);
    binding = ActiviyNetworkCaptureDetailBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    initViewPager();
  }

  private void initViewPager() {
    binding.viewPager.setAdapter(new ViewPagerAdapter(this));
    new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
      tab.setText(TABS[position]);
    }).attach();
  }

  static class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
      super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
      return NetworkCaptureDetailFragment.newInstance(
          position == 0 ? NetworkCaptureDetailFragment.REQUEST :
              NetworkCaptureDetailFragment.RESPONSE);
    }

    @Override
    public int getItemCount() {
      return TABS.length;
    }
  }
}
