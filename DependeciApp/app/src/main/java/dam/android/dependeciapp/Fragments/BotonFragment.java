package dam.android.dependeciapp.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import dam.android.dependeciapp.R;

/**
 * Created by adria on 12/02/2018.
 */

@SuppressLint("ValidFragment")
public class BotonFragment extends Fragment {


    @SuppressLint("ValidFragment")
    public BotonFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = null;
        rootView = inflater.inflate(R.layout.fragment_boton, container, false);
        FrameLayout mapFrame = getActivity().findViewById(R.id.frameMap);
        if (mapFrame != null) {


            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format));
        }

        return rootView;
    }


}