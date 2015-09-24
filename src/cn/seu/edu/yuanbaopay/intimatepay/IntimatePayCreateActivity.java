package cn.seu.edu.yuanbaopay.intimatepay;

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
import android.widget.Toast;
import cn.seu.edu.yuanbaopay.managemoney.ManageMoneyActivity;
import cn.seu.edu.yuanbaopay.managemoney.ManageMoneyInActivity;
import cn.seu.edu.yuanbaopay.paypassword.DialogWidget;
import cn.seu.edu.yuanbaopay.paypassword.PayPasswordView;

import com.example.yuanbaopay.R;
import com.example.yuanbaopay.R.color;

public class IntimatePayCreateActivity extends Activity implements
		OnClickListener {
	private Button btnToCheck;
	private TextView tvTitle;
	private RelativeLayout rlTitle;
	private TextView tvBack;
	private DialogWidget mDialogWidget;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_intimatepay_create);
		initView();
	}

	private void initView() {
		btnToCheck = (Button) findViewById(R.id.btn_to_inputkey_intipay);
		btnToCheck.setOnClickListener(this);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("亲密付");
		tvTitle.setTextColor(Color.WHITE);
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		tvBack.setTextColor(Color.WHITE);
		rlTitle = (RelativeLayout) findViewById(R.id.title_layout);
		rlTitle.setBackgroundColor(Color.rgb(227, 155, 138));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_to_inputkey_intipay:
			mDialogWidget = new DialogWidget(IntimatePayCreateActivity.this,
					getDecorViewDialog());
			mDialogWidget.show();
			break;
		case R.id.title_back:
			finish();
			break;
		default:
			break;
		}
	}
	protected View getDecorViewDialog() {
		// TODO Auto-generated method stub
		return PayPasswordView
				.getInstance(
						"请输入支付密码","为xxx开通亲密付","确认开通",
						this,
						new cn.seu.edu.yuanbaopay.paypassword.PayPasswordView.OnPayListener() {

							@Override
							public void onSurePay(String password) {
								// TODO Auto-generated method stub
								mDialogWidget.dismiss();
								mDialogWidget = null;
								Intent i = new Intent(
										IntimatePayCreateActivity.this,
										IntimatePaySuccessActivity.class);
								startActivity(i);
							}

							@Override
							public void onCancelPay() {
								// TODO Auto-generated method stub
								mDialogWidget.dismiss();
								mDialogWidget = null;
								Toast.makeText(getApplicationContext(), "取消开通",
										Toast.LENGTH_SHORT).show();

							}
						}).getView();
	}

}
