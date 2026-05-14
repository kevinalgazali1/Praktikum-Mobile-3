package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    CardView btnEditProfil;
    TextView tvNama, tvUsername, tvBio, tvPostCount;
    ImageView ivProfilePic, ivBottomProfilePic, ivAddPost, btnHome, iconMenu;
    Uri currentImageUri;
    RecyclerView rvProfileGrid, rvHighlights;
    ProfileGridAdapter gridAdapter;
    List<Post> profilePosts;

    ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();

                        String namaBaru = data.getStringExtra("EXTRA_NAMA");
                        String usernameBaru = data.getStringExtra("EXTRA_USERNAME");
                        String bioBaru = data.getStringExtra("EXTRA_BIO");
                        String uriString = data.getStringExtra("EXTRA_IMAGE_URI");

                        if (namaBaru != null) tvNama.setText(namaBaru);
                        if (usernameBaru != null) tvUsername.setText(usernameBaru);
                        if (bioBaru != null) tvBio.setText(bioBaru);

                        if (uriString != null) {
                            currentImageUri = Uri.parse(uriString);
                            ivProfilePic.setImageURI(currentImageUri);
                            ivBottomProfilePic.setImageURI(currentImageUri);
                        }
                        
                        syncUserPosts(tvUsername.getText().toString());
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> addPostLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        String caption = result.getData().getStringExtra("EXTRA_CAPTION");
                        String imageUri = result.getData().getStringExtra("EXTRA_IMAGE_URI");
                        
                        Post newPost = new Post(
                                R.drawable.joy,
                                tvUsername.getText().toString(), 
                                imageUri, 
                                caption
                        );
                        
                        profilePosts.add(0, newPost);
                        DataDummy.getHomePosts().add(0, newPost);
                        
                        gridAdapter.notifyItemInserted(0);
                        rvProfileGrid.scrollToPosition(0);
                        updatePostCount();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEditProfil = findViewById(R.id.btn_edit_profil);
        tvNama = findViewById(R.id.nama_akun);
        tvBio = findViewById(R.id.tv_bio);
        tvUsername = findViewById(R.id.tv_username_top);
        tvPostCount = findViewById(R.id.tv_post_count);
        ivProfilePic = findViewById(R.id.iv_main_profile_pic);
        ivBottomProfilePic = findViewById(R.id.iv_bottom_profile_pic);
        ivAddPost = findViewById(R.id.icon_plus);
        btnHome = findViewById(R.id.btn_home);
        iconMenu = findViewById(R.id.icon_menu);
        rvProfileGrid = findViewById(R.id.rvProfileGrid);
        rvHighlights = findViewById(R.id.rvHighlights);

        btnEditProfil.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            intent.putExtra("EXTRA_NAMA", tvNama.getText().toString());
            intent.putExtra("EXTRA_USERNAME", tvUsername.getText().toString());
            intent.putExtra("EXTRA_BIO", tvBio.getText().toString());
            if (currentImageUri != null) {
                intent.putExtra("EXTRA_IMAGE_URI", currentImageUri.toString());
            }
            editProfileLauncher.launch(intent);
        });

        ivAddPost.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddPostActivity.class);
            addPostLauncher.launch(intent);
        });

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        iconMenu.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        });

        // Setup Highlights
        rvHighlights.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        HighlightAdapter highlightAdapter = new HighlightAdapter(this, DataDummy.getHighlights());
        rvHighlights.setAdapter(highlightAdapter);

        // Setup Profile Grid
        profilePosts = DataDummy.getProfilePosts();
        rvProfileGrid.setLayoutManager(new GridLayoutManager(this, 3));
        gridAdapter = new ProfileGridAdapter(this, profilePosts);
        rvProfileGrid.setAdapter(gridAdapter);
        
        updatePostCount();
    }

    private void updatePostCount() {
        if (tvPostCount != null && profilePosts != null) {
            tvPostCount.setText(String.valueOf(profilePosts.size()));
        }
    }

    private void syncUserPosts(String newUsername) {
        // Implementation for syncing user posts
    }
}