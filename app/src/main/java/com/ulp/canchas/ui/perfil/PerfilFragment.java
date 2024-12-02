package com.ulp.canchas.ui.perfil;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.ulp.canchas.R;
import com.ulp.canchas.databinding.FragmentPerfilBinding;
import com.ulp.canchas.model.Usuario;

public class PerfilFragment extends Fragment {

    private PerfilViewModel viewModel;
    private FragmentPerfilBinding binding;
    private ActivityResultLauncher<Intent> arl;
    private Intent intent;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        abrirGaleria();

        viewModel.getMUsuario().observe(getViewLifecycleOwner(), new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                binding.etPerfilNombre.setText(usuario.getNombre());
                binding.etPerfilApellido.setText(usuario.getApellido());
                binding.etPerfilEmail.setText(usuario.getEmail());
                String avatar = usuario.getAvatar().isEmpty() ? "default.jpg" : usuario.getAvatar();
                Glide.with(getContext())
                        .load("http://192.168.0.14:5218/av/" + avatar)
                        .placeholder(R.drawable.default_avatar)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transform(new RoundedCorners(70))
                        .into(binding.ivPerfilAvatar);
            }
        });

        viewModel.getMAvatar().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                Glide.with(getContext())
                        .load(uri)
                        .placeholder(R.drawable.default_avatar)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transform(new RoundedCorners(70))
                        .into(binding.ivPerfilAvatar);
            }
        });

        binding.btCambiarAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arl.launch(intent);
            }
        });

        binding.btPerfilGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.modificarUsuario(
                        binding.etPerfilNombre.getText().toString(),
                        binding.etPerfilApellido.getText().toString(),
                        binding.etPerfilEmail.getText().toString()
                );
            }
        });

        binding.btPerfilCambiarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.cambiarClaveFragment);
            }
        });

        viewModel.datosUsuario();

        return root;
    }

    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                viewModel.recibirFoto(result);
            }
        });
    }

}