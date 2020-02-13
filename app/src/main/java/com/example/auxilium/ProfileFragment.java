package com.example.auxilium;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private TextView username, email;
    private ImageView avatar;
    private Button btn_sign_out, btn_help, btn_del_acc;

    private FirebaseAuth f_auth;
    private FirebaseUser f_user;

    public ProfileFragment() {
        f_auth = FirebaseAuth.getInstance();
        f_user = f_auth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        // User Information
        avatar = v.findViewById(R.id.profile_avatar);
        username = v.findViewById(R.id.profile_username);
        email = v.findViewById(R.id.profile_email);

        // Buttons With Associated Functions
        btn_sign_out = v.findViewById(R.id.profile_btn_sign_out);
        btn_help = v.findViewById(R.id.profile_btn_help);
        btn_del_acc = v.findViewById(R.id.profile_btn_del_acc);

        // Setting User Information in Profile Fragment
        avatar.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_default_avatar));
        username.setText(f_user.getDisplayName());
        email.setText(f_user.getEmail());

        // Setting User Avatar
        Uri photoUrl = f_user.getPhotoUrl();
        if (photoUrl == null) {
            Toast.makeText(getActivity(), "No Profile Photo Available.", Toast.LENGTH_LONG).show();
        } else {
            String photo = photoUrl.toString();
            Glide.with(getActivity()).load(photo).into(avatar);
        }

        // Sign Out Function
        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f_auth.signOut();
                Intent i = new Intent(getActivity(), SignInActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        // Help Function
        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), HelpActivity.class);
                startActivity(i);
            }
        });

        // Delete Account Function
        btn_del_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f_auth.signOut();
                f_user.delete();
                Intent i = new Intent(getActivity(), SignInActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        return v;
    }
}
