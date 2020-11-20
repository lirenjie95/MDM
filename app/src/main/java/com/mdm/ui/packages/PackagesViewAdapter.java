package com.mdm.ui.packages;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.mdm.R;
import java.util.List;
import java.util.Map;

public class PackagesViewAdapter extends BaseAdapter {
  private Context context;
  private List<Map<String, String>> packagesDataList;

  /**
   * Default PackagesViewAdapter constructor.
   * @param context activity context
   * @param packagesDataList data info of packages
   */
  public PackagesViewAdapter(Context context, List<Map<String, String>> packagesDataList) {
    this.context = context;
    this.packagesDataList = packagesDataList;
  }

  @Override
  public int getCount() {
    return packagesDataList.size();
  }

  @Override
  public Object getItem(int position) {
    return packagesDataList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View view, ViewGroup viewGroup) {
    Packages packages = null;
    if (view == null) {
      packages = new Packages();
      view = LayoutInflater.from(context).inflate(R.layout.packages_list, null);
      packages.category = (TextView) view.findViewById(R.id.packageCateData);
      packages.date = (TextView) view.findViewById(R.id.packageDateData);
      packages.packageNumber = (TextView) view.findViewById(R.id.packageNumberData);
      packages.status = (TextView) view.findViewById(R.id.packageStatusData);
      view.setTag(packages);
    } else {
      packages = (Packages) view.getTag();
    }
    Map<String, String> map = packagesDataList.get(position);
    packages.category.setText(map.get("Category"));
    packages.date.setText(map.get("Date"));
    String html = String.format(
        "<a href='https://tools.usps.com/go/TrackConfirmAction?qtc_tLabels1=%s'>%s</a>",
        map.get("Package Number"), map.get("Package Number"));
    packages.packageNumber.setMovementMethod(LinkMovementMethod.getInstance());
    packages.packageNumber.setText(Html.fromHtml(html));
    packages.status.setText(map.get("Status"));
    return view;
  }

  private class Packages {
    TextView category;
    TextView date;
    TextView packageNumber;
    TextView status;
  }
}
