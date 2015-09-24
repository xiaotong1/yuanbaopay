package cn.seu.edu.yuanbaopay.redpacket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.yuanbaopay.R;

public class GiveFragment extends Fragment {
	private ListView listview;
	private SimpleAdapter adapter;
	private List dataList;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_myredpacket, container, false);
		listview = (ListView) view.findViewById(R.id.list_mypacket);
		dataList = new ArrayList<Map<String, Object>>();
		adapter = new SimpleAdapter(getActivity(), getData(),
				R.layout.activity_redpacket_mypacket_list_item, new String[] {"image", "type","people","money"  }, new int[] {
			R.id.img_portrait_redreceive, R.id.tv_type_redreceive,R.id.tv_people_redreceive,R.id.tv_money_redreceive});
		listview.setAdapter(adapter);
		return view;
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
}
