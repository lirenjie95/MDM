package com.mdm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MdmOpenHelper extends SQLiteOpenHelper {
  private static final String TAG = "MdmOpenHelper";
  private static final String DATABASE_NAME = "MDM.db";
  private static final int DATABASE_VERSION = 1;

  /**
   * This constructor will generate a SQLiteOpenHelper for our APP.
   * @param context Interface to global information about an application environment.
   * @param name The name of database.
   * @param factory In our APP, this part is always null.
   * @param version Database version.
   */
  public MdmOpenHelper(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
    super(context, name, factory, version);
  }

  /**
   * This constructor will provide a way to create MdmOpenHelper only with context.
   * @param context Only this argument is not null. Others can be found in previous section.
   */
  public MdmOpenHelper(Context context) {
    this(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  /**
   * This method will create the original database.
   * @param db Database.
   */
  @Override
  public void onCreate(SQLiteDatabase db) {
    Log.e(TAG, "Creating...");
  }

  /**
   * This method can upgrade the database.
   * @param db Database.
   * @param oldVersion The old version of database.
   * @param newVersion The new version of database.
   */
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.e(TAG, "Upgrading...");
  }
}
