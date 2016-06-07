package com.ac.qapp;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.ac.qapp.category.CategoryPojo;

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





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        unbinder = ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpDrawer(toolbar);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {

        List<CategoryPojo> stringList = new ArrayList<>();

        CategoryPojo categoryPojo1 = new CategoryPojo();
        categoryPojo1.categoryName = "Sport";

        CategoryPojo categoryPojo2 = new CategoryPojo();
        categoryPojo2.categoryName = "goT";

        CategoryPojo categoryPojo3 = new CategoryPojo();
        categoryPojo3.categoryName = "Bollywood";

        CategoryPojo categoryPojo4 = new CategoryPojo();
        categoryPojo4.categoryName = "Webmyne";

        CategoryPojo categoryPojo5 = new CategoryPojo();
        categoryPojo5.categoryName = "Us";

        stringList.add(categoryPojo1);
        stringList.add(categoryPojo2);
        stringList.add(categoryPojo3);
        stringList.add(categoryPojo4);
        stringList.add(categoryPojo5);


        QuizTypeAdapter quizTypeAdapter = new QuizTypeAdapter(stringList, colorsString,colorsDark);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_recyclerView_padding);
        quizTypeRecyclerView.addItemDecoration(new ItemDecoration(spacingInPixels));
        quizTypeRecyclerView.setHasFixedSize(true);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        quizTypeRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        quizTypeRecyclerView.setAdapter(quizTypeAdapter);
        progressBar.setVisibility(View.GONE);
    }

    private void setUpDrawer(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_rateApp) {

                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


}
