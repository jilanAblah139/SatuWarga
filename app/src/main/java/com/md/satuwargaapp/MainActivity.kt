import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.md.satuwargaapp.R
import com.md.satuwargaapp.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> openFragment(HomeFragment())
                R.id.nav_pengumuman -> openFragment(PengumumanFragment())
                R.id.nav_pelayanan -> openFragment(PelayananFragment())
                R.id.nav_keuangan -> openFragment(KeuanganFragment())
            }
            true
        }

        // Default Fragment
        openFragment(HomeFragment())
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
    }
}
