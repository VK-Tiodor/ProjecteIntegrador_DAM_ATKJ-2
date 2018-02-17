package dam.android.dependeciapp.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import dam.android.dependeciapp.AsyncTasks.CreaRecordatorios;
import dam.android.dependeciapp.Controladores.Conexion;
import dam.android.dependeciapp.Controladores.RecordatorioAdapter;
import dam.android.dependeciapp.Pojo.Recordatorio;
import dam.android.dependeciapp.R;

public class RecordatorioFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private Context context;
    private List<Recordatorio> recordatorioList;
    private Conexion con;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecordatorioFragment() {
    }


    public static RecordatorioFragment newInstance(Context con) {
        RecordatorioFragment fragment = new RecordatorioFragment();
        fragment.SetContext(con);


        return fragment;
    }


    public void SetContext(Context con) {
        this.context = con;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Conexion.isNetDisponible(getContext())) {
            con = new Conexion();
        }

        recordatorioList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recordatorio_list, container, false);
        //appBarMenu.findItem(R.id.action_close_fragment).setVisible(true);
        FABToolbarLayout fabToolbar = (FABToolbarLayout) getActivity().findViewById(R.id.fabtoolbar);
        //setFabToolbar(fabToolbar);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            //Le ponemos un gestor lineal
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            if (con != null) {
                CreaRecordatorios cr = new CreaRecordatorios(con, recordatorioList,context);
                cr.execute();
            }
            recyclerView.addItemDecoration(new DividerItemDecoration(context,LinearLayoutManager.VERTICAL));
           // recyclerView.setItemAnimator(new SlideInDownAnimator());
           // recyclerView.setItemAnimator(new SlideInRightAnimator());
            //recyclerView.setItemAnimator(new SlideInLeftAnimator());
           // recyclerView.setItemAnimator(new SlideInUpAnimator());
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
        // TODO: Hacer lo que toca
        void onListFragmentInteraction(Recordatorio item);
    }


}
