package cn.seu.edu.yuanbaopay.transfer;

import android.app.Activity;
import android.os.Bundle;


import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.example.yuanbaopay.R;

public class TransferLimitActivity extends Activity {
	private TextView tvTitle;
	private TextView tvBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_transfer_limite);
		initView();
	}
	private void initView() {
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("ÏÞ¶îËµÃ÷");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}


}
