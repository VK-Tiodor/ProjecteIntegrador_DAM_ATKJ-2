package dam.android.dependeciapp.Controladores;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dam.android.dependeciapp.Fragments.RecordatorioDetalleFragment;
import dam.android.dependeciapp.Fragments.RecordatorioFragment;
import dam.android.dependeciapp.Pojo.Recordatorio;
import dam.android.dependeciapp.R;

import java.util.List;


public class RecordatorioAdapter extends RecyclerView.Adapter<RecordatorioAdapter.ViewHolder> {

    private final List<Recordatorio> recordatorioList;
    private Context context;
    private RecordatorioAdapter adapter;
    private Menu appBarMenu;


    public RecordatorioAdapter(List<Recordatorio> items, Context con, Menu menu) {
        recordatorioList = items;
        context = con;
        appBarMenu = menu;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recordatorio_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = recordatorioList.get(position);
        holder.tvTitulo.setText(recordatorioList.get(position).titulo);
        holder.tvContenido.setText(recordatorioList.get(position).content);
        holder.tvHora.setText(recordatorioList.get(position).hora);
        holder.tvCuando.setText(recordatorioList.get(position).cuando);
        adapter = this;
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Creamos una nueva instancia del Fragment de Detalle
                    RecordatorioDetalleFragment fragmentDetalle = RecordatorioDetalleFragment.newInstance(holder.mItem, adapter, appBarMenu);
                    //Obtenemos la lista de fragments activos
                    List<Fragment> listFragment = ((FragmentActivity) context).getSupportFragmentManager().getFragments();
                    //Seleccionamos el fragment que sea RecordatorioDetalleFragment y lo eliminamos
                    for (Fragment fragment : listFragment) {
                        if (fragment instanceof RecordatorioDetalleFragment)
                            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    }
                    //Introducimos el Fragment Detalle en el FrameLayout correspondiente
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, fragmentDetalle).addToBackStack(null).commit();
                    //Al hacerlo, hacemos visible el menu
                    appBarMenu.findItem(R.id.action_close_fragment).setVisible(true);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return recordatorioList.size();
    }

    public List<Recordatorio> getRecordatorioList() {
        return recordatorioList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvTitulo;
        public final TextView tvContenido;
        public final TextView tvHora;
        public final TextView tvCuando;
        public Recordatorio mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvTitulo = (TextView) view.findViewById(R.id.tvTitulo);
            tvContenido = (TextView) view.findViewById(R.id.tvContenido);
            tvHora = (TextView) view.findViewById(R.id.tvHora);
            tvCuando = (TextView) view.findViewById(R.id.tvCuando);
        }

    }
}
