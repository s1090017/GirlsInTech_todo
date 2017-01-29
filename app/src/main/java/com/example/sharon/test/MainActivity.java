package com.example.sharon.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.sharon.test.R.id.et_new;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lv_items;
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_items = (ListView)findViewById(R.id.lv_items);
        items = new ArrayList<>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        lv_items.setAdapter(itemsAdapter);
        setupListViewListener();


    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText)findViewById(et_new);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupListViewListener() {
        lv_items.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        Intent intent = new Intent(MainActivity.this,EditItemActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("et_modify",items.get(pos));
                        bundle.putInt("position", pos);
                        intent.putExtras(bundle);
                        startActivityForResult(intent,12345);
                        return true;
                    }
                }
        );
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 12345) {
            if (resultCode == RESULT_OK) {
                int idx = data.getIntExtra("position", 0);
                items.set(idx, data.getStringExtra("et_modify"));
                itemsAdapter.notifyDataSetChanged();
                writeItems();
            }
        }
    }

}
