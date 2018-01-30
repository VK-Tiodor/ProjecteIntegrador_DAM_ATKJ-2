package dam.android.dependeciapp;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNombre;
    private EditText etApellido;
    private EditText etDNI;
    private EditText etPass;
    private EditText etFecha;
    private Spinner spGenero;
    private Spinner spTipoDependiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setUI();
    }

    private void setUI() {
        etNombre=(EditText)findViewById(R.id.etNombre);
        etApellido=(EditText)findViewById(R.id.etApellidos);
        etDNI=(EditText)findViewById(R.id.etDni);
        etPass=(EditText)findViewById(R.id.etPass);
        etFecha=(EditText)findViewById(R.id.etFecha);
        spGenero=(Spinner)findViewById(R.id.spGenero);
        spTipoDependiente=(Spinner)findViewById(R.id.spTipoDependiente);
        ArrayAdapter<CharSequence> adapterGenero = ArrayAdapter.createFromResource(this, R.array.genero,
                android.R.layout.simple_spinner_item);
        spGenero.setAdapter(adapterGenero);
        ArrayAdapter<CharSequence> adapterTipo = ArrayAdapter.createFromResource(this, R.array.tipo_dependiente,
                android.R.layout.simple_spinner_item);
        spTipoDependiente.setAdapter(adapterTipo);
    }


    public void openDatePicker(View v) {
        int yy;
        int mm;
        int dd;
        if (etFecha.getText().toString().equals("")) {
            final Calendar calendario = Calendar.getInstance();
            yy = calendario.get(Calendar.YEAR);
            mm = calendario.get(Calendar.MONTH);
            dd = calendario.get(Calendar.DAY_OF_MONTH);
        } else {
            String fecha = etFecha.getText().toString();
            String[] dateArray = fecha.split("-");

            yy = Integer.valueOf(dateArray[0]);
            mm = (Integer.valueOf(dateArray[1]) - 1);
            dd = (Integer.valueOf(dateArray[2]));
        }
        DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                String fecha = String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1)
                        + "-" + String.valueOf(dayOfMonth);
                etFecha.setText(fecha);
            }
        }, yy, mm, dd);
        datePicker.show();
    }


}
