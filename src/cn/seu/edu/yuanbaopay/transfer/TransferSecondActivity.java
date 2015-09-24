package cn.seu.edu.yuanbaopay.transfer;

import cn.seu.edu.yuanbaopay.redpacket.RedPacketLuckyActivity;
import cn.seu.edu.yuanbaopay.redpacket.RedPacketMainActivity;

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
import android.app.ProgressDialog;
//后端完毕


public class TransferSecondActivity extends Activity implements OnClickListener,Runnable {
	private Button btnTransfer3;
	private TextView tvTitle;
	private TextView tvBack;
	/*
	 * 对话和返回值，数据库必备
	 */
	private ProgressDialog pd;
	private String ret;
	private EditText edtUsername;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_transfer_2);
		initView();
	}

	private void initView() {
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("转账");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		btnTransfer3 = (Button) findViewById(R.id.btn_next_to_transfer3);
		btnTransfer3.setOnClickListener(this);
		edtUsername =(EditText)findViewById(R.id.edtTxt_account_transfer_2);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next_to_transfer3:
//			Intent i = new Intent(TransferSecondActivity.this,
//					TransferThirdActivity.class);
//			startActivity(i);
//			break;
			/*
			 * 后端相关，
			 * 开始交互放在点击事件里
			 */
			 //本句好像是一个圆形的进度条的代码，可以删除
			pd=ProgressDialog.show(this, "提示","Web服务调用中");
	                
			//启动线程
			new Thread(this).start();
			break;
		case R.id.title_back:
			finish();
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
        	TransferSecondActivity.this.pd.dismiss(); 
        	/*
        	 * 验证输入的转账用户是否存在
        	 */
        	if(ret.equals("true")){
        		pref=PreferenceManager.getDefaultSharedPreferences(TransferSecondActivity.this);
				editor=pref.edit();
				editor.putString("transfertype", "转账");
				editor.putString("transfername", edtUsername.getText().toString());
				editor.commit();
        		Intent i=new Intent(TransferSecondActivity.this,TransferThirdActivity.class);
        		i.putExtra("receivername", edtUsername.getText().toString());
        		startActivity(i);
        	}else{
        		AlertDialog.Builder dialog=new AlertDialog.Builder(TransferSecondActivity.this,R.style.dialog);
				dialog.setMessage("对方账户不存在！请重新输入");
				dialog.setCancelable(false);
				dialog.setPositiveButton("确定", null);
				dialog.show();
        	}

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
          String METHOD_NAME = "gettransfername";  
          //以上三条是不需要修改的，在服务器端查询，其中getUserInfo是自己构造的方法名
          
          //Soap Object 是  SoapSerializationEnvelope的重要组成部分 
          SoapObject request = new SoapObject(NAME_SPACE, METHOD_NAME);  
          request.addProperty("username", edtUsername.getText().toString()); 
  
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
          	//Toast.makeText(TransferSecondActivity.this, String.valueOf(envelope.getResponse()), Toast.LENGTH_LONG).show();
          	TransferSecondActivity.this.ret = String.valueOf(envelope.getResponse());
          	//发送消息
          	handler.sendEmptyMessage(1);//通知主线程，web服务调用完毕
          }
          catch(Exception e){
          	Log.v("III", e.getMessage());
          }
  		
  	}

}
