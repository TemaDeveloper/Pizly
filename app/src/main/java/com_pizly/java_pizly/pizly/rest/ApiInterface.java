package com_pizly.java_pizly.pizly.rest;

import java.util.List;

import com_pizly.java_pizly.pizly.models.BannedUser;
import com_pizly.java_pizly.pizly.models.Friend;
import com_pizly.java_pizly.pizly.models.Party;
import com_pizly.java_pizly.pizly.models.Person;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("signup.php")
    Call<ResponseBody> signup(@Field("name") String name,
                              @Field("age") int age,
                              @Field("hobby") String hobby,
                              @Field("description") String description,
                              @Field("phone_no") String phoneNo,
                              @Field("email") String email,
                              @Field("password") String password,
                              @Field("image") String image);


    @FormUrlEncoded
    @POST("userID.php")
    Call<Person> getUserID(@Field("email") String email);

    @FormUrlEncoded
    @POST("userDataGetter.php")
    Call<Person> getUser(@Field("id") int id);

    @FormUrlEncoded
    @POST("userUpdate.php")
    Call<ResponseBody> updateUserData(@Field("id") int id,
                                      @Field("name") String name,
                                      @Field("hobby") String hobby,
                                      @Field("description") String description,
                                      @Field("phone_no") String phoneNo,
                                      @Field("email") String email,
                                      @Field("image") String image);

    @FormUrlEncoded
    @POST("partyAddition.php")
    Call<ResponseBody> addParty(@Field("title") String title,
                                @Field("address") String address,
                                @Field("open_date") String open_date,
                                @Field("rules") String rules,
                                @Field("open_time") String open_time,
                                @Field("finish_time") String finish_time,
                                @Field("additional_info") String additional_info,
                                @Field("pricing") String pricing,
                                @Field("uid") int uid,
                                @Field("imageParty") String imageParty);

    @GET("parties.php")
    Call<List<Party>> getParties(@Query("uid") int uid);

    @GET("allUsers.php")
    Call<List<Person>> getAllUsers(@Query("id") int id);

    @FormUrlEncoded
    @POST("friendAddition.php")
    Call<ResponseBody> addFriend(@Field("uid") int id,
                                 @Field("friendID") int friendID,
                                 @Field("friend_request") int request);

    @GET("requests.php")
    Call<List<Friend>> getRequests(@Query("friendID") int friendID);

    @GET("friends.php")
    Call<List<Friend>> getFriends(@Query("uid") int uid, @Query("friendID") int friendID);

    @FormUrlEncoded
    @POST("friendRequestUpdate.php")
    Call<ResponseBody> updateFriendRequest(@Field("id") int id);

    @FormUrlEncoded
    @POST("blockedUser.php")
    Call<BannedUser> getBlockedUser(@Field("blockedFromID") int blockedFromID);

    @FormUrlEncoded
    @POST("partyUpdate.php")
    Call<ResponseBody> updateYourParty(@Field("id") int id,
                                       @Field("uid") int uid,
                                       @Field("title") String title,
                                       @Field("rules") String rules,
                                       @Field("additional_info") String additional_info,
                                       @Field("imageParty") String imageParty);

    @FormUrlEncoded
    @POST("blockAddition.php")
    Call<ResponseBody> blockUser(@Field("blockedFromID") int uid,
                                 @Field("blockToID") int blockID);

    @FormUrlEncoded
    @POST("friendDeleting.php")
    Call<ResponseBody> removeFriend(@Field("id") int id);

    @GET("allBlockedUsers.php")
    Call<List<BannedUser>> getBlockedFriends(@Query("blockedFromID") int uid);


    @FormUrlEncoded
    @POST("blockRemoving.php")
    Call<ResponseBody> removeBlockedFriend(@Field("id") int id);

    @FormUrlEncoded
    @POST("changingPasswordInProfile.php")
    Call<ResponseBody> changeOldPassword(@Field("email") String email,
                                         @Field("password") String oldPassword,
                                         @Field("new_password") String newPassword,
                                         @Field("new_confirm_password") String newConfirmedPassword);


}
