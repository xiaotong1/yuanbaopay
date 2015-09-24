package cn.seu.edu.yuanbaopay.main;

//后端
import java.io.File;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.seu.edu.yuanbaopay.gesture.GestureVerifyActivity;
import cn.seu.edu.yuanbaopay.redpacket.RedPacketLuckyActivity;
import cn.seu.edu.yuanbaopay.redpacket.RedPacketMainActivity;
import cn.seu.edu.yuanbaopay.security.ForgetPasswordFirstActivity;




//后端完毕
import com.example.yuanbaopay.R;

public class LoginActivity extends Activity implements OnClickListener,
		Runnable {
	private Button btnLogin;
	private TextView tvRegister;
	private EditText etUsername;
	private String username = null;
	private String phonenum = null;
	private String realName = null;
	private String nickName = null;
	private String sex = null;
	private String idNum = null;
	private String balance = null;
	private String mmBalance = null;
	private String cardNum = null;
	private TextView tvForgetPwd;
	private TextView tvGesture;
	/*
	 * 这里是会用到的必须添加的
	 */
	private ProgressDialog pd;
	private String password;
	// 这里是涉及到信息的控件！
	private EditText edtUsername;
	private EditText edtPassword;
	//存储
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		initView();
	}

	private void initView() {
		etUsername = (EditText) findViewById(R.id.user_login);
		etUsername.setText(username);
		btnLogin = (Button) findViewById(R.id.btn_login);
		btnLogin.setOnClickListener(this);
		tvRegister = (TextView) findViewById(R.id.tv_to_register);
		tvRegister.setOnClickListener(this);
		tvForgetPwd = (TextView) findViewById(R.id.tv_to_forgetpwd1);
		tvForgetPwd.setOnClickListener(this);
		tvGesture = (TextView) findViewById(R.id.tv_to_loginingesture);
		tvGesture.setOnClickListener(this);
		edtUsername = (EditText) findViewById(R.id.user_login);
		edtPassword = (EditText) findViewById(R.id.password_login);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_to_loginingesture:
			Intent i_verify = new Intent(LoginActivity.this,
					GestureVerifyActivity.class);
			startActivity(i_verify);
			break;
		case R.id.tv_to_register:
			Intent i_register = new Intent(LoginActivity.this,
					RegisterActivity.class);
			startActivity(i_register);
			break;
		case R.id.tv_to_forgetpwd1:
			Intent i_forget = new Intent(LoginActivity.this,
					ForgetPasswordFirstActivity.class);
			startActivity(i_forget);
			break;
		case R.id.btn_login:
			/*
			 * 后端相关， 开始交互放在点击事件里
			 */
			// 本句好像是一个圆形的进度条的代码，可以删除
			pd = ProgressDialog.show(this, "提示", "正在验证");
			// 启动线程
			new Thread(this).start();
			break;
		default:
			break;
		}
	}

	/*
	 * 后端代码 Handler 把Activity的名字改了 第一个关闭ProgressDialog不能删除
	 * 第二行的代码改了，是自己的业务逻辑实现，如果取到数据要在本页上显示
	 * ，对应设置，如果要进行相应的验证也在这里实现，反正第二行代码就是返回的值你要怎么用就怎么用。没用你返回值干嘛！
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {// handler接收到消息后就会执行此方法
			// 关闭ProgressDialog
			LoginActivity.this.pd.dismiss();

			/*
			 * 验证返回的密码与用户输入的密码是否一致
			 */
			if (password.equals(edtPassword.getText().toString())) {
				pref=PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
				editor=pref.edit();
				editor.putString("username", edtUsername.getText().toString());
				editor.putString("password", password);
				editor.putString("phonenum", phonenum);
				editor.putString("idnum", idNum);
				editor.putString("realname", realName);
				editor.putString("nickname", nickName);
				editor.putString("sex", "男");
				editor.putString("balance", balance);
				editor.putString("mmbalance",mmBalance);
				editor.putString("cardnum", cardNum);
				editor.commit();
				Intent i = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(i);
			} else {
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						LoginActivity.this, R.style.dialog);
				dialog.setMessage("密码错误！");
				dialog.setCancelable(false);
				dialog.setPositiveButton("确定", null);
				dialog.show();
			}
		}
	};

	/*
	 * 后端代码，线程
	 */
	// 调用web service的线程，不能同界面主线程抢CPU运行资源
	@Override
	public void run() {
		// 调用Web Service，并传递给handler负责通信
		// 访问WS服务
		String NAME_SPACE = "http://serves";
		String WDSL_LINK = "http://223.3.79.181:8080/axis2/services/UserSercve?wsdl";
		String METHOD_NAME = "getpassword";
		// 以上三条是不需要修改的，在服务器端查询，其中getUserInfo是自己构造的方法名
		// Soap Object 是 SoapSerializationEnvelope的重要组成部分
		SoapObject request = new SoapObject(NAME_SPACE, METHOD_NAME);
		request.addProperty("username", edtUsername.getText().toString());
		// username是数据库中的字段名，tom是查询的数据引索
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelope.bodyOut = request;
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE ht = new HttpTransportSE(WDSL_LINK);
		try {
			// 本句必须保留。
			ht.call("", envelope);
			// ret为从服务器返回的信息，即调用函数里面return信息
			LoginActivity.this.password = String
					.valueOf(envelope.getResponse());
			// 发送消息
			 //handler.sendEmptyMessage(1);// 通知主线程，web服务调用完毕
		} catch (Exception e) {
			Log.v("III", e.getMessage());
		}
//		// phonenum
//		// Soap Object 是 SoapSerializationEnvelope的重要组成部分
//		SoapObject requestPhone = new SoapObject(NAME_SPACE, "getphonenum");
//		requestPhone.addProperty("username", edtUsername.getText().toString());
//		// username是数据库中的字段名，tom是查询的数据引索
//		SoapSerializationEnvelope envelopePhone = new SoapSerializationEnvelope(
//				SoapEnvelope.VER12);
//		envelopePhone.bodyOut = requestPhone;
//		envelopePhone.dotNet = true;
//		envelopePhone.setOutputSoapObject(requestPhone);
//		// HttpTransportSE ht = new HttpTransportSE(WDSL_LINK);
//		try {
//			// 本句必须保留。
//			ht.call("", envelopePhone);
//			// ret为从服务器返回的信息，即调用函数里面return信息
//			LoginActivity.this.phonenum = String.valueOf(envelopePhone
//					.getResponse());
//			// 发送消息
//			 handler.sendEmptyMessage(1);// 通知主线程，web服务调用完毕
//		} catch (Exception e) {
//			Log.v("III", e.getMessage());
//		}
		// nickname
		// Soap Object 是 SoapSerializationEnvelope的重要组成部分
		SoapObject requestNick = new SoapObject(NAME_SPACE, "getnickname");
		requestNick.addProperty("username", edtUsername.getText().toString());
		// username是数据库中的字段名，tom是查询的数据引索
		SoapSerializationEnvelope envelopeNick = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelopeNick.bodyOut = requestNick;
		envelopeNick.dotNet = true;
		envelopeNick.setOutputSoapObject(requestNick);
		// HttpTransportSE ht = new HttpTransportSE(WDSL_LINK);
		try {
			// 本句必须保留。
			ht.call("", envelopeNick);
			// ret为从服务器返回的信息，即调用函数里面return信息
			LoginActivity.this.nickName = String
					.valueOf(envelopeNick.getResponse());
			// 发送消息
//			 handler.sendEmptyMessage(1);// 通知主线程，web服务调用完毕
		} catch (Exception e) {
			Log.v("III", e.getMessage());
		}
		// realname
		// Soap Object 是 SoapSerializationEnvelope的重要组成部分
		SoapObject requestReal = new SoapObject(NAME_SPACE, "getrealname");
		requestReal.addProperty("username", edtUsername.getText().toString());
		// username是数据库中的字段名，tom是查询的数据引索
		SoapSerializationEnvelope envelopeReal = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelopeReal.bodyOut = requestReal;
		envelopeReal.dotNet = true;
		envelopeReal.setOutputSoapObject(requestReal);
		// HttpTransportSE ht = new HttpTransportSE(WDSL_LINK);
		try {
			// 本句必须保留。
			ht.call("", envelopeReal);
			// ret为从服务器返回的信息，即调用函数里面return信息
			LoginActivity.this.realName = String.valueOf(envelopeReal
					.getResponse());
			// 发送消息
			// handler.sendEmptyMessage(1);// 通知主线程，web服务调用完毕
		} catch (Exception e) {
			Log.v("III", e.getMessage());
		}
		// sex
		// Soap Object 是 SoapSerializationEnvelope的重要组成部分
		SoapObject requestSex = new SoapObject(NAME_SPACE, "getgendar");
		requestSex.addProperty("username", edtUsername.getText().toString());
		// username是数据库中的字段名，tom是查询的数据引索
		SoapSerializationEnvelope envelopeSex = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelopeSex.bodyOut = requestSex;
		envelopeSex.dotNet = true;
		envelopeSex.setOutputSoapObject(requestSex);
		// HttpTransportSE ht = new HttpTransportSE(WDSL_LINK);
		try {
			// 本句必须保留。
			ht.call("", envelopeSex);
			// ret为从服务器返回的信息，即调用函数里面return信息
			LoginActivity.this.sex = String.valueOf(envelopeSex.getResponse());
			// 发送消息
			// handler.sendEmptyMessage(1);// 通知主线程，web服务调用完毕
		} catch (Exception e) {
			Log.v("III", e.getMessage());
		}
		// idNum
		// Soap Object 是 SoapSerializationEnvelope的重要组成部分
		SoapObject requestIDNum = new SoapObject(NAME_SPACE, "getidnum");
		requestIDNum.addProperty("username", edtUsername.getText().toString());
		// username是数据库中的字段名，tom是查询的数据引索
		SoapSerializationEnvelope envelopeIDNum = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelopeIDNum.bodyOut = requestIDNum;
		envelopeIDNum.dotNet = true;
		envelopeIDNum.setOutputSoapObject(requestIDNum);
		// HttpTransportSE ht = new HttpTransportSE(WDSL_LINK);
		try {
			// 本句必须保留。
			ht.call("", envelopeIDNum);
			// ret为从服务器返回的信息，即调用函数里面return信息
			LoginActivity.this.sex = String
					.valueOf(envelopeIDNum.getResponse());
			// 发送消息
			// handler.sendEmptyMessage(1);// 通知主线程，web服务调用完毕
		} catch (Exception e) {
			Log.v("III", e.getMessage());
		}
		// Balance
		// Soap Object 是 SoapSerializationEnvelope的重要组成部分
		SoapObject requestBalance = new SoapObject(NAME_SPACE, "getybbbalance");
		requestBalance.addProperty("username", edtUsername.getText().toString());
		// username是数据库中的字段名，tom是查询的数据引索
		SoapSerializationEnvelope envelopeBalance = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelopeBalance.bodyOut = requestBalance;
		envelopeBalance.dotNet = true;
		envelopeBalance.setOutputSoapObject(requestBalance);
		// HttpTransportSE ht = new HttpTransportSE(WDSL_LINK);
		try {
			// 本句必须保留。
			ht.call("", envelopeBalance);
			// ret为从服务器返回的信息，即调用函数里面return信息
			LoginActivity.this.sex = String
					.valueOf(envelopeBalance.getResponse());
			// 发送消息
			// handler.sendEmptyMessage(1);// 通知主线程，web服务调用完毕
		} catch (Exception e) {
			Log.v("III", e.getMessage());
		}
		// MMBalance
		// Soap Object 是 SoapSerializationEnvelope的重要组成部分
		SoapObject requestMMBalance = new SoapObject(NAME_SPACE, "getmmbalance");
		requestMMBalance.addProperty("username", edtUsername.getText().toString());
		// username是数据库中的字段名，tom是查询的数据引索
		SoapSerializationEnvelope envelopeMMBalance = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelopeMMBalance.bodyOut = requestMMBalance;
		envelopeMMBalance.dotNet = true;
		envelopeMMBalance.setOutputSoapObject(requestMMBalance);
		// HttpTransportSE ht = new HttpTransportSE(WDSL_LINK);
		try {
			// 本句必须保留。
			ht.call("", envelopeMMBalance);
			// ret为从服务器返回的信息，即调用函数里面return信息
			LoginActivity.this.sex = String
					.valueOf(envelopeMMBalance.getResponse());
			// 发送消息
			// handler.sendEmptyMessage(1);// 通知主线程，web服务调用完毕
		} catch (Exception e) {
			Log.v("III", e.getMessage());
		}
		// CardNum
		// Soap Object 是 SoapSerializationEnvelope的重要组成部分
		SoapObject requestCardNum = new SoapObject(NAME_SPACE, "getcardnum");
		requestCardNum.addProperty("username", edtUsername.getText().toString());
		// username是数据库中的字段名，tom是查询的数据引索
		SoapSerializationEnvelope envelopeCardNum = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelopeCardNum.bodyOut = requestCardNum;
		envelopeCardNum.dotNet = true;
		envelopeCardNum.setOutputSoapObject(requestCardNum);
		// HttpTransportSE ht = new HttpTransportSE(WDSL_LINK);
		try {
			// 本句必须保留。
			ht.call("", envelopeCardNum);
			// ret为从服务器返回的信息，即调用函数里面return信息
			LoginActivity.this.sex = String
					.valueOf(envelopeCardNum.getResponse());
			// 发送消息
			 handler.sendEmptyMessage(1);// 通知主线程，web服务调用完毕
		} catch (Exception e) {
			Log.v("III", e.getMessage());
		}

	}

}
