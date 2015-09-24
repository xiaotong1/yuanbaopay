package cn.seu.edu.yuanbaopay.fortune;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuanbaopay.R;

public class FortuneCardActivity extends Activity implements OnClickListener {
	private TextView tv_title;
	private TextView tvBack;
	private TextView btnAdd;
	private ListView listCard;
	private SimpleAdapter adapter;
	private List dataList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_fortune_card);
		initView();
		initListView();
	}

	private void initListView() {
		dataList = new ArrayList<Map<String, Object>>();
		adapter = new SimpleAdapter(this, getData(),
				R.layout.item_list_bankcard, new String[] { "image", "bank",
						"num" }, new int[] { R.id.img_portrait_bank,
						R.id.tv_type_bankcard, R.id.tv_lastnum_bankcard });
		listCard.setAdapter(adapter);
	}

	private List<Map<String, Object>> getData() {

		int[] image = { R.drawable.ic_launcher, R.drawable.ic_launcher };
		String[] bank = { "中国银行", "建设银行" };
		String[] num = { "7419", "5568" };
		for (int i = 0; i < image.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", image[i]);
			map.put("bank", bank[i]);
			map.put("num", num[i]);
			dataList.add(map);
		}
		return dataList;
	}

	private void initView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("我的银行卡");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		btnAdd = (Button) findViewById(R.id.btn_add_bankcard_fortune);
		btnAdd.setOnClickListener(this);
		listCard = (ListView) findViewById(R.id.list_card);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.btn_add_bankcard_fortune:
			Intent i_add = new Intent(FortuneCardActivity.this,
					FortuneAddCardActivity.class);
			startActivity(i_add);
			break;
		default:
			break;
		}
	}

}
