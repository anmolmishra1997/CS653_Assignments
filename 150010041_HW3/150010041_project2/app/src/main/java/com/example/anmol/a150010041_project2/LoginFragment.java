package com.example.anmol.a150010041_project2;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        Button b = (Button) v.findViewById(R.id.login_submit);
        b.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                EditText login_first_name = getActivity().findViewById(R.id.first_name);
                String first_name = login_first_name.getText().toString();
                if (first_name.matches("")) {
                    Toast.makeText(getActivity(), "You did not enter first name", Toast.LENGTH_SHORT).show();
                    return;
                }

                EditText login_last_name = getActivity().findViewById(R.id.last_name);
                String last_name = login_last_name.getText().toString();
                if (last_name.matches("")) {
                    Toast.makeText(getActivity(), "You did not enter last name", Toast.LENGTH_SHORT).show();
                    return;
                }

                EditText login_email = getActivity().findViewById(R.id.email);
                String email = login_email.getText().toString();
                if (email.matches("")) {
                    Toast.makeText(getActivity(), "You did not enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                EditText login_mobile = getActivity().findViewById(R.id.mobile);
                String mobile = login_mobile.getText().toString();
                if (mobile.matches("")) {
                    Toast.makeText(getActivity(), "You did not enter mobile", Toast.LENGTH_SHORT).show();
                    return;
                }

                EditText login_date_of_birth = getActivity().findViewById(R.id.date_of_birth);
                String date_of_birth = login_date_of_birth.getText().toString();
                if (date_of_birth.matches("")) {
                    Toast.makeText(getActivity(), "You did not enter age", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioGroup login_gender = getActivity().findViewById(R.id.gender);
                int gender_id = login_gender.indexOfChild(getActivity().findViewById(login_gender.getCheckedRadioButtonId()));
                if (gender_id == -1) {
                    Toast.makeText(getActivity(), "You did not choose gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton chosen_gender = (RadioButton)  login_gender.getChildAt(gender_id);
                String gender = chosen_gender.getText().toString();

                Toast.makeText(getActivity(), "Registration Successful", Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.login), "yes");
                editor.putString(getString(R.string.first_name), first_name);
                editor.putString(getString(R.string.last_name), last_name);
                editor.putString(getString(R.string.email), email);
                editor.putString(getString(R.string.mobile), mobile);
                editor.putString(getString(R.string.date_of_birth), date_of_birth);
                editor.putString(getString(R.string.gender), gender);
                editor.apply();
            }
        });
        return v;
    }

}
