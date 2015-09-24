package cn.seu.edu.yuanbaopay.redpacket;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yuanbaopay.R;

public class RedPacketIntimateActivity extends Activity implements
		OnClickListener {
	private TextView tvTitle;
	private RelativeLayout rlTitle;
	private Button btnNext;
	private TextView tvBack;
	private EditText etName;
	private EditText etMoney;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_redpacket_intimate);
		initView();
	}

	private void initView() {
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("知心红包");
		tvTitle.setTextColor(Color.WHITE);
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		tvBack.setTextColor(Color.WHITE);
		rlTitle = (RelativeLayout) findViewById(R.id.layout_title_redpacket_intimate);
		rlTitle.setBackgroundColor(Color.rgb(190, 25, 27));
		btnNext = (Button) findViewById(R.id.btn_to_intimate_redpacketintimate1);
		btnNext.setOnClickListener(this);
		etName=(EditText)findViewById(R.id.edTxt_accpeter_redpacketintimate1);
		etMoney=(EditText)findViewById(R.id.edTxt_redpacketamount_redpacketintimate1);
			
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_to_intimate_redpacketintimate1:
			pref=PreferenceManager.getDefaultSharedPreferences(RedPacketIntimateActivity.this);
			editor=pref.edit();
			editor.putString("intitype", "知心红包");
			editor.putString("intiname", etName.getText().toString());
			editor.putString("intimoney", etMoney.getText().toString());
			editor.commit();	
			Intent i = new Intent(RedPacketIntimateActivity.this,
					RedPacketIntimateSecondActivity.class);
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
