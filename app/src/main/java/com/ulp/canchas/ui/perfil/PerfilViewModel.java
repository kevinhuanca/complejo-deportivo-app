package com.ulp.canchas.ui.perfil;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.canchas.model.Usuario;
import com.ulp.canchas.request.ApiClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Usuario> mUsuario;
    private MutableLiveData<Uri> mAvatar;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Usuario> getMUsuario() {
        if (mUsuario == null) {
            mUsuario = new MutableLiveData<>();
        }
        return mUsuario;
    }

    public LiveData<Uri> getMAvatar() {
        if (mAvatar == null) {
            mAvatar = new MutableLiveData<>();
        }
        return mAvatar;
    }

    public void datosUsuario() {
        ApiClient.CanchasService api = ApiClient.getApiCanchas();
        Call<Usuario> llamada = api.perfil(ApiClient.getToken(context));

        llamada.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    mUsuario.postValue(response.body());
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Toast.makeText(context, errorBody, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable throwable) {
                Toast.makeText(context, "Error del servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void modificarUsuario(String nombre, String apellido, String email) {
        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty()) {
            return;
        }

        ApiClient.CanchasService api = ApiClient.getApiCanchas();
        Call<String> llamada = api.perfil(ApiClient.getToken(context), nombre, apellido, email);

        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (mAvatar.getValue() != null) {
                        cambiarAvatar();
                    }
                    Toast.makeText(context, response.body(), Toast.LENGTH_LONG).show();
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Toast.makeText(context, errorBody, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Toast.makeText(context, "Error del servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cambiarAvatar() {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(mAvatar.getValue());
            File file = new File(context.getCacheDir(), "AVATAR.jpg");
            FileOutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();

            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part avatar = MultipartBody.Part.createFormData("avatar", file.getName(), requestBody);

            ApiClient.CanchasService api = ApiClient.getApiCanchas();
            Call<String> llamada = api.avatar(ApiClient.getToken(context), avatar);

            llamada.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, response.body(), Toast.LENGTH_LONG).show();
                    } else {
                        try {
                            String errorBody = response.errorBody().string();
                            Toast.makeText(context, errorBody, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                    Toast.makeText(context, "Error del servidor", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void recibirFoto(ActivityResult result) {
        if(result.getResultCode() == RESULT_OK){
            Intent data =result.getData();
            Uri uri = data.getData();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                context.getContentResolver().takePersistableUriPermission (uri, Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            mAvatar.setValue(uri);
        }
    }

}