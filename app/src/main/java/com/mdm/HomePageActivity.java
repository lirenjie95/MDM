package com.mdm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomePageActivity extends AppCompatActivity {
  private Button mailsButton;
  private Button packagesButton;

  /**
   * Packages management.
   * @param savedInstanceState previous saved a bundle of objects.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.homepage);
    mailsButton = (Button) findViewById(R.id.mailsButton);
    mailsButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(HomePageActivity.this, MailsActivity.class);
        startActivity(intent);
      }
    });
    packagesButton = (Button) findViewById(R.id.packagesButton);
    packagesButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(HomePageActivity.this, PackagesActivity.class);
        startActivity(intent);
      }
    });
    //TODO: complete the whole event design.
  }
}
