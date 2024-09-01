package com.example.mytodoapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import Adapters.recyclerviewAdapter;
import Database.Databasehandler;
import TaskOB.TaskCL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private FloatingActionButton fb;
    private List<TaskCL> tasksList ;
    private recyclerviewAdapter myAdapter;
    private Databasehandler db ;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.mainAct_RecyclerView);
        fb = findViewById(R.id.mainAct_floatingButton);

        db = new Databasehandler(getBaseContext());
        loadTasksFromDatabases();
        myAdapter = new recyclerviewAdapter(tasksList,db, MainActivity.this);

        rv.setAdapter(myAdapter);
        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));



        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtask();
            }
        });



    }

    private void loadTasksFromDatabases() {
        tasksList = new ArrayList<>(db.GetAllTasks());

    }

    private void addtask() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_layout);

        EditText editText_addText = dialog.findViewById(R.id.bottom_sheet_et);
        MaterialButton button = dialog.findViewById(R.id.bottom_sheet_button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tasktext = editText_addText.getText().toString().trim();
              if(!tasktext.isEmpty())  {

                  TaskCL taskAdded = new TaskCL(tasktext,0);

                 long newID = db.InsertTask(taskAdded);
                 if(newID !=-1) {
                     taskAdded.setId((int)newID);
                     tasksList.add(taskAdded);
                     myAdapter.notifyItemInserted(tasksList.size() - 1);
                     dialog.dismiss();
                 }


              }
              else {
                  Toast.makeText(getBaseContext(),"You can't save an empty Task",Toast.LENGTH_SHORT).show();

              }

            }
        });



        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}