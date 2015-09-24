package cn.seu.edu.yuanbaopay.paypassword;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.seu.edu.yuanbaopay.paypassword.KeyboardEnum.ActionEnum;
import cn.seu.edu.yuanbaopay.paypassword.PayPasswordView.OnPayListener;

import com.example.yuanbaopay.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnLongClick;
/**
 * Dialog 显示视图
 * @author LanYan
 *
 */
@SuppressLint("InflateParams")
public class PayPasswordView{

	@ViewInject(R.id.pay_keyboard_del)
	private ImageView del;
	@ViewInject(R.id.pay_keyboard_zero)
	private ImageView zero;
	@ViewInject(R.id.pay_keyboard_one)
	private ImageView one;
	@ViewInject(R.id.pay_keyboard_two)
	private ImageView two;
	@ViewInject(R.id.pay_keyboard_three)
	private ImageView three;
	@ViewInject(R.id.pay_keyboard_four)
	private ImageView four;
	@ViewInject(R.id.pay_keyboard_five)
	private ImageView five;
	@ViewInject(R.id.pay_keyboard_sex)
	private ImageView sex;
	@ViewInject(R.id.pay_keyboard_seven)
	private ImageView seven;
	@ViewInject(R.id.pay_keyboard_eight)
	private ImageView eight;
	@ViewInject(R.id.pay_keyboard_nine)
	private ImageView nine;
	@ViewInject(R.id.pay_cancel)
	private TextView cancel;
	@ViewInject(R.id.pay_sure)
	private TextView sure;
	@ViewInject(R.id.pay_box1)
	private TextView box1;
	@ViewInject(R.id.pay_box2)
	private TextView box2;
	@ViewInject(R.id.pay_box3)
	private TextView box3;
	@ViewInject(R.id.pay_box4)
	private TextView box4;
	@ViewInject(R.id.pay_box5)
	private TextView box5;
	@ViewInject(R.id.pay_box6)
	private TextView box6;
	@ViewInject(R.id.pay_title)
	private TextView title;
	@ViewInject(R.id.pay_content)
	private TextView content;

	private ArrayList<String> mList=new ArrayList<String>();
	private View mView;
	private OnPayListener listener;
	private Context mContext;
	public PayPasswordView(String payTitle,String payContent,String paySure,Context mContext,OnPayListener listener){
		getDecorView(payTitle,payContent,paySure, mContext, listener);
	}
	public static PayPasswordView getInstance(String payTitle,String payContent,String paySure,Context mContext,OnPayListener listener){
		return  new PayPasswordView(payTitle,payContent,paySure,mContext,listener);
	}

	public void getDecorView(String payTitle,String payContent,String paySure,Context mContext,OnPayListener listener){
		this.listener=listener;
		this.mContext=mContext;
		mView=LayoutInflater.from(mContext).inflate(R.layout.item_paypassword, null);
		ViewUtils.inject(this,mView);
		title.setText(payTitle);
		content.setText(payContent);
		sure.setText(paySure);
	}
	@OnClick({R.id.pay_keyboard_del,R.id.pay_keyboard_zero,
		R.id.pay_keyboard_one,R.id.pay_keyboard_two,
		R.id.pay_keyboard_three,R.id.pay_keyboard_four,
		R.id.pay_keyboard_five,R.id.pay_keyboard_sex,
		R.id.pay_keyboard_seven,R.id.pay_keyboard_eight,
		R.id.pay_keyboard_nine,R.id.pay_cancel,
		R.id.pay_sure})
	private void onClick(View v){
		if(v==zero){
			parseActionType(KeyboardEnum.zero);
		}else if(v==one){
			parseActionType(KeyboardEnum.one);
		}else if(v==two){
			parseActionType(KeyboardEnum.two);
		}else if(v==three){
			parseActionType(KeyboardEnum.three);
		}else if(v==four){
			parseActionType(KeyboardEnum.four);
		}else if(v==five){
			parseActionType(KeyboardEnum.five);
		}else if(v==sex){
			parseActionType(KeyboardEnum.sex);
		}else if(v==seven){
			parseActionType(KeyboardEnum.seven);
		}else if(v==eight){
			parseActionType(KeyboardEnum.eight);
		}else if(v==nine){
			parseActionType(KeyboardEnum.nine);
		}else if(v==cancel){
			parseActionType(KeyboardEnum.cancel);
		}else if(v==sure){
			parseActionType(KeyboardEnum.sure);
		}else if(v==del){
			parseActionType(KeyboardEnum.del);
		}
	}

	@OnLongClick(R.id.pay_keyboard_del)
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		parseActionType(KeyboardEnum.longdel);
		return false;
	}

	private void parseActionType(KeyboardEnum type) {
		// TODO Auto-generated method stub
		if(type.getType()==ActionEnum.add){
			if(mList.size()<6){
				mList.add(type.getValue());
				updateUi();
			}
		}else if(type.getType()==ActionEnum.delete){
			if(mList.size()>0){
				mList.remove(mList.get(mList.size()-1));
				updateUi();
			}
		}else if(type.getType()==ActionEnum.cancel){
			listener.onCancelPay();
		}else if(type.getType()==ActionEnum.sure){
			if(mList.size()<6){
				Toast.makeText(mContext, "支付密码必须6位", Toast.LENGTH_SHORT).show();
			}else{
				String payValue="";
				for (int i = 0; i < mList.size(); i++) {
					payValue+=mList.get(i);
				}
				listener.onSurePay(payValue);
			}
		}else if(type.getType()==ActionEnum.longClick){
			mList.clear();
			updateUi();
		}

	}
	private void updateUi() {
		// TODO Auto-generated method stub
		if(mList.size()==0){
			box1.setText("");
			box2.setText("");
			box3.setText("");
			box4.setText("");
			box5.setText("");
			box6.setText("");
		}else if(mList.size()==1){
			box1.setText(mList.get(0));
			box2.setText("");
			box3.setText("");
			box4.setText("");
			box5.setText("");
			box6.setText("");
		}else if(mList.size()==2){
			box1.setText(mList.get(0));
			box2.setText(mList.get(1));
			box3.setText("");
			box4.setText("");
			box5.setText("");
			box6.setText("");
		}else if(mList.size()==3){
			box1.setText(mList.get(0));
			box2.setText(mList.get(1));
			box3.setText(mList.get(2));
			box4.setText("");
			box5.setText("");
			box6.setText("");
		}else if(mList.size()==4){
			box1.setText(mList.get(0));
			box2.setText(mList.get(1));
			box3.setText(mList.get(2));
			box4.setText(mList.get(3));
			box5.setText("");
			box6.setText("");
		}else if(mList.size()==5){
			box1.setText(mList.get(0));
			box2.setText(mList.get(1));
			box3.setText(mList.get(2));
			box4.setText(mList.get(3));
			box5.setText(mList.get(4));
			box6.setText("");
		}else if(mList.size()==6){
			box1.setText(mList.get(0));
			box2.setText(mList.get(1));
			box3.setText(mList.get(2));
			box4.setText(mList.get(3));
			box5.setText(mList.get(4));
			box6.setText(mList.get(5));
		}
	}

	public interface OnPayListener{
		void onCancelPay();
		void onSurePay(String password);
	}

	public View getView(){
		return mView;
	}
	public String getPassword(){
		return mList.toString();
	}

}
