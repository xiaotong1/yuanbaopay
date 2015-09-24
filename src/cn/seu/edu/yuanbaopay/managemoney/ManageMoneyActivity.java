package cn.seu.edu.yuanbaopay.managemoney;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.yuanbaopay.R;

public class ManageMoneyActivity extends Activity implements OnClickListener {
	private TextView tvTitle;
	private TextView tvBack;
	private TextView tvIn;
	private TextView tvOut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_managemoney);
		initView();
	}

	private void initView() {
		tvIn=(TextView)findViewById(R.id.tv_to_in_managemoney);
		tvIn.setOnClickListener(this);
		tvOut=(TextView)findViewById(R.id.tv_to_out_managemoney);
		tvOut.setOnClickListener(this);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("ÒæÒ×±¦");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.tv_to_in_managemoney:
			Intent i_in=new Intent(ManageMoneyActivity.this,ManageMoneyInActivity.class);
			startActivity(i_in);
			break;
		case R.id.tv_to_out_managemoney:
			Intent i_out=new Intent(ManageMoneyActivity.this,ManageMoneyOutActivity.class);
			startActivity(i_out);
			break;
		default:
			break;
		}
	}

}
