package com.crushmateapp.crushmate.activities

import android.os.Bundle
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import com.crushmateapp.crushmate.Adapters.CallLogsTranscriptAdapter
import java.util.*
import com.crushmateapp.crushmate.R

class CallLogsAnalysis : AppCompatActivity() {

    var listAdapter: ExpandableListAdapter? = null
    var expListView: ExpandableListView? = null
    var listDataHeader: kotlin.collections.List<String>? = null
    var listDataChild: HashMap<String, kotlin.collections.List<String>>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.call_logs_analysis)
        expListView = findViewById(R.id.lvExp)
        prepareListData();
        listAdapter = CallLogsTranscriptAdapter(this, listDataHeader, listDataChild)
        expListView?.run { setAdapter(listAdapter) };

    }
    private fun prepareListData() {
        listDataHeader = ArrayList()
        listDataChild = HashMap()

        // Adding child data
        (listDataHeader as ArrayList<String>).add("Service Pitch Call    Monday, 05-Oct-20 16:17:26 UTC")
        (listDataHeader as ArrayList<String>).add("Onboarding Formalities Discussion    Wednesday, 15-Oct-20 10:19:26 UTC")
        (listDataHeader as ArrayList<String>).add("Investment Oportunities Discussion     Wednesday, 15-Oct-20 10:19:26 UTC")

        // Adding child data
        val firstCall: MutableList<String> = ArrayList()
        firstCall.add("Made a call to client to pitch the services offered by the bank, special focus on Advisory proposition and Portfolio Management")

        val secondCall: MutableList<String> = ArrayList()
        secondCall.add("Discussed Bank's client onboarding journey and formalities to be completed. Client looks confident in his future with bank.")

        val thirdCall: MutableList<String> = ArrayList()
        thirdCall.add("Demonstrated Alladin to Client and explained how it is benificial for investment proposition and Portfloio management. ")

        listDataChild!![ (listDataHeader as ArrayList<String>).get(0)] = firstCall // Header, Child data
        listDataChild!![ (listDataHeader as ArrayList<String>).get(1)] = secondCall
        listDataChild!![ (listDataHeader as ArrayList<String>).get(2)] = thirdCall
    }

}