package com.ahmety.movieinvio.Api;

import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ApiErrorUtils {
    public static ApiError parseError(Response<?> response) {
        Converter<ResponseBody, ApiError> converter = ApiClient.retrofit.responseBodyConverter(ApiError.class, new Annotation[0]);
        ApiError error = new ApiError();
        error.setStatusCode(response.code());
        //    error.setMessage("Bilinmeyen Hata");
        //ApiError error = new ApiError(response);

        try {
            error = converter.convert(response.errorBody());
            error.setStatusCode(response.code());

            Log.i("sstatus", "status code : " + error.getStatusCode());

            if (error.getStatusCode() == 401){

                if(error.getMessage() != null && !error.getMessage().isEmpty()){
                    //  DialogHelper.showDialogWithOneButton("",error.getMessage());
                }
                else {
                    //   DialogHelper.showDialogToken("","Geçersiz Token");
                }
            }
         /*   else if (error.getStatusCode() == 429){
                //  DialogHelper.showDialogWithOneButton("",error.getMessage());
                Log.d("asdasdsa", String.valueOf(error));
            }*/

            else{
                //    DialogHelper.showDialogWithOneButton("","Sisteme Ulaşılamıyor");
            }



        } catch (IOException e) {
            return error;
        }

        return error;
    }
}
