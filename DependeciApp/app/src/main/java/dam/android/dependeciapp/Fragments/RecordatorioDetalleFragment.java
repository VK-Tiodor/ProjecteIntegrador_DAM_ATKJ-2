package dam.android.dependeciapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

import dam.android.dependeciapp.Controladores.RecordatorioAdapter;
import dam.android.dependeciapp.Controladores.SQLite.DependenciaDBManager;
import dam.android.dependeciapp.Pojo.Recordatorio;
import dam.android.dependeciapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link RecordatorioDetalleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordatorioDetalleFragment extends Fragment {

    private TextView titulo;
    private TextView contenido;
    private TextView cuando;
    private TextView hora;
    private Recordatorio recordatorio;
    private RecordatorioAdapter adapter;
    private Fragment fragment;
    private Context context;



    public RecordatorioDetalleFragment() {
        // Required empty public constructor
    }



    public static RecordatorioDetalleFragment newInstance(Recordatorio recordatorio, RecordatorioAdapter adapter) {
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
        cuando=v.findViewById(R.id.tvFecha);
        hora=v.findViewById(R.id.tvHora);
        fragment=this;
        ImageView terminar=(ImageView)getActivity().findViewById(R.id.terminado);
        terminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.getRecordatorioList().remove(recordatorio);
                adapter.notifyDataSetChanged();
                DependenciaDBManager.RecordatoriosDBManager db = new DependenciaDBManager.RecordatoriosDBManager(getContext());
                db.delete(String.valueOf(recordatorio.id));

                getFragmentManager().beginTransaction().remove(fragment).commit();
                FABToolbarLayout fabToolbar=(FABToolbarLayout) getActivity().findViewById(R.id.fabtoolbar);
                fabToolbar.hide();

            }
        });
        ImageView cerrar=(ImageView)getActivity().findViewById(R.id.cerrar);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FABToolbarLayout fabToolbar=(FABToolbarLayout) getActivity().findViewById(R.id.fabtoolbar);
                getFragmentManager().beginTransaction().remove(fragment).commit();
                fabToolbar.hide();

            }
        });


        titulo.setText(recordatorio.getTitulo());
        contenido.setText(recordatorio.getContent());
        cuando.setText(recordatorio.getCuando());
        hora.setText(recordatorio.getHora());
        return v;
    }


    @Override
    public void onDetach() {
        super.onDetach();
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

}
