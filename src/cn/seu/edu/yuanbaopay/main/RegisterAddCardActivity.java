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





//���
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


//������





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
	 * �����ǻ��õ��ı�����ӵ�
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
		tvTitle.setText("�����п�");
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
						"������֧������","������п�","ȷ�ϰ�",
						this,
						new cn.seu.edu.yuanbaopay.paypassword.PayPasswordView.OnPayListener() {

							@Override
							public void onSurePay(String password) {
								// TODO Auto-generated method stub
								mDialogWidget.dismiss();
								mDialogWidget = null;
								AlertDialog.Builder dialog=new AlertDialog.Builder(RegisterAddCardActivity.this,R.style.dialog);
								dialog.setMessage("�󶨳ɹ�");
								dialog.setCancelable(false);
								dialog.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										/*
										 * �����أ�
										 * ��ʼ�������ڵ���¼���
										 */
										 //���������һ��Բ�εĽ������Ĵ��룬����ɾ��
										pd=ProgressDialog.show(RegisterAddCardActivity.this, "��ʾ","Web���������");
								                
										//�����߳�
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
								Toast.makeText(getApplicationContext(), "ȡ����",
										Toast.LENGTH_SHORT).show();

							}
						}).getView();
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
        	RegisterAddCardActivity.this.pd.dismiss(); 
        	
        	pref=PreferenceManager.getDefaultSharedPreferences(RegisterAddCardActivity.this);
        	editor=pref.edit();
        	editor.putString("cardnumber", edtCardNum.getText().toString());
        	editor.commit();
 
        	Intent i=new Intent(RegisterAddCardActivity.this,LoginActivity.class);
			Toast.makeText(RegisterAddCardActivity.this, "ע��ɹ����¼��", Toast.LENGTH_SHORT).show();
			startActivity(i);
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
          String METHOD_NAME = "getUserInfo";  
          //���������ǲ���Ҫ�޸ĵģ��ڷ������˲�ѯ������getUserInfo���Լ�����ķ�����
          
          //Soap Object ��  SoapSerializationEnvelope����Ҫ��ɲ��� 
          SoapObject request = new SoapObject(NAME_SPACE, METHOD_NAME);  
          request.addProperty("realname", edtRealName.getText().toString()); 
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
 
          	//������Ϣ
          	handler.sendEmptyMessage(1);//֪ͨ���̣߳�web����������
          }
          catch(Exception e){
          	Log.v("III", e.getMessage());
          }
  		
  	}

}
