package com.raiachat.fragments


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.raiachat.R
import com.raiachat.activities.PromptLoginActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.reflect.KClass

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        homeReportBtn.setOnClickListener {
            openActivity(PromptLoginActivity::class)

        }
    }

    private fun <T : Activity> openActivity(activity: KClass<T>) {
        context!!.startActivity(Intent(context, activity.java))
    }
}
