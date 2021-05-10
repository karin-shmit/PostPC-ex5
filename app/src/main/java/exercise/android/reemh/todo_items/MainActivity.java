package exercise.android.reemh.todo_items;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

  public TodoItemsHolder holder = null;
  private EditText editTask;
  private FloatingActionButton fabCreate;
  private BroadcastReceiver updateDataSetReceiver;
  private App app;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    app = (App) getApplicationContext();

    if (holder == null) {
      holder = new TodoItemsHolderImpl();
    }

    app.loadItems();
    RecyclerView recyclerView = findViewById(R.id.recyclerTodoItemsList);
    TodoItemAdapter recyclerAdapter = new TodoItemAdapter(holder, this);
    recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    recyclerView.setAdapter(recyclerAdapter);

    this.editTask = findViewById(R.id.editTextInsertTask);
    this.fabCreate = findViewById(R.id.buttonCreateTodoItem);

    this.fabCreate.setOnClickListener(v -> {
      String taskDescription = editTask.getText().toString();
      if (taskDescription.equals("")){
        return;
      }
      this.holder.addNewInProgressItem(taskDescription);
      this.editTask.setText("");
      recyclerAdapter.notifyDataSetChanged();
    });

    updateDataSetReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("changedItem")) {
          TodoItem changedItem = (TodoItem) intent.getSerializableExtra("Item");
          holder.editItem(changedItem);
          app.items = holder.getCurrentItems();
          app.saveItems();
          recyclerAdapter.notifyDataSetChanged();
        }
      }
    };
    registerReceiver(updateDataSetReceiver, new IntentFilter("changedItem"));
  }

  @Override
  protected void onStop(){
    super.onStop();
    app.items = this.holder.getCurrentItems();
    app.saveItems();
  }

  @Override
  protected void onResume(){
    super.onResume();
    app.loadItems();
    holder.setItems(app.items);
  }

  @Override
  protected void onDestroy(){
    super.onDestroy();
    this.unregisterReceiver(updateDataSetReceiver);
  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle state){
    super.onSaveInstanceState(state);
    state.putSerializable("holder", this.holder);
  }
}

