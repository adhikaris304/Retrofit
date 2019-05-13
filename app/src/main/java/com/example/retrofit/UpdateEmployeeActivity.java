package com.example.retrofit;

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
    private EditText etEmpID_Up, etName_Up, etAge_Up, etSalary_Up;
    private Button btnSearch_Up, btnRegister_Up, btnDelete_Up;
    private TextView tvData;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employee);
        
        etEmpID_Up=findViewById(R.id.etEmpID_Up);
        etName_Up=findViewById(R.id.etName_Up);
        etAge_Up=findViewById(R.id.etAge_Up);
        etSalary_Up=findViewById(R.id.etSalary_Up);
        btnSearch_Up=findViewById(R.id.btnSearch_Up);
        btnRegister_Up=findViewById(R.id.btnRegister_Up);
        btnDelete_Up=findViewById(R.id.btnDelete_Up);
        tvData=findViewById(R.id.tvData);
        
        
        btnSearch_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search();
            }


            private void Search() {
                String name = etName_Up.getText().toString();
                Float salary = Float.parseFloat(etSalary_Up.getText().toString());
                int age = Integer.parseInt(etAge_Up.getText().toString());

                EmployeeCUD employee = new EmployeeCUD(name, salary, age);

                 Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                final EmployeeAPI employeeAPI = retrofit.create(EmployeeAPI.class);

                Call<Employee> ListCall = employeeAPI.getEmployeeByID(Integer.parseInt(etEmpID_Up.getText().toString()));

                ListCall.enqueue(new Callback<Employee>() {
                    @Override
                    public void onResponse(Call<Employee> call, Response<Employee> response) {
                        Toast.makeText(UpdateEmployeeActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();

                        String content = "";
                        content += "Id : " + response.body().getId() + "\n";
                        content += "Name : " + response.body().getEmployee_name() + "\n";
                        content += "Age : " + response.body().getEmployee_age() + "\n";
                        content += "Salary : " + response.body().getEmployee_salary() + "\n";

                        tvData.setText(content);
                    }

                    @Override
                    public void onFailure(Call<Employee> call, Throwable t) {

                    }
                });


                btnRegister_Up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Register();

                    }

                    private void Register() {
                        String name = etName_Up.getText().toString();
                        Float salary = Float.parseFloat(etSalary_Up.getText().toString());
                        int age = Integer.parseInt(etAge_Up.getText().toString());

                        EmployeeCUD employeeCUD = new EmployeeCUD(name, salary, age);

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        EmployeeAPI employeeAPI = retrofit.create(EmployeeAPI.class);

                        Call<Void> voidCall = employeeAPI.registerEmployee(employeeCUD);

                        voidCall.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(UpdateEmployeeActivity.this, "You have been successfully registered", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(UpdateEmployeeActivity.this, "Error :" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                });


                btnDelete_Up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeleteEmployee();
                    }

                    private void DeleteEmployee() {
                        EmployeeCUD employee = new EmployeeCUD(
                                etName_Up.getText().toString(), Float.parseFloat(etSalary_Up.getText().toString()),
                                Integer.parseInt(etAge_Up.getText().toString())

                        );
                        Call<Void> voidCall =employeeAPI.deleteEmployee(Integer.parseInt(etEmpID_Up.getText().toString()));

                        voidCall.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(UpdateEmployeeActivity.this, "Successfully deleted", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(UpdateEmployeeActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }

                });

            }
        });
    }
}


