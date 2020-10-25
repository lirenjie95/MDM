package com.mdm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

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
    setContentView(R.layout.activity_main);
    signUpButton = (Button) findViewById(R.id.signUpButton);
    signUpButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
      }
    });
    logInButton = (Button) findViewById(R.id.logInButton);
    logInButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
        startActivity(intent);
      }
    });
  }
}