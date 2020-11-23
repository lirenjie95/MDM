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

public class SignUpActivity extends AppCompatActivity {
  private EditText email;
  private EditText password;
  private EditText confirmPassword;
  private EditText address;
  private Button signUpButton;
  private static final int PASSWORD_LEN = 6;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.signup);
    email = (EditText) findViewById(R.id.email);
    password = (EditText) findViewById(R.id.password);
    confirmPassword = (EditText) findViewById(R.id.confirmPassword);
    address = (EditText) findViewById(R.id.address);
    signUpButton = (Button) findViewById(R.id.signUpButton);
    signUpButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String strEmail = email.getText().toString();
        String strPassword = password.getText().toString();
        String strConfirmPassword = confirmPassword.getText().toString();
        String strAddress = address.getText().toString();
        if (checkInfo(strEmail, strPassword, strConfirmPassword, strAddress)) {
          Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
          startActivity(intent);
        }
        finish();
      }
    });
  }

  /**
   * Check if the information passed by users are valid.
   * @param strEmail email (String type).
   * @param strPassword password (String type).
   * @param strConfirmPassword confirm password (String type).
   * @param strAddress address (String type).
   * @return true if all info are valid.
   */
  private boolean checkInfo(String strEmail, String strPassword, String strConfirmPassword,
                            String strAddress) {
    if (TextUtils.isEmpty(strEmail)) {
      Toast.makeText(getApplicationContext(), "Email can't be null.", Toast.LENGTH_LONG).show();
      return false;
    }
    if (TextUtils.isEmpty(strPassword)) {
      Toast.makeText(getApplicationContext(), "Password can't be null.", Toast.LENGTH_LONG).show();
      return false;
    }
    if (strPassword.length() < PASSWORD_LEN) {
      Toast.makeText(getApplicationContext(), "Password should have at least 6 characters.",
          Toast.LENGTH_LONG).show();
      return false;
    }
    if (!strPassword.equals(strConfirmPassword)) {
      Toast.makeText(getApplicationContext(), "Password didn't match.", Toast.LENGTH_LONG).show();
      return false;
    }
    // check for the info.
    MdmOpenHelper dbHelper = MdmOpenHelper.getInstance(this);
    boolean success = dbHelper.insertNewUser(strEmail, strPassword, strAddress);
    if (!success) {
      Toast.makeText(getApplicationContext(),
          "We already have this account. Please go back and login.", Toast.LENGTH_LONG).show();
    }
    return success;
  }
}
