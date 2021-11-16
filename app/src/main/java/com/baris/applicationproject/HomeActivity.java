package com.baris.applicationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baris.applicationproject.ui.form.CreateFormFragment;
import com.baris.applicationproject.ui.list.ListFormFragment;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.fragmentHome)
    LinearLayout fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        setTitle("Anasayfa");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,R.string.Open, R.string.Close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        openGallery();
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


    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        String TAG = "";
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_item_createlist) {
            fragment = new CreateFormFragment();
            TAG = "createFragment";
        } else if (id == R.id.menu_item_showlist) {
            fragment = new ListFormFragment();
            TAG = "listFragment";
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragmentHome,fragment,TAG);

        if(TAG.equals("createFragment")) {
            fragmentTransaction.addToBackStack("formFragment");
        }else {
            fragmentTransaction.addToBackStack(null);
            //fragmentManager.popBackStackImmediate("createFragment",0);
            fragmentManager.popBackStack();
        }
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openGallery() {

        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if(EasyPermissions.hasPermissions(getApplicationContext(), perms)) {

            Toast.makeText(getApplicationContext(),"izin alındı",Toast.LENGTH_LONG).show();

        }else {

            EasyPermissions.requestPermissions(
                    this, // Bulunduğunuz Activity veya Fragment
                    "Devam etmek için izin vermelisiniz.", // İstek için
// bilgilendirme mesajı
                    0, // Bizim belirlediğimiz Int tipinde bir istek kodu
                    Manifest.permission.READ_EXTERNAL_STORAGE);// İzin almak istediğimiz yer
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }
}