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
import cn.seu.edu.yuanbaopay.main.LoginActivity;
import cn.seu.edu.yuanbaopay.paypassword.DialogWidget;
import cn.seu.edu.yuanbaopay.paypassword.PayPasswordView;








import com.example.yuanbaopay.R;
import com.example.yuanbaopay.R.color;

public class RedPacketLuckyActivity extends Activity implements OnClickListener {
	private TextView tvTitle;
	private RelativeLayout rlTitle;
	private TextView tvBack;
	private Button btnEnsure;
	private DialogWidget mDialogWidget;
	private EditText etname;
	private EditText etmoney;
	//存储
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_redpacket_lucky);
		initView();
	}

	private void initView() {
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("幸运红包");
		tvTitle.setTextColor(Color.WHITE);
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		tvBack.setTextColor(Color.WHITE);
		rlTitle = (RelativeLayout) findViewById(R.id.layout_title_redpacket_lucky);
		rlTitle.setBackgroundColor(Color.rgb(190, 25, 27));
		btnEnsure=(Button)findViewById(R.id.btn_ensure_luckypacket);
		btnEnsure.setOnClickListener(this);
		etname=(EditText)findViewById(R.id.edTxt_accpeter_luckypacket);
		etmoney=(EditText)findViewById(R.id.edTxt_redpacketamount_luckypacket);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.btn_ensure_luckypacket:
			mDialogWidget = new DialogWidget(RedPacketLuckyActivity.this,
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
								AlertDialog.Builder dialog=new AlertDialog.Builder(RedPacketLuckyActivity.this,R.style.dialog);
								dialog.setMessage("红包已发出！");
								dialog.setCancelable(false);
								dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										pref=PreferenceManager.getDefaultSharedPreferences(RedPacketLuckyActivity.this);
										editor=pref.edit();
										editor.putString("luckytype", "幸运红包");
										editor.putString("luckyname", etname.getText().toString());
										editor.putString("luckymoney", etmoney.getText().toString());
										editor.commit();
										Intent i=new Intent(RedPacketLuckyActivity.this,RedPacketMainActivity.class);
										startActivity(i);
									}
								});
								dialog.show();
//								Intent i = new Intent(
//										RedPacketLuckyActivity.this,
//										RedPacketMainActivity.class);
//								startActivity(i);
//								Toast.makeText(getApplicationContext(), "发放成功",
//										Toast.LENGTH_SHORT).show();
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
