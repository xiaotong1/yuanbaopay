package cn.seu.edu.yuanbaopay.fortune;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.seu.edu.yuanbaopay.main.MainActivity;

import com.example.yuanbaopay.R;

public class FortuneMainActivity extends Activity implements OnClickListener{
	private TextView tv_title;
	private TextView tvBack;
	private TextView tvCard;
	private LinearLayout llCard;
	private LinearLayout llUser;
	private TextView tvMain;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_fortune_main);
		initView();
	}
	private void initView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("²Æ¸»");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		llCard=(LinearLayout)findViewById(R.id.layout_bankcard_fortunemain);
		llCard.setOnClickListener(this);
		tvCard=(TextView)findViewById(R.id.tv_num_bankcard);
		llUser=(LinearLayout)findViewById(R.id.layout_users_fortunemain);
		llUser.setOnClickListener(this);
		tvMain=(TextView)findViewById(R.id.tv_packet_main);
		tvMain.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.layout_bankcard_fortunemain:
			Intent i_card=new Intent(FortuneMainActivity.this,FortuneCardActivity.class);
			i_card.putExtra("init",false );
			startActivity(i_card);
			break;
		case R.id.tv_packet_main:
			Intent i_packet=new Intent(FortuneMainActivity.this,MainActivity.class);
			startActivity(i_packet);
			break;
		case R.id.layout_users_fortunemain:
			Intent i_users=new Intent(FortuneMainActivity.this,FortuneAccountMainActivity.class);
			startActivity(i_users);
			break;
		default:
			break;
		}
	}

}
