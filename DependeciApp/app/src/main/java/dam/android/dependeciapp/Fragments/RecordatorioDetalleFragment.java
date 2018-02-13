package dam.android.dependeciapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import dam.android.dependeciapp.Controladores.RecordatorioAdapter;
import dam.android.dependeciapp.Pojo.Recordatorio;
import dam.android.dependeciapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecordatorioDetalleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecordatorioDetalleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordatorioDetalleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private TextView titulo;
    private TextView contenido;
    private TextView cuando;
    private TextView hora;
    private Recordatorio recordatorio;
    private RecordatorioAdapter adapter;
    private Button btTerminado;
    private Fragment fragment;

    private OnFragmentInteractionListener mListener;

    public RecordatorioDetalleFragment() {
        // Required empty public constructor
    }


    public static RecordatorioDetalleFragment newInstance(Recordatorio recordatorio,RecordatorioAdapter adapter) {
        RecordatorioDetalleFragment fragment = new RecordatorioDetalleFragment();
        fragment.setAdapter(adapter);
        fragment.setRecordatorio(recordatorio);
        return fragment;
    }
    public void setAdapter(RecordatorioAdapter adapter){
        this.adapter=adapter;

    }
    public void setRecordatorio(Recordatorio recordatorio){
        this.recordatorio=recordatorio;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recordatorio_detalle, container, false);
        titulo=v.findViewById(R.id.tvTitulo);
        contenido=v.findViewById(R.id.tvContenido);
        cuando=v.findViewById(R.id.tvCuando);
        hora=v.findViewById(R.id.tvHora);
        btTerminado=(Button)v.findViewById(R.id.btTerminado);
        fragment=this;
        btTerminado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.getRecordatorioList().remove(recordatorio);
                adapter.notifyDataSetChanged();
                getFragmentManager().beginTransaction().remove(fragment).commit();
            }
        });
        titulo.setText(recordatorio.getTitulo());
        contenido.setText(recordatorio.getContent());
        cuando.setText(recordatorio.getCuando());
        hora.setText(recordatorio.getHora());
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
           // throw new RuntimeException(context.toString()
            //        + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
