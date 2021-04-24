package exercise.android.reemh.todo_items;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TodoItemsHolderImpl implements TodoItemsHolder {
  private List<TodoItem> todoItems;

  public TodoItemsHolderImpl(){
    this.todoItems = new ArrayList<>();
  }

  @Override
  public List<TodoItem> getCurrentItems() {
    return new ArrayList<>(this.todoItems);
  }

  @Override
  public void addNewInProgressItem(String description){
    this.todoItems.add(0,new TodoItem(description));
    this.sortList();
  }

  @Override
  public void markItemDone(int index) throws IndexOutOfBoundsException {
    if (index >= this.todoItems.size()){
      throw new IndexOutOfBoundsException();
    }
    TodoItem item = this.todoItems.get(index);
    item.setIfDone(true);
    this.sortList();
  }

  @Override
  public void markItemInProgress(int index) throws IndexOutOfBoundsException {
    if (index >= this.todoItems.size()){
      throw new IndexOutOfBoundsException();
    }
    TodoItem item = this.todoItems.get(index);
    item.setIfDone(false);
    this.sortList();
  }

  @Override
  public void deleteItem(TodoItem item){
    this.todoItems.remove(item);
  }

  private void sortList(){
    Collections.sort(this.todoItems, (item1, item2) ->{

      if (item1.getIsDone() == item2.getIsDone()){
        return Integer.compare(item2.getTaskId(),item1.getTaskId());
      }
      if (item1.getIsDone()){
        return 1;
      }
      return -1;
    });
  }

}
