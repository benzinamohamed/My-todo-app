package Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import TaskOB.TaskCL;

public class Databasehandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "todoDatabase";
    public static final String TABLE_NAME = "TodoTable";
    public static final int version = 1;
    public static final String col1 = "id";
    public static final String col2 = "task";
    public static final String col3 = "status";

    public Databasehandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(" + col1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + col2 + " TEXT, "
                + col3 + " INTEGER)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public long InsertTask(TaskCL task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col2, task.getTaskname());
        values.put(col3,task.getStatus());
    long ThenewID =   db.insert(TABLE_NAME,null,values);
        db.close();
        return ThenewID;
    }

    @SuppressLint("Range")
    public List<TaskCL> GetAllTasks() {
        List<TaskCL> taskslist = new ArrayList<>();
        try{  SQLiteDatabase db = this.getReadableDatabase();
            Cursor  cursor = db.query(TABLE_NAME,null,null,null,null,null,null);
        if (cursor!=null){
            if(cursor.moveToFirst()){
                do {
                    TaskCL task = new TaskCL();
                    task.setId(cursor.getInt(cursor.getColumnIndex(col1)));
                    task.setTaskname(cursor.getString(cursor.getColumnIndex(col2)));
                    task.setStatus(cursor.getInt(cursor.getColumnIndex(col3)));
                    taskslist.add(task);
                }
                while (cursor.moveToNext());

            }



        }
        }
        catch (Exception e){
            Log.e("DatabaseHandler", "Error getting all tasks", e);
        }
      return taskslist;
    }


    public void DeleteTask (int id){
        SQLiteDatabase db = this.getWritableDatabase();
      db.delete(TABLE_NAME,col1+" = ?",new String[]{String.valueOf(id)});
        db.close();
    }

    public void UpdateTask(int id, String task) {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(col2, task);
        db.update(TABLE_NAME, cv, col1 + "= ?", new String[] {String.valueOf(id)});
        db.close();
    }

    public void UpdateStatus(int id , int status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(col3,status);
        db.update(TABLE_NAME,cv,col1 + "= ?" , new String[]{String.valueOf(id)});
db.close();
    }

}
