package com.haoyh.dbflowdemo.database;

import android.content.Context;
import android.util.Log;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.sqlcipher.SQLCipherOpenHelper;
import com.raizlabs.android.dbflow.structure.database.DatabaseHelperListener;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;

/**
 * @FileName: CustomOpenHelper
 * @Description: 自定义 SQLCipherOpenHelper，实现数据库加密功能
 * @Author: haoyanhui
 * @Date: 2020/3/9 16:26
 */
public class CustomOpenHelper extends SQLCipherOpenHelper {

    public static final String TAG = "hyh";

    // 旧数据库名
    public static final String DB_NAME_OLD = "old_db";
    // 新数据库名
    public static final String DB_NAME_NOW = "new_db";

    private Context mContext;

    public CustomOpenHelper(Context context, DatabaseDefinition databaseDefinition, DatabaseHelperListener listener) {
        super(databaseDefinition, listener);
        this.mContext = context;
        Log.d(TAG, "CustomOpenHelper() ");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
        Log.d(TAG, "onCreate() ");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.d(TAG, "onOpen() " + db.getPath());
        try {
            encryptOldData(mContext, DB_NAME_OLD + ".db", "hyh");
        } catch (Exception e) {
            Log.d(TAG, "onOpen 异常 :");
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        Log.d(TAG, "onUpgrade() ");
    }

    @Override
    protected String getCipherSecret() {
        return "hyh";
    }

    /**
     * 加密未加密的数据
     * 成功之后会删除旧 .db 文件，避免重复备份的可能
     *
     * @param context
     * @param oldDBName 旧数据库名
     * @param password  密码，同 getCipherSecret() 中返回一致
     */
    private void encryptOldData(Context context, String oldDBName, String password) throws Exception {
        Log.d(TAG, "backupOldData() ");
        File originalFile = context.getDatabasePath(oldDBName);
        // 如果旧数据库文件存在，则进行下面的加密备份操作
        if (originalFile.exists()) {
            Log.d(TAG, "exixts ");
            File targetFile = context.getDatabasePath(DB_NAME_NOW + ".db");
            File newFile = File.createTempFile("batdb", "tmp", context.getCacheDir());
            SQLiteDatabase db = SQLiteDatabase.openDatabase(originalFile.getAbsolutePath(), "", null, SQLiteDatabase.OPEN_READWRITE);
            db.execSQL(String.format("ATTACH DATABASE '%s' AS encrypted KEY '%s';", newFile.getAbsolutePath(), password));
            db.beginTransaction();
            db.rawExecSQL("SELECT sqlcipher_export('encrypted')");
            db.setTransactionSuccessful();
            db.endTransaction();
            db.execSQL("DETACH DATABASE encrypted;");
            int version = db.getVersion();
            db.close();
            db = SQLiteDatabase.openDatabase(newFile.getAbsolutePath(), password, null, SQLiteDatabase.OPEN_READWRITE);
            db.setVersion(version);
            db.close();
            originalFile.delete();
            newFile.renameTo(targetFile);
        }
    }
}
