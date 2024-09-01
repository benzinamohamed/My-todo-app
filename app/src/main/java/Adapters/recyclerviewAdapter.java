package Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytodoapp.R;

import java.util.List;

import Database.Databasehandler;
import TaskOB.TaskCL;

public class recyclerviewAdapter extends RecyclerView.Adapter<recyclerviewAdapter.myViewHolder> {


    private List <TaskCL> mylist;
    Databasehandler db ;
    Context context;

    public recyclerviewAdapter(@NonNull List<TaskCL> mylist,Databasehandler db,Activity context) {

        this.mylist = mylist;
        this.db = db;
        this.context=context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_task_layout,parent,false);

        return new myViewHolder(v) ;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position) {

        TaskCL item = mylist.get(holder.getAdapterPosition());

        holder.binder(item);

    }



    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public  class myViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox checkbox;
        private final ImageButton img_Delete_button;
        private final ImageButton img_Edit_button;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.singleLayout_checkbox);
            img_Delete_button = itemView.findViewById(R.id.singleLayout_DeleteButton);
            img_Edit_button = itemView.findViewById(R.id.singleLayout_EditButton);

            img_Delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteFromAdapter();
                }
            });


            img_Edit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        final Dialog dialog = new Dialog((Activity) context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.edit_task_layout);
                        EditText Textarea = dialog.findViewById(R.id.edit_task_layout_et);
                        Button DoneButton = dialog.findViewById(R.id.edit_task_layout_button);
                        String oldText = mylist.get(getAdapterPosition()).getTaskname();
                        Textarea.setText(oldText);

                        DoneButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String input = String.valueOf(Textarea.getText());

                                if (input.equals(oldText)) {
                                    dialog.dismiss();

                                } else if (!input.isEmpty()) {
                                    editTaskFromAdapter(getAdapterPosition(), String.valueOf(Textarea.getText()));
                                    dialog.dismiss();
                                } else {

                                    Toast.makeText(context, "You can't save an empty Task", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        dialog.show();
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.getWindow().setGravity(Gravity.CENTER);


                    }
                }
            });



            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b)
                       editStatusFromAdapter(1,getAdapterPosition());
                    else
                        editStatusFromAdapter(0,getAdapterPosition());
                }
            });


        }


        private void deleteFromAdapter() {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                TaskCL item = mylist.get(position);
                try {
                    int id = item.getId();
                    db.DeleteTask(id);
                    mylist.remove(position);
                    notifyItemRemoved(position);
                } catch (Exception e) {
                    Toast.makeText(context, "Error deleting task: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }


        private void editTaskFromAdapter(int position, String task) {

            TaskCL item = mylist.get(position);
            try {

                int id = item.getId();
                db.UpdateTask(id, task);
                mylist.get(position).setTaskname(task);
                notifyItemChanged(position);
            } catch (Exception e) {
                Toast.makeText(context, "Error updating task: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        private void editStatusFromAdapter(int status,int position) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    TaskCL task = mylist.get(position);
                    try {
                        db.UpdateStatus(task.getId(),status);
                        task.setStatus(status);
                        notifyItemChanged(position);
                    } catch (Exception e) {
                        Toast.makeText(context, "Error updating task status: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }

        public void binder(TaskCL item) {
            checkbox.setText(item.getTaskname());
            checkbox.setChecked(item.getStatus() == 1);
        }
    }



}
