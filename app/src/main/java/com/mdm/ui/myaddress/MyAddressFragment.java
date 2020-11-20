package com.mdm.ui.myaddress;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.mdm.MdmOpenHelper;
import com.mdm.R;
import com.mdm.activity.AddAddressActivity;
import com.mdm.activity.NotFunctionalActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAddressFragment extends Fragment {
  private ListView addressListView;
  String strEmail;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_address, container, false);
    addressListView = (ListView) view.findViewById(R.id.address_list);
    List<Map<String, String>> addressDataList = getAddressData();
    addressListView.setAdapter(new MyAddressViewAdapter(getActivity(), addressDataList));
    if (getArguments() != null) {
      strEmail = getArguments().getString("email");
    }
    return view;
  }

  /**
   * Used for create a MyAddressFragment with parameter.
   * @param strEmail the string type user email, as a parameter
   * @return a MyAddressFragment with email
   */
  public static MyAddressFragment newInstance(String strEmail) {
    MyAddressFragment myAddressFragment = new MyAddressFragment();
    Bundle bundle = new Bundle();
    bundle.putString("email", strEmail);
    myAddressFragment.setArguments(bundle);
    return myAddressFragment;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Button menuButton = getActivity().findViewById(R.id.menuButton);
    menuButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getActivity(), NotFunctionalActivity.class);
        startActivity(intent);
      }
    });
    TextView nameInfo = (TextView) getActivity().findViewById(R.id.nameInfo);
    nameInfo.setText(strEmail);
    Button addAddressButton = (Button) getActivity().findViewById(R.id.addAddressButton);
    addAddressButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getActivity(), AddAddressActivity.class);
        intent.putExtra("email", strEmail);
        startActivity(intent);
      }
    });
  }

  private List<Map<String, String>> getAddressData() {
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    MdmOpenHelper mdmOpenHelper = new MdmOpenHelper(getActivity());
    Cursor cursor = mdmOpenHelper.getUserAddress(strEmail);
    while (cursor.moveToNext()) {
      Map<String, String> map = new HashMap<String, String>();
      map.put("Name", cursor.getString(cursor.getColumnIndex("email")));
      map.put("Address", cursor.getString(cursor.getColumnIndex("address")));
      list.add(map);
    }
    cursor.close();
    return list;
  }
}
