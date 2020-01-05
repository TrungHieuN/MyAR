package com.example.myar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.myar.RoomDatabase.DataSource.CartRepository;
import com.example.myar.RoomDatabase.Local.CartDataSource;
import com.example.myar.RoomDatabase.Local.CartDatabase;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    SectionsPagerAdapter sectionsPagerAdapter;
    TabItem tabProduct;
    TabItem tabAccount;

    public static CartDatabase cartDatabase;
    public static CartRepository cartRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,
                R.color.colorProductBackground));

        initDB();

        tabLayout = findViewById(R.id.tabLayout);
        tabProduct = findViewById(R.id.tabProduct);
        tabAccount = findViewById(R.id.tabAccount);
        viewPager = findViewById(R.id.viewPager);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(sectionsPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1) {
                    toolbar.setBackground(ContextCompat.getDrawable(MainActivity.this,
                            R.drawable.side_nav_bar3));
                    tabLayout.setBackground(ContextCompat.getDrawable(MainActivity.this,
                            R.drawable.side_nav_bar3));
                    getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,
                            R.color.colorToolbar2));

                } else {
                    toolbar.setBackground(ContextCompat.getDrawable(MainActivity.this,
                            R.drawable.side_nav_bar2));
                    tabLayout.setBackground(ContextCompat.getDrawable(MainActivity.this,
                            R.drawable.side_nav_bar2));
                    getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,
                            R.color.colorProductBackground));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }


    private void initDB() {
        cartDatabase = CartDatabase.getInstance(this);
        cartRepository = CartRepository.getInstance(CartDataSource.getInstance(cartDatabase.cartDAO()));
    }
}
