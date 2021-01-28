package sim.coder.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import sim.coder.todolist.fragment.HomeFragment
import sim.coder.todolist.fragment.MoreDetailsFragment


class MainActivity : AppCompatActivity() ,HomeFragment.CallBacks{

    lateinit var  tabLayout: TabLayout
    lateinit var tabViewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)






        tabLayout=findViewById(R.id.taps)
        tabViewPager=findViewById(R.id.vp) as ViewPager2


        tabViewPager.adapter=object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return 3
            }


            override fun createFragment(position: Int): Fragment {
                return  when(position){

                    0->HomeFragment.newInstance("todo")
                    1->HomeFragment.newInstance("inprogress")
                    2->HomeFragment.newInstance("done")

                    else->HomeFragment.newInstance("todo")
                }
            }


        }

        TabLayoutMediator(tabLayout,tabViewPager){tab,position->
            tab.text=when(position){

                0->"toDO"
                1->"in progress"
                2->"Done"
                else -> null
            }

        }.attach()
    }

    override fun onTaskSelected(taskId: Int) {
        val fragment= MoreDetailsFragment.newInstance(taskId)
        val fm = supportFragmentManager
        fm.beginTransaction().replace(R.id.fragment_container,fragment)
                .addToBackStack(null).commit()
    }
}
