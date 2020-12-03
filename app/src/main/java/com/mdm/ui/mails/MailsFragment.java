package com.mdm.ui.mails;

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

public class MailsFragment extends Fragment {
  private ListView mailsListView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
               Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_mails, container, false);
    mailsListView = (ListView) view.findViewById(R.id.mails_list);
    List<Map<String, String>> mailsDataList = getMailsData();
    mailsListView.setAdapter(new MailsViewAdapter(getActivity(), mailsDataList));
    return view;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    ImageButton menuButton = getActivity().findViewById(R.id.menuButton0);
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
    List<Map<String, String>> mailsDataList = getMailsData();
    mailsListView.setAdapter(new MailsViewAdapter(getActivity(), mailsDataList));
  }

  private List<Map<String, String>> getMailsData() {
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    MdmOpenHelper mdmOpenHelper = MdmOpenHelper.getInstance(getActivity());
    Cursor cursor = mdmOpenHelper.getAllSubscribedMails();
    while (cursor.moveToNext()) {
      Map<String, String> map = new HashMap<String, String>();
      map.put("Date", cursor.getString(cursor.getColumnIndex("Date")));
      map.put("Sender", cursor.getString(cursor.getColumnIndex("Sender")));
      map.put("Category", cursor.getString(cursor.getColumnIndex("Category")));
      map.put("Unsubscribed", cursor.getString(cursor.getColumnIndex("Unsubscribed")));
      map.put("MailPhotoId", cursor.getString(cursor.getColumnIndex("MailPhotoId")));
      map.put("Status", cursor.getString(cursor.getColumnIndex("Status")));
      list.add(map);
    }
    cursor.close();
    return list;
  }
}
