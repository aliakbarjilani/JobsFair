package com.example.android.jobsfair;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class signup extends AppCompatActivity {

    private ImageButton userImage;
    private static final int GALLREQ = 1;
    private EditText FirstName, LastName, Email, Password;//, ConfirmPassword
    private Uri uri = null;
    private StorageReference storageReference = null;
    private DatabaseReference mRef;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
        FirstName = (EditText)findViewById(R.id.txtFirstName);
        LastName = (EditText)findViewById(R.id.txtLastName);
        Email = (EditText)findViewById(R.id.txtEmail);
        Password = (EditText)findViewById(R.id.txtPassword);
        //ConfirmPassword = (EditText)findViewById(R.id.edtxtConfirmPassword);
        storageReference = FirebaseStorage.getInstance().getReference("user");
        mRef = FirebaseDatabase.getInstance().getReference("user");
    }

    public void userImageButtonClicked(View view){
        //https://stackoverflow.com/questions/32854575/select-file-from-file-manager-via-intent
        Intent intent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
        intent.putExtra("CONTENT_TYPE", "*/*");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivityForResult(intent, GALLREQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if (resultCode == 2)
        if (requestCode == GALLREQ && resultCode == RESULT_OK){
            uri = data.getData();
            userImage = (ImageButton) findViewById(R.id.userImageButton);
            userImage.setImageURI(uri);
        }
    }

    public void onSignUpButtonClicked(View view){
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
                    final DatabaseReference newPost = mRef.push();
                    newPost.child("FirstName").setValue(strfname);
                    newPost.child("LastName").setValue(strlname);
                    newPost.child("Email").setValue(stremail);
                    newPost.child("Password").setValue(strpass);
                    newPost.child("Image").setValue(downloadurl.toString());
                }
            });
        }
    }

    public void onSignInButtonClicked(View view){
        Toast.makeText(signup.this, "Sign In called.", Toast.LENGTH_LONG).show();
    }
}
