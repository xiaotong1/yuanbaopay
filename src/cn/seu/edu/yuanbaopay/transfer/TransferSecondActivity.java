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
import android.app.ProgressDialog;
//������


public class TransferSecondActivity extends Activity implements OnClickListener,Runnable {
	private Button btnTransfer3;
	private TextView tvTitle;
	private TextView tvBack;
	/*
	 * �Ի��ͷ���ֵ�����ݿ�ر�
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
		tvTitle.setText("ת��");
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
			 * �����أ�
			 * ��ʼ�������ڵ���¼���
			 */
			 //���������һ��Բ�εĽ������Ĵ��룬����ɾ��
			pd=ProgressDialog.show(this, "��ʾ","Web���������");
	                
			//�����߳�
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
        	TransferSecondActivity.this.pd.dismiss(); 
        	/*
        	 * ��֤�����ת���û��Ƿ����
        	 */
        	if(ret.equals("true")){
        		pref=PreferenceManager.getDefaultSharedPreferences(TransferSecondActivity.this);
				editor=pref.edit();
				editor.putString("transfertype", "ת��");
				editor.putString("transfername", edtUsername.getText().toString());
				editor.commit();
        		Intent i=new Intent(TransferSecondActivity.this,TransferThirdActivity.class);
        		i.putExtra("receivername", edtUsername.getText().toString());
        		startActivity(i);
        	}else{
        		AlertDialog.Builder dialog=new AlertDialog.Builder(TransferSecondActivity.this,R.style.dialog);
				dialog.setMessage("�Է��˻������ڣ�����������");
				dialog.setCancelable(false);
				dialog.setPositiveButton("ȷ��", null);
				dialog.show();
        	}

        }  
    };  
	
    
    /*
     * ��˴��룬�߳�
     */
  //����web service���̣߳�����ͬ�������߳���CPU������Դ
  	@Override
  	public void run() {
  		//����Web Service�������ݸ�handler����ͨ��
      	//����WS����
  			String NAME_SPACE = "http://serves";
  			String WDSL_LINK = "http://223.3.79.181:8080/axis2/services/UserSercve?wsdl"; 
          String METHOD_NAME = "gettransfername";  
          //���������ǲ���Ҫ�޸ĵģ��ڷ������˲�ѯ������getUserInfo���Լ�����ķ�����
          
          //Soap Object ��  SoapSerializationEnvelope����Ҫ��ɲ��� 
          SoapObject request = new SoapObject(NAME_SPACE, METHOD_NAME);  
          request.addProperty("username", edtUsername.getText().toString()); 
  
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
          	//Toast.makeText(TransferSecondActivity.this, String.valueOf(envelope.getResponse()), Toast.LENGTH_LONG).show();
          	TransferSecondActivity.this.ret = String.valueOf(envelope.getResponse());
          	//������Ϣ
          	handler.sendEmptyMessage(1);//֪ͨ���̣߳�web����������
          }
          catch(Exception e){
          	Log.v("III", e.getMessage());
          }
  		
  	}

}
