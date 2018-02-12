package dam.android.dependeciapp.Controladores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dam.android.dependeciapp.Fragments.RecordatorioFragment;
import dam.android.dependeciapp.Pojo.Recordatorio;
import dam.android.dependeciapp.R;

import java.util.List;


public class RecordatorioAdapter extends RecyclerView.Adapter<RecordatorioAdapter.ViewHolder> {

    private final List<Recordatorio> recordatorioList;
    private final RecordatorioFragment.OnListFragmentInteractionListener mListener;

    public RecordatorioAdapter(List<Recordatorio> items, RecordatorioFragment.OnListFragmentInteractionListener listener) {
        recordatorioList = items;
        mListener = listener;
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

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    //TODO Hacer que se abra un activity con los datos
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                   // mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return recordatorioList.size();
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
           tvHora=(TextView) view.findViewById(R.id.tvHora);
           tvCuando=(TextView)view.findViewById(R.id.tvCuando);
        }


    }
}
