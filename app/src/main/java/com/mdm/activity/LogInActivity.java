package com.mdm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.mdm.MdmOpenHelper;
import com.mdm.R;

public class LogInActivity extends AppCompatActivity {
  private EditText email;
  private EditText password;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login);
    email = (EditText) findViewById(R.id.email);
    password = (EditText) findViewById(R.id.password);
    Button logInButton = (Button) findViewById(R.id.logInButton);
    logInButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String strEmail = email.getText().toString();
        String strPassword = password.getText().toString();
        if (checkInfo(strEmail, strPassword)) {
          Intent intent = new Intent(LogInActivity.this, HomePageActivity.class);
          intent.putExtra("email", strEmail);
          startActivity(intent);
        }
        finish();
      }
    });
    Button forgetPassButton = (Button) findViewById(R.id.forgetPassButton);
    forgetPassButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(LogInActivity.this, NotFunctionalActivity.class);
        startActivity(intent);
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
    // try to login
    MdmOpenHelper dbHelper = MdmOpenHelper.getInstance(this);
    boolean found = dbHelper.findLogin(strEmail, strPassword);
    if (!found) {
      Toast.makeText(getApplicationContext(), "Password didn't match.", Toast.LENGTH_LONG).show();
    }
    return found;
  }
}
