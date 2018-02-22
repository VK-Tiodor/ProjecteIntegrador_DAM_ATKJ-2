package dam.android.dependeciapp.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import dam.android.dependeciapp.AsyncTasks.CargaRecordatorios;
import dam.android.dependeciapp.Controladores.Conexion;
import dam.android.dependeciapp.Controladores.RecordatorioAdapter;
import dam.android.dependeciapp.Controladores.SQLite.DependenciaDBManager;
import dam.android.dependeciapp.Pojo.Recordatorio;
import dam.android.dependeciapp.R;

@SuppressLint("ValidFragment")
public class RecordatorioFragment extends Fragment implements Comparator<Recordatorio> {

    private OnListFragmentInteractionListener mListener;
    private List<Recordatorio> recordatorioList;
    private RecyclerView recyclerView;
    private FABToolbarLayout fabToolbar;
    private int idUsuario;
    private Conexion conexion;

    public RecordatorioFragment() {
    }

    public Conexion getConexion() {
        return conexion;
    }

    public static RecordatorioFragment newInstance(int id) {
        RecordatorioFragment fragment = new RecordatorioFragment();
        fragment.SetIdUsuario(id);

        return fragment;
    }

    public void SetIdUsuario(int id) {
        idUsuario = id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conexion = new Conexion();
        recordatorioList = new ArrayList<>();

        if (savedInstanceState != null)
            idUsuario = savedInstanceState.getInt("userId");
        //obtenListaRecordatorios();
    }

    private void obtenListaRecordatorios() {
        recordatorioList = new ArrayList<>();
        DependenciaDBManager.RecordatoriosDBManager db = new DependenciaDBManager.RecordatoriosDBManager(getContext());
        db.createTableIfNotExist();
        Cursor cursor = db.getRows();
        if (cursor != null) {
            obtenListaLocal(cursor);
        } else
            obtenListaOnline();
    }

    private void obtenListaLocal(Cursor cursor) {
        int cuenta = cursor.getCount();
        if (cuenta > 0) {
            cursor.moveToFirst();
            do {
                Date fecha = new Date(cursor.getString(3));
                Recordatorio r = new Recordatorio(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(4), fecha);
                recordatorioList.add(r);
            } while (cursor.moveToNext());
            Collections.sort(recordatorioList, this);
            cursor.close();
           // recyclerView.getAdapter().notifyDataSetChanged();

        } else
            obtenListaOnline();
    }

    private void obtenListaOnline() {
        if (Conexion.isNetDisponible(getContext(), true)) {
            CargaRecordatorios cr = new CargaRecordatorios(recordatorioList, getContext(), conexion, idUsuario);
            cr.execute(idUsuario);
            try {
                cr.get();
                if(recyclerView!=null)
                if(recyclerView.getAdapter()!=null)
                    recyclerView.getAdapter().notifyDataSetChanged();
                cr = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getContext(), R.string.no_carga_recordatorios, Toast.LENGTH_LONG).show();
        }
    }

    public void refrescaAvisos() {
        DependenciaDBManager.RecordatoriosDBManager db = new DependenciaDBManager.RecordatoriosDBManager(getContext());
        db.vaciaTabla();
        recordatorioList.clear();
        //recyclerView.getAdapter().notifyDataSetChanged();
        CargaRecordatorios cr = new CargaRecordatorios(recordatorioList, getContext(), conexion, idUsuario);
        cr.execute(idUsuario);
        try {
            cr.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recordatorio_list, container, false);
        //appBarMenu.findItem(R.id.action_close_fragment).setVisible(true);
        fabToolbar = (FABToolbarLayout) getActivity().findViewById(R.id.fabtoolbar);
        //setFabToolbar(fabToolbar);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            //Le ponemos un gestor lineal
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            //ordenamos la lsita con un comparador de fechas
            //Lo hice con Collections en vez de con recordatorioList.sort(this);
            //Porque esta ultima, no esta disponible en versiones menorea a la API 24
            recyclerView.setAdapter(new RecordatorioAdapter(recordatorioList, context, fabToolbar, idUsuario, conexion));
            //obtenListaRecordatorios();
            recyclerView.getAdapter().notifyDataSetChanged();

        }
        refrescaAvisos();

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Recordatorio item);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("userId", idUsuario);
    }

    @Override
    public int compare(Recordatorio o1, Recordatorio o2) {
        if (o1.getFecha().before(o2.getFecha())) {
            return -1;
        } else if (o1.getFecha().after(o2.getFecha())) {
            return 1;
        } else {
            return 0;
        }
    }
}
