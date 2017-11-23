package com.example.sunny.companiesproject;


import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddCompanyFragment extends Fragment implements View.OnClickListener {

    private EditText editName;
    private EditText editAddress;
    private EditText editImage;
    private FragmentManager manager;


    public AddCompanyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_company, container, false);

        manager = getActivity().getSupportFragmentManager();
        editName = (EditText) root.findViewById(R.id.editName);
        editAddress = (EditText) root.findViewById(R.id.editAddress);
        editImage = (EditText) root.findViewById(R.id.editImage);

        root.findViewById(R.id.buttonSave).setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.buttonSave:

                if (editName.getText().toString().isEmpty() || editName.getText().toString().isEmpty() ||
                        editImage.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Sorry you have to fill the all details", Toast.LENGTH_SHORT).show();
                } else {
                    Company company = new Company(editName.getText().toString(), editAddress.getText().toString(),
                            editImage.getText().toString());

                    insertIntoDB(company);

                    Toast.makeText(getContext(), editName.getText().toString() + " saved", Toast.LENGTH_SHORT).show();
                    manager.beginTransaction()
                            .replace(R.id.container, new MainFragment())
                            .commit();
                }
                break;
        }
    }

    private void insertIntoDB(Company company) {

        ContentValues values = new ContentValues();
        values.put(CompanyDBHelper.COL_NAME, company.getName());
        values.put(CompanyDBHelper.COL_ADDRESS, company.getAddress());
        values.put(CompanyDBHelper.COL_IMAGE, company.getImage());

        getActivity().getContentResolver().insert(CompanyProvider.CONTENT_URI, values);

    }
}
