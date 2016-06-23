package com.ac.qapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ac.qapp.category.CategoryPojo;
import com.ac.qapp.helpers.ComplexPreferences;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;

public class DrawerActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.quizTypeRecyclerView)
    RecyclerView quizTypeRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindArray(R.array.default_colors)
    String[] colorsString;
    Unbinder unbinder;

    @BindArray(R.array.default_colors_dark)
    String[] colorsDark;
    private DatabaseReference mFirebaseDatabaseReference;
    private GridLayoutManager mLinearLayoutManager;
    int defaultt = 0x000000;

    private FirebaseRecyclerAdapter<CategoryPojo, CategoryViewHolder>
            mFirebaseAdapter;

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {


        public TextView txtChatText;
        public ImageView imgCatgory;
        public CardView parentCategory;


        public CategoryViewHolder(View v) {
            super(v);
            txtChatText = (TextView) itemView.findViewById(R.id.quizTypeNameTextView);
            imgCatgory = (ImageView) itemView.findViewById(R.id.imgCatgory);
            parentCategory = (CardView) itemView.findViewById(R.id.parentCategory);

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_drawer);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.black));
        }

        unbinder = ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mLinearLayoutManager = new GridLayoutManager(this, 2);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("Categories");
        fetchCategories();

    }

    private void fetchCategories() {

        mFirebaseAdapter = new FirebaseRecyclerAdapter<CategoryPojo,
                CategoryViewHolder>(
                CategoryPojo.class,
                R.layout.item_quiz_type_question,
                CategoryViewHolder.class,
                mFirebaseDatabaseReference) {

            @Override
            protected void populateViewHolder(final CategoryViewHolder viewHolder,
                                              final CategoryPojo friendlyMessage, int position) {
                if (progressBar.isShown()) {
                    progressBar.setVisibility(View.GONE);
                }
                viewHolder.txtChatText.setText(friendlyMessage.catgName);

                Glide.with(DrawerActivity.this).load(friendlyMessage.catgBannerImage).asBitmap().into(new BitmapImageViewTarget(viewHolder.imgCatgory) {
                    @Override
                    public void onResourceReady(Bitmap drawable, GlideAnimation anim) {
                        super.onResourceReady(drawable, anim);
                        if (drawable != null && !drawable.isRecycled()) {
                            Palette palette = Palette.from(drawable).generate();

                            int vibrant = palette.getVibrantColor(defaultt);
                            int vibrantLight = palette.getLightVibrantColor(defaultt);
                            int vibrantDark = palette.getDarkVibrantColor(defaultt);
                            int muted = palette.getMutedColor(defaultt);
                            int mutedLight = palette.getLightMutedColor(defaultt);
                            int mutedDark = palette.getDarkMutedColor(defaultt);

                            viewHolder.txtChatText.setBackgroundColor(vibrantLight);
                            viewHolder.txtChatText.setTextColor(vibrantDark);
                        }

                    }
                });

                viewHolder.parentCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(DrawerActivity.this, friendlyMessage.catgName, Toast.LENGTH_SHORT).show();
                        ComplexPreferences comp = ComplexPreferences.getComplexPreferences(DrawerActivity.this, "user", MODE_PRIVATE);
                        comp.putObject("selected_cat", friendlyMessage);
                        comp.commit();

                        Intent iSubCategory = new Intent(DrawerActivity.this, SubCategoryActivity.class);
                        startActivity(iSubCategory);
                    }
                });

            }

        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition =
                        mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    quizTypeRecyclerView.scrollToPosition(positionStart);
                }
            }

        });
        quizTypeRecyclerView.setLayoutManager(mLinearLayoutManager);
        quizTypeRecyclerView.setAdapter(mFirebaseAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


}
