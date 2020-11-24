package com.mdm.ui.packages;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import com.mdm.MdmOpenHelper;
import com.mdm.R;
import com.mdm.activity.NotFunctionalActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackagesFragment extends Fragment {
  private ListView packagesListView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_packages, container, false);
    packagesListView = (ListView) view.findViewById(R.id.packages_list);
    List<Map<String, String>> packagesDataList = getPackagesData();
    packagesListView.setAdapter(new PackagesViewAdapter(getActivity(), packagesDataList));
    return view;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    ImageButton menuButton = getActivity().findViewById(R.id.menuButton);
    menuButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getActivity(), NotFunctionalActivity.class);
        startActivity(intent);
      }
    });
  }

  private List<Map<String, String>> getPackagesData() {
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    MdmOpenHelper mdmOpenHelper = MdmOpenHelper.getInstance(getActivity());
    Cursor cursor = mdmOpenHelper.getAllPackages();
    while (cursor.moveToNext()) {
      Map<String, String> map = new HashMap<String, String>();
      map.put("Category", cursor.getString(cursor.getColumnIndex("Category")));
      map.put("Date", cursor.getString(cursor.getColumnIndex("Date")));
      map.put("Package Number", cursor.getString(cursor.getColumnIndex("PackageNumber")));
      map.put("Status", cursor.getString(cursor.getColumnIndex("Status")));
      list.add(map);
    }
    cursor.close();
    return list;
  }
}
