package com.insurance.easycover.data.network;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.insurance.easycover.AppSession;
import com.insurance.easycover.data.events.EventsIds;
import com.insurance.easycover.data.events.ListDataEvent;
import com.insurance.easycover.data.events.SimpleEvent;
import com.insurance.easycover.data.events.SingleDataEvent;
import com.insurance.easycover.data.local.AppSharedPreferences;
import com.insurance.easycover.data.models.CreateJob;
import com.insurance.easycover.data.models.ForgotPassword;
import com.insurance.easycover.data.models.Login;
import com.insurance.easycover.data.models.ProgressRequestBody;
import com.insurance.easycover.data.models.Register;
import com.insurance.easycover.data.models.Verify;
import com.insurance.easycover.data.models.response.HandOverData;
import com.insurance.easycover.data.models.response.RenewData;
import com.insurance.easycover.data.models.response.RequestAccept;
import com.insurance.easycover.data.models.response.RequestAcceptAgent;
import com.insurance.easycover.data.models.response.RequestAddQuotation;
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
import com.insurance.easycover.data.models.response.SinUpData;
import com.insurance.easycover.data.models.response.TopDataResponse;
import com.insurance.easycover.data.models.response.TopListDataResponse;
import com.insurance.easycover.data.models.response.TopResponse;
import com.insurance.easycover.data.models.response.User;
import com.insurance.easycover.data.models.response.assignJob.ResponseAssignJob;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.insurance.easycover.AppClass.getContext;

/**
 * Created by NaveedAli on 3/16/2017.
 */

public class NetworkController {
    private IEasyCoverEndPointApi easyCoverServiceFactory;

    private static NetworkController sNetworkController;

    public static NetworkController getInstance() {
        if (sNetworkController == null) {
            sNetworkController = getNewInstance();
        }
        return sNetworkController;
    }

    private synchronized static NetworkController getNewInstance() {
        if (sNetworkController != null)
            return sNetworkController;
        return new NetworkController();
    }

    private NetworkController() {
        easyCoverServiceFactory = EasyCoverServiceFactory.getInstance();
    }

    public void signUp(Register signUp) {
        Call<ResponseSignUp> call = EasyCoverServiceFactory.getInstance().register(signUp);
        Log.i("handover", "json = " + new Gson().toJson(signUp));
        //Call<TopListDataResponse<User>> call = EasyCoverServiceFactory.getInstance().register(signUp);
        call.enqueue(new Callback<ResponseSignUp>() {
            @Override
            public void onResponse(Call<ResponseSignUp> call, Response<ResponseSignUp> response) {
                ResponseSignUp resp = response.body();
                if (resp != null) {
                    if (resp.getResponseCode() == 1) {
                        //User body = resp.data.get(0);
                        //body.save();
                        AppSession.getInstance().setToken(resp.getToken());
                        AppSharedPreferences.getInstance(getContext()).saveToken(resp.getToken());
                        List<User> temp = new ArrayList<User>();
                        User mUser = new User();
                        SinUpData mData = resp.getData();
                        mUser.setId(mData.getId());
                        mUser.setUsername(mData.getUsername());
                        mUser.setEmail(mData.getEmail());
                        mUser.setPhoneno(mData.getPhoneno());
                        mUser.setVerifyToken(mData.getVerifyToken());
                        mUser.setUsertype(mData.getUsertype());
                        mUser.setStatus(mData.getStatus());
                        mUser.setIsAvailable(mData.getIsAvailable());
                        mUser.setUpdatedAt(mData.getUpdatedAt());
                        mUser.setCreatedAt(mData.getCreatedAt());
                        temp.add(mUser);
                        postEventListResponse(true, EventsIds.ID_REGISTER, resp.getMessage(), temp);
                    } else
                        postEventListResponse(false, EventsIds.ID_REGISTER, resp.getMessage(), null);
                } else {
                    try {
                        postEventListResponse(false, EventsIds.ID_REGISTER, getErrorMessage("" + response.errorBody().string()), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseSignUp> call, Throwable t) {
                postEventListResponse(false, EventsIds.ID_REGISTER, "" + t.getMessage(), null);
            }
        });
    }


    public void login(Login login) {
        Call<ResponseLogin> call = EasyCoverServiceFactory.getInstance().login(login);
        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                ResponseLogin resp = response.body();
                if (resp != null) {
                    if (resp.getResponseCode() == 1) {
                        List<User> temp = new ArrayList<User>();
                        temp.add(resp.getData());
                        AppSession.getInstance().setToken(resp.getToken());
                        AppSharedPreferences.getInstance(getContext()).saveToken(resp.getToken());
                        postEventListResponse(true,EventsIds.ID_LOGIN,resp.getMessage(), temp);
                    } else {
                        postEventListResponse(false,EventsIds.ID_LOGIN,"Email or Password is not valid",null);
                    }
                    /*if (resp.responseCode == 1) {
                        ResponseLogin body = resp.data.get(0);
                        //body.save();
                        postEventListResponse(true, EventsIds.ID_LOGIN, resp.message, resp.data);
                    } else
                        postEventListResponse(false, EventsIds.ID_LOGIN, resp.message, null);*/
                } else {
                    postEventListResponse(false, EventsIds.ID_LOGIN, "Email or Password is not valid", null);
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                postEventListResponse(false, EventsIds.ID_LOGIN, "Email or Password is not valid", null);
            }
        });
    }

    public void sendSMS(RequestSMS rsms) {
        Call<TopResponse> call = EasyCoverServiceFactory.getInstance().sendSMS(rsms);
        call.enqueue(new Callback<TopResponse>() {
            @Override
            public void onResponse(Call<TopResponse> call, Response<TopResponse> response) {
                TopResponse resp = response.body();
                if (resp != null) {
                    if (resp.responseCode == 1) {
                        postEventSimpleResponse(true, EventsIds.ID_SENDSMS, resp.message);
                    } else postEventSimpleResponse(false, EventsIds.ID_SENDSMS, resp.message);
                } else {
                    try {
                        postEventSimpleResponse(false, EventsIds.ID_SENDSMS, getErrorMessage("" + response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TopResponse> call, Throwable t) {
                postEventSimpleResponse(false, EventsIds.ID_SENDSMS, "" + t.getMessage());
            }
        });
    }

    public void verifyPhone(Verify verify) {
        Call<TopResponse> call = EasyCoverServiceFactory.getInstance().verifyPhone(verify);
        call.enqueue(new Callback<TopResponse>() {
            @Override
            public void onResponse(Call<TopResponse> call, Response<TopResponse> response) {
                TopResponse resp = response.body();
                if (resp != null) {
                    if (resp.responseCode == 1) {
                        postEventSimpleResponse(true, EventsIds.ID_VERIFY, resp.message);
                    } else postEventSimpleResponse(false, EventsIds.ID_VERIFY, resp.message);
                } else {
                    try {
                        postEventSimpleResponse(false, EventsIds.ID_VERIFY, getErrorMessage("" + response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TopResponse> call, Throwable t) {
                postEventSimpleResponse(false, EventsIds.ID_VERIFY, "" + t.getMessage());
            }
        });
    }

    public void forgetPassword(ForgotPassword forgotPassword) {
        Call<TopListDataResponse<ResponseAccept>> call = EasyCoverServiceFactory.getInstance().forgetPassword(forgotPassword);
        call.enqueue(new Callback<TopListDataResponse<ResponseAccept>>() {
            @Override
            public void onResponse(Call<TopListDataResponse<ResponseAccept>> call, Response<TopListDataResponse<ResponseAccept>> response) {
                TopResponse resp = response.body();
                if (resp != null) {
                    postEventSimpleResponse(resp.responseCode == 1, EventsIds.ID_FORGOT_PASS, "" + resp.message);
                } else {
                    postEventSimpleResponse(false, EventsIds.ID_FORGOT_PASS, "Can not send Mail");
                }
            }

            @Override
            public void onFailure(Call<TopListDataResponse<ResponseAccept>> call, Throwable t) {
                postEventSimpleResponse(false, EventsIds.ID_FORGOT_PASS, "" + t.getMessage());
            }
        });
    }

    public void resetPassword(RequestResetPassword forgotPassword) {
        Call<TopDataResponse<User>> call = EasyCoverServiceFactory.getInstance().resetPassword(forgotPassword);
        call.enqueue(new Callback<TopDataResponse<User>>() {
            @Override
            public void onResponse(Call<TopDataResponse<User>> call, Response<TopDataResponse<User>> response) {
                TopResponse resp = response.body();
                if (resp != null) {
                    postEventSimpleResponse(resp.responseCode == 1, EventsIds.ID_RESET_PASS, "" + resp.message);
                } else {
                    postEventSimpleResponse(false, EventsIds.ID_RESET_PASS, "Can not send Mail");
                }
            }

            @Override
            public void onFailure(Call<TopDataResponse<User>> call, Throwable t) {
                postEventSimpleResponse(false, EventsIds.ID_RESET_PASS, "" + t.getMessage());
            }
        });
    }


    public void updateUser(Map<String, String> map, String photoPath) {

        Map<String, RequestBody> dataMap = new HashMap<>();
        for (String key : map.keySet()) {
            dataMap.put(key, RequestBody.create(MediaType.parse("text/plain"), map.get(key).toString()));
        }

        Call<TopDataResponse<User>> call;
        String token = AppSession.getInstance().getToken();
        if (token.equals("")) {Toast.makeText(getContext(), "Please login", Toast.LENGTH_SHORT).show();return;}
        if (photoPath != null && !photoPath.isEmpty()) {
            File f = new File(photoPath);
            //RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
            RequestBody file = RequestBody.create(MediaType.parse("multipart/form-data"), f);
            final MultipartBody.Part filePart = MultipartBody.Part.createFormData("photo", f.getName(), file);
            call = easyCoverServiceFactory.updateUserProfile("Bearer " + token, dataMap, filePart);
        } else call = easyCoverServiceFactory.updateUserProfile("Bearer " + token, dataMap, null);

        call.enqueue(new Callback<TopDataResponse<User>>() {
            @Override
            public void onResponse(Call<TopDataResponse<User>> call, Response<TopDataResponse<User>> response) {
                if (response.isSuccessful()) {
                    TopDataResponse<User> data = response.body();
                    if (data.responseCode == 1) {
//                        AppSharedPreferences.getInstance(getContext()).saveUserData(new Gson().toJson(data.data.get(0)));
//                        AppSession.getInstance().setUserData(data.data.get(0));
//                        User oldUser = new Gson().fromJson(AppSharedPreferences.getInstance(getContext()).getUserData(), User.class);
//                        for (Map.Entry<String, String> entry : map.entrySet())
//                        {
//                            System.out.println(entry.getKey() + "/" + entry.getValue());
//                        }
//                        List<User> temp = new ArrayList<User>();
//                        temp.add(oldUser);

                        postEventSingleResponse(true, EventsIds.ID_UPDATE_PROFILE, data.message, data.data);
                    } else {
                        postEventSingleResponse(false, EventsIds.ID_UPDATE_PROFILE, data.message, null);
                    }
                } else {
                    postEventSingleResponse(false, EventsIds.ID_UPDATE_PROFILE, "" + getErrorMessage(response.errorBody().toString()), null);
                }
            }

            @Override
            public void onFailure(Call<TopDataResponse<User>> call, Throwable t) {
                postEventSingleResponse(false, EventsIds.ID_UPDATE_PROFILE, "" + t.getMessage(), null);

            }
        });

    }


    public void createNewJob(CreateJob createJob) {
        String token = AppSession.getInstance().getToken();
        if (token.equals("")) {Toast.makeText(getContext(), "Please login", Toast.LENGTH_SHORT).show();return;}
        //Call<ResponseLogin> call = EasyCoverServiceFactory.getInstance().login(login);
        Call<TopDataResponse<ResponseCreateJob>> call = EasyCoverServiceFactory.getInstance().createNewJob("Bearer " + token,createJob);
        call.enqueue(new Callback<TopDataResponse<ResponseCreateJob>>() {
            @Override
            public void onResponse(Call<TopDataResponse<ResponseCreateJob>> call, Response<TopDataResponse<ResponseCreateJob>> response) {
                if (response.isSuccessful()) {
                    TopDataResponse<ResponseCreateJob> body = response.body();
                    if (body.responseCode == 1) {
                        postEventSingleResponse(true, EventsIds.ID_CREATE_JOB, "" + body.message, body.data);
                    } else
                        postEventSingleResponse(false, EventsIds.ID_CREATE_JOB, "" + body.message, null);

                } else
                    postEventSingleResponse(false, EventsIds.ID_CREATE_JOB, "" + getErrorMessage(response.errorBody().toString()), null);
            }

            @Override
            public void onFailure(Call<TopDataResponse<ResponseCreateJob>> call, Throwable t) {
                postEventSingleResponse(false, EventsIds.ID_CREATE_JOB, "" + t.getMessage(), null);
            }
        });
    }

    public void assignJob() {
        String token = AppSession.getInstance().getToken();
        if (token.equals("")) {Toast.makeText(getContext(), "Please login", Toast.LENGTH_SHORT).show();return;}
        Call<ResponseAssignJob> call = EasyCoverServiceFactory.getInstance().assignJob("Bearer " + token);
        call.enqueue(new Callback<ResponseAssignJob>() {
            @Override
            public void onResponse(Call<ResponseAssignJob> call, Response<ResponseAssignJob> response) {
                ResponseAssignJob resp = response.body();
                if (resp != null) {
                    if (resp.getResponseCode() == 1) {
                        postEventListResponse(true,EventsIds.ID_ASSIGNJOB,resp.getMessage(), resp.getData());
                    } else {
                        postEventListResponse(false,EventsIds.ID_ASSIGNJOB,resp.getMessage(),null);
                    }
                } else {
                    try {
                        postEventListResponse(false, EventsIds.ID_ASSIGNJOB, getErrorMessage("" + response.errorBody().string()), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseAssignJob> call, Throwable t) {
                postEventListResponse(false, EventsIds.ID_ASSIGNJOB, t.getMessage(), null);
            }
        });
    }

    public void handover(HandOverData handover) {
        String token = AppSession.getInstance().getToken();
        if (token.equals("")) {Toast.makeText(getContext(), "Please login", Toast.LENGTH_SHORT).show();return;}
        Call<TopDataResponse<ResponseHandOverData>> call = EasyCoverServiceFactory.getInstance().handoverJob("Bearer " + token,handover);
        call.enqueue(new Callback<TopDataResponse<ResponseHandOverData>>() {
            @Override
            public void onResponse(Call<TopDataResponse<ResponseHandOverData>> call, Response<TopDataResponse<ResponseHandOverData>> response) {
                TopDataResponse<ResponseHandOverData> resp = response.body();
                if (resp != null) {
                    if (resp.responseCode == 1) {
                        postEventSingleResponse(true,EventsIds.ID_HANDOVER,resp.message, resp.data);
                    } else {
                        postEventSingleResponse(false,EventsIds.ID_HANDOVER,resp.message,null);
                    }
                } else {
                    try {
                        postEventSingleResponse(false, EventsIds.ID_HANDOVER, getErrorMessage("" + response.errorBody().string()), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TopDataResponse<ResponseHandOverData>> call, Throwable t) {
                postEventSingleResponse(false, EventsIds.ID_HANDOVER, t.getMessage(), null);
            }
        });
    }

    public void completeJob(RequestComplete rec) {
        String token = AppSession.getInstance().getToken();
        if (token.equals("")) {Toast.makeText(getContext(), "Please login", Toast.LENGTH_SHORT).show();return;}
        Call<TopResponse> call = EasyCoverServiceFactory.getInstance().completeJob("Bearer " + token, rec);
        call.enqueue(new Callback<TopResponse>() {
            @Override
            public void onResponse(Call<TopResponse> call, Response<TopResponse> response) {
                TopResponse resp = response.body();
                if (resp != null) {
                    if (resp.responseCode == 1) {
                        postEventSimpleResponse(true, EventsIds.ID_COMPLETE, resp.message);
                    } else {
                        postEventSimpleResponse(false,EventsIds.ID_COMPLETE,resp.message);
                    }
                } else {
                    try {
                        postEventSimpleResponse(false, EventsIds.ID_COMPLETE, getErrorMessage("" + response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TopResponse> call, Throwable t) {
                postEventSimpleResponse(false, EventsIds.ID_COMPLETE, t.getMessage());
            }
        });
    }

    public void reNewData(RenewData renewdata) {
        String token = AppSession.getInstance().getToken();
        if (token.equals("")) {Toast.makeText(getContext(), "Please login", Toast.LENGTH_SHORT).show();return;}
        Call<TopDataResponse<ResponseHandOverData>> call = EasyCoverServiceFactory.getInstance().renewJob("Bearer " + token,renewdata);
        call.enqueue(new Callback<TopDataResponse<ResponseHandOverData>>() {
            @Override
            public void onResponse(Call<TopDataResponse<ResponseHandOverData>> call, Response<TopDataResponse<ResponseHandOverData>> response) {
                TopDataResponse<ResponseHandOverData> resp = response.body();
                if (resp != null) {
                    if (resp.responseCode == 1) {
                        postEventSingleResponse(true,EventsIds.ID_HANDOVER,resp.message, resp.data);
                    } else {
                        postEventSingleResponse(false,EventsIds.ID_HANDOVER,resp.message,null);
                    }
                } else {
                    try {
                        postEventSingleResponse(false, EventsIds.ID_HANDOVER, getErrorMessage("" + response.errorBody().string()), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TopDataResponse<ResponseHandOverData>> call, Throwable t) {
                postEventSingleResponse(false, EventsIds.ID_HANDOVER, t.getMessage(), null);
            }
        });
    }

    public void getAllAssignedJobs() {
        String token = AppSession.getInstance().getToken();
        if (token.equals("")) {Toast.makeText(getContext(), "Please login", Toast.LENGTH_SHORT).show();return;}
        Call<TopListDataResponse<ResponseCompletedJobs>> call = EasyCoverServiceFactory.getInstance().allAssignedJobs("Bearer " + token);
        call.enqueue(new Callback<TopListDataResponse<ResponseCompletedJobs>>() {
            @Override
            public void onResponse(Call<TopListDataResponse<ResponseCompletedJobs>> call, Response<TopListDataResponse<ResponseCompletedJobs>> response) {
                TopListDataResponse<ResponseCompletedJobs> resp = response.body();
                if (resp != null) {
                    if (resp.responseCode == 1) {
                        postEventListResponse(true,EventsIds.ID_ALLASSIGNEDJOBS,resp.message, resp.data);
                    } else {
                        postEventListResponse(false,EventsIds.ID_ALLASSIGNEDJOBS,resp.message,null);
                    }
                } else {
                    try {
                        postEventListResponse(false, EventsIds.ID_ALLASSIGNEDJOBS, getErrorMessage("" + response.errorBody().string()), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TopListDataResponse<ResponseCompletedJobs>> call, Throwable t) {
                postEventListResponse(false, EventsIds.ID_ALLASSIGNEDJOBS, t.getMessage(), null);
            }
        });
    }

    public void acceptJob(RequestAccept rat) {
        String token = AppSession.getInstance().getToken();
        if (token.equals("")) {Toast.makeText(getContext(), "Please login", Toast.LENGTH_SHORT).show();return;}
        Call<TopDataResponse<ResponseAccept>> call = EasyCoverServiceFactory.getInstance().acceptJob("Bearer " + token,rat);
        call.enqueue(new Callback<TopDataResponse<ResponseAccept>>() {
            @Override
            public void onResponse(Call<TopDataResponse<ResponseAccept>> call, Response<TopDataResponse<ResponseAccept>> response) {
                TopDataResponse<ResponseAccept> resp = response.body();
                if (resp != null) {
                    if (resp.responseCode == 1) {
                        postEventSingleResponse(true,EventsIds.ID_ACCEPTJOB,resp.message, resp.data);
                    } else {
                        postEventSingleResponse(false,EventsIds.ID_ACCEPTJOB,resp.message,null);
                    }
                } else {
                    try {
                        postEventSingleResponse(false, EventsIds.ID_ACCEPTJOB, getErrorMessage("" + response.errorBody().string()), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TopDataResponse<ResponseAccept>> call, Throwable t) {
                postEventSingleResponse(false, EventsIds.ID_ACCEPTJOB, t.getMessage(), null);
            }
        });
    }

    public void acceptAgent(RequestAcceptAgent rat) {
        String token = AppSession.getInstance().getToken();
        if (token.equals("")) {Toast.makeText(getContext(), "Please login", Toast.LENGTH_SHORT).show();return;}
        Call<TopDataResponse<ResponseAccept>> call = EasyCoverServiceFactory.getInstance().acceptAgent("Bearer " + token,rat);
        call.enqueue(new Callback<TopDataResponse<ResponseAccept>>() {
            @Override
            public void onResponse(Call<TopDataResponse<ResponseAccept>> call, Response<TopDataResponse<ResponseAccept>> response) {
                TopDataResponse<ResponseAccept> resp = response.body();
                if (resp != null) {
                    if (resp.responseCode == 1) {
                        postEventSingleResponse(true,EventsIds.ID_ACCEPTJOB,resp.message, resp.data);
                    } else {
                        postEventSingleResponse(false,EventsIds.ID_ACCEPTJOB,resp.message,null);
                    }
                } else {
                    try {
                        postEventSingleResponse(false, EventsIds.ID_ACCEPTJOB, getErrorMessage("" + response.errorBody().string()), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TopDataResponse<ResponseAccept>> call, Throwable t) {
                postEventSingleResponse(false, EventsIds.ID_ACCEPTJOB, t.getMessage(), null);
            }
        });
    }

    public void addQuotation(RequestAddQuotation raq) {
        String token = AppSession.getInstance().getToken();
        if (token.equals("")) {Toast.makeText(getContext(), "Please login", Toast.LENGTH_SHORT).show();return;}
        Call<TopDataResponse<ResponseAddQuotation>> call = EasyCoverServiceFactory.getInstance().addQuotation("Bearer " + token, raq);
        call.enqueue(new Callback<TopDataResponse<ResponseAddQuotation>>() {
            @Override
            public void onResponse(Call<TopDataResponse<ResponseAddQuotation>> call, Response<TopDataResponse<ResponseAddQuotation>> response) {
                TopDataResponse<ResponseAddQuotation> resp = response.body();
                if (resp != null) {
                    if (resp.responseCode == 1) {
                        postEventSimpleResponse(true,EventsIds.ID_ADDQUOTATION,resp.message);
                    } else {
                        postEventSimpleResponse(false,EventsIds.ID_ADDQUOTATION,resp.message);
                    }
                } else {
                    try {
                        postEventSimpleResponse(false, EventsIds.ID_ADDQUOTATION, getErrorMessage("" + response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TopDataResponse<ResponseAddQuotation>> call, Throwable t) {
                postEventSimpleResponse(false, EventsIds.ID_ADDQUOTATION, t.getMessage());
            }
        });
    }

    public void getQuotation() {
        String token = AppSession.getInstance().getToken();
        if (token.equals("")) {Toast.makeText(getContext(), "Please login", Toast.LENGTH_SHORT).show();return;}
        Call<TopListDataResponse<ResponseGetQuotation>> call = EasyCoverServiceFactory.getInstance().getQuotation("Bearer " + token);
        call.enqueue(new Callback<TopListDataResponse<ResponseGetQuotation>>() {
            @Override
            public void onResponse(Call<TopListDataResponse<ResponseGetQuotation>> call, Response<TopListDataResponse<ResponseGetQuotation>> response) {
                TopListDataResponse<ResponseGetQuotation> resp = response.body();
                if (resp != null) {
                    if (resp.responseCode == 1) {
                        postEventListResponse(true,EventsIds.ID_GETQUOTATION,resp.message, resp.data);
                    } else {
                        postEventListResponse(false,EventsIds.ID_GETQUOTATION,resp.message, null);
                    }
                } else {
                    try {
                        postEventListResponse(false, EventsIds.ID_GETQUOTATION, getErrorMessage("" + response.errorBody().string()), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TopListDataResponse<ResponseGetQuotation>> call, Throwable t) {
                postEventListResponse(false, EventsIds.ID_GETQUOTATION, t.getMessage(), null);
            }
        });
    }

    public void getQuotationById(RequestGetQuotationById rQuotId) {
        String token = AppSession.getInstance().getToken();
        if (token.equals("")) {Toast.makeText(getContext(), "Please login", Toast.LENGTH_SHORT).show();return;}
        Call<TopListDataResponse<ResponseGetQuotation>> call = EasyCoverServiceFactory.getInstance().getQuotationById("Bearer " + token, rQuotId);
        call.enqueue(new Callback<TopListDataResponse<ResponseGetQuotation>>() {
            @Override
            public void onResponse(Call<TopListDataResponse<ResponseGetQuotation>> call, Response<TopListDataResponse<ResponseGetQuotation>> response) {
                TopListDataResponse<ResponseGetQuotation> resp = response.body();
                if (resp != null) {
                    if (resp.responseCode == 1) {
                        postEventListResponse(true,EventsIds.ID_GETQUOTATION,resp.message, resp.data);
                    } else {
                        postEventListResponse(false,EventsIds.ID_GETQUOTATION,resp.message, null);
                    }
                } else {
                    try {
                        postEventListResponse(false, EventsIds.ID_GETQUOTATION, getErrorMessage("" + response.errorBody().string()), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TopListDataResponse<ResponseGetQuotation>> call, Throwable t) {
                postEventListResponse(false, EventsIds.ID_GETQUOTATION, t.getMessage(), null);
            }
        });
    }

    public void getOrderHistory() {
        String token = AppSession.getInstance().getToken();
        if (token.equals("")) {Toast.makeText(getContext(), "Please login", Toast.LENGTH_SHORT).show();return;}
        Call<TopListDataResponse<ResponseOrderHistory>> call = EasyCoverServiceFactory.getInstance().getOrderHistory("Bearer " + token);
        call.enqueue(new Callback<TopListDataResponse<ResponseOrderHistory>>() {
            @Override
            public void onResponse(Call<TopListDataResponse<ResponseOrderHistory>> call, Response<TopListDataResponse<ResponseOrderHistory>> response) {
                TopListDataResponse<ResponseOrderHistory> resp = response.body();
                if (resp != null) {
                    if (resp.responseCode == 1) {
                        postEventListResponse(true,EventsIds.ID_GETORDERHISTORY,resp.message, resp.data);
                    } else {
                        postEventListResponse(false,EventsIds.ID_GETORDERHISTORY,resp.message, null);
                    }
                } else {
                    try {
                        postEventListResponse(false, EventsIds.ID_GETORDERHISTORY, getErrorMessage("" + response.errorBody().string()), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TopListDataResponse<ResponseOrderHistory>> call, Throwable t) {
                postEventListResponse(false, EventsIds.ID_GETORDERHISTORY, t.getMessage(), null);
            }
        });
    }

    public void getJobDetail(RequestJobDetail jobDetail) {
        String token = AppSession.getInstance().getToken();
        if (token.equals("")) {Toast.makeText(getContext(), "Please login", Toast.LENGTH_SHORT).show();return;}
        Call<TopDataResponse<ShowJob>> call = EasyCoverServiceFactory.getInstance().getJobDetail("Bearer " + token,jobDetail);
        call.enqueue(new Callback<TopDataResponse<ShowJob>>() {
            @Override
            public void onResponse(Call<TopDataResponse<ShowJob>> call, Response<TopDataResponse<ShowJob>> response) {
                TopDataResponse<ShowJob> resp = response.body();
                if (resp != null) {
                    if (resp.responseCode == 1) {
                        postEventSingleResponse(true,EventsIds.ID_GETJOBDETAIL,resp.message, resp.data);
                    } else {
                        postEventSingleResponse(false,EventsIds.ID_GETJOBDETAIL,resp.message,null);
                    }
                } else {
                    try {
                        postEventSingleResponse(false, EventsIds.ID_GETJOBDETAIL, getErrorMessage("" + response.errorBody().string()), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TopDataResponse<ShowJob>> call, Throwable t) {
                postEventSingleResponse(false, EventsIds.ID_GETJOBDETAIL, t.getMessage(), null);
            }
        });
    }

    public void getQuotDocument(RequestGetQuotDocument requestDoc) {
        String token = AppSession.getInstance().getToken();
        if (token.equals("")) {
            Toast.makeText(getContext(), "Please login", Toast.LENGTH_SHORT).show();
            return;
        }
        Call<TopListDataResponse<ResponseQuotDocumentData>> call = EasyCoverServiceFactory.getInstance().getQuotDocument("Bearer " + token, requestDoc);
        call.enqueue(new Callback<TopListDataResponse<ResponseQuotDocumentData>>() {
            @Override
            public void onResponse(Call<TopListDataResponse<ResponseQuotDocumentData>> call, Response<TopListDataResponse<ResponseQuotDocumentData>> response) {
                TopListDataResponse<ResponseQuotDocumentData> resp = response.body();
                if (resp != null) {
                    if (resp.responseCode == 1) {
                        postEventListResponse(true, EventsIds.ID_GETQUOTDOCUMENT, resp.message, resp.data);
                    } else {
                        postEventListResponse(false, EventsIds.ID_GETQUOTDOCUMENT, resp.message, null);
                    }
                } else {
                    try {
                        postEventListResponse(false, EventsIds.ID_GETQUOTDOCUMENT, getErrorMessage("" + response.errorBody().string()), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TopListDataResponse<ResponseQuotDocumentData>> call, Throwable t) {
                postEventListResponse(false, EventsIds.ID_GETQUOTDOCUMENT, t.getMessage(), null);
            }
        });
    }

    public void getAcceptedJobList() {
        String token = AppSession.getInstance().getToken();
        if (token.equals("")) {Toast.makeText(getContext(), "Please login", Toast.LENGTH_SHORT).show();return;}
        Call<TopListDataResponse<ResponseAcceptedJobs>> call = EasyCoverServiceFactory.getInstance().getAcceptedJobList("Bearer " + token);
        call.enqueue(new Callback<TopListDataResponse<ResponseAcceptedJobs>>() {
            @Override
            public void onResponse(Call<TopListDataResponse<ResponseAcceptedJobs>> call, Response<TopListDataResponse<ResponseAcceptedJobs>> response) {
                TopListDataResponse<ResponseAcceptedJobs> resp = response.body();
                if (resp != null) {
                    if (resp.responseCode == 1) {
                        postEventListResponse(true,EventsIds.ID_ACCEPTEDJOB,resp.message, resp.data);
                    } else {
                        postEventListResponse(false,EventsIds.ID_ACCEPTEDJOB,resp.message,null);
                    }
                } else {
                    try {
                        postEventListResponse(false, EventsIds.ID_ACCEPTEDJOB, getErrorMessage("" + response.errorBody().string()), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TopListDataResponse<ResponseAcceptedJobs>> call, Throwable t) {
                postEventListResponse(false, EventsIds.ID_ACCEPTEDJOB, t.getMessage(), null);
            }
        });
    }

    public void getAgentHistory() {
        String token = AppSession.getInstance().getToken();
        if (token.equals("")) {Toast.makeText(getContext(), "Please login", Toast.LENGTH_SHORT).show();return;}
        Call<TopListDataResponse<ResponseCompletedJobs>> call = EasyCoverServiceFactory.getInstance().getAgentHistory("Bearer " + token);
        call.enqueue(new Callback<TopListDataResponse<ResponseCompletedJobs>>() {
            @Override
            public void onResponse(Call<TopListDataResponse<ResponseCompletedJobs>> call, Response<TopListDataResponse<ResponseCompletedJobs>> response) {
                TopListDataResponse<ResponseCompletedJobs> resp = response.body();
                if (resp != null) {
                    if (resp.responseCode == 1) {
                        postEventListResponse(true,EventsIds.ID_GETCOMPLETEDJOBS,resp.message, resp.data);
                    } else {
                        postEventListResponse(false,EventsIds.ID_GETCOMPLETEDJOBS,resp.message,null);
                    }
                } else {
                    try {
                        postEventListResponse(false, EventsIds.ID_GETCOMPLETEDJOBS, getErrorMessage("" + response.errorBody().string()), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TopListDataResponse<ResponseCompletedJobs>> call, Throwable t) {
                postEventListResponse(false, EventsIds.ID_GETCOMPLETEDJOBS, t.getMessage(), null);
            }
        });
    }

    public void getCompletedJobList() {
        String token = AppSession.getInstance().getToken();
        if (token.equals("")) {Toast.makeText(getContext(), "Please login", Toast.LENGTH_SHORT).show();return;}
        Call<TopListDataResponse<ResponseCompletedJobs>> call = EasyCoverServiceFactory.getInstance().getCompletedJobList("Bearer " + token);
        call.enqueue(new Callback<TopListDataResponse<ResponseCompletedJobs>>() {
            @Override
            public void onResponse(Call<TopListDataResponse<ResponseCompletedJobs>> call, Response<TopListDataResponse<ResponseCompletedJobs>> response) {
                TopListDataResponse<ResponseCompletedJobs> resp = response.body();
                if (resp != null) {
                    if (resp.responseCode == 1) {
                        postEventListResponse(true,EventsIds.ID_GETCOMPLETEDJOBS,resp.message, resp.data);
                    } else {
                        postEventListResponse(false,EventsIds.ID_GETCOMPLETEDJOBS,resp.message,null);
                    }
                } else {
                    try {
                        postEventListResponse(false, EventsIds.ID_GETCOMPLETEDJOBS, getErrorMessage("" + response.errorBody().string()), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TopListDataResponse<ResponseCompletedJobs>> call, Throwable t) {
                postEventListResponse(false, EventsIds.ID_GETCOMPLETEDJOBS, t.getMessage(), null);
            }
        });
    }

    public void getCustomerCompletedJob() {
        String token = AppSession.getInstance().getToken();
        if (token.equals("")) {Toast.makeText(getContext(), "Please login", Toast.LENGTH_SHORT).show();return;}
        Call<TopListDataResponse<ResponseCompletedJobs>> call = EasyCoverServiceFactory.getInstance().getCustomerCompletedJob("Bearer " + token);
        call.enqueue(new Callback<TopListDataResponse<ResponseCompletedJobs>>() {
            @Override
            public void onResponse(Call<TopListDataResponse<ResponseCompletedJobs>> call, Response<TopListDataResponse<ResponseCompletedJobs>> response) {
                TopListDataResponse<ResponseCompletedJobs> resp = response.body();
                if (resp != null) {
                    if (resp.responseCode == 1) {
                        postEventListResponse(true,EventsIds.ID_GETCUSTOMERCOMPLETEDJOB,resp.message, resp.data);
                    } else {
                        postEventListResponse(false,EventsIds.ID_GETCUSTOMERCOMPLETEDJOB,resp.message,null);
                    }
                } else {
                    try {
                        postEventListResponse(false, EventsIds.ID_GETCUSTOMERCOMPLETEDJOB, getErrorMessage("" + response.errorBody().string()), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TopListDataResponse<ResponseCompletedJobs>> call, Throwable t) {
                postEventListResponse(false, EventsIds.ID_GETCUSTOMERCOMPLETEDJOB, t.getMessage(), null);
            }
        });
    }

    public void getAllJobs() {
        String token = AppSession.getInstance().getToken();
        if (token.equals("")) {Toast.makeText(getContext(), "Please login", Toast.LENGTH_SHORT).show();return;}
        Call<ResponseGetAllJob> call = EasyCoverServiceFactory.getInstance().getAllJobs("Bearer " + token);
        call.enqueue(new Callback<ResponseGetAllJob>() {
            @Override
            public void onResponse(Call<ResponseGetAllJob> call, Response<ResponseGetAllJob> response) {
                if (response.isSuccessful()) {
                    ResponseGetAllJob body = response.body();
                    if (body.getResponseCode() == 1) {
//                        SingleDataEvent eventBusData = new SingleDataEvent(true, EventsIds.ID_GETALL_JOB, body.getMessage(), null);
//                        EventBus.getDefault().post(eventBusData);
                        List<ShowJob> temp = new ArrayList<ShowJob>();
                        for (int i = 0; i < body.getData().size(); i ++) {
                            temp.add(body.getData().get(i).getJobs());
                        }
                        postEventListResponse(true, EventsIds.ID_GETALL_JOB, "" + body.getMessage(), temp);
                    } else {
                        postEventListResponse(false, EventsIds.ID_GETALL_JOB, "" + body.getMessage(), null);
                    }
                } else {
                    postEventListResponse(false, EventsIds.ID_GETALL_JOB, "" + getErrorMessage(response.errorBody().toString()),null);
                }
            }

            @Override
            public void onFailure(Call<ResponseGetAllJob> call, Throwable t) {
                SingleDataEvent eventBusDatas = new SingleDataEvent(false, EventsIds.ID_GETALL_JOB, t.getMessage(), null);
                EventBus.getDefault().post(eventBusDatas);
            }
        });
    }

    public void getReferralCode() {
        String token = AppSession.getInstance().getToken();
        if (token.equals("")) {Toast.makeText(getContext(), "Please login", Toast.LENGTH_SHORT).show();return;}
        Call<TopDataResponse<ResponseReferralCode>> call = EasyCoverServiceFactory.getInstance().getReferralCode("Bearer " + token);
        call.enqueue(new Callback<TopDataResponse<ResponseReferralCode>>() {
            @Override
            public void onResponse(Call<TopDataResponse<ResponseReferralCode>> call, Response<TopDataResponse<ResponseReferralCode>> response) {
                if (response.isSuccessful()) {
                    TopDataResponse<ResponseReferralCode> body = response.body();
                    if (body.responseCode == 1) {
                        postEventSingleResponse(true, EventsIds.ID_GETREFERRALCODE, "" + body.message, body.data);
                    } else {
                        postEventSingleResponse(false, EventsIds.ID_GETREFERRALCODE, "" + body.message, null);
                    }
                } else {
                    postEventSingleResponse(false, EventsIds.ID_GETREFERRALCODE, "" + getErrorMessage(response.errorBody().toString()),null);
                }
            }

            @Override
            public void onFailure(Call<TopDataResponse<ResponseReferralCode>> call, Throwable t) {
                postEventSingleResponse(false, EventsIds.ID_GETREFERRALCODE, "" + t.getMessage(),null);
            }
        });
    }

    public void getInsuranceType() {
        String token = AppSession.getInstance().getToken();
        if (token.equals("")) {Toast.makeText(getContext(), "Please login", Toast.LENGTH_SHORT).show();return;}
        Call<TopListDataResponse<ResponseGetInsuranceType>> call = EasyCoverServiceFactory.getInstance().getInsuranceType("Bearer " + token);
        call.enqueue(new Callback<TopListDataResponse<ResponseGetInsuranceType>>() {
            @Override
            public void onResponse(Call<TopListDataResponse<ResponseGetInsuranceType>> call, Response<TopListDataResponse<ResponseGetInsuranceType>> response) {
                if (response.isSuccessful()) {
                    TopListDataResponse<ResponseGetInsuranceType> body = response.body();
                    if (body.responseCode == 1) {
                        postEventListResponse(true, EventsIds.ID_GETINSURANCETYPE, "" + body.message, body.data);
                    } else {
                        postEventListResponse(false, EventsIds.ID_GETINSURANCETYPE, "" + body.message, null);
                    }
                } else {
                    postEventListResponse(false, EventsIds.ID_GETINSURANCETYPE, "" + getErrorMessage(response.errorBody().toString()),null);
                }
            }

            @Override
            public void onFailure(Call<TopListDataResponse<ResponseGetInsuranceType>> call, Throwable t) {
                postEventListResponse(false, EventsIds.ID_GETINSURANCETYPE, "" + t.getMessage(),null);
            }
        });
    }

    public void getCompany() {
        String token = AppSession.getInstance().getToken();
        if (token.equals("")) {Toast.makeText(getContext(), "Please login", Toast.LENGTH_SHORT).show();return;}
        Call<TopListDataResponse<ResponseCompany>> call = EasyCoverServiceFactory.getInstance().getCompany("Bearer " + token);
        call.enqueue(new Callback<TopListDataResponse<ResponseCompany>>() {
            @Override
            public void onResponse(Call<TopListDataResponse<ResponseCompany>> call, Response<TopListDataResponse<ResponseCompany>> response) {
                if (response.isSuccessful()) {
                    TopListDataResponse<ResponseCompany> body = response.body();
                    if (body.responseCode == 1) {
                        postEventListResponse(true, EventsIds.ID_GETCOMPANY, "" + body.message, body.data);
                    } else {
                        postEventListResponse(false, EventsIds.ID_GETCOMPANY, "" + body.message, null);
                    }
                } else {
                    postEventListResponse(false, EventsIds.ID_GETCOMPANY, "" + getErrorMessage(response.errorBody().toString()),null);
                }
            }

            @Override
            public void onFailure(Call<TopListDataResponse<ResponseCompany>> call, Throwable t) {
                postEventListResponse(false, EventsIds.ID_GETCOMPANY, "" + t.getMessage(),null);
            }
        });
    }


    public void uploadImageFile(String filePath, final ProgressRequestBody.UploadCallbacks callbacks) {
        File file = new File(filePath);
        //RequestBody fileBody = RequestBody.create(MediaType.parse("file/*"), file);
        ProgressRequestBody fileBody = new ProgressRequestBody(file, callbacks);
        final MultipartBody.Part filePart = MultipartBody.Part.createFormData("photo", file.getName(), fileBody);

        Call<TopDataResponse<String>> call = easyCoverServiceFactory.uploadImageFile(filePart);
        call.enqueue(new Callback<TopDataResponse<String>>() {
            @Override
            public void onResponse(Call<TopDataResponse<String>> call, Response<TopDataResponse<String>> response) {
                if (response.isSuccessful()) {
                    TopDataResponse<String> data = response.body();
                    if (data.responseCode == 1) {
                        postEventSingleResponse(true, EventsIds.ID_UPLOAD_IMAGE, data.message, data.data);
                    } else {
                        postEventSingleResponse(false, EventsIds.ID_UPLOAD_IMAGE, data.message, null);
                    }
                } else {
                    postEventSingleResponse(false, EventsIds.ID_UPLOAD_IMAGE, "" + getErrorMessage(response.errorBody().toString()), null);
                }
            }
            @Override
            public void onFailure(Call<TopDataResponse<String>> call, Throwable t) {
                postEventSingleResponse(false, EventsIds.ID_UPLOAD_IMAGE, "" + t.getMessage(), null);
            }
        });
    }


    public void uploadDocument(String filePath, final ProgressRequestBody.UploadCallbacks callbacks) {
        File file = new File(filePath);

        ProgressRequestBody fileBody = new ProgressRequestBody(file, callbacks);
        final MultipartBody.Part filePart = MultipartBody.Part.createFormData("document[]", file.getName(), fileBody);
        //RequestBody fileBody = RequestBody.create(MediaType.parse("file/*"), file);
        Call<TopListDataResponse<String>> call = easyCoverServiceFactory.uploadDocument(filePart);
        call.enqueue(new Callback<TopListDataResponse<String>>() {
            @Override
            public void onResponse(Call<TopListDataResponse<String>> call, Response<TopListDataResponse<String>> response) {
                if (response.isSuccessful()) {
                    TopListDataResponse<String> resData = response.body();
                    if (callbacks != null) {
                        try {
                            String fileId = resData.data.get(0);
                            if (fileId != null && !TextUtils.isEmpty(fileId)) {
                                callbacks.onFinish(fileId);
                            } else {
                                callbacks.onError(response.message());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    if (callbacks != null)
                        callbacks.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<TopListDataResponse<String>> call, Throwable t) {
                callbacks.onError(getErrorMessage("" + t.getMessage()));
            }

        });

    }
                    /*TopDataResponse<String> rsp = response.body();
                    if (rsp.responseCode == 1) {
                        postEventSingleResponse(true, EventsIds.ID_UPLOAD_FILE, "" + rsp.message, rsp.data);

                    } else {
                        postEventSingleResponse(false, EventsIds.ID_UPLOAD_FILE, "" + rsp.message, null);

                    }

                } else
                    postEventSingleResponse(false, EventsIds.ID_UPLOAD_FILE, "" + getErrorMessage(response.errorBody().toString()), null);

            }

            @Override
            public void onFailure(Call<TopDataResponse<String>> call, Throwable t) {
                postEventSingleResponse(false, EventsIds.ID_UPLOAD_FILE, "" + getErrorMessage(t.getMessage()), null);

            }
        });*/

    /*
            public void addNewCustomer(Customer customer) {
                Call<BaseResponse<Customer>> call = EasyCoverServiceFactory.getInstance().addNewCustomer(customer);
                call.enqueue(new Callback<BaseResponse<Customer>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Customer>> call, Response<BaseResponse<Customer>> response) {
                        BaseResponse<Customer> resp = response.body();
                        if (resp != null) {
                            if (resp.responseCod.equals("1")) {
                                Customer body = resp.data;
                                //body.save();
                                postEventListResponse(true, EventsIds.ID_ADD_CUSTOMER, resp.message, body);
                            } else postEventSimpleResponse(false, EventsIds.ID_ADD_CUSTOMER, resp.message);
                        } else {
                            try {
                                postEventSimpleResponse(false, EventsIds.ID_ADD_CUSTOMER, getErrorMessage("" + response.errorBody().string()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Customer>> call, Throwable t) {
                        postEventSimpleResponse(false, EventsIds.ID_LOGIN, "" + t.getMessage());
                    }
                });
            }

            public void getCustomers() {
                Call<BaseListResponse<Customer>> call = rappleApi.getAllCustomers();
                call.enqueue(new Callback<BaseListResponse<Customer>>() {
                    @Override
                    public void onResponse(Call<BaseListResponse<Customer>> call, Response<BaseListResponse<Customer>> response) {
                        BaseListResponse<Customer> resp = response.body();
                        if (resp != null) {
                            if (resp.responseCod.equals("1")) {
                                List<Customer> listData = resp.listData;
                                //body.save();
                                postEventListResponse(true, EventsIds.ID_CUSTOMERS_LIST, resp.message, listData);
                            } else postEventListResponse(false, EventsIds.ID_CUSTOMERS_LIST, resp.message);
                        } else {
                            try {
                                postEventListResponse(false, EventsIds.ID_CUSTOMERS_LIST, getErrorMessage("" + response.errorBody().string()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseListResponse<Customer>> call, Throwable t) {
                        postEventListResponse(false, EventsIds.ID_CUSTOMERS_LIST, "" + t.getMessage());
                    }
                });
            }

            public void updateCustomer(UpdateCust updateCust){
                Call<BaseResponse<Customer>> call = rappleApi.updateCustomer(updateCust);
                call.enqueue(new Callback<BaseResponse<Customer>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Customer>> call, Response<BaseResponse<Customer>> response) {

                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Customer>> call, Throwable t) {

                    }
                });
            }

            public void deleteCustomer(DeleteCustomer deleteCustomer){
                Call<BaseResponse> call = rappleApi.deleteCustomer(deleteCustomer);
                call.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {

                    }
                });
            }












            */

    private String getErrorMessage(String errorBody) {
        try {
            TopResponse errorResp = new Gson().fromJson(errorBody, TopResponse.class);
            return errorResp.message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown Error Occurred";

    }


    private void postEventSingleResponse(boolean status, int responseId, String message, Object data) {
        SingleDataEvent eventBusData = new SingleDataEvent(status, responseId, message, data);
        EventBus.getDefault().post(eventBusData);
    }

    private void postEventListResponse(boolean status, int responseId, String message, List data) {
        ListDataEvent eventBusData = new ListDataEvent(status, responseId, message, data);
        EventBus.getDefault().post(eventBusData);
    }

    private void postEventListResponse(boolean status, int responseId, String message) {
        ListDataEvent eventBusData = new ListDataEvent(status, responseId, message);
        EventBus.getDefault().post(eventBusData);
    }

    private void postEventSimpleResponse(boolean status, int responseId, String message) {
        SimpleEvent eventBusData = new SimpleEvent(status, responseId, message);
        EventBus.getDefault().post(eventBusData);
    }


}

