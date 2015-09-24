package cn.seu.edu.yuanbaopay.note;

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

public class NoteActivity extends Activity implements OnClickListener {
	private Button btnAdd;
	private TextView tvTitle;
	private TextView tvBack;
	private ListView lvNote;
	private SimpleAdapter adapter;
	private List dataList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_note);
		
		initView();
		initListView();
	}

	private void initListView() {
		dataList = new ArrayList<Map<String, Object>>();
		adapter = new SimpleAdapter(this, getData(),
				R.layout.activity_redpacket_mypacket_list_item, new String[] {"image", "type","people","money"  }, new int[] {
			R.id.img_portrait_redreceive, R.id.tv_type_redreceive,R.id.tv_people_redreceive,R.id.tv_money_redreceive});
		lvNote.setAdapter(adapter);
		
	}

	private List<Map<String, Object>> getData() {

		int[] image = { R.drawable.ic_launcher, R.drawable.ic_launcher,
				R.drawable.ic_launcher };
		String[] type ={"传统红包","传统红包","知心红包"};
		String[] people={"tia","dongge","xiaopang"};
		String[] money={"22","32","99"};
		for (int i = 0; i < image.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", image[i]);
			map.put("type",type[i]);
			map.put("people",people[i]);
			map.put("money",money[i]);
			dataList.add(map);
		}
		return dataList;
	}

	private void initView() {
		btnAdd = (Button) findViewById(R.id.btn_add_note);
		btnAdd.setOnClickListener(this);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("记账本");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		lvNote=(ListView)findViewById(R.id.list_note);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add_note:
			Intent i = new Intent(NoteActivity.this, AddNoteActivity.class);
			startActivity(i);
			break;
		case R.id.title_back:
			finish();
			break;
		default:
			break;
		}
	}

}
