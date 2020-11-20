package com.mdm.ui.archive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.mdm.R;
import java.util.List;
import java.util.Map;

public class ArchiveViewAdapter extends BaseAdapter {
  private Context context;
  private List<Map<String, String>> archiveDataList;

  /**
   * Default ArchiveViewAdapter constructor.
   * @param context activity context
   * @param archiveDataList data info of archive mails
   */
  public ArchiveViewAdapter(Context context, List<Map<String, String>> archiveDataList) {
    this.context = context;
    this.archiveDataList = archiveDataList;
  }

  @Override
  public int getCount() {
    return archiveDataList.size();
  }

  @Override
  public Object getItem(int position) {
    return archiveDataList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View view, ViewGroup viewGroup) {
    Archive archive = null;
    if (view == null) {
      archive = new Archive();
      view = LayoutInflater.from(context).inflate(R.layout.archive_list, null);
      archive.category = (TextView) view.findViewById(R.id.archiveCateInfo);
      archive.date = (TextView) view.findViewById(R.id.archiveDateInfo);
      archive.sender = (TextView) view.findViewById(R.id.archiveSenderInfo);
      view.setTag(archive);
    } else {
      archive = (Archive) view.getTag();
    }
    Map<String, String> map = archiveDataList.get(position);
    archive.category.setText(map.get("Category"));
    archive.date.setText(map.get("Date"));
    archive.sender.setText(map.get("Sender"));
    return view;
  }

  private class Archive {
    TextView category;
    TextView date;
    TextView sender;
  }
}