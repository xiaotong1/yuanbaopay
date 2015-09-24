package cn.seu.edu.yuanbaopay.bill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import cn.seu.edu.yuanbaopay.pay.PayActivity;

import com.example.yuanbaopay.R;

public class BillActivity extends Activity implements OnClickListener {
	private TextView tv_title;
	private TextView tvBack;
	private ListView lv_bill;
	private SimpleAdapter adapter;
	private List dataList;
	private List<String> type;
	private List<String> name;;
	private List<String> money;
	private SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		pref = PreferenceManager.getDefaultSharedPreferences(BillActivity.this);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bill);
		init();
		initListView();
	}

	private void init() {
		lv_bill = (ListView) findViewById(R.id.list_bill);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("’Àµ•");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);

	}

	private void initListView() {
		dataList = new ArrayList<Map<String, Object>>();
		adapter = new SimpleAdapter(this, getData(),
				R.layout.activity_redpacket_mypacket_list_item, new String[] {
						"type", "people", "money" }, new int[] {
						R.id.tv_type_redreceive, R.id.tv_people_redreceive,
						R.id.tv_money_redreceive });
		lv_bill.setAdapter(adapter);

	}

	private List<Map<String, Object>> getData() {
		name = new ArrayList<String>();
		type = new ArrayList<String>();
		money = new ArrayList<String>();
		if(pref.contains("paytype")){
			String paytype = pref.getString("paytype", null);
			if(pref.contains("payname")){
				String payname = pref.getString("payname", null);
				if (!payname.equals(null)) {
					name.add(payname);
					type.add(paytype);
				}
			}
			if(pref.contains("paymoney")){
				String paymoney = pref.getString("paymoney", null);
				if (!paymoney.equals(null)) {
					money.add(paymoney);
				}
			}
		}

		if(pref.contains("transfertype")){
			String transfertype = pref.getString("transfertype", null);
			
			if(pref.contains("transfername")){
				String transfername= pref.getString("transfername", null);
				if (!transfername.equals(null)) {
					name.add(transfername);
					type.add(transfertype);
				}
			}
			if(pref.contains("transfermoney")){
				String transfermoney= pref.getString("transfermoney", null);
				if (!transfermoney.equals(null)) {
					money.add(transfermoney);
				}
			}
		}

		if(pref.contains("luckytype")){
			String luckytype = pref.getString("luckytype", null);
			if(pref.contains("luckyname")){
				String luckyname = pref.getString("luckyname", null);
				if (!luckyname.equals(null)) {
					name.add(luckyname);
					type.add(luckytype);
				}
			}
			
			if(pref.contains("luckymoney")){
				String luckymoney = pref.getString("luckymoney", null);
				if (!luckymoney.equals(null)) {
					money.add(luckymoney);
				}
			}
		}

		if(pref.contains("traditiontype")){
			String traditiontype = pref.getString("traditiontype", null);
			if(pref.contains("traditionname")){
				String traditionname = pref.getString("traditionname", null);
				if (!traditionname.equals(null)) {
					name.add(traditionname);
					type.add(traditiontype);
				}
			}
			
			if(pref.contains("traditionmoney")){
				String traditionmoney = pref.getString("traditionmoney", null);

				if (!traditionmoney.equals(null)) {
					money.add(traditionmoney);
				}
				
			}
		}

		if(pref.contains("intitype")){
			String intitype = pref.getString("intitype", null);
			if(pref.contains("intiname")){
				String intiname = pref.getString("intiname", null);

				if (!intiname.equals(null)) {
					name.add(intiname);
					type.add(intitype);
				}

			}
			if(pref.contains("intimoney")){
				String intimoney = pref.getString("intimoney", null);
				if (!intimoney.equals(null)) {
					money.add(intimoney);
				}
			}
		}
		

		for (int i = 0; i < type.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("type", type.get(i));

			map.put("people", name.get(i));

			map.put("money", money.get(i));

			dataList.add(map);
		}
		return dataList;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		default:
			break;
		}
	}
}
