package dam.android.dependeciapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

import java.util.List;

import dam.android.dependeciapp.AsyncTasks.LanzaLlamada;
import dam.android.dependeciapp.Controladores.Conexion;
import dam.android.dependeciapp.Controladores.SQLite.DependenciaDBManager;
import dam.android.dependeciapp.Fragments.BotonFragment;
import dam.android.dependeciapp.Fragments.MapFragment;
import dam.android.dependeciapp.Fragments.RecordatorioDetalleFragment;
import dam.android.dependeciapp.Fragments.RecordatorioFragment;
import dam.android.dependeciapp.Pojo.Usuario;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private FloatingActionButton fab;
    private FloatingActionButton fabGigante;
    private Usuario user;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private FABToolbarLayout fabToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        if(i!=null)
        user = (Usuario) i.getSerializableExtra("user");
        setUI();
    }

    private void setUI() {
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fabToolbar = (FABToolbarLayout) findViewById(R.id.fabtoolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null && Conexion.isNetDisponible(getApplicationContext())) {
                    LanzaLlamada llamada = new LanzaLlamada(view, getApplicationContext());
                    llamada.execute(user.getIdPersona() + "");
                } else {
                    Toast.makeText(getApplicationContext(), R.string.no_conexion_aviso, Toast.LENGTH_SHORT).show();
                }
            }
        });
        fabGigante = (FloatingActionButton) findViewById(R.id.fab_gigante);
        fabGigante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null && Conexion.isNetDisponible(getApplicationContext())) {
                    LanzaLlamada llamada = new LanzaLlamada(view, getApplicationContext());
                    llamada.execute(user.getIdPersona() + "");
                } else {
                    Toast.makeText(getApplicationContext(), R.string.no_conexion_aviso, Toast.LENGTH_SHORT).show();
                }
            }
        });
        fabGigante.hide();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView tvNombre = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvNombre);
        tvNombre.setText(user.getNombre() + " " + user.getApellidos());
        mViewPager = (ViewPager) findViewById(R.id.tabsContainer);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        //Se ejecuta cada vez que cambiamos de tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int posicion = tab.getPosition();
                switch (posicion) {
                    case 1:
                        fab.show();
                        break;
                    case 2:
                        //Hacemos el FAB mas grande
                        View screenView = findViewById(R.id.drawer_layout);
                        float centrex = screenView.getWidth() / 3;
                        float centreY = screenView.getHeight() / 3;
                        fab.animate().scaleX(4).scaleY(4).translationX(-centrex).translationY(-centreY + 50).setDuration(200);
                        fabGigante.show();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int posicion = tab.getPosition();
                switch (posicion) {
                    case 0:
                        cierraRecordatorioDetalle();
                        appBarLayout.setExpanded(true);
                        break;
                    case 2:
                        fabGigante.hide();
                        fab.show();
                        fab.animate().scaleX(1).scaleY(1).translationX(0).translationY(0).setDuration(500);


                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

    }

    //Metodo para cerrar el RecordatorioDetalleFragment que pueda haber abierto
    private void cierraRecordatorioDetalle() {
        List<Fragment> listFragment = getSupportFragmentManager().getFragments();
        //Seleccionamos el fragment que sea RecordatorioDetalleFragment y lo eliminamos
        for (Fragment fragment : listFragment) {
            if (fragment instanceof RecordatorioDetalleFragment) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                fabToolbar.hide();
                fab.hide();
               // fab.animate().scaleX(1).scaleY(1).translationX(0).translationY(0).setDuration(500);


            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //  getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    //On selected del menu del Navigation View
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_datosPersonales:
                Intent i = new Intent(this, UsuarioActivity.class);
                i.putExtra("Usuario", user);
                startActivity(i);
                break;
            case R.id.nav_cerrarSesion:
                cerrarSesion();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void cerrarSesion() {
        user = null;
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        i.putExtra("CIERRA_SESION", true);
        startActivity(i);
        DependenciaDBManager.UsuarioDBManager db = new DependenciaDBManager.UsuarioDBManager(getApplicationContext());
        db.emptyTable();
        finish();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("user",user);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        user = (Usuario) savedInstanceState.getSerializable("user");
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    //Le pasamos el menu para poder hacerlo visible al abrir el fragmento de Recordatorio Detalle
                    return RecordatorioFragment.newInstance(user.getIdPersona());
                case 1:
                    return new MapFragment();
                case 2:
                    return new BotonFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}