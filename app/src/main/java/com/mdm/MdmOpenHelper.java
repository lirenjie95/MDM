package com.mdm;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MdmOpenHelper extends SQLiteOpenHelper {
  private static final String TAG = "MdmOpenHelper";
  private final Context context;
  private static final String DATABASE_NAME = "MDM.db";
  private static final String DATABASE_PATH = "/data"
      + Environment.getDataDirectory().getAbsolutePath() + "/"
      + "com.mdm" + "/databases";
  private static final int DATABASE_VERSION = 1;
  public static final String USER_INFO_TABLE = "UserInfo";
  public static final String MAILS_TABLE = "Mails";
  public static final String PACKAGES_TABLE = "Packages";

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
    this.context = context;
  }

  /**
   * This constructor will provide a way to create MdmOpenHelper only with context.
   * @param context Only this argument is not null. Others can be found in previous section.
   */
  public MdmOpenHelper(Context context) {
    this(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  /**
   * Creates a empty database on the system and rewrites it with your own database.
   * */
  public void createDataBase() throws IOException {
    boolean dbExist = checkDataBase();
    if (!dbExist) {
      //By calling this method and empty database will be created into the default system path
      //of your application so we are gonna be able to overwrite that database with our database.
      this.getReadableDatabase();
      try {
        copyDataBase();
      } catch (IOException e) {
        throw new Error("Error copying database");
      }
    }
  }

  /**
   * Check if the database already exist to avoid re-copying the file each time
   * when you open the application.
   * @return true if it exists, false if it doesn't
   */
  private boolean checkDataBase() {
    SQLiteDatabase checkDb = null;
    try {
      String myPath = DATABASE_PATH + DATABASE_NAME;
      checkDb = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    } catch (SQLiteException e) {
      Log.e(TAG, "checkDataBase: doesn't exist error.");
      throw new SQLException("database does't exist yet.");
    }
    if (checkDb != null) {
      checkDb.close();
    }
    return checkDb != null;
  }

  /**
   * Copies your database from your local assets-folder to the just created empty database in the
   * system folder, from where it can be accessed and handled.
   * This is done by transferring byte stream.
   * */
  private void copyDataBase() throws IOException {
    //Open your local db as the input stream
    InputStream myInput = context.getAssets().open(DATABASE_NAME);
    // Path to the just created empty db
    String outFileName = DATABASE_PATH + DATABASE_NAME;
    //Open the empty db as the output stream
    OutputStream myOutput = new FileOutputStream(outFileName);
    //transfer bytes from the input file to the output file
    byte[] buffer = new byte[1 << 10];
    int length;
    while ((length = myInput.read(buffer)) > 0) {
      myOutput.write(buffer, 0, length);
    }
    //Close the streams
    myOutput.flush();
    myOutput.close();
    myInput.close();
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
