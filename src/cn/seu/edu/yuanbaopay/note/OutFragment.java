package cn.seu.edu.yuanbaopay.note;

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
import android.widget.SimpleAdapter;

import com.example.yuanbaopay.R;

public class OutFragment extends Fragment {
	private GridView gridView;
	private SimpleAdapter adapter;
	private List dataList;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gridview_add_note, container, false);
		gridView = (GridView) view.findViewById(R.id.gridView_addnote);
		dataList = new ArrayList<Map<String, Object>>();
		adapter = new SimpleAdapter(getActivity(), getData(),
				R.layout.item_grid, new String[] { "pic", "name" }, new int[] {
						R.id.ItemImage, R.id.ItemText });
		gridView.setAdapter(adapter);
		return view;
	}

	private List<Map<String, Object>> getData() {

		int[] drawable = { R.drawable.communicate, R.drawable.entertainment,
				R.drawable.findfriend, R.drawable.resturant,
				R.drawable.shopping, R.drawable.snack,
				R.drawable.sport, R.drawable.utility };
		String[] iconName = { "通讯", "娱乐", "交友", "餐饮", "购物", "小吃", "运动", "水电"};
		for (int i = 0; i < drawable.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pic", drawable[i]);
			map.put("name", iconName[i]);
			dataList.add(map);
		}
		return dataList;
	}
}
