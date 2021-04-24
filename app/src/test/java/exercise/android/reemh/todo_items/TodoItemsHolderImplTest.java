package exercise.android.reemh.todo_items;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TodoItemsHolderImplTest {
  @Test
  public void when_addingTodoItem_then_callingListShouldHaveThisItem(){
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());

    // test
    holderUnderTest.addNewInProgressItem("do shopping");

    // verify
    Assert.assertEquals(1, holderUnderTest.getCurrentItems().size());
  }

  @Test
  public void addMultipleItems(){
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());

    // test
    holderUnderTest.addNewInProgressItem("1");
    holderUnderTest.addNewInProgressItem("2");
    holderUnderTest.addNewInProgressItem("3");
    holderUnderTest.addNewInProgressItem("4");

    // verify
    Assert.assertEquals(4, holderUnderTest.getCurrentItems().size());
  }

  @Test
  public void addingTodoItem_and_thenDeleteIt(){
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());

    // test
    holderUnderTest.addNewInProgressItem("1");
    holderUnderTest.deleteItem(holderUnderTest.getCurrentItems().get(0));

    // verify
    Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());
  }

  @Test
  public void deleteMultiplyItems(){
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());

    // test
    holderUnderTest.addNewInProgressItem("1");
    holderUnderTest.addNewInProgressItem("2");
    holderUnderTest.addNewInProgressItem("3");
    holderUnderTest.addNewInProgressItem("4");

    TodoItem one = holderUnderTest.getCurrentItems().get(0);
    TodoItem two = holderUnderTest.getCurrentItems().get(1);
    TodoItem three = holderUnderTest.getCurrentItems().get(2);
    TodoItem four = holderUnderTest.getCurrentItems().get(3);

    holderUnderTest.deleteItem(two);
    holderUnderTest.deleteItem(four);

    // verify
    Assert.assertEquals(2, holderUnderTest.getCurrentItems().size());
    Assert.assertEquals(one, holderUnderTest.getCurrentItems().get(0));
    Assert.assertEquals(three, holderUnderTest.getCurrentItems().get(1));
  }

  @Test
  public void lastItemToBeAddedIsFirstOnList(){
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());

    // test
    holderUnderTest.addNewInProgressItem("1");
    holderUnderTest.addNewInProgressItem("2");
    holderUnderTest.addNewInProgressItem("3");
    holderUnderTest.addNewInProgressItem("4");

    // verify
    Assert.assertEquals(4, holderUnderTest.getCurrentItems().size());
    Assert.assertEquals("4", holderUnderTest.getCurrentItems().get(0).getTaskDescription());
  }

  @Test
  public void markItem_andThen_itemGoToButtom(){
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());

    // test
    holderUnderTest.addNewInProgressItem("1");
    holderUnderTest.addNewInProgressItem("2");
    holderUnderTest.addNewInProgressItem("3");
    holderUnderTest.addNewInProgressItem("4");

    TodoItem four = holderUnderTest.getCurrentItems().get(0);

    holderUnderTest.markItemDone(0);

    // verify
    Assert.assertEquals(4, holderUnderTest.getCurrentItems().size());
    Assert.assertEquals(four, holderUnderTest.getCurrentItems().get(3));
  }

  @Test
  public void unmarkItem_andThen_itemGoBackToOriginalPosition(){
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());

    // test
    holderUnderTest.addNewInProgressItem("1");
    holderUnderTest.addNewInProgressItem("2");
    holderUnderTest.addNewInProgressItem("3");
    holderUnderTest.addNewInProgressItem("4");

    TodoItem four = holderUnderTest.getCurrentItems().get(0);

    holderUnderTest.markItemDone(0);
    holderUnderTest.markItemInProgress(3);

    // verify
    Assert.assertEquals(4, holderUnderTest.getCurrentItems().size());
    Assert.assertEquals(four, holderUnderTest.getCurrentItems().get(0));
  }

  @Test
  public void markItemWithIndexOutOfBound(){
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());

    // test
    holderUnderTest.addNewInProgressItem("1");
    holderUnderTest.addNewInProgressItem("2");
    holderUnderTest.addNewInProgressItem("3");
    holderUnderTest.addNewInProgressItem("4");

    try{
      holderUnderTest.markItemDone(5);
      fail("Not thrown exception");
    }
    catch (IndexOutOfBoundsException e){
      assertTrue(true);
    }


    holderUnderTest.markItemDone(0);
    holderUnderTest.markItemInProgress(3);

    // verify
    Assert.assertEquals(4, holderUnderTest.getCurrentItems().size());
  }

  @Test
  public void deleteItemThatDoesntExist(){
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());

    // test
    holderUnderTest.addNewInProgressItem("1");
    holderUnderTest.addNewInProgressItem("2");
    holderUnderTest.addNewInProgressItem("3");
    holderUnderTest.addNewInProgressItem("4");

    TodoItem five = new TodoItem("5");

    // verify
    Assert.assertEquals(4, holderUnderTest.getCurrentItems().size());
    holderUnderTest.deleteItem(five);
    Assert.assertEquals(4, holderUnderTest.getCurrentItems().size());
  }

  @Test
  public void checkMarkedItem(){
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());

    // test
    holderUnderTest.addNewInProgressItem("1");
    holderUnderTest.addNewInProgressItem("2");
    holderUnderTest.addNewInProgressItem("3");
    holderUnderTest.addNewInProgressItem("4");

    holderUnderTest.markItemDone(0);

    // verify
    Assert.assertTrue(holderUnderTest.getCurrentItems().get(3).getIsDone());
  }

  @Test
  public void checkUnmarkedItem(){
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());

    // test
    holderUnderTest.addNewInProgressItem("1");
    holderUnderTest.addNewInProgressItem("2");
    holderUnderTest.addNewInProgressItem("3");
    holderUnderTest.addNewInProgressItem("4");


    // verify
    Assert.assertFalse(holderUnderTest.getCurrentItems().get(0).getIsDone());
  }

}