package com.mdm.ui.archive;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import com.mdm.MdmOpenHelper;
import com.mdm.R;
import com.mdm.activity.NotFunctionalActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArchiveFragment extends Fragment {
  private ListView archiveListView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_archive, container, false);
    archiveListView = (ListView) view.findViewById(R.id.archive_list);
    List<Map<String, String>> archiveDataList = getMailsData();
    archiveListView.setAdapter(new ArchiveViewAdapter(getActivity(), archiveDataList));
    return view;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    ImageButton menuButton = getActivity().findViewById(R.id.menuButton3);
    menuButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getActivity(), NotFunctionalActivity.class);
        startActivity(intent);
      }
    });
  }

  @Override
  public void onResume() {
    super.onResume();
    List<Map<String, String>> archiveDataList = getMailsData();
    archiveListView.setAdapter(new ArchiveViewAdapter(getActivity(), archiveDataList));
  }

  private List<Map<String, String>> getMailsData() {
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    MdmOpenHelper mdmOpenHelper = MdmOpenHelper.getInstance(getActivity());
    Cursor cursor = mdmOpenHelper.getArchive();
    while (cursor.moveToNext()) {
      Map<String, String> map = new HashMap<String, String>();
      map.put("Date", cursor.getString(cursor.getColumnIndex("Date")));
      map.put("Sender", cursor.getString(cursor.getColumnIndex("Sender")));
      map.put("Category", cursor.getString(cursor.getColumnIndex("Category")));
      list.add(map);
    }
    cursor.close();
    return list;
  }
}
