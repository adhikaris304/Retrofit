package EmployeeAPI;

import java.util.List;

import model.Employee;
import retrofit2.Call;
import retrofit2.http.GET;

public interface EmployeeAPI {
    @GET("employees")
    Call<List<Employee>> getAllEmployee();

}

