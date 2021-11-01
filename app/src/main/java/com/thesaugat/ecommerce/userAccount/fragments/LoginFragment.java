
package com.thesaugat.ecommerce.userAccount.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.thesaugat.ecommerce.api.ApiClient;
import com.thesaugat.ecommerce.api.responses.LoginResponse;
import com.thesaugat.ecommerce.home.MainActivity;
import com.thesaugat.ecommerce.R;
import com.thesaugat.ecommerce.utils.SharedPrefUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment implements View.OnClickListener {
    EditText emailEt, passwordET;
    LinearLayout loginBtn;
    ProgressBar circularProgress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailEt = view.findViewById(R.id.emailET);
        passwordET = view.findViewById(R.id.passwordET);
        loginBtn = view.findViewById(R.id.loginLL);
        circularProgress = view.findViewById(R.id.circularProgress);
        loginBtn.setOnClickListener(this);

    }

    public void toggleProgress(Boolean toogle){
        if(toogle)
            circularProgress.setVisibility(View.VISIBLE);
        else
            circularProgress.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {

        if (v == loginBtn) {

            String email, password;
            email = emailEt.getText().toString();
            password = passwordET.getText().toString();
            if (email.isEmpty() && password.isEmpty())
                Toast.makeText(getContext(), "Email or Password is Empty!", Toast.LENGTH_LONG).show();
            else {
                toggleProgress(true);
                System.out.println("22222222222222 Email is: " + email);
                System.out.println("22222222222222 Password is: " + password);

                Call<LoginResponse> login = ApiClient.getClient().login(email, password);
                login.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        toggleProgress(false);
                        LoginResponse loginResponse = response.body();
                        if (response.isSuccessful()) {
                            if (response.body().getError()) {
                                System.out.println("22222222222222 Error  is: " + loginResponse.getError());
                                Toast.makeText(getActivity(),loginResponse.getMessgae(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_LONG).show();
                                System.out.println("22222222222222 Api Key  is: " + loginResponse.getApiKey());
                                System.out.println("22222222222222 email   is: " + loginResponse.getEmail());
                                System.out.println("22222222222222 created at   is: " + loginResponse.getCreatedAt());
                                SharedPrefUtils.setBoolean(getActivity(), getString(R.string.isLogged), true);
                                SharedPrefUtils.setString(getActivity(), getString(R.string.name_key), loginResponse.getName());
                                SharedPrefUtils.setString(getActivity(), getString(R.string.email_id), loginResponse.getEmail());
                                SharedPrefUtils.setString(getActivity(), getString(R.string.created_key), loginResponse.getCreatedAt());
                                SharedPrefUtils.setString(getActivity(), getString(R.string.api_key), loginResponse.getApiKey());

                                getActivity().startActivity(new Intent(getContext(), MainActivity.class));
                                getActivity().finish();

                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        toggleProgress(false);

                    }
                });

            }



//            else if (email.equals("thesaugatt@gmail.com") && password.equals("Pass123")) {
//                Toast.makeText(getContext(), "Welcome", Toast.LENGTH_LONG).show();
//                SharedPrefUtils.setBoolean(getActivity(), getString(R.string.isLogged), true);
//                getActivity().startActivity(new Intent(getContext(), MainActivity.class));
//                getActivity().finish();
//
//
//            } else
//                Toast.makeText(getContext(), "Wrong email or password", Toast.LENGTH_LONG).show();
        }
    }
}