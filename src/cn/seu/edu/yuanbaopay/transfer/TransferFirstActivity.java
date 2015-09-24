package cn.seu.edu.yuanbaopay.transfer;

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

import com.example.yuanbaopay.R;

public class TransferFirstActivity extends Activity implements OnClickListener {
	private Button btnTransfer2;
	private TextView tvTitle;
	private TextView tvBack;
	private Button btnToBank;
	private ListView lvRecent;
	private SimpleAdapter adapter;
	private List dataList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_transfer);
		initView();
		initListView();
	}

	private void initListView() {
		lvRecent = (ListView) findViewById(R.id.list_recent);
		dataList = new ArrayList<Map<String, Object>>();
		adapter = new SimpleAdapter(this, getData(),
				R.layout.item_list_recent_contactpeople, new String[] {
						"image", "people" }, new int[] {
						R.id.img_portrait_recent, R.id.tv_people_recent });
		lvRecent.setAdapter(adapter);

	}

	private List<Map<String, Object>> getData() {

		int[] image = { R.drawable.ic_launcher, R.drawable.ic_launcher,
				R.drawable.ic_launcher };
		String[] people = { "dongge", "tia", "xiaopang" };
		for (int i = 0; i < image.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", image[i]);
			map.put("people", people[i]);
			dataList.add(map);
		}
		return dataList;
	}

	private void initView() {
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("в╙ук");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		btnTransfer2 = (Button) findViewById(R.id.btn_transfer1);
		btnTransfer2.setOnClickListener(this);
		btnToBank = (Button) findViewById(R.id.btn_transfer_to_bankcard);
		btnToBank.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_transfer1:
			Intent i = new Intent(TransferFirstActivity.this,
					TransferSecondActivity.class);
			startActivity(i);

			break;
		case R.id.title_back:
			finish();
			break;
		case R.id.btn_transfer_to_bankcard:
			Intent ito_bank = new Intent(TransferFirstActivity.this,
					TransferToBankActivity.class);
			startActivity(ito_bank);
			break;
		default:
			break;
		}
	}

}
