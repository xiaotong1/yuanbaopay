package cn.seu.edu.yuanbaopay.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import cn.seu.edu.yuanbaopay.fortune.FortuneAddCardActivity;
import cn.seu.edu.yuanbaopay.fortune.FortuneCardActivity;
import cn.seu.edu.yuanbaopay.paypassword.DialogWidget;
import cn.seu.edu.yuanbaopay.paypassword.PayPasswordView;





//后端
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.app.ProgressDialog;


//后端完毕





import com.example.yuanbaopay.R;

public class RegisterAddCardActivity extends Activity implements OnClickListener,Runnable{
	private Button btnNext;
	private TextView tvTitle;
	private TextView tvBack;
	private DialogWidget mDialogWidget;
	private PopupWindow bankWindow;
	private TextView CB;
	private LinearLayout llChooseBank;
	
	/*
	 * 这里是会用到的必须添加的
	 */
	private ProgressDialog pd;
	private String ret;
	private TextView tvChosenBank;
	private EditText edtRealName;
	private EditText edtCardNum;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register_addcard);
		initView();
	}

	private void initView() {
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("绑定银行卡");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		btnNext=(Button)findViewById(R.id.btn_tie_addcard_register);
		btnNext.setOnClickListener(this);
		llChooseBank=(LinearLayout)findViewById(R.id.layout_bank_addcard_register);
		llChooseBank.setOnClickListener(this);
		tvChosenBank=(TextView)findViewById(R.id.tv_bank_addcard_register);
		edtRealName=(EditText)findViewById(R.id.edTxt_name_addcard_register);
		edtCardNum=(EditText)findViewById(R.id.edTxt_cardnum_addcard_register);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.title_back:
			finish();
			break;
		case R.id.btn_tie_addcard_register:
			mDialogWidget = new DialogWidget(RegisterAddCardActivity.this,
					getDecorViewDialog());
			mDialogWidget.show();
			break;
		case R.id.layout_bank_addcard_register:
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
						"请输入支付密码","添加银行卡","确认绑定",
						this,
						new cn.seu.edu.yuanbaopay.paypassword.PayPasswordView.OnPayListener() {

							@Override
							public void onSurePay(String password) {
								// TODO Auto-generated method stub
								mDialogWidget.dismiss();
								mDialogWidget = null;
								AlertDialog.Builder dialog=new AlertDialog.Builder(RegisterAddCardActivity.this,R.style.dialog);
								dialog.setMessage("绑定成功");
								dialog.setCancelable(false);
								dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										/*
										 * 后端相关，
										 * 开始交互放在点击事件里
										 */
										 //本句好像是一个圆形的进度条的代码，可以删除
										pd=ProgressDialog.show(RegisterAddCardActivity.this, "提示","Web服务调用中");
								                
										//启动线程
										new Thread(RegisterAddCardActivity.this).start();
										
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
	/*
	 * 后端代码
	 * Handler
	 * 把Activity的名字改了
	 * 第一个关闭ProgressDialog不能删除
	 * 第二行的代码改了，是自己的业务逻辑实现，如果取到数据要在本页上显示，对应设置，如果要进行相应的验证也在这里实现，反正第二行代码就是返回的值你要怎么用就怎么用。没用你返回值干嘛！
	 */
	private Handler handler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {// handler接收到消息后就会执行此方法  
        	//关闭ProgressDialog 
        	RegisterAddCardActivity.this.pd.dismiss(); 
        	
        	pref=PreferenceManager.getDefaultSharedPreferences(RegisterAddCardActivity.this);
        	editor=pref.edit();
        	editor.putString("cardnumber", edtCardNum.getText().toString());
        	editor.commit();
 
        	Intent i=new Intent(RegisterAddCardActivity.this,LoginActivity.class);
			Toast.makeText(RegisterAddCardActivity.this, "注册成功请登录！", Toast.LENGTH_SHORT).show();
			startActivity(i);
        }  
    };  
    
    /*
     * 后端代码，线程
     */
  //调用web service的线程，不能同界面主线程抢CPU运行资源
  	@Override
  	public void run() {
  		//调用Web Service，并传递给handler负责通信
      	//访问WS服务
  		String NAME_SPACE = "http://serves";
		String WDSL_LINK = "http://223.3.79.181:8080/axis2/services/UserSercve?wsdl";
          String METHOD_NAME = "getUserInfo";  
          //以上三条是不需要修改的，在服务器端查询，其中getUserInfo是自己构造的方法名
          
          //Soap Object 是  SoapSerializationEnvelope的重要组成部分 
          SoapObject request = new SoapObject(NAME_SPACE, METHOD_NAME);  
          request.addProperty("realname", edtRealName.getText().toString()); 
          //username是数据库中的字段名，tom是查询的数据引索 
          SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);  
          envelope.bodyOut = request;  
          envelope.dotNet = true;  
          envelope.setOutputSoapObject(request);  
          HttpTransportSE ht = new HttpTransportSE(WDSL_LINK); 
          try{
                  //本句必须保留。
          	ht.call("", envelope); 
          	//ret为从服务器返回的信息，即调用函数里面return信息
 
          	//发送消息
          	handler.sendEmptyMessage(1);//通知主线程，web服务调用完毕
          }
          catch(Exception e){
          	Log.v("III", e.getMessage());
          }
  		
  	}

}
