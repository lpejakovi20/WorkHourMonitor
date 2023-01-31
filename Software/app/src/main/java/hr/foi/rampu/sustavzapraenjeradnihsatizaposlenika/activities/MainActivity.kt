package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.R
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.adapters.MainPagerAdapter
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.fragments.ToDoListFragment
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.fragments.QRscannerFragment
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.fragments.ScheduleFragment
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.fragments.StatisticsFragment

class MainActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager2: ViewPager2
    lateinit var navDrawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    override fun onBackPressed() {

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tabs)
        viewPager2 = findViewById(R.id.viewpager)
        navDrawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val mainPagerAdapter = MainPagerAdapter(supportFragmentManager, lifecycle)

        mainPagerAdapter.addFragment(
            MainPagerAdapter.FragmentItem(
                R.string.ScanQR,
                R.drawable.qr_code,
                QRscannerFragment::class
            )
        )
        mainPagerAdapter.addFragment(
            MainPagerAdapter.FragmentItem(
                R.string.Statistics,
                R.drawable.statistics,
                StatisticsFragment::class
            )
        )
        mainPagerAdapter.addFragment(
            MainPagerAdapter.FragmentItem(
                R.string.Checklist,
                R.drawable.checklist,
                ToDoListFragment::class
            )
        )
        mainPagerAdapter.addFragment(
            MainPagerAdapter.FragmentItem(
                R.string.Schedule,
                R.drawable.schedule,
                ScheduleFragment::class
            )
        )

        viewPager2.adapter = mainPagerAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.setText(mainPagerAdapter.fragmentItems[position].titleRes)
            tab.setIcon(mainPagerAdapter.fragmentItems[position].iconRes)
        }.attach()
    }
}