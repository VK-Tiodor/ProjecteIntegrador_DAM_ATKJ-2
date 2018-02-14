package dam.android.dependeciapp;

import android.animation.Animator;
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
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.TextView;

import java.util.List;

import dam.android.dependeciapp.AsyncTasks.LanzaLlamada;
import dam.android.dependeciapp.Controladores.Conexion;
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
    private Usuario user;
    private Conexion con;
    private Menu appBarMenu;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (Usuario) getIntent().getSerializableExtra("user");
        //con = (Conexion) getIntent().getSerializableExtra("conexion");
        setUI();
    }

    private void setUI() {
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        //Guardamos el tamaño del Fab, para poder resituirlo a su tamaño original
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null) {
                    LanzaLlamada llamada = new LanzaLlamada(view, getApplicationContext());
                    llamada.execute(user.getIdPersona() + "");
                }
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView tvNombre = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvNombre);
        tvNombre.setText(user.getNombre() + " " + user.getApellidos());
        mViewPager = (ViewPager) findViewById(R.id.container);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        //Se ejecuta cada vez que cambiamos de tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int posicion = tab.getPosition();
                switch (posicion) {
                    case 0:
                        //Ponemos al FAB su tamaño original
                        break;
                    case 1:
                        //Ponemos al FAB su tamaño original
                        //Expandimos el appBarLayout, para poder cerrar RecordatorioDetalleFragment,
                        //en caso de que haya alguno abieto
                        appBarLayout.setExpanded(true);
                        break;
                    case 2:
                        //Hacemos el FAB mas grande
                        fab.animate().scaleX(5).scaleY(5).translationX(-400).translationY(-450).setDuration(500);
                        //Expandimos el appBarLayout simplemente por estetica
                        appBarLayout.setExpanded(true);
                        cierraRecordatorioDetalle();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(tab.getPosition() == 2){
                    fab.animate().scaleX(1).scaleY(1).translationX(0).translationY(0).setDuration(500);
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
                appBarMenu.findItem(R.id.action_close_fragment).setVisible(false);
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        appBarMenu = menu;
        //Hacemos que no se vea, ya que su unica funcion es cerrar los RecordatorioDetalleFragment
        //y mientras no haya ninguno abierto no tiene sentido que sea visible
        appBarMenu.findItem(R.id.action_close_fragment).setVisible(false);
        //Hacemos aqui el adaptador para que el menu no sea null a la hora de crear el SectionsPageAdapter
        //ya que el setUI se inicia antes que este metodo
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        return true;
    }

    //Selected del menu del toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_close_fragment) {
            cierraRecordatorioDetalle();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //On selected del menu del Navigation View
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_datosPersonales:
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
        finish();
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
                    return RecordatorioFragment.newInstance(getApplicationContext(), appBarMenu);
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