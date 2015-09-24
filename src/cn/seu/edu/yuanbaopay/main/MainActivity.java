package cn.seu.edu.yuanbaopay.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import cn.seu.edu.yuanbaopay.bill.BillActivity;
import cn.seu.edu.yuanbaopay.fortune.FortuneMainActivity;
import cn.seu.edu.yuanbaopay.info.InfoActivity;
import cn.seu.edu.yuanbaopay.intimatepay.IntimatePayMainActivity;
import cn.seu.edu.yuanbaopay.intimatepay.IntimatePayWelcomeActivity;
import cn.seu.edu.yuanbaopay.managemoney.ManageMoneyActivity;
import cn.seu.edu.yuanbaopay.note.NoteActivity;
import cn.seu.edu.yuanbaopay.pay.PayActivity;
import cn.seu.edu.yuanbaopay.redpacket.RedPacketMainActivity;
import cn.seu.edu.yuanbaopay.service.ServiceMainActivity;
import cn.seu.edu.yuanbaopay.transfer.TransferFirstActivity;

import com.example.yuanbaopay.R;

public class MainActivity extends Activity implements OnItemClickListener,
		OnClickListener {
	private TextView tvTitle;
	private GridView gridView;
	private SimpleAdapter adapter;
	private List dataList;
	private ImageView portrait;
	private TextView tvFortune;
	private TextView tvPacket;
	private ViewPager viewPager;
	private ArrayList<View> pageview;
	private ImageView imageView;
	private ImageView[] imageViews;
	private ViewGroup group;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		initGridView();
		initViewPager();
		
	}

	private void initViewPager() {

		viewPager = (ViewPager) findViewById(R.id.viewPager_ad);

		LayoutInflater inflater = getLayoutInflater();
		View view1 = inflater.inflate(R.layout.item01, null);
		View view2 = inflater.inflate(R.layout.item02, null);
		View view3 = inflater.inflate(R.layout.item03, null);
		View view4 = inflater.inflate(R.layout.item04, null);

		pageview = new ArrayList<View>();
		pageview.add(view1);
		pageview.add(view2);
		pageview.add(view3);
		pageview.add(view4);

		// 数据适配器
		final PagerAdapter mPagerAdapter = new PagerAdapter() {

			@Override
			// 获取当前窗体界面数
			public int getCount() {
				// TODO Auto-generated method stub
				return pageview.size();
			}

			@Override
			// 断是否由对象生成界面
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}

			// 是从ViewGroup中移出当前View
			public void destroyItem(View arg0, int arg1, Object arg2) {
				((ViewPager) arg0).removeView(pageview.get(arg1));
			}

			// 返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
			public Object instantiateItem(View arg0, int arg1) {
				((ViewPager) arg0).addView(pageview.get(arg1));
				return pageview.get(arg1);
			}

		};
		// 绑定适配器

		viewPager.setAdapter(mPagerAdapter);

		group = (ViewGroup) findViewById(R.id.viewGroup_ad);

		// 有多少张图就有多少个点点
		imageViews = new ImageView[pageview.size()];
		for (int i = 0; i < pageview.size(); i++) {
			imageView = new ImageView(MainActivity.this);
			imageView.setLayoutParams(new LayoutParams(20, 20));
			imageView.setPadding(20, 0, 20, 0);
			imageViews[i] = imageView;

			// 默认第一张图显示为选中状态
			if (i == 0) {
				imageViews[i].setBackgroundResource(R.drawable.cash_gift);
			} else {
				imageViews[i].setBackgroundResource(R.drawable.phone);
			}

			group.addView(imageViews[i]);
		}

		// pageView监听器
		class GuidePageChangeListener implements OnPageChangeListener {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			// 如果切换了，就把当前的点点设置为选中背景，其他设置未选中背景
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				for (int i = 0; i < imageViews.length; i++) {
					imageViews[arg0]
							.setBackgroundResource(R.drawable.cash_gift);
					if (arg0 != i) {
						imageViews[i].setBackgroundResource(R.drawable.phone);
					}
				}

			}

		}
		// 绑定监听事件
		viewPager.setOnPageChangeListener(new GuidePageChangeListener());

		// Timer timer = null;
		// timer.schedule(new TimerTask() {
		//
		// @Override
		// public void run() {
		// int count = mPagerAdapter.getCount();
		// if (count > 1) { // 多于1个，才循环
		// int index = viewPager.getCurrentItem();
		// index = (index + 1) % count;
		// viewPager.setCurrentItem(index, true);
		// }
		// }
		// },1500 ,1500);
	}

	private void initGridView() {
		gridView = (GridView) findViewById(R.id.gridView);
		dataList = new ArrayList<Map<String, Object>>();
		adapter = new SimpleAdapter(this, getData(), R.layout.item_grid,
				new String[] { "pic", "name" }, new int[] { R.id.ItemImage,
						R.id.ItemText });
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);
	}

	private void initView() {
		tvFortune = (TextView) findViewById(R.id.tv_fortune_main);
		tvFortune.setOnClickListener(this);
		tvPacket = (TextView) findViewById(R.id.tv_packet_main);
		tvPacket.setOnClickListener(this);
		tvTitle = (TextView) findViewById(R.id.tv_title_main);
		tvTitle.setText("荷包");
		tvTitle.setTextColor(Color.WHITE);
		portrait = (ImageView) findViewById(R.id.portrait_main);
		portrait.setOnClickListener(this);
	}

	private List<Map<String, Object>> getData() {

		int[] drawable = { R.drawable.mainicon_mm,
				R.drawable.mainicon_transfer, R.drawable.mainicon_pay,
				R.drawable.mainicon_redpacket, R.drawable.mainicon_note,
				R.drawable.mainicon_bill, R.drawable.mainicon_service,
				R.drawable.mainicon_intipay, };
		String[] iconName = { "余额宝", "转账", "付款", "红包", "记账", "账单", "客服", "亲密付" };
		for (int i = 0; i < drawable.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pic", drawable[i]);
			map.put("name", iconName[i]);
			dataList.add(map);
		}
		return dataList;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long id) {
		switch (position) {
		case 0:
			Intent i_mm = new Intent(MainActivity.this,
					ManageMoneyActivity.class);
			startActivity(i_mm);
			break;
		case 1:
			Intent i_transfer = new Intent(MainActivity.this,
					TransferFirstActivity.class);
			startActivity(i_transfer);
			break;
		case 2:
			Intent i_pay = new Intent(MainActivity.this, PayActivity.class);
			startActivity(i_pay);
			break;
		case 3:
			Intent i_redpacket = new Intent(MainActivity.this,
					RedPacketMainActivity.class);
			startActivity(i_redpacket);
			break;
		case 4:
			Intent i_note = new Intent(MainActivity.this, NoteActivity.class);
			startActivity(i_note);
			break;
		case 5:
			Intent i_bill = new Intent(MainActivity.this, BillActivity.class);
			startActivity(i_bill);
			break;
		case 6:
			Intent i_service = new Intent(MainActivity.this,
					ServiceMainActivity.class);
			startActivity(i_service);
			break;
		case 7:
			Intent i_intimate = new Intent(MainActivity.this,
					IntimatePayWelcomeActivity.class);
			startActivity(i_intimate);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.portrait_main:
			Intent i_info = new Intent(MainActivity.this, InfoActivity.class);
			startActivity(i_info);
			break;
		case R.id.tv_packet_main:
			break;
		case R.id.tv_fortune_main:
			Intent i_fortune = new Intent(MainActivity.this,
					FortuneMainActivity.class);
			startActivity(i_fortune);
			break;

		default:
			break;
		}
	}

}
