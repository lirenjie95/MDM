package com.mdm;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
  private EditText email;
  private EditText password;
  private EditText confirmPassword;
  private EditText address;
  private Button signUpButton;
  private static final int PASSWORD_LEN = 6;

  /**
   * Create the sign up page upon entry.
   * @param savedInstanceState previous saved a bundle of objects.
   */
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
          //TODO: insert new user into database
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
    // check for the info.
    MdmOpenHelper dbHelper = new MdmOpenHelper(this);
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM " +
        dbHelper.USER_INFO_TABLE +
        "WHERE email = ?",
        new String[]{strEmail});
    if (cursor.getCount() > 0) {
      Toast.makeText(getApplicationContext(),
          "We already have this account. Please go back and login.", Toast.LENGTH_LONG).show();
      cursor.close();
      db.close();
      return false;
    }
    cursor.close();
    // new user, put those info into database.
    ContentValues values = new ContentValues();
    values.put("email", strEmail);
    values.put("password", strPassword);
    values.put("address", strAddress);
    db.insert(dbHelper.USER_INFO_TABLE, null, values);
    db.close();
    return true;
  }
}
