package edu.cuc.stephen.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//用于管理数据库的创建和版本更新
public class StudentsSQLiteHelper extends SQLiteOpenHelper{
    //  getReadableDatabase();          创建或打开一个只读数据库
    //  getWritableDatabase()           创建或打开一个读写数据库
    private String tableName = "students";
    private String createTable = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT NOT NULL, " +
            "age INTEGER not null, " +
            "sex TEXT NOT NULL)";

    public StudentsSQLiteHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    @Override           //首次创建数据库时调用
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createTable);
        insertNewRecord(sqLiteDatabase, "张三", "女", 18);
        insertNewRecord(sqLiteDatabase, "李四", "女", 19);
        String sql = "INSERT INTO " + tableName + " (name, sex, age) VALUES ('王五', '男', 20)";
        sqLiteDatabase.execSQL(sql);
    }


    @Override           //数据库版本发生变化更新时调用
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    private void insertNewRecord(SQLiteDatabase sqLiteDatabase, String name, String sex, int age) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("sex", sex);
        values.put("age", age);
        sqLiteDatabase.insert(tableName, null, values);
        values.clear();
    }
}
