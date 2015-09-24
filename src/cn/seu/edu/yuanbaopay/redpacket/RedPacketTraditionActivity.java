package cn.seu.edu.yuanbaopay.redpacket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.seu.edu.yuanbaopay.paypassword.DialogWidget;
import cn.seu.edu.yuanbaopay.paypassword.PayPasswordView;










import com.example.yuanbaopay.R;
import com.example.yuanbaopay.R.color;

public class RedPacketTraditionActivity extends Activity implements
		OnClickListener{
	private TextView tvTitle;
	private RelativeLayout rlTitle;
	private TextView tvBack;
	private Button btnEnsure;
	private DialogWidget mDialogWidget;
	private EditText trname;
	private EditText trmoney;
	//存储
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_redpacket_tradition);
		pref=PreferenceManager.getDefaultSharedPreferences(RedPacketTraditionActivity.this);
		editor=pref.edit();
		initView();
	}

	private void initView() {
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("传统红包");
		tvTitle.setTextColor(Color.WHITE);
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		tvBack.setTextColor(Color.WHITE);
		rlTitle = (RelativeLayout) findViewById(R.id.layout_title_redpacket_tradition);
		rlTitle.setBackgroundColor(Color.rgb(190, 25, 27));
		btnEnsure=(Button)findViewById(R.id.btn_ensure_traditionpacket);
		btnEnsure.setOnClickListener(this);
		trname=(EditText)findViewById(R.id.edTxt_accpeter_traditionpacket);
		trmoney=(EditText)findViewById(R.id.edTxt_redpacketamount_traditionpacket);
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.btn_ensure_traditionpacket:
			mDialogWidget = new DialogWidget(RedPacketTraditionActivity.this,
					getDecorViewDialog());
			mDialogWidget.show();
			break;

		default:
			break;
		}
	}
	
	
	
	
	
	
	protected View getDecorViewDialog() {
		// TODO Auto-generated method stub
		return PayPasswordView
				.getInstance(
						"请输入支付密码",
						"红包金额：xx元",
						"确认",
						this,
						new cn.seu.edu.yuanbaopay.paypassword.PayPasswordView.OnPayListener() {

							@Override
							public void onSurePay(String password) {
								// TODO Auto-generated method stub
								mDialogWidget.dismiss();
								mDialogWidget = null;
								AlertDialog.Builder dialog=new AlertDialog.Builder(RedPacketTraditionActivity.this,R.style.dialog);
								dialog.setMessage("红包已发出！");
								dialog.setCancelable(false);
								dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										
										
										editor.putString("traditiontype", "传统红包");
										editor.putString("traditionname", trname.getText().toString());
										editor.putString("traditionmoney", trmoney.getText().toString());
										editor.commit();
										Intent i=new Intent(RedPacketTraditionActivity.this,RedPacketMainActivity.class);
										startActivity(i);
									}
								});
								dialog.show();
							}

							@Override
							public void onCancelPay() {
								// TODO Auto-generated method stub
								mDialogWidget.dismiss();
								mDialogWidget = null;
								Toast.makeText(getApplicationContext(), "取消红包",
										Toast.LENGTH_SHORT).show();

							}
						}).getView();
	}
	
	
	
}
