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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditPostActivity extends AppCompatActivity {

    private TextInputEditText postNameEdt,postDescEdt;
    private Button updatePostBtn,deletePostBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String postID;
    private PostRVModal postRVModal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        postNameEdt = findViewById(R.id.idEditPostName);
        postDescEdt = findViewById(R.id.idEditPostText);
        updatePostBtn = findViewById(R.id.idBtnUpdatePost);
        deletePostBtn = findViewById(R.id.idBtnDeletePost);
        loadingPB = findViewById(R.id.idPBLoading);
        postDescEdt = findViewById(R.id.idEditPostText);
        firebaseDatabase = FirebaseDatabase.getInstance();

        postRVModal = getIntent().getParcelableExtra("post");
        if (postRVModal!=null){
            postNameEdt.setText(postRVModal.getPostName());
            postDescEdt.setText(postRVModal.getPostDescription());
            postID = postRVModal.getPostID();

        }
        databaseReference = firebaseDatabase.getReference("Posts").child(postID);
        updatePostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                String postName = postNameEdt.getText().toString();
                String postDesc = postDescEdt.getText().toString();

                Map<String,Object> map = new HashMap<>();
                map.put("postName",postName);
                map.put("postDescription",postDesc);
                map.put("postID",postID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditPostActivity.this, "Post successfully updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditPostActivity.this,MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        loadingPB.setVisibility(View.GONE);
                        Toast.makeText(EditPostActivity.this, "Failed to update post", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        deletePostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePost();
            }
        });

    }
    private void deletePost() {
        databaseReference.removeValue();
        Toast.makeText(this, "Post removed", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditPostActivity.this,MainActivity.class));
    }
}