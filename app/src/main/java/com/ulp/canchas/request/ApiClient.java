package com.ulp.canchas.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ulp.canchas.model.Cancha;
import com.ulp.canchas.model.HoraView;
import com.ulp.canchas.model.Usuario;

import java.time.LocalDate;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class ApiClient {

    public static final String URLBASE = "http://192.168.0.14:5218/api/";
    private static SharedPreferences sp;

    public static CanchasService getApiCanchas() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(CanchasService.class);
    }

    private static SharedPreferences getSharedPreference(Context context){
        if(sp == null){
            sp = context.getSharedPreferences("usuario",0);
        }
        return sp;
    }

    public static void saveToken(Context context, String token) {
        SharedPreferences sp = getSharedPreference(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static String getToken(Context context) {
        SharedPreferences sp = getSharedPreference(context);
        return sp.getString("token", null);
    }

    public interface CanchasService {

        @FormUrlEncoded
        @POST("usuarios/login")
        Call<String> login(
                @Field("Email") String email,
                @Field("Clave") String clave
        );

        @FormUrlEncoded
        @POST("usuarios/registrar")
        Call<String> registrar(
                @Field("Nombre") String nombre,
                @Field("Apellido") String apellido,
                @Field("Email") String email,
                @Field("ConfirmarEmail") String reEmail,
                @Field("Clave") String clave,
                @Field("ConfirmarClave") String reClave
        );

        @FormUrlEncoded
        @POST("usuarios/email")
        Call<String> email(
                @Field("email") String email
        );

        @GET("usuarios/perfil")
        Call<Usuario> perfil(
                @Header("Authorization") String token
        );

        @FormUrlEncoded
        @PUT("usuarios/perfil")
        Call<String> perfil(
                @Header("Authorization") String token,
                @Field("Nombre") String nombre,
                @Field("Apellido") String apellido,
                @Field("Email") String email
        );

        @Multipart
        @PUT("usuarios/avatar")
        Call<String> avatar(
                @Header("Authorization") String token,
                @Part MultipartBody.Part avatar
        );

        @FormUrlEncoded
        @PUT("usuarios/clave")
        Call<String> clave(
                @Header("Authorization") String token,
                @Field("Actual") String actual,
                @Field("Nueva") String nueva,
                @Field("Repetida") String repetida
        );

        @GET("canchas/todos")
        Call<List<Cancha>> todos(
                @Header("Authorization") String token
        );

        @GET("horarios/disponible/{idCancha}/{fecha}")
        Call<List<HoraView>> disponibles(
                @Header("Authorization") String token,
                @Path("idCancha") int idCancha,
                @Path("fecha") LocalDate fecha
        );

    }
}
