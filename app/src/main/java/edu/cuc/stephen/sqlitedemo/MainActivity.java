package edu.cuc.stephen.sqlitedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String dbName = "students.db";
    private static final String tableName = "students";
    private SQLiteDatabase database;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = (TextView) findViewById(R.id.textViewResult);
        StudentsSQLiteHelper helper = new StudentsSQLiteHelper(MainActivity.this, dbName);
        database = helper.getWritableDatabase();
        //每个程序都有自己的数据库，默认情况下是互不干扰
        //database = openOrCreateDatabase(dbName, MODE_PRIVATE, null);
        try {
            Cursor cursor = database.rawQuery("select * from " + tableName, null);
            cursor.close();
            textViewResult.append("找到表，列出数据\n");
            Button listAll = (Button) findViewById(R.id.buttonListAll);
            listAll.performClick();
        } catch (Exception e) {
            e.printStackTrace();
            textViewResult.append("表不存在或为空，创建...\n");
            createTable();
            textViewResult.append("创建完成\n");
        }
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "age INTEGER not null, " +
                "sex TEXT NOT NULL)";
        insertNewRecord("张三", "女", 18);
        insertNewRecord("李四", "女", 19);
        database.execSQL(sql);
        sql = "INSERT INTO " + tableName + " (name, sex, age) VALUES ('王五', '男', 20)";
        database.execSQL(sql);
    }

    private void insertNewRecord(String name, String sex, int age) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("sex", sex);
        values.put("age", age);
        database.insert(tableName, null, values);
        values.clear();
    }

    public void onClickListAll(View view) {
        if (R.id.buttonListAll == view.getId()) {
            try {
                Cursor cursor = database.rawQuery("select * from " + tableName, null);
                while (cursor.moveToNext()) {
                    textViewResult.append("_id: " + cursor.getInt(cursor.getColumnIndex("_id")) +
                            ", name: " + cursor.getString(cursor.getColumnIndex("name")) +
                            ", age: " + cursor.getInt(cursor.getColumnIndex("age")) +
                            ", sex: " + cursor.getString(cursor.getColumnIndex("sex")) + "\n");
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void insertRecord(View view) {
        if(R.id.buttonInsert == view.getId()){
            String sql = "INSERT INTO " + tableName + " (name, sex, age) VALUES ('后加测试', '不详', 0)";
            database.execSQL(sql);
            textViewResult.append("插入测试数据，现查询出所有记录：\n");
            Button listAll = (Button) findViewById(R.id.buttonListAll);
            listAll.performClick();
        }
    }
}
