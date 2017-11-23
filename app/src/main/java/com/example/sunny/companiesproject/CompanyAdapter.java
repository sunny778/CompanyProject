package com.example.sunny.companiesproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sunny on 22/11/2017.
 */

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyHolder> {

    private Context context;
    private ArrayList<Company> companies;
    private SharedPreferences sp;

    public CompanyAdapter(Context context, ArrayList<Company> companies) {
        this.context = context;
        this.companies = companies;
    }

    public void setCompanies(ArrayList<Company> companies) {
        this.companies = companies;
        notifyDataSetChanged();
    }

    @Override
    public CompanyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.company_style, parent, false);

        sp = context.getSharedPreferences(context.getString(R.string.sp_company), Context.MODE_PRIVATE);
        return new CompanyHolder(view);
    }

    @Override
    public void onBindViewHolder(CompanyHolder holder, int position) {
        holder.bind(companies.get(position));
    }

    @Override
    public int getItemCount() {
        if (companies != null){
            return companies.size();
        }
        return 0;
    }

    public class CompanyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textName;
        private TextView textAddress;
        private ImageView imageView;

        private Company company;

        public CompanyHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            textName = (TextView) itemView.findViewById(R.id.textName);
            textAddress = (TextView) itemView.findViewById(R.id.textAddress);
            imageView = (ImageView) itemView.findViewById(R.id.imageView1);
        }

        public void bind(Company company) {

            this.company = company;
//            try {
//                Picasso
//                        .with(context)
//                        .load("")
//                        .resize(80, 80)
//                        .centerCrop()
//                        .into(imageView);
//
//            }catch (IllegalArgumentException ex) {
//                Log.d("image", ex.getMessage());
//            }

            try {

                int id = context.getResources().getIdentifier(company.getImage(), "drawable", context.getPackageName());
                Drawable drawable = context.getResources().getDrawable(id);
                imageView.setImageDrawable(drawable);
            }catch (Exception e){
                e.getMessage();
            }

            textName.setText(company.getName());
            textAddress.setText(company.getAddress());


//            Toast.makeText(context, company.getName() + " - " + company.getId() + "", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onClick(View view) {

            ((MainActivity) context).moveToCompanyFragment();
            sp.edit()
                    .putLong("company_id", company.getId())
                    .apply();
        }
    }
}
