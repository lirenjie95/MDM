package com.mdm.ui.myaddress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.mdm.R;
import java.util.List;
import java.util.Map;

public class MyAddressViewAdapter extends BaseAdapter {
  private Context context;
  private List<Map<String, String>> addressDataList;

  /**
   * Default MyAddressViewAdapter constructor.
   * @param context activity context
   * @param addressDataList data info of archive mails
   */
  public MyAddressViewAdapter(Context context, List<Map<String, String>> addressDataList) {
    this.context = context;
    this.addressDataList = addressDataList;
  }

  @Override
  public int getCount() {
    return addressDataList.size();
  }

  @Override
  public Object getItem(int position) {
    return addressDataList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View view, ViewGroup viewGroup) {
    TextView addressInfo = null;
    if (view == null) {
      view = LayoutInflater.from(context).inflate(R.layout.address_list, null);
      addressInfo = (TextView) view.findViewById(R.id.addressInfo);
      view.setTag(addressInfo);
    } else {
      addressInfo = (TextView) view.getTag();
    }
    Map<String, String> map = addressDataList.get(position);
    addressInfo.setText(map.get("Address"));
    return view;
  }
}
