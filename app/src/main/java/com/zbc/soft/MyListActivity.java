package com.zbc.soft;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.app.ListActivity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyListActivity extends ListActivity implements OnScrollListener {
	private ListView listView;
	private int visibleLastIndex = 0; // 最后的可视项索引
	private int visibleItemCount; // 当前窗口可见项总数
	private ListViewAdapter adapter;
	private View loadMoreView;
	private TextView loadMoreButton;
	private Handler handler = new Handler();
	private Context context = null;
	private ImageView spaceshipImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_list);
		loadMoreView = getLayoutInflater().inflate(R.layout.load_more, null);
		loadMoreButton = (TextView) loadMoreView
				.findViewById(R.id.loadMoreButton);
		listView = getListView(); // 获取id是list的ListView
		context = this;
		// LayoutInflater inflater = LayoutInflater.from(context);
		// View v = inflater.inflate(R.layout.load_more, null);
		spaceshipImage = (ImageView) loadMoreView.findViewById(R.id.loadImg);
		listView.addFooterView(loadMoreView); // 设置列表底部视图
		initAdapter();
		setListAdapter(adapter); // 自动为id是list的ListView设置适配器
		listView.setOnScrollListener(this); // 添加滑动监听
		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.layout.loading_animation);
		spaceshipImage.setAnimation(hyperspaceJumpAnimation);
		hyperspaceJumpAnimation.start();
	}

	/**
	 * 初始化适配器
	 */
	private void initAdapter() {
		ArrayList<String> items = new ArrayList<String>();
		for (int i = 0; i < 15; i++) {
			items.add(String.valueOf(i + 1));
		}
		adapter = new ListViewAdapter(this, items);
	}

	/**
	 * 滑动时被调用
	 */
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
	}

	/**
	 * 滑动状态改变时被调用
	 */
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int itemsLastIndex = adapter.getCount() - 1; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& visibleLastIndex == lastIndex) {
			// 如果是自动加载,可以在这里放置异步加载数据的代码
			loadMoreButton.setText("loading...");
			Toast.makeText(MyListActivity.this, "获取成功", Toast.LENGTH_LONG)
					.show();
			loadData();

			adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
			listView.setSelection(visibleLastIndex - visibleItemCount + 1); // 设置选中项
		}
	}

	/**
	 * 点击按钮事件
	 * 
	 * @param view
	 */
	public void loadMore(View view) {

		// 使用ImageView显示动画

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {

				loadData();
				adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				listView.setSelection(visibleLastIndex - visibleItemCount + 1); // 设置选中项
				loadMoreButton.setText("load more"); // 恢复按钮文字
			}
		}, 2000);
	}

	/**
	 * 模拟加载数据
	 */
	private void loadData() {
		int count = adapter.getCount();
		for (int i = count; i < count + 10; i++) {
			adapter.addItem(String.valueOf(i + 1));
		}
	}
}
