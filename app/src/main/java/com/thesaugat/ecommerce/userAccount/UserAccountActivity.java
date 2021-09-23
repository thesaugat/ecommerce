package com.thesaugat.ecommerce.userAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.thesaugat.ecommerce.R;
import com.thesaugat.ecommerce.userAccount.fragments.LoginFragment;
import com.thesaugat.ecommerce.userAccount.fragments.RegisterFragment;

public class UserAccountActivity extends AppCompatActivity implements View.OnClickListener {
    TextView registerTV, signUpRegisterTV;

    boolean isRegister = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        getSupportFragmentManager().beginTransaction().add(R.id.formContainerFL, new LoginFragment()).commit();
        registerTV = findViewById(R.id.registerTV);
        signUpRegisterTV = findViewById(R.id.signUpRegisterTV);
        registerTV.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if (v == registerTV) {
            if (!isRegister) {

                getSupportFragmentManager().beginTransaction().replace(R.id.formContainerFL, new RegisterFragment()).commit();

            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.formContainerFL, new LoginFragment()).commit();
            }
            changeTexts();
            isRegister = !isRegister;
        }
    }

    void changeTexts() {
        if (!isRegister) {
            registerTV.setText("Login");
            signUpRegisterTV.setText("Already Have an Account? ");
        } else {
            registerTV.setText("Register");
            signUpRegisterTV.setText("New to Android Class? ");
        }
    }

}