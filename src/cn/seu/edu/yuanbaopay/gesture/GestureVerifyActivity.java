package cn.seu.edu.yuanbaopay.gesture;

import com.example.yuanbaopay.R;

import cn.seu.edu.yuanbaopay.gesture.GestureDrawline.GestureCallBack;
import cn.seu.edu.yuanbaopay.main.LoginActivity;
import cn.seu.edu.yuanbaopay.main.MainActivity;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * 手势绘制/校验界面
 *
 */
public class GestureVerifyActivity extends Activity implements android.view.View.OnClickListener {
	/** 手机号码*/
	public static final String PARAM_PHONE_NUMBER = "PARAM_PHONE_NUMBER";
	/** 意图 */
	public static final String PARAM_INTENT_CODE = "PARAM_INTENT_CODE";
	private RelativeLayout mTopLayout;
	private TextView mTextTitle;
	private TextView mTextCancel;
	private ImageView mImgUserLogo;
	private TextView mTextPhoneNumber;
	private TextView mTextTip;
	private FrameLayout mGestureContainer;
	private GestureContentView mGestureContentView;
	private TextView mTextForget;
	private TextView mTextOther;
	private String mParamPhoneNumber;
	private long mExitTime = 0;
	private int mParamIntentCode;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	private String pwd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_gesture_verify);
		ObtainExtraData();
		setUpViews();
		setUpListeners();
	}
	
	private void ObtainExtraData() {
		pref=PreferenceManager.getDefaultSharedPreferences(GestureVerifyActivity.this);
		editor=pref.edit();
		mParamPhoneNumber = pref.getString("phonenum", "18066082826");
		pwd=pref.getString("gesturepwd", "1235789");
	}
	
	private void setUpViews() {
		mTopLayout = (RelativeLayout) findViewById(R.id.top_layout);
		mTextTitle = (TextView) findViewById(R.id.text_title);
		mTextCancel = (TextView) findViewById(R.id.text_cancel);
		mImgUserLogo = (ImageView) findViewById(R.id.user_logo);
		mTextPhoneNumber = (TextView) findViewById(R.id.text_phone_number);
		mTextTip = (TextView) findViewById(R.id.text_tip);
		mGestureContainer = (FrameLayout) findViewById(R.id.gesture_container);

		
		
		// 初始化一个显示各个点的viewGroup
		mGestureContentView = new GestureContentView(this, true, pwd,
				new GestureCallBack() {

					@Override
					public void onGestureCodeInput(String inputCode) {

					}

					@Override
					public void checkedSuccess() {
						mGestureContentView.clearDrawlineState(0L);
						Toast.makeText(GestureVerifyActivity.this, "密码正确", 1000).show();
						GestureVerifyActivity.this.finish();
						//进入功能界面
						Intent i_main = new Intent(GestureVerifyActivity.this,
								MainActivity.class);
						startActivity(i_main);
					}

					@Override
					public void checkedFail() {
						mGestureContentView.clearDrawlineState(1300L);
						mTextTip.setVisibility(View.VISIBLE);
						mTextTip.setText(Html
								.fromHtml("<font color='#c70c1e'>密码错误</font>"));
						// 左右移动动画
						Animation shakeAnimation = AnimationUtils.loadAnimation(GestureVerifyActivity.this, R.anim.shake);
						mTextTip.startAnimation(shakeAnimation);
//						mGestureContentView.clearDrawlineState(1300L);
					}
				});
		// 设置手势解锁显示到哪个布局里面
		mGestureContentView.setParentView(mGestureContainer);
	}
	
	private void setUpListeners() {
		mTextCancel.setOnClickListener(this);
	}
	
	private String getProtectedMobile(String phoneNumber) {
		if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 11) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		builder.append(phoneNumber.subSequence(0,3));
		builder.append("****");
		builder.append(phoneNumber.subSequence(7,11));
		//append()是往动态字符串数组添加,与String相加在内存上不一样，是在一起的
		return builder.toString();
	}
	
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text_cancel:
			this.finish();
			break;
		default:
			break;
		}
	}
	
}
