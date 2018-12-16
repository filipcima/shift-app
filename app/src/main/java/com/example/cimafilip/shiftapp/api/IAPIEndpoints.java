package com.example.cimafilip.shiftapp.api;

import com.example.cimafilip.shiftapp.models.*;
import com.example.cimafilip.shiftapp.models.helpers.NotificationHelper;
import com.example.cimafilip.shiftapp.models.helpers.RequestHelper;
import com.example.cimafilip.shiftapp.models.helpers.ShiftHelper;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IAPIEndpoints {

    @GET("people/{surname}")
    Call<User> getUser(@Path("surname") String surname);

    @GET("people")
    Call<UserList> getUsers(@Query("where") String query);

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
    Call<ShiftHelper> patchShift(@Path("id") String id,
                                 @Body ShiftHelper shift,
                                 @Header("If-Match") String etag);

    @PATCH("notifications/{id}")
    Call<NotificationHelper> patchNotification(@Path("id") String id,
                                               @Body NotificationHelper notification,
                                               @Header("If-Match") String etag);

    @POST("requests")
    Call<RequestHelper> postRequest(@Body RequestHelper request);

    @POST("notifications")
    Call<NotificationHelper> postNotification(@Body NotificationHelper notification);

}
