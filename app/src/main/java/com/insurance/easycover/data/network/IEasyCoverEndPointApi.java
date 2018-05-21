package com.insurance.easycover.data.network;


import com.insurance.easycover.data.models.CreateJob;
import com.insurance.easycover.data.models.ForgotPassword;
import com.insurance.easycover.data.models.Login;
import com.insurance.easycover.data.models.Register;
import com.insurance.easycover.data.models.Verify;
import com.insurance.easycover.data.models.response.HandOverData;
import com.insurance.easycover.data.models.response.RenewData;
import com.insurance.easycover.data.models.response.RequestAccept;
import com.insurance.easycover.data.models.response.RequestAcceptAgent;
import com.insurance.easycover.data.models.response.RequestAddQuotation;
import com.insurance.easycover.data.models.response.RequestAssignedJob;
import com.insurance.easycover.data.models.response.RequestComplete;
import com.insurance.easycover.data.models.response.RequestGetQuotDocument;
import com.insurance.easycover.data.models.response.RequestGetQuotationById;
import com.insurance.easycover.data.models.response.RequestJobDetail;
import com.insurance.easycover.data.models.response.RequestResetPassword;
import com.insurance.easycover.data.models.response.RequestSMS;
import com.insurance.easycover.data.models.response.ResponseAccept;
import com.insurance.easycover.data.models.response.ResponseAcceptedJobs;
import com.insurance.easycover.data.models.response.ResponseAddQuotation;
import com.insurance.easycover.data.models.response.ResponseCompany;
import com.insurance.easycover.data.models.response.ResponseCompletedJobs;
import com.insurance.easycover.data.models.response.ResponseCreateJob;
import com.insurance.easycover.data.models.response.ResponseGetAllJob;
import com.insurance.easycover.data.models.response.ResponseGetInsuranceType;
import com.insurance.easycover.data.models.response.ResponseGetQuotation;
import com.insurance.easycover.data.models.response.ResponseHandOverData;
import com.insurance.easycover.data.models.response.ResponseLogin;
import com.insurance.easycover.data.models.response.ResponseOrderHistory;
import com.insurance.easycover.data.models.response.ResponseQuotDocumentData;
import com.insurance.easycover.data.models.response.ResponseReferralCode;
import com.insurance.easycover.data.models.response.ResponseSignUp;
import com.insurance.easycover.data.models.response.ResponseUpdate;
import com.insurance.easycover.data.models.response.ShowJob;
import com.insurance.easycover.data.models.response.TopDataResponse;
import com.insurance.easycover.data.models.response.TopListDataResponse;
import com.insurance.easycover.data.models.response.TopResponse;
import com.insurance.easycover.data.models.response.User;
import com.insurance.easycover.data.models.response.assignJob.ResponseAssignJob;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * @author
 */
public interface IEasyCoverEndPointApi {

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    @POST("customer/create")
    Call<ResponseSignUp> register(@Body Register signUp);

    @POST("user/emailExist")
    Call<TopResponse> emailExist(@Body Register user);

    @POST("user/login")
    Call<ResponseLogin> login(@Body Login login);

    @POST("user/sendSMS")
    Call<TopResponse> sendSMS(@Body RequestSMS verify);

    @POST("user/checkVerfityCode")
    Call<TopResponse> verifyPhone(@Body Verify verify);

    @POST("user/forgetPassword")
    Call<TopListDataResponse<ResponseAccept>> forgetPassword(@Body ForgotPassword forgotPassword);

    @POST("user/resetPassword")
    Call<TopDataResponse<User>> resetPassword(@Body RequestResetPassword forgotPassword);

    @POST("jobs/acceptedJobList")
    Call<ResponseGetAllJob> getAllJobs(@Header("Authorization") String token);


    @Multipart
    @POST("user/update")
    Call<TopDataResponse<User>> updateUserProfile(@Header("Authorization") String token, @PartMap Map<String, RequestBody> map, @Part MultipartBody.Part image);
    //Call<TopDataResponse<User>> updateUserProfile(@Header("Authorization") String token, @PartMap Map<String, RequestBody> map, @Part("photo\"; filename=\"image.jpeg\"") RequestBody file);

//    @Multipart
//    @POST("customer/update")
//    Call<TopListDataResponse<User>> updateUserProfile(@PartMap Map<String, RequestBody> map);

    @POST("jobs/create")
    Call<TopDataResponse<ResponseCreateJob>> createNewJob(@Header("Authorization") String token, @Body CreateJob createJob);

    @POST("agent/assign")
    Call<ResponseAssignJob> assignJob(@Header("Authorization") String token);

    @POST("jobs/acceptedJobList")
    Call<TopListDataResponse<ResponseAcceptedJobs>> getAcceptedJobList(@Header("Authorization") String token);

    @POST("agent/view/history")
    Call<TopListDataResponse<ResponseCompletedJobs>> getAgentHistory(@Header("Authorization") String token);

    @POST("agent/agentCompletedJob")
    Call<TopListDataResponse<ResponseCompletedJobs>> getCompletedJobList(@Header("Authorization") String token);

    @POST("jobs/customerGetCompletedJob")
    Call<TopListDataResponse<ResponseCompletedJobs>> getCustomerCompletedJob(@Header("Authorization") String token);

    @POST("jobs/jobDetail")
    Call<TopDataResponse<ShowJob>> getJobDetail(@Header("Authorization") String token,@Body RequestJobDetail detail);

    @POST("jobs/getQuotDocument")
    Call<TopListDataResponse<ResponseQuotDocumentData>> getQuotDocument(@Header("Authorization") String token, @Body RequestGetQuotDocument getDocument);

    @POST("user/referral")
    Call<TopDataResponse<ResponseReferralCode>> getReferralCode(@Header("Authorization") String token);

    @POST("jobs/getInsuranceType")
    Call<TopListDataResponse<ResponseGetInsuranceType>> getInsuranceType(@Header("Authorization") String token);

    @POST("user/getcompany")
    Call<TopListDataResponse<ResponseCompany>> getCompany(@Header("Authorization") String token);

    @POST("agent/allview")
    Call<TopListDataResponse<ResponseCompletedJobs>> allAssignedJobs(@Header("Authorization") String token);

    @POST("jobs/action")
    Call<TopDataResponse<ResponseAccept>> acceptJob(@Header("Authorization") String token,@Body RequestAccept rat);

    @POST("jobs/acceptAgent")
    Call<TopDataResponse<ResponseAccept>> acceptAgent(@Header("Authorization") String token,@Body RequestAcceptAgent rat);

    @POST("agent/addQuotation")
    Call<TopDataResponse<ResponseAddQuotation>> addQuotation(@Header("Authorization") String token, @Body RequestAddQuotation raq);

    @POST("agent/getQuotation")
    Call<TopListDataResponse<ResponseGetQuotation>> getQuotation(@Header("Authorization") String token);

    @POST("agent/getQuotation")
    Call<TopListDataResponse<ResponseGetQuotation>> getQuotationById(@Header("Authorization") String token, @Body RequestGetQuotationById rQuotId);

    @POST("jobs/acceptedJobList")
    Call<TopListDataResponse<ResponseOrderHistory>> getOrderHistory(@Header("Authorization") String token);

//    @POST("jobs/view")
//    Call<TopListDataResponse<ResponseAssignedJob>> getAssignedJob(@Header("Authorization") String token,@Body RequestAssignedJob raj);

    @POST("jobs/handover")
    Call<TopDataResponse<ResponseHandOverData>> handoverJob(@Header("Authorization") String token, @Body HandOverData handOver);

    @POST("jobs/completeJob")
    Call<TopResponse> completeJob(@Header("Authorization") String token, @Body RequestComplete rec);

    @POST("jobs/renewJob")
    Call<TopDataResponse<ResponseHandOverData>> renewJob(@Header("Authorization") String token, @Body RenewData handOver);

    @Multipart
    @POST("upload/documents")
    Call<TopListDataResponse<String>> uploadDocument(@Part MultipartBody.Part file);
    //Call<String> uploadDocument(@Part("document[]\"; filename=\"file.jpeg\"") RequestBody file);
    //Call<TopListDataResponse<String>> uploadFile(@Part("documents[]\"; filename=\"file.jpeg\"") RequestBody file);
    //Call<JsonObject> uploadFile(@Part("documents[]\"; filename=\"file.jpeg\"") RequestBody file);
    //Call<TopListDataResponse<String>> uploadFile(@Part("documents[]\"; filename=\"file.jpeg\"") RequestBody file);


    @Multipart
    @POST("upload")
    Call<TopDataResponse<String>> uploadImageFile(@Part MultipartBody.Part file);
    //Call<TopDataResponse<String>> uploadSingleFile(@Part("document\"; filename=\"file.jpeg\"") RequestBody file);

    /**
     * Login/SignUp
     */
   /* @POST("owner/addSubAdmin")
    Call<BaseResponse<UserData>> register(@Body SignUp signUp);

    @POST("user/Login")
    Call<BaseResponse<UserData>> login(@Body Login login);

    @POST("user/add")
    Call<BaseResponse<Customer>> addNewCustomer(@Body Customer customer);

    @GET("user")
    Call<BaseListResponse<Customer>> getAllCustomers();

    @POST("delete")
    Call<BaseResponse> deleteCustomer(@Body DeleteCustomer deleteCustomer);

    @POST("user/update")
    Call<BaseResponse<Customer>> updateCustomer(@Body UpdateCust updateCust);
*/
}