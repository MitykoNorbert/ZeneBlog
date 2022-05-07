package com.example.zeneblog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.zeneblog.databinding.BottomSheetDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PostRVAdapter.PostClickInterface{

    private RecyclerView postRV;
    private ProgressBar loadingPB;
    private FloatingActionButton addFAB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<PostRVModal> postRVModalArrayList;
    private RelativeLayout bottomSheetRL;
    private PostRVAdapter postRVAdapter;
    private FirebaseAuth mAuth;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postRV = findViewById(R.id.idRVPosts);
        loadingPB = findViewById(R.id.idPBLoading);
        addFAB = findViewById(R.id.idAddFAB);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Posts");
        postRVModalArrayList = new ArrayList<>();
        bottomSheetRL = findViewById(R.id.idRLBSheet);
        mAuth=FirebaseAuth.getInstance();
        logout=findViewById(R.id.idLogOut);

        postRVAdapter = new PostRVAdapter(postRVModalArrayList,this,this);
        postRV.setLayoutManager(new LinearLayoutManager(this));
        postRV.setAdapter(postRVAdapter);
        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddPostActivity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Successfully logged out.", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);

            }
        });
         getAllPosts();

    }
    private void getAllPosts(){
        postRVModalArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                postRVModalArrayList.add(snapshot.getValue(PostRVModal.class));
                postRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                postRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                postRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                postRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onPostClick(int position) {
        displayBottomSheet(postRVModalArrayList.get(position));
    }
    private void displayBottomSheet(PostRVModal postRVModal){

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout= LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog,bottomSheetRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView postNameTV = layout.findViewById(R.id.idTVPostName);
        TextView postDescTV = layout.findViewById(R.id.idTVDescription);
        TextView postAuthorTV = layout.findViewById(R.id.idTVPostAuthor);
        Button editBtn = layout.findViewById(R.id.idBtnEdit);
        Button viewDetailsBtn = layout.findViewById(R.id.idBtnDetails);


        postNameTV.setText(postRVModal.getPostName());
        postDescTV.setText(postRVModal.getPostDescription());
        postAuthorTV.setText(postRVModal.getPostAuthor());
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAuth.getCurrentUser().getEmail().equals(postAuthorTV.getText().toString())){
                    Intent i = new Intent(MainActivity.this,EditPostActivity.class);
                    i.putExtra("post",postRVModal);
                    startActivity(i);
                }else{
                    Toast.makeText(MainActivity.this, "You can only edit your own posts.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        viewDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(postRVModal.getPostDescription()));//link
                startActivity(i);*/
            }
        });

    }


}