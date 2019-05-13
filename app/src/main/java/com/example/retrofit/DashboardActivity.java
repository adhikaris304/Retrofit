package com.example.retrofit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {

    private Button btnCreate_Dash, btnUpdate_Dash, btnView_Dash, btnSearch_Dash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnCreate_Dash=findViewById(R.id.btnCreate_Dash);
        btnSearch_Dash=findViewById(R.id.btnSearch_Dash);
        btnView_Dash=findViewById(R.id.btnView_dash);
        btnUpdate_Dash=findViewById(R.id.btnUpdate_Dash);

        btnCreate_Dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplication(),RegisterEmployeeActivity.class);
                startActivity(intent);
            }
        });

        btnSearch_Dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplication(), SearchEmployeeActivity.class);
                startActivity(intent);
            }
        });

        btnUpdate_Dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), UpdateEmployeeActivity.class);
                startActivity(intent);
            }
        });

        btnView_Dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
