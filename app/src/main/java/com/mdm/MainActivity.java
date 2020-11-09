package com.mdm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
  private Button signUpButton;
  private Button logInButton;

  /**
   * A default main activity method. Provide entry point of login & sign-up.
   * @param savedInstanceState previous saved a bundle of objects.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setUpDatabase();
    setContentView(R.layout.initial);
    signUpButton = (Button) findViewById(R.id.signUpButton);
    signUpButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
      }
    });
    logInButton = (Button) findViewById(R.id.logInButton);
    logInButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, LogInActivity.class);
        startActivity(intent);
      }
    });
  }

  private void setUpDatabase() {
    MdmOpenHelper myDbHelper = new MdmOpenHelper(this);
    try {
      myDbHelper.createDataBase();
    } catch (IOException ioe) {
      throw new Error("Unable to create database");
    }
  }
}