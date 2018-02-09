package com.paulapps.kereseresapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseDatabase firebase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private Button btSignUp;
    private EditText etEmail, etPassword, etName, etApartment, etCode;
    private String emailUser, passwordUser, nameUser, codeUser, apartmentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

     //getSupportActionBar().hide();
        //Establecer la conexion
        firebase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        btSignUp = (Button) findViewById(R.id.btnSingUp);
        etEmail = (EditText) findViewById(R.id.etEmailSingUp);
        etPassword = (EditText) findViewById(R.id.etPasswordSignUp);
        etName = findViewById(R.id.etNameSingUp);
        etApartment = findViewById(R.id.etAparmetnSignUp);
        etCode = findViewById(R.id.etCodeSingUp);


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    //registrar();
                }
            }
        };
    }

//lo activa el boton SignUp
    public void registrar(View v){
        UserRegister();
    }

    private void UserRegister() {
        emailUser = etEmail.getText().toString().trim();
        passwordUser = etPassword.getText().toString().trim();
        nameUser = etName.getText().toString().trim();
        apartmentUser = etApartment.getText().toString().trim();
        codeUser = etCode.getText().toString().trim();

        //verficamos q los editText eno este vacios
        if (TextUtils.isEmpty(emailUser)) {
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(passwordUser)) {
            Toast.makeText(this, "Se debe ingresar una contraseña", Toast.LENGTH_LONG).show();
            return;
        } else {

            //creamos nuevo usuario
            mAuth.createUserWithEmailAndPassword(emailUser, passwordUser)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(SignUpActivity.this, "Se ha registrado el usuario", Toast.LENGTH_LONG).show();
                                //updateUI(user);
                                Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(i);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignUpActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_LONG).show();
                                // updateUI(null);
                            }
                        }
                    });
          }
    }


    public void Cancelar(View v) {
        Intent i = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(i);
    }
}