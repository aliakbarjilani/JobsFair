package com.example.android.jobsfair;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class add_job_admin extends Activity {

    private ImageButton imgbJob;
    private static final int GALLREQ = 1;
    private EditText txtJobTitle, txtJobDesc, txtInstantApply, txtApplicationURL, txtApplicationEmail;//, ConfirmPassword
    private CheckBox chkInstantApply;
    private Spinner spnCity, spnCategory, spnSubCategory;
    private Uri uri = null;
    private StorageReference storageReference = null;
    private DatabaseReference mRef;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_admin);

        txtJobTitle = (EditText)findViewById(R.id.txtJobTitle);
        txtJobDesc = (EditText)findViewById(R.id.txtJobDesc);
        chkInstantApply = (CheckBox) findViewById(R.id.chkInstantApply);
        txtInstantApply = (EditText)findViewById(R.id.txtInstantApply1);
        txtApplicationURL = (EditText)findViewById(R.id.txtApplicationURL);
        txtApplicationEmail = (EditText)findViewById(R.id.txtApplicationEmail);

        // You can even remove the name "jobs" since you can save the image anywhere in the database.
        storageReference = FirebaseStorage.getInstance().getReference("jobs");
        mRef = FirebaseDatabase.getInstance().getReference("jobs");
    }

    public void onJobImageButtonClicked(View view){
        //Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        //galleryIntent.setType("Image/*"); // pick the image formats.
        //startActivityForResult(galleryIntent, GALLREQ);
        //https://stackoverflow.com/questions/32854575/select-file-from-file-manager-via-intent
        Intent intent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
        intent.putExtra("CONTENT_TYPE", "*/*");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivityForResult(intent, GALLREQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // To get the URI of the Image from Gallery Intent upon result.
        super.onActivityResult(requestCode, resultCode, data);
        // if (resultCode == 2)
        if (requestCode == GALLREQ && resultCode == RESULT_OK){
            uri = data.getData();
            imgbJob = (ImageButton) findViewById(R.id.imgbJob);
            imgbJob.setImageURI(uri);
        }
    }

    public void onSignUpButtonClicked(View view){
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("Image/*");
        startActivityForResult(galleryIntent, GALLREQ);
    }

    public void onAddJobButtonClicked(View view){
        // Compared with FoodOrderClient tutorial 31:00.
        // Set Database rules: auth == null initially till app is completed..
        // Set Storage rules: auth == null till app allows logins.
        final String strjobtitle = txtJobTitle.getText().toString().trim();
        final String strjobdesc = txtJobDesc.getText().toString().trim();
        //final String strcity = spnCity.getSelectedItem().toString().trim();
        //final String strcategory = spnCategory.getSelectedItem().toString().trim();
        //final String strsubcategory = spnSubCategory.getSelectedItem().toString().trim();
        final Boolean chkinstantapply = chkInstantApply.isChecked();
        //final String strinstantapply = txtInstantApply.getText().toString().trim();
        final String strapplicationurl = txtApplicationURL.getText().toString().trim();
        final String strapplicationemail = txtApplicationEmail.getText().toString().trim();

        if ( !TextUtils.isEmpty(strjobtitle) ){  //&& !TextUtils.isEmpty(spnCity.getSelectedItem().toString()) && !TextUtils.isEmpty(spnCategory.getSelectedItem().toString())
            StorageReference filepath = storageReference.child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final Uri downloadurl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(add_job_admin.this,"Image uploaded", Toast.LENGTH_LONG).show();
                    final DatabaseReference newPost = mRef.push();
                    newPost.child("Image").setValue(downloadurl.toString());
                    newPost.child("JobTitle").setValue(strjobtitle);
                    newPost.child("JobDesc").setValue(strjobdesc);
                    newPost.child("City").setValue("Cork");//strcity);
                    newPost.child("Category").setValue("Software");//strcategory);
                    newPost.child("SubCategory").setValue("Consultant");//strsubcategory);
                    newPost.child("InstantApply").setValue("1");
                    newPost.child("ApplicationURL").setValue(strapplicationurl);
                    newPost.child("ApplicationEmail").setValue(strapplicationemail);
                    //newPost.child("Password").setValue(strapplicationurl);
                    Toast.makeText(add_job_admin.this,"Job Added", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
