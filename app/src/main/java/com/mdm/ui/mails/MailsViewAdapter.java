package com.mdm.ui.mails;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.mdm.R;
import com.mdm.activity.MailsActivity;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class MailsViewAdapter extends BaseAdapter {
  private Context context;
  private List<Map<String, String>> mailsDataList;

  /**
   * Default MailsViewAdapter constructor.
   * @param context activity context
   * @param mailsDataList data info of packages
   */
  public MailsViewAdapter(Context context, List<Map<String, String>> mailsDataList) {
    this.context = context;
    this.mailsDataList = mailsDataList;
  }

  @Override
  public int getCount() {
    return mailsDataList.size();
  }

  @Override
  public Object getItem(int position) {
    return mailsDataList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View view, ViewGroup viewGroup) {
    Mails mails = null;
    if (view == null) {
      mails = new Mails();
      view = LayoutInflater.from(context).inflate(R.layout.mails_list, null);
      mails.category = (TextView) view.findViewById(R.id.mailsCateInfo);
      mails.date = (TextView) view.findViewById(R.id.mailsDateInfo);
      mails.sender = (TextView) view.findViewById(R.id.mailsFromInfo);
      mails.status = (TextView) view.findViewById(R.id.mailsStatusInfo);
      mails.openButton = (Button) view.findViewById(R.id.openButton);
      view.setTag(mails);
    } else {
      mails = (Mails) view.getTag();
    }
    Map<String, String> map = mailsDataList.get(position);
    mails.category.setText(map.get("Category"));
    mails.date.setText(map.get("Date"));
    mails.status.setText(map.get("Status"));
    mails.sender.setText(map.get("Sender"));
    mails.openButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(context, MailsActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("mailsDataList", (Serializable) mailsDataList);
        context.startActivity(intent);
      }
    });
    return view;
  }

  private class Mails {
    TextView category;
    TextView date;
    TextView sender;
    TextView status;
    Button openButton;
  }
}
