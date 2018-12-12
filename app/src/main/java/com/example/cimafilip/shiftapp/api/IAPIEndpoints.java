package com.example.cimafilip.shiftapp.api;

import com.example.cimafilip.shiftapp.models.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IAPIEndpoints {

    @GET("people/{surname}")
    Call<User> getUser(@Path("surname") String surname);

    @GET("notifications")
    Call<NotificationList> getNotifications(@Query("where") String query,
                                            @Query("sort") String sort,
                                            @Query("embedded") String embedded);

    @GET("superior_plans")
    Call<SuperiorPlanList> getSuperiorPlans(@Query("where") String query,
                                       @Query("max_results") String limit,
                                       @Query("sort") String sort,
                                       @Query("embedded") String embedded);

    @GET("shifts")
    Call<ShiftList> getShifts(@Query("where") String query,
                              @Query("embedded") String embedded);

    @GET("shifts")
    Call<ShiftList> getShiftsByPlan(@Query("where") String query,
                                    @Query("sort") String sort,
                                    @Query("embedded") String embedded);


    @GET("shifts")
    Call<ShiftList> getNextShift(@Query("where") String query,
                                 @Query("max_results") String limit,
                                 @Query("sort") String sort,
                                 @Query("embedded") String embedded);

    @PATCH("shifts/{id}")
    Call<Shift> patchShift(@Path("id") String id);

}
