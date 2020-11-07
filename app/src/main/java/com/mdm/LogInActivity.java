package com.mdm;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {
  private EditText email;
  private EditText password;
  private Button logInButton;

  /**
   * Create the sign up page upon entry.
   * @param savedInstanceState previous saved a bundle of objects.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login);
    email = (EditText) findViewById(R.id.email);
    password = (EditText) findViewById(R.id.password);
    logInButton = (Button) findViewById(R.id.logInButton);
    logInButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String strEmail = email.getText().toString();
        String strPassword = password.getText().toString();
        if (checkInfo(strEmail, strPassword)) {
          Intent intent = new Intent(LogInActivity.this, HomePageActivity.class);
          startActivity(intent);
        }
        finish();
      }
    });
  }

  /**
   * Check if the information passed by users have one record in the database.
   * @param strEmail email (String type).
   * @param strPassword password (String type).
   * @return true if all info are correct.
   */
  private boolean checkInfo(String strEmail, String strPassword) {
    if (TextUtils.isEmpty(strEmail)) {
      Toast.makeText(getApplicationContext(), "Email can't be null.", Toast.LENGTH_LONG).show();
      return false;
    }
    if (TextUtils.isEmpty(strPassword)) {
      Toast.makeText(getApplicationContext(), "Password can't be null.", Toast.LENGTH_LONG).show();
      return false;
    }
    //TODO: check the database to find username & password.
    return true;
  }
}
