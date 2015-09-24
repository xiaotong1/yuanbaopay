package cn.seu.edu.yuanbaopay.main;

//���
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




//������
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
	 * �����ǻ��õ��ı�����ӵ�
	 */
	private ProgressDialog pd;
	private String password;
	// �������漰����Ϣ�Ŀؼ���
	private EditText edtUsername;
	private EditText edtPassword;
	//�洢
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
			 * �����أ� ��ʼ�������ڵ���¼���
			 */
			// ���������һ��Բ�εĽ������Ĵ��룬����ɾ��
			pd = ProgressDialog.show(this, "��ʾ", "������֤");
			// �����߳�
			new Thread(this).start();
			break;
		default:
			break;
		}
	}

	/*
	 * ��˴��� Handler ��Activity�����ָ��� ��һ���ر�ProgressDialog����ɾ��
	 * �ڶ��еĴ�����ˣ����Լ���ҵ���߼�ʵ�֣����ȡ������Ҫ�ڱ�ҳ����ʾ
	 * ����Ӧ���ã����Ҫ������Ӧ����֤Ҳ������ʵ�֣������ڶ��д�����Ƿ��ص�ֵ��Ҫ��ô�þ���ô�á�û���㷵��ֵ���
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {// handler���յ���Ϣ��ͻ�ִ�д˷���
			// �ر�ProgressDialog
			LoginActivity.this.pd.dismiss();

			/*
			 * ��֤���ص��������û�����������Ƿ�һ��
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
				editor.putString("sex", "��");
				editor.putString("balance", balance);
				editor.putString("mmbalance",mmBalance);
				editor.putString("cardnum", cardNum);
				editor.commit();
				Intent i = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(i);
			} else {
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						LoginActivity.this, R.style.dialog);
				dialog.setMessage("�������");
				dialog.setCancelable(false);
				dialog.setPositiveButton("ȷ��", null);
				dialog.show();
			}
		}
	};

	/*
	 * ��˴��룬�߳�
	 */
	// ����web service���̣߳�����ͬ�������߳���CPU������Դ
	@Override
	public void run() {
		// ����Web Service�������ݸ�handler����ͨ��
		// ����WS����
		String NAME_SPACE = "http://serves";
		String WDSL_LINK = "http://223.3.79.181:8080/axis2/services/UserSercve?wsdl";
		String METHOD_NAME = "getpassword";
		// ���������ǲ���Ҫ�޸ĵģ��ڷ������˲�ѯ������getUserInfo���Լ�����ķ�����
		// Soap Object �� SoapSerializationEnvelope����Ҫ��ɲ���
		SoapObject request = new SoapObject(NAME_SPACE, METHOD_NAME);
		request.addProperty("username", edtUsername.getText().toString());
		// username�����ݿ��е��ֶ�����tom�ǲ�ѯ����������
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelope.bodyOut = request;
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE ht = new HttpTransportSE(WDSL_LINK);
		try {
			// ������뱣����
			ht.call("", envelope);
			// retΪ�ӷ��������ص���Ϣ�������ú�������return��Ϣ
			LoginActivity.this.password = String
					.valueOf(envelope.getResponse());
			// ������Ϣ
			 //handler.sendEmptyMessage(1);// ֪ͨ���̣߳�web����������
		} catch (Exception e) {
			Log.v("III", e.getMessage());
		}
//		// phonenum
//		// Soap Object �� SoapSerializationEnvelope����Ҫ��ɲ���
//		SoapObject requestPhone = new SoapObject(NAME_SPACE, "getphonenum");
//		requestPhone.addProperty("username", edtUsername.getText().toString());
//		// username�����ݿ��е��ֶ�����tom�ǲ�ѯ����������
//		SoapSerializationEnvelope envelopePhone = new SoapSerializationEnvelope(
//				SoapEnvelope.VER12);
//		envelopePhone.bodyOut = requestPhone;
//		envelopePhone.dotNet = true;
//		envelopePhone.setOutputSoapObject(requestPhone);
//		// HttpTransportSE ht = new HttpTransportSE(WDSL_LINK);
//		try {
//			// ������뱣����
//			ht.call("", envelopePhone);
//			// retΪ�ӷ��������ص���Ϣ�������ú�������return��Ϣ
//			LoginActivity.this.phonenum = String.valueOf(envelopePhone
//					.getResponse());
//			// ������Ϣ
//			 handler.sendEmptyMessage(1);// ֪ͨ���̣߳�web����������
//		} catch (Exception e) {
//			Log.v("III", e.getMessage());
//		}
		// nickname
		// Soap Object �� SoapSerializationEnvelope����Ҫ��ɲ���
		SoapObject requestNick = new SoapObject(NAME_SPACE, "getnickname");
		requestNick.addProperty("username", edtUsername.getText().toString());
		// username�����ݿ��е��ֶ�����tom�ǲ�ѯ����������
		SoapSerializationEnvelope envelopeNick = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelopeNick.bodyOut = requestNick;
		envelopeNick.dotNet = true;
		envelopeNick.setOutputSoapObject(requestNick);
		// HttpTransportSE ht = new HttpTransportSE(WDSL_LINK);
		try {
			// ������뱣����
			ht.call("", envelopeNick);
			// retΪ�ӷ��������ص���Ϣ�������ú�������return��Ϣ
			LoginActivity.this.nickName = String
					.valueOf(envelopeNick.getResponse());
			// ������Ϣ
//			 handler.sendEmptyMessage(1);// ֪ͨ���̣߳�web����������
		} catch (Exception e) {
			Log.v("III", e.getMessage());
		}
		// realname
		// Soap Object �� SoapSerializationEnvelope����Ҫ��ɲ���
		SoapObject requestReal = new SoapObject(NAME_SPACE, "getrealname");
		requestReal.addProperty("username", edtUsername.getText().toString());
		// username�����ݿ��е��ֶ�����tom�ǲ�ѯ����������
		SoapSerializationEnvelope envelopeReal = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelopeReal.bodyOut = requestReal;
		envelopeReal.dotNet = true;
		envelopeReal.setOutputSoapObject(requestReal);
		// HttpTransportSE ht = new HttpTransportSE(WDSL_LINK);
		try {
			// ������뱣����
			ht.call("", envelopeReal);
			// retΪ�ӷ��������ص���Ϣ�������ú�������return��Ϣ
			LoginActivity.this.realName = String.valueOf(envelopeReal
					.getResponse());
			// ������Ϣ
			// handler.sendEmptyMessage(1);// ֪ͨ���̣߳�web����������
		} catch (Exception e) {
			Log.v("III", e.getMessage());
		}
		// sex
		// Soap Object �� SoapSerializationEnvelope����Ҫ��ɲ���
		SoapObject requestSex = new SoapObject(NAME_SPACE, "getgendar");
		requestSex.addProperty("username", edtUsername.getText().toString());
		// username�����ݿ��е��ֶ�����tom�ǲ�ѯ����������
		SoapSerializationEnvelope envelopeSex = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelopeSex.bodyOut = requestSex;
		envelopeSex.dotNet = true;
		envelopeSex.setOutputSoapObject(requestSex);
		// HttpTransportSE ht = new HttpTransportSE(WDSL_LINK);
		try {
			// ������뱣����
			ht.call("", envelopeSex);
			// retΪ�ӷ��������ص���Ϣ�������ú�������return��Ϣ
			LoginActivity.this.sex = String.valueOf(envelopeSex.getResponse());
			// ������Ϣ
			// handler.sendEmptyMessage(1);// ֪ͨ���̣߳�web����������
		} catch (Exception e) {
			Log.v("III", e.getMessage());
		}
		// idNum
		// Soap Object �� SoapSerializationEnvelope����Ҫ��ɲ���
		SoapObject requestIDNum = new SoapObject(NAME_SPACE, "getidnum");
		requestIDNum.addProperty("username", edtUsername.getText().toString());
		// username�����ݿ��е��ֶ�����tom�ǲ�ѯ����������
		SoapSerializationEnvelope envelopeIDNum = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelopeIDNum.bodyOut = requestIDNum;
		envelopeIDNum.dotNet = true;
		envelopeIDNum.setOutputSoapObject(requestIDNum);
		// HttpTransportSE ht = new HttpTransportSE(WDSL_LINK);
		try {
			// ������뱣����
			ht.call("", envelopeIDNum);
			// retΪ�ӷ��������ص���Ϣ�������ú�������return��Ϣ
			LoginActivity.this.sex = String
					.valueOf(envelopeIDNum.getResponse());
			// ������Ϣ
			// handler.sendEmptyMessage(1);// ֪ͨ���̣߳�web����������
		} catch (Exception e) {
			Log.v("III", e.getMessage());
		}
		// Balance
		// Soap Object �� SoapSerializationEnvelope����Ҫ��ɲ���
		SoapObject requestBalance = new SoapObject(NAME_SPACE, "getybbbalance");
		requestBalance.addProperty("username", edtUsername.getText().toString());
		// username�����ݿ��е��ֶ�����tom�ǲ�ѯ����������
		SoapSerializationEnvelope envelopeBalance = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelopeBalance.bodyOut = requestBalance;
		envelopeBalance.dotNet = true;
		envelopeBalance.setOutputSoapObject(requestBalance);
		// HttpTransportSE ht = new HttpTransportSE(WDSL_LINK);
		try {
			// ������뱣����
			ht.call("", envelopeBalance);
			// retΪ�ӷ��������ص���Ϣ�������ú�������return��Ϣ
			LoginActivity.this.sex = String
					.valueOf(envelopeBalance.getResponse());
			// ������Ϣ
			// handler.sendEmptyMessage(1);// ֪ͨ���̣߳�web����������
		} catch (Exception e) {
			Log.v("III", e.getMessage());
		}
		// MMBalance
		// Soap Object �� SoapSerializationEnvelope����Ҫ��ɲ���
		SoapObject requestMMBalance = new SoapObject(NAME_SPACE, "getmmbalance");
		requestMMBalance.addProperty("username", edtUsername.getText().toString());
		// username�����ݿ��е��ֶ�����tom�ǲ�ѯ����������
		SoapSerializationEnvelope envelopeMMBalance = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelopeMMBalance.bodyOut = requestMMBalance;
		envelopeMMBalance.dotNet = true;
		envelopeMMBalance.setOutputSoapObject(requestMMBalance);
		// HttpTransportSE ht = new HttpTransportSE(WDSL_LINK);
		try {
			// ������뱣����
			ht.call("", envelopeMMBalance);
			// retΪ�ӷ��������ص���Ϣ�������ú�������return��Ϣ
			LoginActivity.this.sex = String
					.valueOf(envelopeMMBalance.getResponse());
			// ������Ϣ
			// handler.sendEmptyMessage(1);// ֪ͨ���̣߳�web����������
		} catch (Exception e) {
			Log.v("III", e.getMessage());
		}
		// CardNum
		// Soap Object �� SoapSerializationEnvelope����Ҫ��ɲ���
		SoapObject requestCardNum = new SoapObject(NAME_SPACE, "getcardnum");
		requestCardNum.addProperty("username", edtUsername.getText().toString());
		// username�����ݿ��е��ֶ�����tom�ǲ�ѯ����������
		SoapSerializationEnvelope envelopeCardNum = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelopeCardNum.bodyOut = requestCardNum;
		envelopeCardNum.dotNet = true;
		envelopeCardNum.setOutputSoapObject(requestCardNum);
		// HttpTransportSE ht = new HttpTransportSE(WDSL_LINK);
		try {
			// ������뱣����
			ht.call("", envelopeCardNum);
			// retΪ�ӷ��������ص���Ϣ�������ú�������return��Ϣ
			LoginActivity.this.sex = String
					.valueOf(envelopeCardNum.getResponse());
			// ������Ϣ
			 handler.sendEmptyMessage(1);// ֪ͨ���̣߳�web����������
		} catch (Exception e) {
			Log.v("III", e.getMessage());
		}

	}

}
