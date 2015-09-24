package cn.seu.edu.yuanbaopay.transfer;

import com.example.yuanbaopay.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TransferForthActivity extends Activity implements OnClickListener {
	private TextView tvTitle;
	private TextView tvBack;
	private Button btnNext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_transfer_4);
		initView();
	}

	private void initView() {
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("в╙ук");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		btnNext=(Button)findViewById(R.id.btn_returntomain);
		btnNext.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.btn_returntomain:
			Intent i_main=new Intent(TransferForthActivity.this,TransferFirstActivity.class);
			startActivity(i_main);
			break;
		default:
			break;
		}
	}

}
