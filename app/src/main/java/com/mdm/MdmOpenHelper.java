package com.mdm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
  private static MdmOpenHelper instance;
  private final Context context;
  private static final String DATABASE_NAME = "MDM.db";
  private static String DATABASE_PATH;
  private static final int DATABASE_VERSION = 2;
  public static final String USER_INFO_TABLE = "UserInfo";
  public static final String MAILS_TABLE = "Mails";
  public static final String PACKAGES_TABLE = "Packages";

  /**
   * This constructor will generate a SQLiteOpenHelper for our APP.
   * The type is private to fit the singleton pattern.
   * @param context Interface to global information about an application environment.
   * @param name The name of database.
   * @param factory In our APP, this part is always null.
   * @param version Database version.
   */
  private MdmOpenHelper(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
    super(context, name, factory, version);
    this.context = context;
    DATABASE_PATH = this.context.getDatabasePath(name).getPath();
  }

  /**
   * This constructor will provide a way to create MdmOpenHelper only with context.
   * @param context Only this argument is not null. Others can be found in previous section.
   * @return the MdmOpenHelper instance for all callers.
   */
  public static synchronized MdmOpenHelper getInstance(Context context) {
    if (instance == null) {
      instance = new MdmOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    return instance;
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
      String myPath = DATABASE_PATH;
      checkDb = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    } catch (SQLiteException e) {
      Log.e(TAG, "checkDataBase: doesn't exist error.");
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
    String outFileName = DATABASE_PATH;
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

  @Override
  public void onCreate(SQLiteDatabase db) {
    Log.e(TAG, "Creating...");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.e(TAG, "Upgrading...");
  }

  /**
   * Check if we have this user in the database.
   * @param strEmail the string type email/username
   * @param strPassword the string type password
   * @return found this user or not
   */
  public boolean findLogin(String strEmail, String strPassword) {
    boolean found = true;
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM "
            + USER_INFO_TABLE
            + " WHERE email = ? AND password = ?",
        new String[]{strEmail, strPassword});
    if (cursor.getCount() < 1) {
      found = false;
    }
    cursor.close();
    db.close();
    return found;
  }

  /**
   * Add a new user from SignUp.
   * @param strEmail the string type email/username
   * @param strPassword the string type password
   * @param strAddress the string type address
   * @return if add the new user successfully, return true
   */
  public boolean insertNewUser(String strEmail, String strPassword, String strAddress) {
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM "
            + USER_INFO_TABLE
            + " WHERE email = ?",
        new String[]{strEmail});
    if (cursor.getCount() > 0) {
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
    db.insert(USER_INFO_TABLE, null, values);
    db.close();
    return true;
  }

  /**
   * Get all packages data.
   * @return a cursor as the result
   */
  public Cursor getAllPackages() {
    SQLiteDatabase db = this.getWritableDatabase();
    return db.rawQuery("SELECT * FROM " + PACKAGES_TABLE, null);
  }

  /**
   * Get all mails data.
   * @return a cursor as the result
   */
  public Cursor getAllSubscribedMails() {
    SQLiteDatabase db = this.getReadableDatabase();
    return db.rawQuery("SELECT * FROM " + MAILS_TABLE + " WHERE Unsubscribed = ?",
        new String[]{"FALSE"});
  }

  /**
   * Get all unsubscribed mails data.
   * @return a cursor as the result
   */
  public Cursor getArchive() {
    SQLiteDatabase db = this.getReadableDatabase();
    return db.rawQuery("SELECT * FROM " + MAILS_TABLE + " WHERE Archive = ?",
        new String[]{"TRUE"});
  }

  /**
   * Get all addresses of current user.
   * @param strEmail the email of user
   * @return a cursor as the result
   */
  public Cursor getUserAddress(String strEmail) {
    SQLiteDatabase db = this.getReadableDatabase();
    return db.rawQuery("SELECT * FROM " + USER_INFO_TABLE + " WHERE email = ?",
        new String[]{strEmail});
  }

  /**
   * Unsubscribe a mail.
   * @param mailPhotoId MailPhotoId as the private key
   */
  public void unsubscribeMail(String mailPhotoId) {
    SQLiteDatabase db = this.getWritableDatabase();
    // find the mail, and then unsubscribe
    ContentValues values = new ContentValues();
    values.put("Unsubscribed", "TRUE");
    db.update(MAILS_TABLE, values, "MailPhotoId = ?", new String[]{mailPhotoId});
    db.close();
  }

  /**
   * Move a mail to archive.
   * @param mailPhotoId MailPhotoId as the private key
   */
  public void archiveMail(String mailPhotoId) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("Archive", "TRUE");
    db.update(MAILS_TABLE, values, "MailPhotoId = ?", new String[]{mailPhotoId});
    db.close();
  }

  /**
   * Add a new address for current user.
   * @param strEmail the string type email/username
   * @param strAddress the string type address
   */
  public void addNewAddress(String strEmail, String strAddress) {
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM "
            + USER_INFO_TABLE
            + " WHERE email = ?",
        new String[]{strEmail});
    String strPassword = cursor.getString(cursor.getColumnIndex("password"));
    cursor.close();
    // new address, put a new record into database.
    ContentValues values = new ContentValues();
    values.put("email", strEmail);
    values.put("password", strPassword);
    values.put("address", strAddress);
    db.insert(USER_INFO_TABLE, null, values);
    db.close();
  }
}
