package com.example.sunny.companiesproject;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyFragment extends Fragment {

    private TextView textName;
    private TextView textAddress;
    private ImageView imageView;

    private SharedPreferences sp;

    public CompanyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_company, container, false);

        textName = (TextView) root.findViewById(R.id.textName);
        textAddress = (TextView) root.findViewById(R.id.textAddress);
        imageView = (ImageView) root.findViewById(R.id.imageView1);

        sp = getActivity().getSharedPreferences("company", Context.MODE_PRIVATE);

        getDetails();

        return root;
    }

    private void getDetails() {

        String name = "";
        String address = "";
        long id = sp.getLong("company_id", 0);

        Cursor cursor = getActivity().getContentResolver().query(CompanyProvider.CONTENT_URI, null,
                CompanyDBHelper.COL_ID + "=" + id, null, null );

        while (cursor.moveToNext()){
            name = cursor.getString(cursor.getColumnIndex(CompanyDBHelper.COL_NAME));
            address = cursor.getString(cursor.getColumnIndex(CompanyDBHelper.COL_ADDRESS));
        }

        textName.setText(name);
        textAddress.setText(address);


//            Picasso
//                    .with(getContext())
//                    .load(company.getImage())
//                    .resize(300, 300)
//                    .into(imageView);
    }

}
