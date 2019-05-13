package com.example.retrofit;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import EmployeeAPI.EmployeeAPI;
import model.Employee;
import model.EmployeeCUD;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateEmployeeActivity extends AppCompatActivity {

    private final static String BASE_URL = "http://dummy.restapiexample.com/api/v1/";
    private EditText etEmpNO, etEmpName, EmpAge, etEmpSalary;
    private Button btnSearchEmp, btnUpdate, btnDelete;
    Retrofit retrofit;
    EmployeeAPI employeeAPI;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employee);

        etEmpNO = findViewById(R.id.etEmpNo);
        etEmpName = findViewById(R.id.etEmpName);
        EmpAge = findViewById(R.id.EmpAge);
        etEmpSalary = findViewById(R.id.etEmpSalary);
        btnSearchEmp = findViewById(R.id.btnSearchEmp);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmployee();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmployee();
            }
        });

        btnSearchEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search();
            }
        });
    }

    private void CreateInstance() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        employeeAPI = retrofit.create(EmployeeAPI.class);
    }

    private void Search() {
        CreateInstance();

        Call<Employee> listCall = employeeAPI.getEmployeeByID(
                Integer.parseInt(etEmpNO.getText().toString()));
        listCall.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                etEmpName.setText(response.body().getEmployee_name());
                etEmpSalary.setText(Float.toString(response.body().getEmployee_salary()));
                EmpAge.setText(Integer.toString(response.body().getEmployee_age()));
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Toast.makeText(UpdateEmployeeActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void updateEmployee() {
        CreateInstance();
        EmployeeCUD employee = new EmployeeCUD(
                etEmpName.getText().toString(),
                Float.parseFloat(etEmpSalary.getText().toString()),
                Integer.parseInt(EmpAge.getText().toString()));
        Call<Void> voidCall = employeeAPI.updateEmployee(Integer.parseInt(etEmpNO.getText().toString()), employee);

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(UpdateEmployeeActivity.this, "Updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UpdateEmployeeActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void deleteEmployee() {
        CreateInstance();
        final Call<Void> voidcall = employeeAPI.deleteEmployee(Integer.parseInt(etEmpNO.getText().toString()));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                voidcall.enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(UpdateEmployeeActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateEmployeeActivity.this,UpdateEmployeeActivity.class);
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(UpdateEmployeeActivity.this, "Error: "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();


    }

}
