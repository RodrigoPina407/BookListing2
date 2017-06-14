package com.example.android.booklisting;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText mTextField;
    private Button mSearchButton;

    private String mSearchTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextField = (EditText) findViewById(R.id.edit_text);

        Button button = (Button) findViewById(R.id.main_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSearchTerms = mTextField.getText().toString();
                Intent intent = new Intent(getApplicationContext(), BookList.class);

                intent.putExtra("search", mSearchTerms);

                startActivity(intent);

            }
        });


    }

}
