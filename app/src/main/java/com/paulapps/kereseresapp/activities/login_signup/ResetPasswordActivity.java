package com.paulapps.kereseresapp.activities.login_signup;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.paulapps.kereseresapp.R;

public class ResetPasswordActivity extends AppCompatActivity {
    EditText ResetPass;
    Button Reset;
    TextView InfoReset,tituloApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ResetPass = (EditText) findViewById(R.id.etResetPass);
        Reset = (Button) findViewById(R.id.btReset);
        InfoReset = (TextView) findViewById(R.id.tvInfoReset);
        tituloApp = (TextView) findViewById(R.id.tituloApp);

        //Fuente titulo
        Typeface myFont = Typeface.createFromAsset(getAssets(), "Strawberry Blossom.ttf");
        tituloApp.setTypeface(myFont);
    }

    //Cogemos el email al clickar en el boton y lo pasamos al metodo resetUserPassword
    public void Resetear(View v) {
        String email = ResetPass.getText().toString();
        if (email.equals("")) {
            Toast.makeText(getApplicationContext(), "You must to introduce an email", Toast.LENGTH_SHORT).show();
        } else {
            resetUserPassword(email);
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }

    //RECUPERAR CONTRASEÑA (comprueba que el email existe y si es así, le envia un correo)
    public void resetUserPassword(String email) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Reset password instructions has sent to your email",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Email doesn't exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //  progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}



