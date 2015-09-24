package cn.seu.edu.yuanbaopay.intimatepay;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yuanbaopay.R;

public class IntimatePayWelcomeActivity extends Activity implements
		OnClickListener {
	private ImageButton imgbtnNext;
	private TextView tvBack;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_intimatepay_welcome);
		initView();

	}

	private void initView() {
		
		imgbtnNext = (ImageButton) findViewById(R.id.btn_tie_intimatepay);
		imgbtnNext.setOnClickListener(this);
		tvBack=(TextView)findViewById(R.id.tv_back_welcome);
		tvBack.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_tie_intimatepay:
			Intent i = new Intent(IntimatePayWelcomeActivity.this,
					IntimatePayMainActivity.class);
			startActivity(i);
			break;
		case R.id.tv_back_welcome:
			finish();
			break;
		default:
			break;

		}
	}

}
