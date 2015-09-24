package cn.seu.edu.yuanbaopay.note;

import java.util.ArrayList;
import java.util.List;

import cn.seu.edu.yuanbaopay.transfer.TransferSecondActivity;
import cn.seu.edu.yuanbaopay.transfer.TransferThirdActivity;

import com.example.yuanbaopay.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddNoteActivity extends FragmentActivity implements OnClickListener {
	private TextView tvTitle;
	private TextView tvBack;
	private ViewPager viewPager;
	private FragmentPagerAdapter adapter;
	private List<Fragment>views;
	private List<String>titles;
	private PagerTabStrip tab;
	private Button btntoNote;
	/*
	 * 对话和返回值，数据库必备
	 */
	
	private EditText notemoneynumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_note_add);
		initView();
		initViewPager();
	}

	private void initViewPager() {
		viewPager=(ViewPager)findViewById(R.id.viewpager);
		views=new ArrayList<Fragment>();
		titles=new ArrayList<String>();
		titles.add("支出");
		titles.add("收入");
		tab=(PagerTabStrip)findViewById(R.id.tab);
		InFragment in=new InFragment();
		OutFragment out=new OutFragment();
		views.add(in);
		views.add(out);
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

	private void initView() {
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("记一笔");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		btntoNote = (Button)findViewById(R.id.btn_addmoney_noteadd);
		btntoNote.setOnClickListener(this);
		notemoneynumber = (EditText)findViewById(R.id.inputmoney_note_add);
		notemoneynumber.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.btn_addmoney_noteadd:
			Intent i = new Intent(AddNoteActivity.this, NoteActivity.class);
			startActivity(i);
			break;
			
		default:
			break;
		}
	}
	
	
	 
    
    
   

}
