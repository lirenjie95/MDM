package com.mdm.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.mdm.MdmOpenHelper;
import com.mdm.R;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class MailsActivity extends AppCompatActivity implements View.OnClickListener {
  private Context context;
  private int position;
  private List<Map<String, String>> mailsDataList;
  private String mailPhotoId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.mails);
    context = MailsActivity.this;
    Intent intent = getIntent();
    position = (int) intent.getIntExtra("position", -1);
    mailsDataList = (List<Map<String, String>>) intent.getSerializableExtra("mailsDataList");
    Map<String, String> map = mailsDataList.get(position);
    mailPhotoId = map.get("MailPhotoId");
    bindView(map);
    bindButton();
  }

  @SuppressLint("UseCompatLoadingForDrawables")
  private void bindView(Map<String, String> map) {
    TextView mailDateInfo = (TextView) findViewById(R.id.mailDateInfo);
    String estimatedDelivery = "Estimated Delivery:" + map.get("Date");
    mailDateInfo.setText(estimatedDelivery);
    TextView mailCateInfo = (TextView) findViewById(R.id.mailCateInfo);
    mailCateInfo.setText(map.get("Category"));
    TextView mailStatusInfo = (TextView) findViewById(R.id.mailStatusInfo);
    mailStatusInfo.setText(map.get("Status"));
    TextView mailFromInfo = (TextView) findViewById(R.id.mailFromInfo);
    mailFromInfo.setText(map.get("Sender"));
    ApplicationInfo appInfo = getApplicationInfo();
    int picId = getResources().getIdentifier(mailPhotoId, "drawable", appInfo.packageName);
    ImageView mailImageView = (ImageView) findViewById(R.id.mailImageView);
    mailImageView.setImageDrawable(getResources().getDrawable(picId));
  }

  private void bindButton() {
    Button unsubscribeButton = (Button) findViewById(R.id.unsubscribeButton);
    unsubscribeButton.setOnClickListener(this);
    ImageButton returnButton = (ImageButton) findViewById(R.id.returnButton);
    returnButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    Button keepReceiveButton = (Button) findViewById(R.id.keepReceiveButton);
    keepReceiveButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(context, "Will move to archive.",
            Toast.LENGTH_SHORT).show();
        MdmOpenHelper mdmOpenHelper = MdmOpenHelper.getInstance(context);
        mdmOpenHelper.archiveMail(mailPhotoId);
        finish();
      }
    });
    ImageButton menuButton = (ImageButton) findViewById(R.id.menuButton);
    menuButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(context, NotFunctionalActivity.class);
        startActivity(intent);
      }
    });
    ImageButton previousButton = (ImageButton) findViewById(R.id.previousButton);
    if (position != 0) {
      previousButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent(context, MailsActivity.class);
          intent.putExtra("position", position - 1);
          intent.putExtra("mailsDataList", (Serializable) mailsDataList);
          startActivity(intent);
          finish();
        }
      });
    }
    ImageButton nextButton = (ImageButton) findViewById(R.id.nextButton);
    if (position != mailsDataList.size() - 1) {
      nextButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent(context, MailsActivity.class);
          intent.putExtra("position", position + 1);
          intent.putExtra("mailsDataList", (Serializable) mailsDataList);
          startActivity(intent);
          finish();
        }
      });
    }
  }

  @Override
  public void onClick(View v) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    AlertDialog alert = builder.setTitle("Notice: ")
        .setMessage("Please confirm that you want to cancel the subscription. "
            + "You cannot change the setting once you confirm the cancellation. "
            + "Note: You will still receive the mail this time. "
            + "We will let the sender know you unsubscribe this kind of mail right away.")
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(context, "You will keep receiving this mail.",
                Toast.LENGTH_SHORT).show();
          }
        })
        .setPositiveButton("Confirm Unsubscribe", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(context, "Your subscription is successful.",
                Toast.LENGTH_SHORT).show();
            MdmOpenHelper mdmOpenHelper = MdmOpenHelper.getInstance(context);
            mdmOpenHelper.unsubscribeMail(mailPhotoId);
            mdmOpenHelper.archiveMail(mailPhotoId);
            finish();
          }
        }).create();
    alert.show();
  }
}
