package cn.seu.edu.yuanbaopay.fortune;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import cn.seu.edu.yuanbaopay.paypassword.DialogWidget;
import cn.seu.edu.yuanbaopay.paypassword.PayPasswordView;

import com.example.yuanbaopay.R;

public class FortuneBalanceToBankActivity extends Activity implements
OnClickListener{
	private Button btnNext;
	private DialogWidget mDialogWidget;
	private TextView CB;
	private PopupWindow bankWindow;
	private LinearLayout llChooseBank;
	private TextView tvChoosenBank;
	private TextView tv_title;
	private TextView tvBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_fortune_balance_tobank);
		initView();
	}

	private void initView() {
		btnNext=(Button)findViewById(R.id.btn_ensure_tobank);
		btnNext.setOnClickListener(this);
		llChooseBank=(LinearLayout)findViewById(R.id.layout_choose_bank_tobank);
		llChooseBank.setOnClickListener(this);
		tvChoosenBank=(TextView)findViewById(R.id.tv_choosebank_tobank);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("提现");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_ensure_tobank:
			mDialogWidget = new DialogWidget(FortuneBalanceToBankActivity.this,
					getDecorViewDialog());
			mDialogWidget.show();
			break;
		case R.id.layout_choose_bank_tobank:
			showWindow();
			break;
		case R.id.CB_choose_bank:
			tvChoosenBank.setText(CB.getText().toString());
			break;
		case R.id.title_back:
			finish();
			break;
		}
	}
	protected View getDecorViewDialog() {
		// TODO Auto-generated method stub
		return PayPasswordView
				.getInstance(
						"请输入支付密码",
						"余额转出",
						"确认",
						this,
						new cn.seu.edu.yuanbaopay.paypassword.PayPasswordView.OnPayListener() {

							@Override
							public void onSurePay(String password) {
								// TODO Auto-generated method stub
								mDialogWidget.dismiss();
								mDialogWidget = null;
								AlertDialog.Builder dialog=new AlertDialog.Builder(FortuneBalanceToBankActivity.this,R.style.dialog);
								dialog.setMessage("转出申请成功，2小时内到账");
								dialog.setCancelable(false);
								dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										Intent i=new Intent(FortuneBalanceToBankActivity.this,FortuneAccountMainActivity.class);
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
	private void showWindow() {
		View contentView = LayoutInflater.from(getApplicationContext())
			    .inflate(R.layout.scroll_choose_bank, null);
		bankWindow=new PopupWindow();
		bankWindow.setContentView(contentView);
		bankWindow.setWidth(LayoutParams.FILL_PARENT);
		bankWindow.setHeight(300);
		bankWindow.setBackgroundDrawable(new BitmapDrawable());
		bankWindow.setOutsideTouchable(true);
		bankWindow.showAtLocation(this.findViewById(R.id.tv_title), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
		CB=(TextView)contentView.findViewById(R.id.CB_choose_bank);
		CB.setOnClickListener(this);
	}

	
}
