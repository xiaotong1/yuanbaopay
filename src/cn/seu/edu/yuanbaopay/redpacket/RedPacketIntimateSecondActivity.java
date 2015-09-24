package cn.seu.edu.yuanbaopay.redpacket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.seu.edu.yuanbaopay.main.LoginActivity;
import cn.seu.edu.yuanbaopay.main.RegisterCreatepwdActivity;
import cn.seu.edu.yuanbaopay.managemoney.ManageMoneyActivity;
import cn.seu.edu.yuanbaopay.managemoney.ManageMoneyInActivity;
import cn.seu.edu.yuanbaopay.paypassword.DialogWidget;
import cn.seu.edu.yuanbaopay.paypassword.PayPasswordView;





import com.example.yuanbaopay.R;
import com.example.yuanbaopay.R.color;

public class RedPacketIntimateSecondActivity extends Activity implements
		OnClickListener {
	private TextView tvTitle;
	private RelativeLayout rlTitle;
	private TextView tvBack;
	private Button btnEnsure;
	private DialogWidget mDialogWidget;
	
	/*
	 * 对话和返回值，数据库必备
	 */
	
	private EditText redpacketquestion;
	private EditText redpacketanswer1;
	private EditText redpacketanswer2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_redpacket_intimate_2);
		initView();

	}

	private void initView() {
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("知心红包");
		tvTitle.setTextColor(Color.WHITE);
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		tvBack.setTextColor(Color.WHITE);
		rlTitle = (RelativeLayout) findViewById(R.id.layout_title_redpacket_intimate2);
		rlTitle.setBackgroundColor(Color.rgb(190, 25, 27));
		btnEnsure=(Button)findViewById(R.id.btn_ensure_intimate_packet);
		btnEnsure.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.btn_ensure_intimate_packet:
			mDialogWidget = new DialogWidget(RedPacketIntimateSecondActivity.this,
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
								AlertDialog.Builder dialog=new AlertDialog.Builder(RedPacketIntimateSecondActivity.this,R.style.dialog);
								dialog.setMessage("红包已发出！");
								dialog.setCancelable(false);
								dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										Intent i=new Intent(RedPacketIntimateSecondActivity.this,RedPacketMainActivity.class);
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
