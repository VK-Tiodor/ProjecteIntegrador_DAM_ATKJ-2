package dam.android.dependeciapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import dam.android.dependeciapp.Controladores.RecordatorioAdapter;
import dam.android.dependeciapp.Pojo.Recordatorio;
import dam.android.dependeciapp.R;
import dam.android.dependeciapp.Controladores.RecordatorioContent;


public class RecordatorioFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private OnListFragmentInteractionListener mListener;
    private Context context;
    private Menu appBarMenu;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecordatorioFragment() {
    }


    public static RecordatorioFragment newInstance(Context con,Menu appBarMenu) {
        RecordatorioFragment fragment = new RecordatorioFragment();
        fragment.SetContext(con);
        fragment.setAppBarMenu(appBarMenu);
        return fragment;
    }
    public void SetContext(Context con){
        this.context=con;
    }
    public void setAppBarMenu(Menu menu){
        appBarMenu=menu;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recordatorio_list, container, false);
        //appBarMenu.findItem(R.id.action_close_fragment).setVisible(true);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            //Segun el numero de columnas se pone el linear o el grid
                recyclerView.setLayoutManager(new LinearLayoutManager(context));

            recyclerView.setAdapter(new RecordatorioAdapter(RecordatorioContent.ITEMS,context,appBarMenu));
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
