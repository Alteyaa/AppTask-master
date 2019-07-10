package com.apptask.activities;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.apptask.App;
import com.apptask.R;
import com.apptask.TaskAdapter;
import com.apptask.model.Task;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    TaskAdapter taskAdapter;
    List<Task> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
        boolean isShown = preferences.getBoolean("isShown", false);

        if (!isShown) {
            startActivity(new Intent(this, OnBoardActivity.class));
            finish();
        }

        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FormActivity.class));

            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        initList();
    }

    private void initList() {
        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_my);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        taskAdapter = new TaskAdapter(list);
        recyclerView.setAdapter(taskAdapter);
        App.getInstance().getDatabase().taskDao().getAll().
                observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> task) {
                list.clear();
                list.addAll(task);
                taskAdapter.notifyDataSetChanged();
            }
        });


        taskAdapter.setClickListener(new TaskAdapter.ClickListener() {
            @Override
            public void onItemClick(int pos) {
                Task task = list.get(pos);
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                intent.putExtra("task", task);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int pos) {
                Task task = list.get(pos);
                showAlert(task);

            }
        });
    }

    private void showAlert(final Task task) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Вы хотите удалить запись " + task.getTitle() + "?");
        builder.setNegativeButton("Отмена", null);
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                App.getInstance().getDatabase().taskDao().delete(task);

            }
        });
        builder.create().show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                App.getInstance().getDatabase().taskDao().getAll().observe(this,
                        new Observer<List<Task>> () {
                            @Override
                            public void onChanged(@Nullable List<Task> task) {
                                list.clear();
                                list.addAll(task);
                                taskAdapter.notifyDataSetChanged();
                            }
                        });
            }
        }
    }
}


