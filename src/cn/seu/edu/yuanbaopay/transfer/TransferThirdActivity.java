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








//���
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
import android.app.ProgressDialog;//������

public class TransferThirdActivity extends Activity implements OnClickListener,Runnable {
	private TextView tvTitle;
	private Button btnEnsureTransfer;
	private TextView tvBack;
	private DialogWidget mDialogWidget;
	private TextView tvLimit;
	
	/*
	 * �Ի��ͷ���ֵ�����ݿ�ر�
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
		tvTitle.setText("ת��");
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
			 * �����أ�
			 * ��ʼ�������ڵ���¼���
			 */
			 //���������һ��Բ�εĽ������Ĵ��룬����ɾ��
			pd=ProgressDialog.show(this, "��ʾ","Web���������");
	                
			//�����߳�
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
	 * ��˴���
	 * Handler
	 * ��Activity�����ָ���
	 * ��һ���ر�ProgressDialog����ɾ��
	 * �ڶ��еĴ�����ˣ����Լ���ҵ���߼�ʵ�֣����ȡ������Ҫ�ڱ�ҳ����ʾ����Ӧ���ã����Ҫ������Ӧ����֤Ҳ������ʵ�֣������ڶ��д�����Ƿ��ص�ֵ��Ҫ��ô�þ���ô�á�û���㷵��ֵ���
	 */
	private Handler handler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {// handler���յ���Ϣ��ͻ�ִ�д˷���  
        	//�ر�ProgressDialog 
        	TransferThirdActivity.this.pd.dismiss(); 
        	/*
        	 * ������Ľ��ͱ�עͨ�����ݿⷢ�͸�ת����,Ȼ������TransferForthActivity
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
						"������֧������","ת�˸�xxxxxxԪ","ȷ��ת��",
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
								Toast.makeText(getApplicationContext(), "ȡ��ת��",
										Toast.LENGTH_SHORT).show();

							}
						}).getView();
	}
	
	
	/*
     * ��˴��룬�߳�
     */
  //����web service���̣߳�����ͬ�������߳���CPU������Դ
  	@Override
  	public void run() {
  		//����Web Service�������ݸ�handler����ͨ��
      	//����WS����
  		Object MTL = null;
  			String NAME_SPACE = "http://serves";
  			String WDSL_LINK = "http://223.3.79.181:8080/axis2/services/UserSercve?wsdl";
          String METHOD_NAME = "insertTradeInfo";  
          //���������ǲ���Ҫ�޸ĵģ��ڷ������˲�ѯ������getUserInfo���Լ�����ķ�����
          
          //Soap Object ��  SoapSerializationEnvelope����Ҫ��ɲ��� 
          SoapObject request = new SoapObject(NAME_SPACE, METHOD_NAME);  
          request.addProperty("payername", pref.getString("realname","ZHANG"));
          request.addProperty("receivername",receivername);
          request.addProperty("num",transfermoney.getText().toString()); 
          
       
          //username�����ݿ��е��ֶ�����tom�ǲ�ѯ���������� 
          SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);  
          envelope.bodyOut = request;  
          envelope.dotNet = true;  
          envelope.setOutputSoapObject(request);  
          HttpTransportSE ht = new HttpTransportSE(WDSL_LINK); 
          try{
                  //������뱣����
          	ht.call("", envelope); 
          	//retΪ�ӷ��������ص���Ϣ�������ú�������return��Ϣ
//          	TransferThirdActivity.this.ret = String.valueOf(envelope.getResponse());
//          	//������Ϣ
          	handler.sendEmptyMessage(1);//֪ͨ���̣߳�web����������
          }
          catch(Exception e){
          	Log.v("III", e.getMessage());
          }
  		
  	}
	
	
}
