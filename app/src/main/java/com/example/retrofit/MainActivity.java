package com.example.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import EmployeeAPI.EmployeeAPI;
import model.Employee;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView tvEmployee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    tvEmployee=findViewById(R.id.tvEmployee);

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://dummy.restapiexample.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EmployeeAPI employeeAPI=retrofit.create(EmployeeAPI.class);
        Call<List<Employee>> ListCall = employeeAPI.getAllEmployee();


        ListCall.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                List<Employee>employeeList =response.body();
                for (Employee employee:employeeList){
                    String content="";
                    content+= "ID : " + employee.getId() +"\n";
                    content+= "Name : " + employee.getEmployee_name() +"\n";
                    content+= "Age : " + employee.getEmployee_age() +"\n";
                    content+= "Salary : " + employee.getEmployee_salary() +"\n";
                    content+= "------------------------- "+"\n";

                    tvEmployee.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
tvEmployee.setText("Error " + t.getMessage());

            }
        });



    }
}
