package cn.seu.edu.yuanbaopay.transfer;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import cn.seu.edu.yuanbaopay.managemoney.ManageMoneyActivity;
import cn.seu.edu.yuanbaopay.managemoney.ManageMoneyInActivity;
import cn.seu.edu.yuanbaopay.managemoney.ManageMoneyOutActivity;
import cn.seu.edu.yuanbaopay.paypassword.DialogWidget;
import cn.seu.edu.yuanbaopay.paypassword.PayPasswordView;

import com.example.yuanbaopay.R;




//后端
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.text.Editable;
import android.util.Log;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.ProgressDialog;//后端完毕

public class TransferToBankActivity extends Activity implements OnClickListener{
	private TextView tv_title;
	private TextView tvBack;
	private Button btnEnsure;
	private DialogWidget mDialogWidget;
	private TextView tvLimit;
	private PopupWindow bankWindow;
	private TextView CB;
	private LinearLayout llChooseBank;
	private TextView tvChosenBank;
	
	/*
	 * 对话和返回值，数据库必备
	 */
	private ProgressDialog pd;
	private String ret;
	private EditText transfertobankname;
	private EditText transfertobanknum;
	private EditText transfertobankaccount;//转账金额
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_transfer_tobank);
		initView();
	}
	private void initView() {
		tvLimit=(TextView)findViewById(R.id.tv_explainlimite_tobank);
		tvLimit.setOnClickListener(this);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("转出到银行卡");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		btnEnsure=(Button)findViewById(R.id.btn_ensure_tobank);
		btnEnsure.setOnClickListener(this);
		llChooseBank=(LinearLayout)findViewById(R.id.layout_choosecard_transfer);
		llChooseBank.setOnClickListener(this);
		tvChosenBank=(TextView)findViewById(R.id.tv_choosebank_tobank);
		tvChosenBank.setOnClickListener(this);//银行类型
//		transfertobankname=(EditText)findViewById(R.id.edtTxt_name_tobank);
//		transfertobankname.setOnClickListener(this);
//		transfertobanknum=(EditText)findViewById(R.id.edtTet_cardnum_tobank);
//		transfertobanknum.setOnClickListener(this);
//		transfertobankname=(EditText)findViewById(R.id.edtTxt_money_tobank);
//		transfertobankname.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.btn_ensure_tobank:
			mDialogWidget = new DialogWidget(TransferToBankActivity.this,
					getDecorViewDialog());
			mDialogWidget.show();
			break;
			
			
		case R.id.tv_explainlimite_tobank:
			Intent i=new Intent(TransferToBankActivity.this,TransferLimitActivity.class);
			startActivity(i);
			break;
		case R.id.layout_choosecard_transfer:
			showWindow();
			break;
		case R.id.CB_choose_bank:
			tvChosenBank.setText(CB.getText().toString());
			break;
		}
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
		
		
		 
	    

	
	protected View getDecorViewDialog() {
		// TODO Auto-generated method stub
		return PayPasswordView
				.getInstance(
						"请输入支付密码","转出到银行卡5000元","确认转出",
						this,
						new cn.seu.edu.yuanbaopay.paypassword.PayPasswordView.OnPayListener() {

							@Override
							public void onSurePay(String password) {
								// TODO Auto-generated method stub
								mDialogWidget.dismiss();
								mDialogWidget = null;
								Intent i = new Intent(
										TransferToBankActivity.this,
										TransferForthActivity.class);
								startActivity(i);
							}

							@Override
							public void onCancelPay() {
								// TODO Auto-generated method stub
								mDialogWidget.dismiss();
								mDialogWidget = null;
								Toast.makeText(getApplicationContext(), "取消转出",
										Toast.LENGTH_SHORT).show();

							}
						}).getView();
	}

}
