package com.example.android.jobsfair;

import android.app.Activity;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class landing extends Activity {

    EditText txtEmail, txtPswd;
    Button btnAdminSignUp, btnSignUp, btnSignin;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        txtEmail = (EditText) findViewById(R.id.editEmail);
        txtPswd = (EditText) findViewById(R.id.editPswd);
        btnSignin = (Button) findViewById(R.id.btnSignin);
        btnAdminSignUp = (Button) findViewById(R.id.btnAdminSignUp);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("user");
        txtEmail.setText("new@something.com");
        txtPswd.setText("password");
        //How to Build Food Order Android App for Restaurant (Android studio)
        //https://www.youtube.com/watch?v=5rQILkqDpWU
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void onSignInButtonClicked(View view) {

        Toast.makeText(landing.this, "Signin Called.", Toast.LENGTH_LONG).show();
        String owner_email = txtEmail.getText().toString().trim();
        String owner_pass = txtPswd.getText().toString().trim();

        if (!TextUtils.isEmpty(owner_email) && !TextUtils.isEmpty(owner_pass)) {

            mAuth.signInWithEmailAndPassword(owner_email, owner_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {//.isComplete()) {
                        checkOwnerExist();
                    }
                }
            });
        }
    }

    public void onAdminSignUpButtonClicked(View view)
    {
        Toast.makeText(landing.this, "Admin Signup called.", Toast.LENGTH_LONG).show();
        Intent AdmindashboardIntent = new Intent(landing.this, dashboard_admin.class);
        startActivity(AdmindashboardIntent);
    }

    public void onRegisterButtonClicked(View view) {
        Toast.makeText(landing.this, "Registration called.", Toast.LENGTH_LONG).show();
        Intent RegisterIntent = new Intent(landing.this, registration.class);
        startActivity(RegisterIntent);
    }

    public void onRegFormButtonClicked(View view) {

        Toast.makeText(landing.this, "Registration called again.", Toast.LENGTH_LONG).show();
        Intent RegFormIntent = new Intent(landing.this, signup.class);
        startActivity(RegFormIntent);
    }

    public void checkOwnerExist() {
        Toast.makeText(landing.this, "Signin Called.", Toast.LENGTH_LONG).show();
        final String owner_id = mAuth.getCurrentUser().getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               if (dataSnapshot.hasChild(owner_id)){
                   startActivity( new Intent( landing.this , dashboard_admin.class));
                   Toast.makeText(landing.this, "Signin Successful.", Toast.LENGTH_LONG).show();
               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
        });
    }
}
