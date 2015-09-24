package cn.seu.edu.yuanbaopay.note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.yuanbaopay.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

public class InFragment extends Fragment {

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

		int[] drawable = { R.drawable.cash_gift, R.drawable.concurrently,
				R.drawable.manage_money, R.drawable.salary };
		String[] iconName = { "礼金", "兼职", "理财", "薪酬" };
		for (int i = 0; i < drawable.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pic", drawable[i]);
			map.put("name", iconName[i]);
			dataList.add(map);
		}
		return dataList;
	}

}
