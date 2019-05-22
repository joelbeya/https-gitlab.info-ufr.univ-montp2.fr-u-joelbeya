package com.example.easycourse.network;

import com.example.easycourse.model.Course;
import com.example.easycourse.model.User;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EasyCourseAPI {
    /*
    * Add new User
    * */
    @POST("users/signup")
    Observable<User> register(@Body User user);

    /*
    * Login User
    * */
    @POST("users/login")
    @FormUrlEncoded
    Observable<String> login(
            @Field("email") String email,
            @Field("password") String password
    );

    /*
    * Add new Course
    * */
    @POST("courses/")
    Observable<Course> addCourse(@Body Course course);

    /*
    * Get a course by its name
    * */
    @GET("courses/{name}")
    @FormUrlEncoded
    Observable<Course> getCourseByName(@Field("name") String name);

    /*
    * Get all courses for a schoolLevel
    * */
    @GET("courses/{schoolLevel}")
    @FormUrlEncoded
    Observable<Course> getCourseBySchoolLevel(@Field("schoolLevel") String schoolLevel);

    /* Autres méthodes à implémenter sur le serveur */

    @GET("users/{email}")
    Observable<User> getProfile(@Path("email") String email);

    @PUT("users/{email}")
    Observable<Response> changePassword(@Path("email") String email, @Body User user);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordInit(@Path("email") String email);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordFinish(@Path("email") String email, @Body User user);
}
