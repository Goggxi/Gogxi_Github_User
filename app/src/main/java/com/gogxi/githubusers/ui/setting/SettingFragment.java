package com.gogxi.githubusers.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.gogxi.githubusers.R;
import com.gogxi.githubusers.ui.home.HomeActivity;
import com.gogxi.githubusers.ui.reminder.DailyReminderReceiver;

import java.util.Objects;

public class SettingFragment extends Fragment implements View.OnClickListener{
    public static String DAILY;
    private static String SETTING_PREFS = "";
    private DailyReminderReceiver mDailyReminderReceiver;
    private Switch mSwitchDaily;
    private boolean dailyCheck = false;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
        Objects.requireNonNull(((HomeActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(R.string.setting);
        Objects.requireNonNull(((HomeActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ImageButton mImageButton = view.findViewById(R.id.btn_language);
        mSwitchDaily = view.findViewById(R.id.switch_daily);

        mDailyReminderReceiver = new DailyReminderReceiver();

        getPreferences();

        mSwitchDaily.setOnCheckedChangeListener((buttonView, dailyIsChecked) -> {
            dailyCheck = dailyIsChecked;
            setPreferences();
            if (dailyIsChecked) {
                mDailyReminderReceiver.dailyReminderOn(getActivity());
                Toast.makeText(getActivity(), "Daily Reminder is Active", Toast.LENGTH_LONG).show();
            } else {
                mDailyReminderReceiver.dailyReminderOff(getActivity());
                Toast.makeText(getActivity(), "Daily Reminder is Non Active", Toast.LENGTH_LONG).show();
            }
        });

        mImageButton.setOnClickListener(this);
    }

    private void setPreferences() {
        SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(SETTING_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(DAILY, dailyCheck);
        editor.apply();
    }

    private void getPreferences() {
        SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(SETTING_PREFS, Context.MODE_PRIVATE);
        mSwitchDaily.setChecked(sharedPreferences.getBoolean(DAILY, false));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_language) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
    }
}
