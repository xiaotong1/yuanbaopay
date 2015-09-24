package cn.seu.edu.yuanbaopay.redpacket;

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

public class RedPacketMainActivity extends Activity implements OnClickListener{
	private Button btnLucky;
	private Button btnTraditional;
	private Button btnIntimate;
	private TextView tvTitle;
	private RelativeLayout rlTitle;
	private TextView tvBack;
	private TextView tvMyPacket;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_redpacket);
		initView();
	}

	private void initView() {
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("ºì°ü");
		tvTitle.setTextColor(Color.WHITE);
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		tvBack.setTextColor(Color.WHITE);
		rlTitle = (RelativeLayout) findViewById(R.id.layout_title_mypacket);
		rlTitle.setBackgroundColor(Color.rgb(190, 25, 27));
		btnLucky = (Button) findViewById(R.id.btn_redpacket_tolucky);
		btnLucky.setOnClickListener(this);
		btnTraditional = (Button) findViewById(R.id.btn_redpacket_totradition);
		btnTraditional.setOnClickListener(this);
		btnIntimate = (Button) findViewById(R.id.btn_redpacket_tointimate);
		btnIntimate.setOnClickListener(this);
		tvMyPacket=(TextView)findViewById(R.id.tv_title_mypacket);
		tvMyPacket.setTextColor(Color.WHITE);
		tvMyPacket.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_redpacket_tointimate:
			Intent i_intimate = new Intent(RedPacketMainActivity.this,
					RedPacketIntimateActivity.class);
			startActivity(i_intimate);
			break;
		case R.id.btn_redpacket_tolucky:
			Intent i_lucky = new Intent(RedPacketMainActivity.this,
					RedPacketLuckyActivity.class);
			startActivity(i_lucky);
			break;
		case R.id.btn_redpacket_totradition:
			Intent i_tradition = new Intent(RedPacketMainActivity.this,
					RedPacketTraditionActivity.class);
			startActivity(i_tradition);
			break;
		case R.id.title_back:
			finish();
			break;
		case R.id.tv_title_mypacket:
			Intent i_mypacket=new Intent(RedPacketMainActivity.this,MypacketMainActivity.class);
			startActivity(i_mypacket);
			break;
		default:
			break;
		}
	}

}
