package com.mdm.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import com.mdm.R;
import com.mdm.ui.archive.ArchiveFragment;
import com.mdm.ui.mails.MailsFragment;
import com.mdm.ui.myaddress.MyAddressFragment;
import com.mdm.ui.packages.PackagesFragment;

public class HomePageActivity extends AppCompatActivity
    implements RadioGroup.OnCheckedChangeListener {
  private Fragment mailsFragment;
  private Fragment packagesFragment;
  private Fragment myAddressFragment;
  private Fragment archiveFragment;
  private FragmentManager fm;
  private String strEmail;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    strEmail = (String) getIntent().getStringExtra("email");
    setContentView(R.layout.homepage);
    fm = getFragmentManager();
    RadioGroup rgTabBar = (RadioGroup) findViewById(R.id.rgTabBar);
    rgTabBar.setOnCheckedChangeListener(this);
    RadioButton rbMails = (RadioButton) findViewById(R.id.rbMails);
    rbMails.setChecked(true);
  }

  @Override
  public void onCheckedChanged(RadioGroup group, int checkedId) {
    FragmentTransaction fmTransaction = fm.beginTransaction();
    hideAllFragment(fmTransaction);
    switch (checkedId) {
      case R.id.rbMails:
        if (mailsFragment == null) {
          mailsFragment = new MailsFragment();;
          fmTransaction.add(R.id.ly_content, mailsFragment);
        } else {
          fmTransaction.show(mailsFragment);
        }
        break;
      case R.id.rbPackages:
        if (packagesFragment == null) {
          packagesFragment = new PackagesFragment();
          fmTransaction.add(R.id.ly_content, packagesFragment);
        } else {
          fmTransaction.show(mailsFragment);
        }
        break;
      case R.id.rbAddress:
        if (myAddressFragment == null) {
          myAddressFragment = MyAddressFragment.newInstance(strEmail);;
          fmTransaction.add(R.id.ly_content, myAddressFragment);
        } else {
          fmTransaction.show(myAddressFragment);
        }
        break;
      case R.id.rbArchive:
        if (archiveFragment == null) {
          archiveFragment = new ArchiveFragment();
          fmTransaction.add(R.id.ly_content, archiveFragment);
        } else {
          fmTransaction.show(archiveFragment);
        }
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + checkedId);
    }
    fmTransaction.commit();
  }

  private void hideAllFragment(FragmentTransaction fragmentTransaction) {
    if (mailsFragment != null) {
      fragmentTransaction.hide(mailsFragment);
    }
    if (packagesFragment != null) {
      fragmentTransaction.hide(packagesFragment);
    }
    if (myAddressFragment != null) {
      fragmentTransaction.hide(myAddressFragment);
    }
    if (archiveFragment != null) {
      fragmentTransaction.hide(archiveFragment);
    }
  }
}
