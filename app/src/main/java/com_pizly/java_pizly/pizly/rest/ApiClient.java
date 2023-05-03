package com_pizly.java_pizly.pizly.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String ROOT_URL = "https://pizly.000webhostapp.com/pizly_db/";

    public static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance(){
        //OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();
        if(retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create(/*gson*/))
                    //.client(client)
                    .build();
        }
        return retrofit;
    }

    public static ApiInterface getApiService() {
        return getRetrofitInstance().create(ApiInterface.class);
    }

 /*   static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            Log.v("Request ",String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            Log.v("Request ",String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));


            final String responseString = new String(response.body().bytes());

            Log.v("Request ","Response: " + responseString);

            return  response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), responseString))
                    .build();
        }
    }*/

}
