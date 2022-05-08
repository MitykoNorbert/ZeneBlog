package com.example.zeneblog;

import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class PosterListActivity {
    private RecyclerView postRV;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<PostRVModal> postRVModalArrayList;
    private RelativeLayout bottomSheetRL;
    private PostRVAdapter postRVAdapter;
    private FirebaseAuth mAuth;
    private Button logout;
    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;


}
