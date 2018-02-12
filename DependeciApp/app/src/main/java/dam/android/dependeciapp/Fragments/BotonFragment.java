package dam.android.dependeciapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dam.android.dependeciapp.R;

/**
 * Created by adria on 12/02/2018.
 */

public class BotonFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public BotonFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = null;
        rootView = inflater.inflate(R.layout.fragment_boton, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format));
        return rootView;
    }
}