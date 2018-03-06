package com.xnliang.yishibao.module.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JackLiu on 2018-03-06.
 */

public class UserDbHelp extends SQLiteOpenHelper {

    private static final String CREATE_USERDATA="create table userData(" +
            "id integer primary key autoincrement,"
            +"name text,"
            +"password text)";

    private static final String CREATE_USEDETAIL="create table userDetail(" +
            "id integer primary key autoincrement,"
            +"name text,"
            +"password text,"
            +"coin text,"
            +"score text,"
            +"avatar text,"
            +"mobile text,"
            +"position text)";

    private Context mContext;


    public UserDbHelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERDATA);
        db.execSQL(CREATE_USEDETAIL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
