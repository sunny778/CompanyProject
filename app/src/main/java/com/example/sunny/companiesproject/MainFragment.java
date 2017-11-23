package com.example.sunny.companiesproject;


import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private RecyclerView companyList;
    private CompanyAdapter adapter;
    private ArrayList<Company> companies;
    private FragmentManager fragmentManager;
    private String jsonString;
    private SharedPreferences sp;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        fragmentManager = getActivity().getSupportFragmentManager();
        companyList = (RecyclerView) root.findViewById(R.id.companiesList);

        companies = new ArrayList<>();
        sp = getActivity().getSharedPreferences(getString(R.string.sp_company), Context.MODE_PRIVATE);

        companyList.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new CompanyAdapter(getContext(), companies);
        companyList.setAdapter(adapter);

        Boolean isDeleted = sp.getBoolean(getString(R.string.sp_is_deleted), false);

        boolean isFirsRun = sp.getBoolean(getString(R.string.sp_first_run), true);

        if (isFirsRun){
            InputStream is = getResources().openRawResource(R.raw.json_text);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "windows-1255"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            String jsonString = writer.toString();

            readJson(jsonString);

            sp.edit()
                    .putBoolean(getString(R.string.sp_first_run), false)
                    .apply();
        }else {
            getDBInfo();
        }

//        JsonReceiver jsonReceiver = new JsonReceiver();
//        IntentFilter filter = new IntentFilter(CompanyService.ACTION_JSON);
//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(jsonReceiver, filter);

//        try{
//            getDBInfo();
//
//        }catch (NullPointerException ex){
//            Log.d("MainFragment", ex.getMessage());
//        }


//        File jsonFile = new File(Environment.getExternalStorageDirectory(), getString(R.raw.json));
//        try {
//            FileInputStream stream = new FileInputStream(jsonFile);
//            jsonString = null;
//
//            FileChannel fc = stream.getChannel();
//            MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
//
//            jsonString = Charset.defaultCharset().decode(buffer).toString();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        Intent intent = new Intent(getContext(), CompanyService.class);
//        intent.putExtra("url", "http://www.comax.co.il/json_companies.txt");
//        getActivity().startService(intent);

        return root;
    }

    private void readJson(String jsonString) {

//        try {
//            JSONObject jsonObject = new JSONObject(jsonString);
//            JSONArray companiesArr = jsonObject.getJSONArray("companies");
//            JSONObject companyObj = companiesArr.getJSONObject(1);
//            JSONObject comax = companyObj.getJSONObject("מייקרוסופט");
//            String city = comax.getString("city");
//
//            Toast.makeText(getContext(), city + "", Toast.LENGTH_SHORT).show();
//
//        } catch (JSONException e) {
//            Log.d("" , e.getMessage());
//        }


        JSONObject jsonObject = null;
        try {

            jsonObject = new JSONObject(jsonString);
            JSONArray companiesArr = jsonObject.getJSONArray("companies");

            for (int i = 0; i < companiesArr.length(); i++) {


                JSONObject companyObj = companiesArr.getJSONObject(i);
                String name = companyObj.getString("name");
                String address = companyObj.getString("address");

                Company company = new Company(name, address, "");
                insertIntoDB(company);
                getDBInfo();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void getDBInfo() {
        companies.clear();
        Cursor cursor;

        cursor = getActivity().getContentResolver().query(CompanyProvider.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()){

            long id = cursor.getLong(cursor.getColumnIndex(CompanyDBHelper.COL_ID));
            String name = cursor.getString(cursor.getColumnIndex(CompanyDBHelper.COL_NAME));
            String address = cursor.getString(cursor.getColumnIndex(CompanyDBHelper.COL_ADDRESS));
            String image = cursor.getString(cursor.getColumnIndex(CompanyDBHelper.COL_IMAGE));

//            Toast.makeText(getContext(), name + "" + id + "", Toast.LENGTH_SHORT).show();
            companies.add(new Company(id, name, address, image));
        }

        adapter.setCompanies(companies);
    }

    private void insertIntoDB(Company company) {

        ContentValues values = new ContentValues();
        values.put(CompanyDBHelper.COL_NAME, company.getName());
        values.put(CompanyDBHelper.COL_ADDRESS, company.getAddress());
        values.put(CompanyDBHelper.COL_IMAGE, company.getImage());

        getActivity().getContentResolver().insert(CompanyProvider.CONTENT_URI, values);

    }

//    private class JsonReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            String json = intent.getStringExtra("json");
//
//            try {
//                JSONArray jsonArray = new JSONArray(json);
//
////                for (int i = 0; i < jsonArray.length(); i++) {
////
////                    JSONObject jsonObject = jsonArray.getJSONObject(0);
////                    JSONObject comaxObj = jsonObject.getJSONObject("קומקס");
////                    String address = comaxObj.getString("address");
////                }
////
////                companies.add(new Company("comax", address, ""));
////
////                Toast.makeText(context, "hh", Toast.LENGTH_SHORT).show();
//
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            adapter.setCompanies(companies);
//            companyList.setAdapter(adapter);
//        }
//    }
}
