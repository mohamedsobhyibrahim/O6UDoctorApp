package org.o6u.doctorapp.registrationActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import org.o6u.doctorapp.R;
import org.o6u.doctorapp.activities.MainActivity;
import org.o6u.doctorapp.customViews.CustomButton;
import org.o6u.doctorapp.customViews.CustomTextView;
import org.o6u.doctorapp.validation.InputValidation;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private CustomTextView titleToolbarTextView;
    private TextInputLayout emailTIL;
    private TextInputLayout passwordTIL;
    private CustomTextView forgetPasswordTextView;
    private CustomButton loginButton;
    private CustomButton googleButton;
    private CustomButton facebookButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private ProgressDialog mLoginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        mAuth = FirebaseAuth.getInstance();
        mLoginProgress = new ProgressDialog(this);
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Doctors");
    }

    private void initViews(){

        mToolbar = findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        titleToolbarTextView = findViewById(R.id.toolbar_title_text_view);
        titleToolbarTextView.setText(R.string.login_account);

        emailTIL = findViewById(R.id.login_email_text_input_layout);
        passwordTIL = findViewById(R.id.login_password_text_input_layout);
        passwordTIL.getEditText().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        forgetPasswordTextView = findViewById(R.id.login_forget_password_text_view);
        forgetPasswordTextView.setOnClickListener(this);
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
        googleButton = findViewById(R.id.login_google_button);
        googleButton.setOnClickListener(this);
        facebookButton = findViewById(R.id.login_facebook_button);
        facebookButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view == loginButton){

            InputValidation inputValidation = new InputValidation();

            if(inputValidation.loginValidation(emailTIL, passwordTIL)){

                String email = emailTIL.getEditText().getText().toString();
                String password = passwordTIL.getEditText().getText().toString();

                mLoginProgress.setTitle("Logging In");
                mLoginProgress.setMessage("Please Wait...");
                mLoginProgress.setCanceledOnTouchOutside(false);
                mLoginProgress.show();

                loginUser(email, password);
            }

        } else if(view == forgetPasswordTextView){
        } else if(view == googleButton){
        } else if(view == facebookButton){

        }
    }

    private void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    mLoginProgress.dismiss();

                    String current_user_id = mAuth.getCurrentUser().getUid();
                    String deviceToken = FirebaseInstanceId.getInstance().getToken();

                    mUserDatabase.child(current_user_id).child("deviceToken").setValue(deviceToken)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(mainIntent);
                                    finish();
                                }
                            });
                } else {

                    mLoginProgress.hide();
                    Toast.makeText(LoginActivity.this, "Cannot Sign In. Please Try Again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
