package com.mdm.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.mdm.MdmOpenHelper;
import com.mdm.R;

public class AddAddressActivity extends AppCompatActivity {
  private TextView email;
  private EditText newAddress;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.add_address);
    email = (TextView) findViewById(R.id.textPersonName);
    String strEmail = (String) getIntent().getStringExtra("email");
    email.setText(strEmail);
    newAddress = (EditText) findViewById(R.id.newAddress);
    Button confirmButton = (Button) findViewById(R.id.confirmButton);
    confirmButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (TextUtils.isEmpty(newAddress.getText().toString())) {
          Toast.makeText(getApplicationContext(), "New Address can't be null.",
              Toast.LENGTH_SHORT).show();
          return;
        }
        String strAddress = newAddress.getText().toString();
        Toast.makeText(getApplicationContext(), "New Address has been added.",
            Toast.LENGTH_SHORT).show();
        MdmOpenHelper dbHelper = new MdmOpenHelper(AddAddressActivity.this);
        dbHelper.addNewAddress(email.getText().toString(), strAddress);
        finish();
      }
    });
    Button cancelButton = (Button) findViewById(R.id.cancelButton);
    cancelButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(getApplicationContext(), "Discard changes.", Toast.LENGTH_SHORT).show();
        finish();
      }
    });
  }
}
