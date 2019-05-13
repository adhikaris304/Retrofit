package EmployeeAPI;

import java.util.List;

import model.Employee;
import model.EmployeeCUD;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EmployeeAPI {
    @GET("employees")
    Call<List<Employee>> getAllEmployee();

    @GET("employee/{empID}")
    Call<Employee> getEmployeeByID(@Path("empID") int empId);

    @POST("create")
    Call<Void> registerEmployee (@Body EmployeeCUD emp);

   @PUT("update/{empID}")
    Call<Void> updateEmployee(@Path("empID") int empId, EmployeeCUD emp);

   @DELETE("delete/empId")
    Call<Void> deleteEmployee(@Path("empId")int empId);
}

