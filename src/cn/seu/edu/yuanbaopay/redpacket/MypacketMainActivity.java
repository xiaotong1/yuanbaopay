package cn.seu.edu.yuanbaopay.redpacket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;













import com.example.yuanbaopay.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MypacketMainActivity extends FragmentActivity implements OnClickListener{
	
	private TextView tvTitle;
	private TextView tvBack;
	private ViewPager viewPager;
	private FragmentPagerAdapter adapter;
	private List<Fragment>views;
	private List<String>titles;
	private RelativeLayout rlTitle;
	private PagerTabStrip tab;
	ListView list;
	String type[] = new String[]{
			"知心红包","传统红包","知心红包"
	};
	String people[] = new String[]{
			"tia","dongge","nihao"
	};
	String money[] = new String[]{
			"8","8","8"
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_redpacket_mypacket_main);	 
	    //这里是滑动板块
	        initView();
			initViewPager();
	}
	
	
	private void initView() {
		rlTitle = (RelativeLayout) findViewById(R.id.title_layout);
		rlTitle.setBackgroundColor(Color.rgb(190, 25, 27));
		tvTitle=(TextView)findViewById(R.id.tv_title);
		tvTitle.setText("我的红包");
		tvTitle.setTextColor(Color.WHITE);
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
	}
	
	private void initViewPager() {
		viewPager=(ViewPager)findViewById(R.id.viewpager);
		views=new ArrayList<Fragment>();
		titles=new ArrayList<String>();
		titles.add("我收到的红包");
		titles.add("我发出的红包");
		tab=(PagerTabStrip)findViewById(R.id.tab);
		ReceiveFragment receive=new ReceiveFragment();
		GiveFragment give=new GiveFragment();
		views.add(receive);
		views.add(give);
		adapter =new FragmentPagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				return views.size();
			}
			
			@Override
			public Fragment getItem(int arg0) {
				return views.get(arg0);
			}
			
			@Override
			public CharSequence getPageTitle(int position) {
				return titles.get(position);
			}
		};
		viewPager.setAdapter(adapter);
	}
	

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		default:
			break;
		}
		
	}

}

 
