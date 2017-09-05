package com.ben.router;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ben.annotations.annotation.Router;

@Router(action = "test2Activity")
public class Test2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
    }
}
