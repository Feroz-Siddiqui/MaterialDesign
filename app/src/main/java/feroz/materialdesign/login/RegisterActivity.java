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
import android.widget.Toast;

import com.example.feroz.materialdesign.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import feroz.materialdesign.MainActivity;

import static feroz.materialdesign.login.LoginActivity.isValidEmail;

public class RegisterActivity extends AppCompatActivity {
    private EditText email,password,mobile,first_name,last_name;
    private Button signin;
    private ImageButton user_profile_photo;
    private  SharedPreferences sharedpreferences;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sharedpreferences = getSharedPreferences(getResources().getString(R.string.shared_preference_key), Context.MODE_PRIVATE);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        mobile  = (EditText) findViewById(R.id.mobile);
        signin = (Button) findViewById(R.id.register);
        user_profile_photo = (ImageButton) findViewById(R.id.user_profile_photo);
        first_name = (EditText) findViewById(R.id.first_name);
        last_name = (EditText) findViewById(R.id.last_name);
        Picasso.with(RegisterActivity.this)
                .load(R.drawable.dashboard)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_clear_black_24dp)
                .fit()
                .into(user_profile_photo);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText() != null && !email.getText().toString().trim().equalsIgnoreCase("") && isValidEmail(email.getText()) ) {
                    if(password.getText() != null && !password.getText().toString().trim().equalsIgnoreCase("")) {
                        if(first_name.getText() != null && !first_name.getText().toString().trim().equalsIgnoreCase("")) {
                            if(last_name.getText() != null && !last_name.getText().toString().trim().equalsIgnoreCase("")) {
                                if (mobile.getText() != null && !mobile.getText().toString().trim().equalsIgnoreCase("") && mobile.getText().toString().length() == 10) {
                                    createFirebaseUser(email, password, mobile,first_name,last_name);
                                } else {
                                    mobile.setError("Please enter 10 digit mobile to proceed");
                                    mobile.requestFocus();
                                }
                            }else{
                                last_name.setError("Please Enter your First Name");
                                last_name.requestFocus();
                            }
                        }else{
                            first_name.setError("Please Enter your First Name");
                            first_name.requestFocus();
                        }
                    }else{
                        password.setError("Please enter password to proceed");
                        password.requestFocus();
                    }
                }else {
                    email.setError("Please enter a valid email");
                    email.requestFocus();

                }
                }
        });


        mAuth = FirebaseAuth.getInstance();


    }

    private void createFirebaseUser(final EditText email, final EditText password, final EditText mobile,final EditText first_name,final EditText last_name) {
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("LolliPOP", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "User already Exist please go to sign in ",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference();
                            HashMap<String,String> map = new HashMap<String, String>();
                            map.put(getResources().getString(R.string.email),email.getText().toString());
                            map.put(getResources().getString(R.string.password),password.getText().toString());
                            map.put("mobile",mobile.getText().toString());
                            map.put(getResources().getString(R.string.first_name),first_name.getText().toString());
                            map.put(getResources().getString(R.string.last_name),last_name.getText().toString());

                            DatabaseReference mypostref = myRef.push();
                            mypostref.setValue(map);


                            if(sharedpreferences !=null) {
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(getResources().getString(R.string.email), email.getText().toString());
                                editor.putString(getResources().getString(R.string.password), password.getText().toString());
                                editor.putString(getResources().getString(R.string.user_unique_id), mypostref.getKey());
                                editor.putString(getResources().getString(R.string.first_name), first_name.getText().toString());
                                editor.putString(getResources().getString(R.string.last_name), last_name.getText().toString());

                                editor.putString("mobile", mobile.getText().toString());

                                editor.apply();
                                editor.commit();
                            }
                            Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(i);
                        }

                        // ...
                    }
                });


    }





}
