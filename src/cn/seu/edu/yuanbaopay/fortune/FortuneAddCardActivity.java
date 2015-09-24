package cn.seu.edu.yuanbaopay.fortune;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import cn.seu.edu.yuanbaopay.main.LoginActivity;
import cn.seu.edu.yuanbaopay.main.RegisterAddCardActivity;
import cn.seu.edu.yuanbaopay.paypassword.DialogWidget;
import cn.seu.edu.yuanbaopay.paypassword.PayPasswordView;

import com.example.yuanbaopay.R;

public class FortuneAddCardActivity extends Activity implements OnClickListener{
	private TextView tv_title;
	private TextView tvBack;
	private Button btnEnsure;
	private DialogWidget mDialogWidget;
	private PopupWindow bankWindow;
	private TextView CB;
	private LinearLayout llChooseBank;
	private TextView tvChosenBank;
	private EditText edtCardNum;
	private EditText edtRealName;
	private SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_fortune_card_add);
		initView();
	}

	private void initView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("添加银行卡");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		btnEnsure=(Button)findViewById(R.id.btn_addcard_fortune);
		btnEnsure.setOnClickListener(this);
		llChooseBank=(LinearLayout)findViewById(R.id.layout_choose_bank);
		llChooseBank.setOnClickListener(this);
		tvChosenBank=(TextView)findViewById(R.id.tv_bank_fortuneaddcard);
		edtCardNum=(EditText)findViewById(R.id.edTxt_cardnum_fortuneaddcard);
		pref=PreferenceManager.getDefaultSharedPreferences(FortuneAddCardActivity.this);
		edtRealName=(EditText)findViewById(R.id.edTxt_name_fortuneaddcard);
		edtRealName.setText(pref.getString("realname", "孟嘉琪"));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.btn_addcard_fortune:
			mDialogWidget = new DialogWidget(FortuneAddCardActivity.this,
					getDecorViewDialog());
			mDialogWidget.show();
			break;
		case R.id.layout_choose_bank:
			showWindow();
			break;
		case R.id.CB_choose_bank:
			tvChosenBank.setText(CB.getText().toString());
			break;
		default:
			break;
		}
	}
	protected View getDecorViewDialog() {
		// TODO Auto-generated method stub
		return PayPasswordView
				.getInstance(
						"请输入支付密码","添加银行卡","确认绑定",
						this,
						new cn.seu.edu.yuanbaopay.paypassword.PayPasswordView.OnPayListener() {

							@Override
							public void onSurePay(String password) {
								// TODO Auto-generated method stub
								mDialogWidget.dismiss();
								mDialogWidget = null;
								AlertDialog.Builder dialog=new AlertDialog.Builder(FortuneAddCardActivity.this,R.style.dialog);
								dialog.setMessage("绑定成功");
								dialog.setCancelable(false);
								dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										Intent i=new Intent(FortuneAddCardActivity.this,FortuneCardActivity.class);
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
								Toast.makeText(getApplicationContext(), "取消绑定",
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
