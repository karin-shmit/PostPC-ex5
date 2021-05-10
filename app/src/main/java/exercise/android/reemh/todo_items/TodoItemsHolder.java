package exercise.android.reemh.todo_items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public interface TodoItemsHolder extends Serializable {

  /** Get a copy of the current items list */
  List<TodoItem> getCurrentItems();

  /**
   * Creates a new TodoItem and adds it to the list, with the @param description and status=IN-PROGRESS
   * Subsequent calls to [getCurrentItems()] should have this new TodoItem in the list
   */
  void addNewInProgressItem(String description);

  /** mark the @param item as DONE */
  void markItemDone(int index);

  /** mark the @param item as IN-PROGRESS */
  void markItemInProgress(int index);

  /** delete the @param item */
  void deleteItem(TodoItem item);

  /** edit item */
  void editItem(TodoItem item);

  /** set items on holder */
  void setItems(List<TodoItem> items);
}
