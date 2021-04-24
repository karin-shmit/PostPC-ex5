package exercise.android.reemh.todo_items;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

  public TodoItemsHolder holder = null;
  private EditText editTask;
  private FloatingActionButton fabCreate;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (holder == null) {
      holder = new TodoItemsHolderImpl();
    }

    RecyclerView recyclerView = findViewById(R.id.recyclerTodoItemsList);
    TodoItemAdapter recyclerAdapter = new TodoItemAdapter(holder);
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
  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putSerializable("itemHolder", holder);
    outState.putString("currentDescription", this.editTask.getText().toString());
  }


}

