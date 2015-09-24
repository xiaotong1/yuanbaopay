package cn.seu.edu.yuanbaopay.intimatepay;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.seu.edu.yuanbaopay.main.MainActivity;
import cn.seu.edu.yuanbaopay.note.AddNoteActivity;
import cn.seu.edu.yuanbaopay.note.NoteActivity;

import com.example.yuanbaopay.R;
import com.example.yuanbaopay.R.color;

public class IntimatePayStateActivity extends Activity implements OnClickListener{

	private RelativeLayout rlTitle;
	private TextView tvTitle;
	private TextView tvBack;
	private Button btntointimatemain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_intimatepay_state);
		initView();
	}

	private void initView() {
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("亲密付");
		tvTitle.setTextColor(Color.WHITE);
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		tvBack.setTextColor(Color.WHITE);
		rlTitle = (RelativeLayout) findViewById(R.id.title_layout);
		rlTitle.setBackgroundColor(Color.rgb(227, 155, 138));
		btntointimatemain = (Button)findViewById(R.id.btn_intimatepaystate_tointimatemain);
		btntointimatemain.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.btn_intimatepaystate_tointimatemain:
			Intent i = new Intent(IntimatePayStateActivity.this, MainActivity.class);
			startActivity(i);
			break;
		}
	}
}
