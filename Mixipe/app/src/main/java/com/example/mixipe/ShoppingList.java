package com.example.mixipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mixipe.Adapters.ShoppingListAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ShoppingList extends AppCompatActivity {

    private Button backButton;

    EditText input;
    ImageView enter;

    static ListView listView;
    static ArrayList<String> items;
    static ShoppingListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        listView = findViewById(R.id.shoppingList_view);
        input = findViewById(R.id.list_input);
        enter = findViewById(R.id.list_add);

        items = new ArrayList<>();

        // display the item name when clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = items.get(i);
                makeToast(name);
            }
        });

        // if item is long pressed, remove item
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                makeToast("Removed: "+ items.get(i));
                removeItem(i);
                return false;
            }
        });

        adapter = new ShoppingListAdapter(getApplicationContext(), items);
        listView.setAdapter(adapter);

        // add item to list when add button is pressed
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = input.getText().toString();
                if(text == null || text.length() == 0) {
                    makeToast("Enter an item");
                }
                else {
                    addItem(text);
                    input.setText("");
                    makeToast("Added "+ text);
                }
            }
        });


        // return user to home page when home button is pressed
        backButton = (Button) findViewById(R.id.back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity();
            }
        });

        loadContent();
    }

    // reads content of shopping list from file and load into list view
    public void loadContent() {
        File path = getApplicationContext().getFilesDir();
        File readFrom = new File(path, "list.txt");
        byte[] content = new byte[(int) readFrom.length()];

        FileInputStream stream = null;
        try {
            stream = new FileInputStream(readFrom);
            stream.read(content);

            String s = new String(content);
            s = s.substring(1, s.length() - 1);
            String split[] = s.split(", ");

            items = new ArrayList<>(Arrays.asList(split));
            adapter = new ShoppingListAdapter(this, items);
            listView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // saves shopping list contents before app is terminated
    @Override
    protected void onDestroy() {
        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, "list.txt"));
            writer.write(items.toString().getBytes());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    // open swipe page when home button is pressed
    public void openActivity() {
        onDestroy();
        Intent intent = new Intent(this, Swipe.class);
        startActivity(intent);
    }

    // add item
    public static void addItem(String item) {
        items.add(item);
        listView.setAdapter(adapter);
    }

    // remove item
    public static void removeItem(int remove) {
        items.remove(remove);
        listView.setAdapter(adapter);
    }

    // function to make toast message
    Toast t;

    private void makeToast(String s) {
        if (t != null) t.cancel();
        t = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
        t.show();
    }
}