package feroz.materialdesign.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.feroz.materialdesign.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rey.material.widget.ProgressView;
import com.squareup.picasso.Picasso;

import feroz.materialdesign.MainActivity;


public class LoginActivity extends AppCompatActivity {
    private EditText email,password;
    private Button signin,register;
    private FirebaseAuth mAuth;
    private ImageButton user_profile_photo;
    private SharedPreferences sharedpreferences;
    private String sharedprefemail,sharedprefpassword;
    private ProgressView progressView;
    private RelativeLayout main_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signin = (Button) findViewById(R.id.signin);
        register = (Button) findViewById(R.id.register);
        user_profile_photo = (ImageButton) findViewById(R.id.user_profile_photo);
        sharedpreferences = getSharedPreferences(getResources().getString(R.string.shared_preference_key), Context.MODE_PRIVATE);
        sharedprefemail =sharedpreferences.getString(getResources().getString(R.string.email),"");
        sharedprefpassword = sharedpreferences.getString(getResources().getString(R.string.password),"");
        mAuth = FirebaseAuth.getInstance();
        progressView = (ProgressView) findViewById(R.id.progress);
        if(!sharedprefemail.equalsIgnoreCase("")
                && !sharedprefpassword.equalsIgnoreCase("")){
            checkFirebaseLogin(sharedprefemail,sharedprefpassword,false);
        }

        main_layout = (RelativeLayout) findViewById(R.id.main_layout) ;

        Picasso.with(LoginActivity.this)
                .load(R.drawable.dashboard)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_clear_black_24dp)
                .fit()
                .into(user_profile_photo);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressView.setVisibility(View.VISIBLE);
                main_layout.setVisibility(View.GONE);
                if(email.getText() != null && !email.getText().toString().trim().equalsIgnoreCase("") && isValidEmail(email.getText())){

                    if(password.getText() != null && !password.getText().toString().trim().equalsIgnoreCase("")){
                            checkFirebaseLogin(email.getText().toString(),password.getText().toString(),true);

                    }else{
                        password.setError("Please enter password to proceed");
                        password.requestFocus();
                        progressView.setVisibility(View.GONE);
                        main_layout.setVisibility(View.VISIBLE);

                    }
                }else {
                    email.setError("Please enter a valid email");
                email.requestFocus();
                    progressView.setVisibility(View.GONE);
                    main_layout.setVisibility(View.VISIBLE);

                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

    }


    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    public void checkFirebaseLogin(String emails, String passwords, final boolean flag){
        mAuth.signInWithEmailAndPassword(emails, passwords)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Firebase", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        progressView.setVisibility(View.GONE);
                        main_layout.setVisibility(View.VISIBLE);


                        if (!task.isSuccessful()) {
                            if(flag) {
                                Log.w("Firebase", "signInWithEmail:failed", task.getException());
                                Toast.makeText(LoginActivity.this, "Please Check Your Email/Password",
                                        Toast.LENGTH_SHORT).show();
                                email.setError("Please check email");
                                password.setError("please check password");
                            }
                        }else {
                            if(sharedpreferences !=null) {
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(getResources().getString(R.string.email), email.getText().toString());
                                editor.putString(getResources().getString(R.string.password), password.getText().toString());
                                editor.apply();
                                editor.commit();
                            }
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                        }

                        // ...
                    }
                });
    }

}
