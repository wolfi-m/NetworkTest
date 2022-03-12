package at.aau.se2.networktest;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import at.aau.se2.networktest.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment implements Observer {

    private FragmentFirstBinding binding;
    private ClientConnection cc;

    public FirstFragment() {
        cc = new ClientConnection();
        cc.addObserver(this);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cc.setToServer(binding.inputMatrikelnummer.getText().toString());
                new Thread(cc).start();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void update(Observable observable, Object o) {
        binding.ServerResponse.setText(cc.getFromServer());
    }
}