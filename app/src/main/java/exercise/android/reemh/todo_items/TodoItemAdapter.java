package exercise.android.reemh.todo_items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class TodoItemViewHolder extends RecyclerView.ViewHolder{
    TextView taskDescription;
    CheckBox checkBox;
    ImageView deleteButton;


    public TodoItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.taskDescription = itemView.findViewById(R.id.taskDescription);
        this.checkBox = itemView.findViewById(R.id.markCheckbox);
        this.deleteButton = itemView.findViewById(R.id.deleteButton);
    }
}

public class TodoItemAdapter extends RecyclerView.Adapter<TodoItemViewHolder>{
    private TodoItemsHolder itemHolder;
    private boolean isBind;

    public TodoItemAdapter(TodoItemsHolder itemHolder){
        this.itemHolder = itemHolder;
    }

    @NonNull
    @Override
    public TodoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_todo_item, parent, false);
        return new TodoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoItemViewHolder holder, int position) {
        this.isBind = true;
        TodoItem curItem = this.itemHolder.getCurrentItems().get(position);
        holder.taskDescription.setText(curItem.getTaskDescription());
        holder.checkBox.setChecked(curItem.getIsDone());
        holder.checkBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int position = holder.getLayoutPosition();
                TodoItem item = itemHolder.getCurrentItems().get(position);
                if (holder.checkBox.isChecked()){
                    itemHolder.markItemDone(position);
                    notifyDataSetChanged();
                }
                else{
                    itemHolder.markItemInProgress(position);
                    notifyDataSetChanged();
                }
            }
        });


        holder.deleteButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               itemHolder.deleteItem(curItem);
               notifyDataSetChanged();
           }
        }
        );
    }

    @Override
    public int getItemCount() {
        return this.itemHolder.getCurrentItems().size();
    }
}

