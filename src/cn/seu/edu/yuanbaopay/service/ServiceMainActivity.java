package cn.seu.edu.yuanbaopay.service;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.example.yuanbaopay.R;

public class ServiceMainActivity extends Activity
{
	private TextView tvTitle;
	private TextView tvBack;
	protected static final String TAG = "MainActivity";
	private int[] mImgIds;
	private MyJazzyViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_service_main);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("µ÷Ï·¿Í·þ");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		mImgIds = new int[] { R.drawable.huangweicong, R.drawable.luxinghan, R.drawable.mengbo,
				R.drawable.mengjiaqi,R.drawable.wuyutian,R.drawable.xiaotong,R.drawable.zhangweidong };
		mViewPager = (MyJazzyViewPager) findViewById(R.id.id_viewPager);
		mViewPager.setAdapter(new PagerAdapter()
		{

			@Override
			public boolean isViewFromObject(View arg0, Object arg1)
			{
				return arg0 == arg1;
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object)
			{
				container.removeView((View) object);
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position)
			{
				ImageView imageView = new ImageView(ServiceMainActivity.this);
				imageView.setImageResource(mImgIds[position]);
				imageView.setScaleType(ScaleType.CENTER_CROP);
				container.addView(imageView);
				mViewPager.setObjectForPosition(imageView, position);
				return imageView;
			}

			@Override
			public int getCount()
			{
				return mImgIds.length;
			}
		});

	}

}
