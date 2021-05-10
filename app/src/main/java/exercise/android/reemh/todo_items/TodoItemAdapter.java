package exercise.android.reemh.todo_items;

import android.content.Context;
import android.content.Intent;
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
    Context context;
    LayoutInflater inflater;

    public TodoItemAdapter(TodoItemsHolder itemHolder, Context context){
        this.itemHolder = itemHolder;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TodoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.inflater.inflate(R.layout.row_todo_item, parent, false);
        return new TodoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoItemViewHolder holder, int position) {
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

        holder.taskDescription.setOnClickListener(view ->{
            Intent editIntent = new Intent(this.context, EditScreenActivity.class);
            editIntent.putExtra("Item", curItem);
            this.context.startActivity(editIntent);
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

