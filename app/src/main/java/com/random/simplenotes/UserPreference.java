package com.random.simplenotes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserPreference.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserPreference#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserPreference extends PreferenceFragment {

    @Override
    public void addPreferencesFromResource(int preferencesResId) {
        super.addPreferencesFromResource(preferencesResId);
        
    }
}
