package cn.seu.edu.yuanbaopay.intimatepay;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yuanbaopay.R;
import com.example.yuanbaopay.R.color;

public class IntimatePayMainActivity extends Activity implements
		OnClickListener {
	private Button btnToCreateIntimate;
	private TextView tvTitle;
	private RelativeLayout rlTitle;
	private TextView tvBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_intimatepay);
		initView();
	}

	private void initView() {
		btnToCreateIntimate = (Button) findViewById(R.id.btn_to_create_intipay);
		btnToCreateIntimate.setOnClickListener(this);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("Ç×ÃÜ¸¶");
		tvTitle.setTextColor(Color.WHITE);
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		tvBack.setTextColor(Color.WHITE);
		rlTitle = (RelativeLayout) findViewById(R.id.title_layout);
		rlTitle.setBackgroundColor(Color.rgb(227, 155, 138));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_to_create_intipay:
			Intent i = new Intent(IntimatePayMainActivity.this,
					IntimatePayCreateActivity.class);
			startActivity(i);
			break;
		case R.id.title_back:
			finish();
			break;
		default:
			break;

		}
	}
}
