package dam.android.dependeciapp.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import dam.android.dependeciapp.AsyncTasks.LanzaLlamada;
import dam.android.dependeciapp.Controladores.Conexion;
import dam.android.dependeciapp.Pojo.Usuario;
import dam.android.dependeciapp.R;

/**
 * Created by adria on 12/02/2018.
 */

@SuppressLint("ValidFragment")
public class BotonFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    @SuppressLint("ValidFragment")
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