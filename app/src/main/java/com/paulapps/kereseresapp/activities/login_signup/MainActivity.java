package com.paulapps.kereseresapp.activities.login_signup;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.paulapps.kereseresapp.R;
import com.paulapps.kereseresapp.activities.ListViewActivity;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private FirebaseDatabase firebase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private GoogleApiClient googleApiClient;
    private Button btOK, btnSingUp, btGoogle;
    private EditText etEmail, etPassword;
    private String email;
    private String password;
    private TextView tituloApp;
    public static final int SIGN_IN_CODE = 777;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // getSupportActionBar().hide();
        btnSingUp = (Button) findViewById(R.id.btnSingUp);
        btOK = (Button) findViewById(R.id.btnAceptar);
        btGoogle = (Button) findViewById(R.id.btGoogle);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tituloApp = (TextView) findViewById(R.id.tituloApp);


        //Fuente titulo
        Typeface myFont = Typeface.createFromAsset(getAssets(), "Strawberry Blossom.ttf");
        tituloApp.setTypeface(myFont);

        //GOOGLE
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //Nos comunica con las Apis de google
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //FIREBASE
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    LogIn();

                }
            }
        };
    }


    public void LogIn() {

        Intent i = new Intent(MainActivity.this, ListViewActivity.class);
        startActivity(i);
    }


    //verifica si esta logueado el usuario en firebse
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthStateListener);

    /*    OptionalPendingResult<GoogleSignInResult> opr= Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result=opr.get();
            handleSignInResult(result);
        }else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult signInResult) {
                    handleSignInResult(signInResult);

                }
            });
        }*/
    }

    public void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    //Se activa al pulsar el boton OK
    public void aceptar(View v) {
        SignIn();
    }

    //Comprueba si está el usuario autentificado en firebase para dejarle entrar a la app o no
    private void SignIn() {
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        if(email.equals("")|| password.equals("")) {
            Toast.makeText(MainActivity.this, "Introduce email y contraseña", Toast.LENGTH_LONG).show();

        }else{

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                         if (task.isSuccessful()) {
                             // Sign in success, update UI with the signed-in user's information
                             Toast.makeText(MainActivity.this, "Inicio sesión correcto", Toast.LENGTH_LONG).show();
                             // updateUI(user);
                         } else {
                             // If sign in fails, display a message to the user.
                             // updateUI(null);
                             Toast.makeText(MainActivity.this, "Email o contraseña incorrectos", Toast.LENGTH_LONG).show();
                             etPassword.setText("");
                         }

                    }
                });
        }
    }

    //GOOGLE
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    //Si no esta autentificado llama a GoogleSignInApi para autentificarse
    public void GoogleIn(View v) {
        Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(i, SIGN_IN_CODE);

    }

    //obtenemos el resultado al pulsar el boton, de la autentificacion
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    //Controla q la autentificacion de google este bien
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            firebaseAuthWithGoogle(result.getSignInAccount());
            //    GoogleSignInAccount account=result.getSignInAccount();
            //nameTextView,setText(account.getDisplayName()); asi cogeriamos datos de la cuenta google

        } else {
            Toast.makeText(this, "No se pudo iniciar sesión con google", Toast.LENGTH_SHORT).show();
        }
    }

    //NOS AUTENTIFICAMOS EN FIREBASE CON GOOGLE
    private void firebaseAuthWithGoogle(GoogleSignInAccount signInAccount) {

        //obtenemos token del id del objeto googleSignInAccount y autentificamos con firebase
        AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            //con este metodo vemos si la conexion se hizo bien o no
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), " Autentificado correctamente con firebase", Toast.LENGTH_SHORT).show();
                    //  FirebaseUser user = mAuth.getCurrentUser();//con esto se obtienen los datos de la cuenta del usuario
                }
            }
        });
    }

    //Para ir a la pantalla de registro
    public void SignUp(View v) {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivity(i);
    }


    //Para ir a la pantalla de recuperar contraseña
    public  void ForgetPassword(View v){
        Intent i = new Intent(this, ResetPasswordActivity.class);
        startActivity(i);
    }




//para sacar info del usuario
  /*  public void InfoUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();


            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }
    }*/
}

