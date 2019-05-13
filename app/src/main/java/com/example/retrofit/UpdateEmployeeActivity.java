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
    private EditText etEmpNO, etEmpName, EmpAge, etEmpSalary;
    private Button btnSearchEmp, btnRegister, btnDelete;
    private TextView tvData;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employee);
        
        etEmpNO=findViewById(R.id.etEmpNo);
        etEmpName=findViewById(R.id.etEmpName);
        EmpAge=findViewById(R.id.EmpAge);
        etEmpSalary=findViewById(R.id.etEmpSalary);
        btnSearchEmp=findViewById(R.id.btnSearchEmp);
        btnRegister=findViewById(R.id.btnRegister);
        btnDelete=findViewById(R.id.btnDelete);
        tvData=findViewById(R.id.tvData);
        
        
        btnSearchEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search();
            }


            private void Search() {
                String name = etEmpName.getText().toString();
                Float salary = Float.parseFloat(etEmpSalary.getText().toString());
                int age = Integer.parseInt(EmpAge.getText().toString());

                EmployeeCUD employee = new EmployeeCUD(name, salary, age);

                 Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                final EmployeeAPI employeeAPI = retrofit.create(EmployeeAPI.class);

                Call<Employee> ListCall = employeeAPI.getEmployeeByID(Integer.parseInt(etEmpNO.getText().toString()));

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


                btnRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Register();

                    }

                    private void Register() {
                        String name = etEmpName.getText().toString();
                        Float salary = Float.parseFloat(etEmpSalary.getText().toString());
                        int age = Integer.parseInt(EmpAge.getText().toString());

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


                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeleteEmployee();
                    }

                    private void DeleteEmployee() {
                        EmployeeCUD employee = new EmployeeCUD(
                                etEmpName.getText().toString(), Float.parseFloat(
                                        etEmpSalary.getText().toString()),
                                Integer.parseInt(EmpAge.getText().toString())

                        );
                        Call<Void> voidCall =employeeAPI.deleteEmployee(Integer.parseInt(etEmpNO.getText().toString()));

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


