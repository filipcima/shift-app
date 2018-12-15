package com.example.cimafilip.shiftapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cimafilip.shiftapp.R;
import com.example.cimafilip.shiftapp.api.APIClient;
import com.example.cimafilip.shiftapp.api.IAPIEndpoints;
import com.example.cimafilip.shiftapp.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity2 extends AppCompatActivity {
    EditText mPasswordTextInput, mEmailTextInput;
    Button mSignInButton;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login2);

        mSignInButton = findViewById(R.id.email_sign_in_button);
        mPasswordTextInput = findViewById(R.id.passwordTextInput);
        mEmailTextInput = findViewById(R.id.emailTextInput);

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin(mEmailTextInput.getText().toString(), mPasswordTextInput.getText().toString());
            }
        });
    }

    private void performLogin(String mEmail, final String mPassword) {
        IAPIEndpoints apiService = APIClient.getApiService();
        Call<User> call = apiService.getUser(mEmail);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int statusCode = response.code();
                if (statusCode >= 200 && statusCode < 400) {
                    User user = response.body();
                    if (user != null) {
                        if (user.getPasswordHash().equals(mPassword)) {
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            prefs.edit().putString("idUser", user.get_id()).apply();
                            prefs.edit().putString("nameUser", user.getFirstName()).apply();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity2.this, "Nepovedlo se přihlásit.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("FAIL", t.getLocalizedMessage());
            }
        });
    }
}
