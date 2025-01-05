package com.example.t3a3_climent_pablo.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.t3a3_climent_pablo.R
import com.example.t3a3_climent_pablo.fragments.GlobalPositionFragment
import com.example.t3a3_climent_pablo.fragments.HomeFragment
import com.example.t3a3_climent_pablo.pojo.Cliente
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var cliente: Cliente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Recibir cliente desde el intent
        cliente = intent.getSerializableExtra("Cliente") as Cliente
        Log.d("MainActivity", "Cliente: $cliente")

        // Configurar el Navigation Drawer
        drawerLayout = findViewById(R.id.drawer_layout)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav_drawer, R.string.close_nav_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Cargar el HomeFragment al inicio
        if (savedInstanceState == null) {
            val fragment = HomeFragment()
            val bundle = Bundle()
            bundle.putSerializable("Cliente", cliente) // Pasar cliente como argumento
            fragment.arguments = bundle // Asignar argumentos al fragmento

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment).commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                val fragment = HomeFragment()
                val bundle = Bundle()
                bundle.putSerializable("Cliente", cliente)
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment).commit()
            }
            R.id.nav_posicion_global -> {
                val fragment = GlobalPositionFragment()
                val bundle = Bundle()
                bundle.putSerializable("cliente", cliente)
                bundle.putSerializable("ListaCuentas", ArrayList(cliente.obtenerListaCuentas()))
                fragment.arguments = bundle

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment).commit()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
