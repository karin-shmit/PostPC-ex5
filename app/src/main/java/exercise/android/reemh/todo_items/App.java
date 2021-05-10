package exercise.android.reemh.todo_items;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    List<TodoItem> items;
    SharedPreferences sp;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        loadItems();
    }
    public void loadItems() {
        items =  new ArrayList<>();
        String itemsJson = sp.getString("todoItems", "");
        if (!itemsJson.equals("")) {
            Type listType = new TypeToken<ArrayList<TodoItem>>(){}.getType();
            items = new Gson().fromJson(itemsJson, listType);
        }
    }

    public void saveItems() {
        String itemsJson = new Gson().toJson(items);
        sp.edit().putString("todoItems", itemsJson).apply();
    }
}