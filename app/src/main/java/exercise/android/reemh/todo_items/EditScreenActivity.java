package exercise.android.reemh.todo_items;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class EditScreenActivity extends AppCompatActivity {
    private TextView createdAtText, lastModifiedAt;
    private EditText descriptionEditText;
    private CheckBox doneCheckbox;
    protected TodoItem curItem;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat hourFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_screen);

        createdAtText = findViewById(R.id.creationTimeText);
        lastModifiedAt = findViewById(R.id.LastModifiedTimeText);
        descriptionEditText = findViewById(R.id.TaskDescriptionEdit);
        doneCheckbox = findViewById(R.id.EditScreenCheckBox);

        Intent curIntent = getIntent();
        this.curItem = (TodoItem) curIntent.getSerializableExtra("Item");


        hourFormat = new SimpleDateFormat("K:mm a");
        dateFormat = new SimpleDateFormat("dd MMMM yyyy hh:mm aaa");
        createdAtText.setText(dateFormat.format(curItem.getCreationTimestamp()));

        this.changeLastModifiedTime();

        descriptionEditText.setText(curItem.getTaskDescription());
        doneCheckbox.setChecked(curItem.getIsDone());
        doneCheckbox.setOnCheckedChangeListener((btn, isChecked) -> {
            curItem.setIfDone(isChecked);
            changeLastModifiedTime();
        });

        descriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                curItem.setTask(s.toString());
                changeLastModifiedTime();
            }
        });
    }

    private void changeLastModifiedTime() {
        Date curDate = new Date();
        long timePassed = curDate.getTime() - this.curItem.getLastModifiedTimestamp().getTime();
        long minutesPassed = TimeUnit.MILLISECONDS.toMinutes(timePassed);
        if (minutesPassed < 60) {
            lastModifiedAt.setText(String.format("%s minutes ago", minutesPassed));
            return;
        }

        long hoursPassed = minutesPassed / 60;
        if (hoursPassed < 24) {
            lastModifiedAt.setText(String.format("Today at %s", this.hourFormat.format(curItem.getLastModifiedTimestamp())));
            return;
        }

        lastModifiedAt.setText(dateFormat.format(this.curItem.getLastModifiedTimestamp()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent changed = new Intent("changedItem");
        changed.putExtra("Item", this.curItem);
        sendBroadcast(changed);
    }
}