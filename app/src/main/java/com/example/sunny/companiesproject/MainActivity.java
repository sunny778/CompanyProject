package com.example.sunny.companiesproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        sp = getSharedPreferences(getString(R.string.sp_company), MODE_PRIVATE);

        fragmentManager.beginTransaction()
                .add(R.id.container, new MainFragment())
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.itemBack:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new MainFragment())
                        .commit();
                break;

            case R.id.itemAddCompany:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new AddCompanyFragment())
                        .addToBackStack("")
                        .commit();
                break;

            case R.id.itemDeleteAll:
                deleteAllDB();
                break;

            case R.id.itemExit:
                Intent intent1 = new Intent(Intent.ACTION_MAIN);
                intent1.addCategory(Intent.CATEGORY_HOME);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllDB() {
        getContentResolver().delete(CompanyProvider.CONTENT_URI, null, null);
        sp.edit()
                .putBoolean(getString(R.string.sp_is_deleted), true)
                .apply();

        fragmentManager.beginTransaction()
                .replace(R.id.container, new MainFragment())
                .commit();
    }

    protected void moveToCompanyFragment(){

        fragmentManager.beginTransaction()
                .replace(R.id.container, new CompanyFragment())
                .addToBackStack("")
                .commit();

    }

}

