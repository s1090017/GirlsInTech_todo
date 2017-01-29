package com.example.sharon.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    private EditText et_modify;
    private Button btn_save;
    private int index = 0;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.btn_save:
                    Intent intent = new Intent(EditItemActivity.this,MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("et_modify",et_modify.getText().toString());
                    bundle.putInt("position", index);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        et_modify = (EditText)findViewById(R.id.et_modify);
        btn_save = (Button)findViewById(R.id.btn_save);
        btn_save.setOnClickListener(clickListener);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        et_modify.setText(bundle.getString("et_modify"));
        et_modify.setSelection(bundle.getString("et_modify").length());
        index = intent.getIntExtra("position", 0);

    }
}
