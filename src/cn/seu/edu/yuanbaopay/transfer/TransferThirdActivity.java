package cn.seu.edu.yuanbaopay.transfer;

import cn.seu.edu.yuanbaopay.paypassword.DialogWidget;
import cn.seu.edu.yuanbaopay.paypassword.PayPasswordView;

import com.example.yuanbaopay.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;








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
import android.preference.PreferenceManager;
import android.app.ProgressDialog;//后端完毕

public class TransferThirdActivity extends Activity implements OnClickListener,Runnable {
	private TextView tvTitle;
	private Button btnEnsureTransfer;
	private TextView tvBack;
	private DialogWidget mDialogWidget;
	private TextView tvLimit;
	
	/*
	 * 对话和返回值，数据库必备
	 */
	private ProgressDialog pd;
	private String ret;
	private EditText transfermoney;
	private String receivername;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_transfer_3);
		Intent i=getIntent();
		receivername=i.getStringExtra("receivername");
		pref=PreferenceManager.getDefaultSharedPreferences(TransferThirdActivity.this);
		editor=pref.edit();
		initView();
	}

	private void initView() {
		tvLimit=(TextView)findViewById(R.id.tv_explainlimite_transfer3);
		tvLimit.setOnClickListener(this);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("转账");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		btnEnsureTransfer = (Button) findViewById(R.id.btn_ensure_transfer);
		btnEnsureTransfer.setOnClickListener(this);
		transfermoney =(EditText)findViewById(R.id.edtTxt_amount_transfer3);
		transfermoney.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.btn_ensure_transfer:
//			mDialogWidget = new DialogWidget(TransferThirdActivity.this,
//					getDecorViewDialog());
//			mDialogWidget.show();
			
			/*
			 * 后端相关，
			 * 开始交互放在点击事件里
			 */
			 //本句好像是一个圆形的进度条的代码，可以删除
			pd=ProgressDialog.show(this, "提示","Web服务调用中");
	                
			//启动线程
			new Thread(this).start();
			break;
			
		case R.id.tv_explainlimite_transfer3:
			Intent i_limit=new Intent(TransferThirdActivity.this,TransferLimitActivity.class);
			startActivity(i_limit);
			break;
		default:
			break;
		}
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
        	TransferThirdActivity.this.pd.dismiss(); 
        	/*
        	 * 将输入的金额和备注通过数据库发送给转账人,然后跳到TransferForthActivity
        	 */
        	
    		editor.putString("transfermoney", transfermoney.getText().toString());
    		editor.commit();
        	Intent i=new Intent(TransferThirdActivity.this,TransferForthActivity.class);
			startActivity(i);
			

        }  
    };  
	
	protected View getDecorViewDialog() {
		// TODO Auto-generated method stub
		return PayPasswordView
				.getInstance(
						"请输入支付密码","转账给xxxxxx元","确认转账",
						this,
						new cn.seu.edu.yuanbaopay.paypassword.PayPasswordView.OnPayListener() {

							@Override
							public void onSurePay(String password) {
								// TODO Auto-generated method stub
								mDialogWidget.dismiss();
								mDialogWidget = null;
								Intent i = new Intent(
										TransferThirdActivity.this,
										TransferForthActivity.class);
								startActivity(i);
							}

							@Override
							public void onCancelPay() {
								// TODO Auto-generated method stub
								mDialogWidget.dismiss();
								mDialogWidget = null;
								Toast.makeText(getApplicationContext(), "取消转账",
										Toast.LENGTH_SHORT).show();

							}
						}).getView();
	}
	
	
	/*
     * 后端代码，线程
     */
  //调用web service的线程，不能同界面主线程抢CPU运行资源
  	@Override
  	public void run() {
  		//调用Web Service，并传递给handler负责通信
      	//访问WS服务
  		Object MTL = null;
  			String NAME_SPACE = "http://serves";
  			String WDSL_LINK = "http://223.3.79.181:8080/axis2/services/UserSercve?wsdl";
          String METHOD_NAME = "insertTradeInfo";  
          //以上三条是不需要修改的，在服务器端查询，其中getUserInfo是自己构造的方法名
          
          //Soap Object 是  SoapSerializationEnvelope的重要组成部分 
          SoapObject request = new SoapObject(NAME_SPACE, METHOD_NAME);  
          request.addProperty("payername", pref.getString("realname","ZHANG"));
          request.addProperty("receivername",receivername);
          request.addProperty("num",transfermoney.getText().toString()); 
          
       
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
//          	TransferThirdActivity.this.ret = String.valueOf(envelope.getResponse());
//          	//发送消息
          	handler.sendEmptyMessage(1);//通知主线程，web服务调用完毕
          }
          catch(Exception e){
          	Log.v("III", e.getMessage());
          }
  		
  	}
	
	
}
