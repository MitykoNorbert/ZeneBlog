package com.example.zeneblog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddPostActivity extends AppCompatActivity {

    private TextInputEditText postNameEdt,postDescEdt;
    private Button addPostBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String postID;
    private String postAuthor;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        postNameEdt = findViewById(R.id.idEditPostName);
        postDescEdt = findViewById(R.id.idEditPostText);
        mAuth =FirebaseAuth.getInstance();
        addPostBtn = findViewById(R.id.idBtnAddPost);
        loadingPB = findViewById(R.id.idPBLoading);
        postDescEdt = findViewById(R.id.idEditPostText);
        firebaseDatabase = FirebaseDatabase.getInstance();


        databaseReference = firebaseDatabase.getReference("Posts");
        addPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                String postName = postNameEdt.getText().toString();
                String postDesc = postDescEdt.getText().toString();
                postAuthor = mAuth.getCurrentUser().getEmail();
                postID = postName;

                PostRVModal postRVModal = new PostRVModal(postName,postDesc,postID,postAuthor);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(postID).setValue(postRVModal);
                            finish();
                            startActivity(new Intent(AddPostActivity.this,MainActivity.class));
                        loadingPB.setVisibility(View.GONE);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddPostActivity.this, "post failed..."+error.toString(), Toast.LENGTH_SHORT).show();
                        loadingPB.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}