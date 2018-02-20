package dam.android.dependeciapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

import java.util.concurrent.ExecutionException;

import dam.android.dependeciapp.AsyncTasks.CreaRecordatorios;
import dam.android.dependeciapp.Controladores.Conexion;
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
    private int idUsuario;


    public RecordatorioDetalleFragment() {
        // Required empty public constructor
    }

    public static RecordatorioDetalleFragment newInstance(Recordatorio recordatorio, RecordatorioAdapter adapter, int id) {
        RecordatorioDetalleFragment fragment = new RecordatorioDetalleFragment();
        fragment.setAdapter(adapter);
        fragment.setRecordatorio(recordatorio);
        fragment.setIdUsuario(id);
        return fragment;
    }

    public void setAdapter(RecordatorioAdapter adapter) {
        this.adapter = adapter;
    }
    public void setRecordatorio(Recordatorio recordatorio) {
        this.recordatorio = recordatorio;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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
        titulo = v.findViewById(R.id.tvTitulo);
        contenido = v.findViewById(R.id.tvContenido);
        cuando = v.findViewById(R.id.tvFecha);
        hora = v.findViewById(R.id.tvHora);
        fragment = this;
        ImageView terminar = (ImageView) getActivity().findViewById(R.id.terminado);
        terminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.getRecordatorioList().remove(recordatorio);
                DependenciaDBManager.RecordatoriosDBManager db = new DependenciaDBManager.RecordatoriosDBManager(getContext());
                db.delete(String.valueOf(recordatorio.id));
                //Si no hay ningun recordatorio en el List se cargan desde la base de datos
                CreaRecordatorios cr = null;
                if (adapter.getRecordatorioList().size() == 0) {
                    if (Conexion.isNetDisponible(getContext())) {
                        try {
                            Conexion con = new Conexion();
                            cr = new CreaRecordatorios(adapter.getRecordatorioList(), getContext(), con);
                            cr.execute(idUsuario);
                            Boolean b = cr.get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getContext(), R.string.no_carga_recordatorios, Toast.LENGTH_LONG).show();
                    }
                }
                getFragmentManager().beginTransaction().remove(fragment).commit();
                FABToolbarLayout fabToolbar = (FABToolbarLayout) getActivity().findViewById(R.id.fabtoolbar);
                fabToolbar.hide();
                adapter.notifyDataSetChanged();
            }
        });
        ImageView cerrar = (ImageView) getActivity().findViewById(R.id.cerrar);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FABToolbarLayout fabToolbar = (FABToolbarLayout) getActivity().findViewById(R.id.fabtoolbar);
                getFragmentManager().beginTransaction().remove(fragment).commit();
                fabToolbar.hide();

            }
        });
        titulo.setText(recordatorio.getTitulo());
        contenido.setText(recordatorio.getContent());
        String cuando = Recordatorio.obtrenCuando(getContext(), recordatorio);
        this.cuando.setText(cuando);
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
