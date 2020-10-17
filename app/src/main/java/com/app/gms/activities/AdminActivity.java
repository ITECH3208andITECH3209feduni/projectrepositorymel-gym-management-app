package com.app.gms.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.app.gms.fragments.EventFragment;
import com.app.gms.fragments.HomeFragment;
import com.app.gms.fragments.NewsFragment;
import com.app.gms.fragments.PlansFragment;
import com.app.gms.fragments.TimingFragment;
import com.app.gms.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        findViews();
        setUpNavigationDrawer();
        loadFragment(new HomeFragment());
        bottomNavigationView.setSelectedItemId(R.id.btn_home);
        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment=null;
                    switch (item.getItemId())
                    {
                        case R.id.news_feed:
                            selectedFragment=new NewsFragment();
                            break;
                        case R.id.events:
                            selectedFragment=new EventFragment();
                            break;
                        case R.id.btn_home:
                            selectedFragment=new HomeFragment();
                            break;
                        case R.id.gym_plans:
                            selectedFragment=new PlansFragment();
                            break;
                        case R.id.gym_timing:
                            selectedFragment=new TimingFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    return true;
                }
            };

    private void setUpNavigationDrawer() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(AdminActivity.this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    private void findViews() {
        toolbar=findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottomNavigationView=findViewById(R.id.navigation);
    }



    @Override
    public void onBackPressed() {
        backPress();
    }

    private void backPress() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);

        builder.setTitle("Rate This App");
        builder.setMessage("If you enjoy this app, please take a moment to rate this app. It won't take more than a minute. Thank you for your support!");
        builder.setCancelable(true);

        builder.setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AdminActivity.super.onBackPressed();
            }
        });

        builder.setNegativeButton("NO, THANKS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.setNeutralButton("RATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Coming Soon...",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog dialog = builder.create();

        dialog.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.rate_us) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
            builder.setTitle("Rate This App");
            builder.setMessage("If you enjoy this app, please take a moment to rate this app. It won't take more than a minute. Thank you for your support!");
            builder.setCancelable(true);
            builder.setNeutralButton("RATE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(),"Coming Soon...",Toast.LENGTH_LONG).show();
                }
            });
            AlertDialog dialog = builder.create();

            dialog.show();
        }
        else if(id==R.id.gallery)
        {
            Intent intent=new Intent(this,GalleryAdmin.class);
            startActivity(intent);
        }
        else if(id==R.id.about_us)
        {
            Intent intent=new Intent(this,AboutUs.class);
            startActivity(intent);
        }
        else if(id==R.id.privacy)
        {
            Intent intent=new Intent(this,PrivacyPolicy.class);
            startActivity(intent);
        }
        else if(id==R.id.share)
        {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            Intent.createChooser(sendIntent,"Share via");
            startActivity(sendIntent);
        }
        if (id==R.id.exit)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);

            builder.setTitle("Rate This App");
            builder.setMessage("If you enjoy this app, please take a moment to rate this app. It won't take more than a minute. Thank you for your support!");
            builder.setCancelable(true);

            builder.setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    AdminActivity.super.onBackPressed();
                }
            });

            builder.setNegativeButton("NO, THANKS", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });

            builder.setNeutralButton("RATE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(),"Coming Soon...",Toast.LENGTH_LONG).show();
                }
            });
            AlertDialog dialog = builder.create();

            dialog.show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.change)
        {
            Intent intent = new Intent(this, AdminPassChange.class);
            startActivity(intent);
            finish();
        }else if (id == R.id.notifi) {
            Intent intent = new Intent(this, AdminNotification.class);
            startActivity(intent);
        }
        if (id==R.id.logout)
        {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if (id==R.id.help)
        {
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
        }
        if (id==R.id.query)
        {
            Intent intent=new Intent(AdminActivity.this,ChatActivity.class);
            startActivity(intent);
        }
        if (id==R.id.message)
        {
            Intent intent=new Intent(AdminActivity.this,ChatActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}