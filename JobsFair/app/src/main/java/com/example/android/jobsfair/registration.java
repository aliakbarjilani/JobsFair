package com.example.android.jobsfair;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class registration extends Activity {
    private ImageButton userImage;
    private static final int GALLREQ = 1;
    private EditText FirstName, LastName, Email, Password;//, ConfirmPassword
    private Uri uri = null;
    private StorageReference storageReference = null;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(registration.this, "Sign up created..", Toast.LENGTH_LONG).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Get credentials from the form.
        FirstName = (EditText)findViewById(R.id.edtxtFirstName);
        LastName = (EditText)findViewById(R.id.edtxtLastName);
        // Add Checkbox for IsAdmin..
        Email = (EditText)findViewById(R.id.edtxtEmail);
        Password = (EditText)findViewById(R.id.edtxtPassword);
        //ConfirmPassword = (EditText)findViewById(R.id.edtxtConfirmPassword);

        mAuth = FirebaseAuth.getInstance();
        //storageReference = FirebaseStorage.getInstance().getReference("user");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");//("user");

    }


    public void userImageButtonClicked(View view){
        //https://stackoverflow.com/questions/32854575/select-file-from-file-manager-via-intent
        Intent intent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
        intent.putExtra("CONTENT_TYPE", "*/*");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivityForResult(intent, GALLREQ);
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if (resultCode == 2)
        if (requestCode == GALLREQ && resultCode == RESULT_OK){
            uri = data.getData();
            userImage = (ImageButton) findViewById(R.id.userImageButton);
            userImage.setImageURI(uri);
        }
    }*/

    public void onSignUpButtonClicked(View view){
        Toast.makeText(registration.this, "Sign up called.." , Toast.LENGTH_LONG).show();
        try {
            final String email_text = Email.toString().trim();
            String pass_text = Password.toString().trim();
            //if (!TextUtils.isEmpty(email_text) && !TextUtils.isEmpty(pass_text) ) {

            //  Toast.makeText(this, "Please Check if u have already an account or low password", Toast.LENGTH_SHORT).show();
            mAuth.createUserWithEmailAndPassword(email_text, pass_text).addOnCompleteListener(
                new OnCompleteListener<AuthResult>()
                {
                    //@Override Removed specifically.
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if (task.isSuccessful()) { // Enabled specifically.
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user = mDatabase.child(user_id);
                            current_user.child("Name").setValue(email_text);
                            Toast.makeText(registration.this, "Name stored.." , Toast.LENGTH_LONG).show();
                            startActivity( new Intent(registration.this , dashboard.class));
                            Email.setText("");
                            Password.setText("");
                        //}
                    }
                });
            //}
        } catch (Exception e){
            Toast.makeText(registration.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        /*
        //Intent signInIntent = new Intent(signIn.this, landing.class);
        //startActivity(signInIntent);
        final String strfname = FirstName.getText().toString().trim();
        final String strlname = LastName.getText().toString().trim();
        final String stremail = Email.getText().toString().trim();
        final String strpass = Password.getText().toString().trim();

        if (!TextUtils.isEmpty(stremail) && !TextUtils.isEmpty(strpass)){
            StorageReference filepath = storageReference.child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final Uri downloadurl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(signup.this,"Image uploaded", Toast.LENGTH_LONG).show();
                    final DatabaseReference newPost = mDatabase.push();
                    newPost.child("FirstName").setValue(strfname);
                    newPost.child("LastName").setValue(strlname);
                    newPost.child("Email").setValue(stremail);
                    newPost.child("Password").setValue(strpass);
                    newPost.child("Image").setValue(downloadurl.toString());
                }
            });
        }*/
    }

    public void onSignInButtonClicked(View view){
        Toast.makeText(registration.this, "Sign In called.", Toast.LENGTH_LONG).show();
    }

}
