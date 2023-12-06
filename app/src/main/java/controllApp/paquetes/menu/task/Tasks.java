package controllApp.paquetes.menu.task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import controllApp.paquetes.R;
import com.google.android.material.tabs.TabLayout;

import controllApp.paquetes.menu.Menu;

public class Tasks extends AppCompatActivity {

    private TabLayout tabLayout;
    private viewPagerAdapter adapter;
    private ViewPager2 vista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        tabLayout = (TabLayout) findViewById(R.id.tabTareas);
        vista = (ViewPager2) findViewById(R.id.viewPager);
        adapter = new viewPagerAdapter(this);
        vista.setAdapter(adapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vista.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });// addOnTabSelectedListener
        vista.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });// registerOnPageChangeCallback

    }// on create

    public void aMenuFromTask(View v){
        Intent menu = new Intent(this, Menu.class);
        startActivity(menu);
    }
}// class