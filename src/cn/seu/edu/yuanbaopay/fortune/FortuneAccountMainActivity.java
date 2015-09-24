package cn.seu.edu.yuanbaopay.fortune;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yuanbaopay.R;

public class FortuneAccountMainActivity extends Activity implements android.view.View.OnClickListener{
	private LinearLayout llCharge;
	private LinearLayout llToBank;
	private TextView tv_title;
	private TextView tvBack;
	private SharedPreferences pref;
	private TextView tvBalance;
	private TextView tvMMBalance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_fortune_account_main);
		initView();
	}
	
	private void initView() {
		llCharge=(LinearLayout)findViewById(R.id.layout_charge_fortune);
		llCharge.setOnClickListener(this);
		llToBank=(LinearLayout)findViewById(R.id.layout_tobank_fortune);
		llToBank.setOnClickListener(this);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("ÃÌº”“¯––ø®");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		tvBalance=(TextView)findViewById(R.id.tv_accountbalance_fortuneaccountmain);
		tvBalance.setText(pref.getString("balance", "0.0"));
		tvMMBalance=(TextView)findViewById(R.id.money_mm);
		tvMMBalance.setText(pref.getString("mmbalance", "12.34"));
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.layout_charge_fortune:
			Intent i_charge=new Intent(FortuneAccountMainActivity.this,FortuneBalanceChargeActivity.class);
			startActivity(i_charge);
			break;
		case R.id.layout_tobank_fortune:
			Intent i_tobank=new Intent(FortuneAccountMainActivity.this,FortuneBalanceToBankActivity.class);
			startActivity(i_tobank);
			break;
		case R.id.title_back:
			finish();
			break;
		}
	}

}
