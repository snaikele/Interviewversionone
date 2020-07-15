package com.example.interviewversionone.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.interviewversionone.R;
import com.example.interviewversionone.TabLayoutOne_Quant;
import com.example.interviewversionone.TabLayoutThree_Logicall;
import com.example.interviewversionone.TabLayoutTwo_English;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class MCQList extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private TabLayoutOne_Quant tabLayoutOne_quant;
    private TabLayoutTwo_English tabLayoutTwo_english;
    private TabLayoutThree_Logicall tabLayoutThree_logicall;

    private static final String TAG = "TabLayoutInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_c_q_list);

        Toolbar toolbar = findViewById(R.id.MCQ_toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        tabLayoutOne_quant = new TabLayoutOne_Quant();
        tabLayoutTwo_english = new TabLayoutTwo_English();
        tabLayoutThree_logicall = new TabLayoutThree_Logicall();

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(tabLayoutOne_quant, "QUNAT");
        viewPagerAdapter.addFragment(tabLayoutTwo_english, "ENGLISH");
        viewPagerAdapter.addFragment(tabLayoutThree_logicall, "LOGICAL");
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.common_google_signin_btn_icon_dark_focused);
        tabLayout.getTabAt(1).setIcon(R.drawable.common_google_signin_btn_icon_dark_focused);
        tabLayout.getTabAt(2).setIcon(R.drawable.common_google_signin_btn_icon_dark_focused);

        BadgeDrawable badgeDrawable = tabLayout.getTabAt(0).getOrCreateBadge();
        badgeDrawable.setVisible(true);
        /*badgeDrawable.setNumber(12);*/

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }

}
