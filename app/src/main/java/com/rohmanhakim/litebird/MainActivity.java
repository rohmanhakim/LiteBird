package com.rohmanhakim.litebird;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.rohmanhakim.litebird.litho.LithoFeedActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnRecyclerView;

    Button btnLitho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRecyclerView = (Button) findViewById(R.id.btnRecyclerView);
        btnRecyclerView.setOnClickListener(this);
        btnLitho = (Button) findViewById(R.id.btnLitho);
        btnLitho.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLitho:
                startActivity(new Intent(MainActivity.this, LithoFeedActivity.class));
                break;
            case R.id.btnRecyclerView:
                startActivity(new Intent(MainActivity.this, FeedsActivity.class));
                break;
        }
    }
}
