package com.lymin.nexonlinemarket.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.lymin.nexonlinemarket.view.dialog.DialogAllCountryCode;
import com.lymin.nexonlinemarket.R;
import com.lymin.nexonlinemarket.databinding.ActivityPhoneLoginBinding;
import com.lymin.nexonlinemarket.firebases.FirebaseService;
import com.lymin.nexonlinemarket.managers.UsersManager;
import com.lymin.nexonlinemarket.user.UsersModel;
import com.lymin.nexonlinemarket.user.UsersRealm;
import com.lymin.nexonlinemarket.view.BaseActivity;


import java.util.concurrent.TimeUnit;

import io.realm.Realm;

public class PhoneLoginActivity extends BaseActivity {

    private ActivityPhoneLoginBinding binding;
    private ProgressDialog p;
    private static final String TAG = "PhoneAuthActivity";

    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;
    private FirebaseAuth mAuth;

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_phone_login);

        Realm.init(this);

        p =  new ProgressDialog(this);

        initSpinnerCode();
        binding.btnContinuew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validatePhoneNumber()) {
                    return;
                }


                String phone = binding.spinnerCode.getText().toString()+binding.edPhone.getText().toString().trim();
                p.setMessage("Sending verifying code to "+phone);
                p.show();
                startPhoneNumberVerification(phone);
            }
        });

        binding.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = binding.edVerify.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    binding.edVerify.setError("Cannot be empty.");
                    return;
                }

                verifyPhoneNumberWithCode(mVerificationId, code);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                Log.d(TAG, "onVerificationCompleted:" + credential);
                mVerificationInProgress = false;
                updateUI(STATE_VERIFY_SUCCESS, credential);
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    binding.edPhone.setError("Invalid phone number.");
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }

                // Show a message and update the UI
                // [START_EXCLUDE]
                updateUI(STATE_VERIFY_FAILED);
                // [END_EXCLUDE]
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // [START_EXCLUDE]
                // Update UI
                updateUI(STATE_CODE_SENT);
                // [END_EXCLUDE]
            }
        };

    }

    private void initSpinnerCode() {
        binding.spinnerCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogAllCountryCode(PhoneLoginActivity.this).show(new DialogAllCountryCode.OnCallBack() {
                    @Override
                    public void onSelected(int flag, String code) {
                        binding.spinnerCode.setText("+"+code);
                    }
                });
            }
        });
    }


    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
    // [END resend_verification]

    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // [START_EXCLUDE]
                            updateUI(STATE_SIGNIN_SUCCESS, user);
                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                binding.edVerify.setError("Invalid code.");
                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
                            updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                        }
                    }
                });
    }
    // [END sign_in_with_phone]

    private void updateUI(int uiState) {
        updateUI(uiState, mAuth.getCurrentUser(), null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            updateUI(STATE_SIGNIN_SUCCESS, user);
        } else {
            updateUI(STATE_INITIALIZED);
        }
    }

    private void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case STATE_INITIALIZED:
                Log.d(TAG, "updateUI: A");
                break;
            case STATE_CODE_SENT:
                // Code sent state, show the verification field, the
                Log.d(TAG, "updateUI: B");
                binding.layout1.setVisibility(View.GONE);
                binding.layout2.setVisibility(View.VISIBLE);
                p.dismiss();
                break;
            case STATE_VERIFY_FAILED:
                // Verification has failed, show all options
                Log.d(TAG, "updateUI: C");
                p.dismiss();
                break;
            case STATE_VERIFY_SUCCESS:
                // Verification has succeeded, proceed to firebase sign in

                Log.d(TAG, "updateUI: D");
                // Set the verification text based on the credential
                if (cred != null) {
                    if (cred.getSmsCode() != null) {
                        binding.edVerify.setText(cred.getSmsCode());
                    }
                }
                p.dismiss();
                break;
            case STATE_SIGNIN_FAILED:
                // No-op, handled by sign-in check
                Log.d(TAG, "updateUI: E");
                p.dismiss();
                break;
            case STATE_SIGNIN_SUCCESS:
                // Np-op, handled by sign-in check
                Log.d(TAG, "updateUI: SS");
                break;
        }

        if (user == null) {
            // Signed out
            Log.d(TAG, "updateUI: F");
        } else {
            // Signed in
            Log.d(TAG, "updateUI: G " + user.getUid());
            UsersModel usersM = new UsersModel(
                    user.getUid(), "", "", "", "", "", "", "", "", 0.0, 0.0);
            UsersRealm usersR = new UsersRealm(
                    user.getUid(), "", "", "", "", "", "", "", "", 0.0, 0.0);
            FirebaseService.addNewUser(usersM);
            new UsersManager().addUser(usersR);
            SignUpNameActivity.launch(PhoneLoginActivity.this,user.getUid());
            }
        }

    private boolean validatePhoneNumber() {
        String phoneNumber = binding.edPhone.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            binding.edPhone.setError("Invalid phone number.");
            return false;
        }

        return true;
    }


    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        // [START_EXCLUDE]
        if (mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification(binding.edPhone.getText().toString());
        }
        // [END_EXCLUDE]
    }
    // [END on_start_check_user]
}