package com.ziyadkuttiady.myjourney;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class First2Fragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first2, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView;
        DataBaseHelper myDbHelper;
        ArrayList<ItemHistory> historyArrayList = new ArrayList<>();

//        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(First2Fragment.this)
//                        .navigate(R.id.action_First2Fragment_to_Second2Fragment);
//            }
//        });

//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
//        String name = sharedPreferences.getString("name_of_user", "no");
//        String phone = sharedPreferences.getString("phone_number_of_user", "no");
//        String address = sharedPreferences.getString("address_of_user", "no");
//
//        if (name.equals("no") &&
//                phone.equals("no") &&
//                address.equals("no")) {
//            startActivity(new Intent(getActivity().getApplicationContext(), PersonalDataCollectActivity.class));
//        }
//        myDbHelper = new DataBaseHelper(getActivity().getApplicationContext());
//
//        listView = view.findViewById(R.id.listViewHistory);
//
//
//
//
//        Cursor cursor = myDbHelper.getAllData();
//
//        //small test
//        if (cursor.getCount() == 0) {
//            return;
//        }
//
//        StringBuffer buffer = new StringBuffer();
//
//        final CustomHistoryAdapter adapter = new CustomHistoryAdapter(getActivity().getApplicationContext(), historyArrayList);
//        String  lastAdded = "";
//        while (cursor.moveToNext()) {
////                    buffer.append("__________________________"+"\n");
//            buffer.append("ID: " + cursor.getString(0) + "\n");
//            buffer.append("V_DATE: " + cursor.getString(1) + "\n");
//            buffer.append("START_TIME: " + cursor.getString(2) + "\n");
//            buffer.append("END_TIME: " + cursor.getString(3) + "\n");
//            buffer.append("PLACE: " + cursor.getString(4) + "\n");
//            buffer.append("PURPOSE: " + cursor.getString(5) + "\n");
//            buffer.append("DESC: " + cursor.getString(6) + "\n\n");
//
//            if (lastAdded.equals(cursor.getString(1))){
//                historyArrayList.add(new CustomHistoryEntryItem(cursor.getString(4)));
////            }else{
////                historyArrayList.add(new CustomHistorySectionItem(cursor.getString(1)));
////                historyArrayList.add(new CustomHistoryEntryItem(cursor.getString(4)));
//
//            }
////            historyArrayList.add(new CustomHistorySectionItem(cursor.getString(1)));
////            historyArrayList.add(new CustomHistoryEntryItem(cursor.getString(4)));
//            adapter.notifyDataSetChanged();
//            lastAdded = cursor.getString(1);
//
//
//        }
//
//        // set adapter
//        listView.setAdapter(adapter);
//        listView.setTextFilterEnabled(true);
//        adapter.notifyDataSetChanged();
    }

}
