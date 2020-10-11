package com.app.gms.activities;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.app.gms.R;
import com.app.gms.fragments.EventsMember;
import com.app.gms.fragments.HomeMember;
import com.app.gms.fragments.NewsMember;
import com.app.gms.fragments.PlansMember;
import com.app.gms.fragments.TimingMember;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class MembersActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    TextView email;
    String Email, Password;
    ImageButton btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        findViews();
        LinearLayout navBar = findViewById(R.id.nav_bar);
        setUpNavigationDrawer();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Email = extras.getString("Email");
        Password = extras.getString("Password");
        loadFragment(new HomeMember());
        bottomNavigationView.setSelectedItemId(R.id.btn_home);
        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.member_menu, menu);
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

    public BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.news_feed:
                            selectedFragment = new NewsMember();
                            break;
                        case R.id.events:
                            selectedFragment = new EventsMember();
                            break;
                        case R.id.btn_home:
                            selectedFragment = new HomeMember();
                            break;
                        case R.id.gym_plans:
                            selectedFragment = new PlansMember();
                            break;
                        case R.id.gym_timing:
                            selectedFragment = new TimingMember();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    private void setUpNavigationDrawer() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MembersActivity.this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    private void findViews() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottomNavigationView = findViewById(R.id.navigation);
        email = findViewById(R.id.nav_email);
    }


    @Override
    public void onBackPressed() {
        backPress();
    }

    private void backPress() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MembersActivity.this);

        builder.setTitle("Rate This App");
        builder.setMessage("If you enjoy this app, please take a moment to rate this app. It won't take more than a minute. Thank you for your support!");
        builder.setCancelable(true);

        builder.setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MembersActivity.super.onBackPressed();
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
                Toast.makeText(getApplicationContext(), "Coming Soon...", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog dialog = builder.create();

        dialog.show();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.rate_us) {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.activity_rate_app);
            final Button btnRate=dialog.findViewById(R.id.btnRate);
            btnExit=dialog.findViewById(R.id.exit);
            final RatingBar ratingBar=dialog.findViewById(R.id.ratingBar1);
            btnRate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"Rate Successful",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            btnExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else if (id == R.id.profile) {
            Intent intent = new Intent(this, MemberProfile.class);
            Bundle bundle = new Bundle();
            bundle.putString("Email", Email);
            bundle.putString("Password", Password);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.gallery) {
            Intent intent = new Intent(this, PictureGallery.class);
            startActivity(intent);
        } else if (id == R.id.about_us) {
            Intent intent = new Intent(this, AboutUs.class);
            startActivity(intent);
        } else if (id == R.id.privacy) {
            Intent intent = new Intent(this, PrivacyPolicy.class);
            startActivity(intent);
        } else if (id == R.id.share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            Intent.createChooser(sendIntent, "Share via");
            startActivity(sendIntent);
        }
        if (id == R.id.exit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MembersActivity.this);

            builder.setTitle("Rate This App");
            builder.setMessage("If you enjoy this app, please take a moment to rate this app. It won't take more than a minute. Thank you for your support!");
            builder.setCancelable(true);

            builder.setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MembersActivity.super.onBackPressed();
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
                    Toast.makeText(getApplicationContext(), "Coming Soon...", Toast.LENGTH_LONG).show();
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
        int id = item.getItemId();
        if (id == R.id.change) {
            Intent intent = new Intent(this, MemberPassChange.class);
            startActivity(intent);
        }else if (id == R.id.notifi) {
            Intent intent = new Intent(this, MemberNotification.class);
            startActivity(intent);
        }
        else if (id == R.id.logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
