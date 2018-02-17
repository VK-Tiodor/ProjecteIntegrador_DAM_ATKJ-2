package dam.android.dependeciapp.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import dam.android.dependeciapp.AsyncTasks.CreaRecordatorios;
import dam.android.dependeciapp.Controladores.Conexion;
import dam.android.dependeciapp.Controladores.RecordatorioAdapter;
import dam.android.dependeciapp.Pojo.Recordatorio;
import dam.android.dependeciapp.R;

@SuppressLint("ValidFragment")
public class RecordatorioFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private List<Recordatorio> recordatorioList;
    private Conexion con;
    private RecyclerView recyclerView;
    private FABToolbarLayout fabToolbar;
    private int idUsuario;


    public RecordatorioFragment() {

    }


    public static RecordatorioFragment newInstance( int id) {
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
        if(savedInstanceState!=null)
            idUsuario=savedInstanceState.getInt("userId");
        obtenListaSiPuedes();

    }

    private void obtenListaSiPuedes() {
        recordatorioList = new ArrayList<>();
        if (Conexion.isNetDisponible(getContext())) {
            con = new Conexion();
            if (con != null) {
                CreaRecordatorios cr = new CreaRecordatorios(con, recordatorioList, getContext());
                cr.execute(idUsuario);
            }
        }
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

            recyclerView.setAdapter(new RecordatorioAdapter(recordatorioList, context, fabToolbar));
            recyclerView.getAdapter().notifyDataSetChanged();
        }
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
        outState.putInt("userId",idUsuario);
    }
}
