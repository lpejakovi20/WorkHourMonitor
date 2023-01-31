package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.adapters.MainPagerAdapter
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.fragments.*

class AdminActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager2: ViewPager2
    lateinit var navDrawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        tabLayout = findViewById(R.id.tl_admin_layout)
        viewPager2 = findViewById(R.id.vp_admin)
        navDrawerLayout = findViewById(R.id.dl_admin_activity)
        navView = findViewById(R.id.nv_admin)

        val mainPagerAdapter = MainPagerAdapter(supportFragmentManager, lifecycle)

        mainPagerAdapter.addFragment(
            MainPagerAdapter.FragmentItem(
                R.string.Report,
                R.drawable.ic_baseline_insert_drive_file_24,
                ReportGenerator::class
            )
        )
        mainPagerAdapter.addFragment(
            MainPagerAdapter.FragmentItem(
                R.string.Plan,
                R.drawable.schedule,
                CreateScheduleFragment::class
            )
        )
        mainPagerAdapter.addFragment(
            MainPagerAdapter.FragmentItem(
                R.string.QRGen,
                R.drawable.qr_code,
                QRGeneratorFragment::class
            )
        )


        viewPager2.adapter = mainPagerAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.setText(mainPagerAdapter.fragmentItems[position].titleRes)
            tab.setIcon(mainPagerAdapter.fragmentItems[position].iconRes)
        }.attach()

    }



}