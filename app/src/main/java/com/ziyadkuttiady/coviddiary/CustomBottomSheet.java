package com.ziyadkuttiady.coviddiary;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import static com.ziyadkuttiady.coviddiary.HomeScreenActivity.id;


public class CustomBottomSheet extends BottomSheetDialogFragment {
    DataBaseHelper myDbHelper;
    private BottomSheetListener bottomSheetListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet, container, false);


        myDbHelper = new DataBaseHelper(getActivity().getApplicationContext());


        Cursor cursor = myDbHelper.getData(id);
        ;

        if (cursor.moveToNext()) {
            //StartingPointPlace
            TextView bottom_sheet_start_point_place = v.findViewById(R.id.editTextStartingPointPlace);
            bottom_sheet_start_point_place.setText(cursor.getString(5));

            //editTextStartingPointDate
            TextView bottom_sheet_start_point_date = v.findViewById(R.id.editTextStartingPointDate);
            bottom_sheet_start_point_date.setText(cursor.getString(1));

            //StartingPointTime
            TextView bottom_sheet_start_point_time = v.findViewById(R.id.editTextStartingPointTime);
            bottom_sheet_start_point_time.setText(cursor.getString(3));

            //DestinationPlace
            TextView bottom_sheet_end_point_place = v.findViewById(R.id.editTextDestinationPlace);
            bottom_sheet_end_point_place.setText(cursor.getString(6));

            //DestinationDate
            TextView bottom_sheet_end_point_date = v.findViewById(R.id.editTextDestinationDate);
            bottom_sheet_end_point_date.setText(cursor.getString(2));

            //DestinationTime
            TextView bottom_sheet_end_point_time = v.findViewById(R.id.editTextDestinationTime);
            bottom_sheet_end_point_time.setText(cursor.getString(4));

            //Purpose
            TextView bottom_sheet_purpose = v.findViewById(R.id.editTextPurpose);
            bottom_sheet_purpose.setText(cursor.getString(7));

            //VehicleType
            RadioButton bottom_sheet_vehicle_type_public = v.findViewById(R.id.radioButtonPublic);
            if (cursor.getString(9).equals("Public")) {
                bottom_sheet_vehicle_type_public.setChecked(true);

            }


            //VehicleCategory
            TextView bottom_sheet_vehicle_category = v.findViewById(R.id.editTextVehicleCategory);
            bottom_sheet_vehicle_category.setText(cursor.getString(10));

            //VehicleNo
            TextView bottom_sheet_vehicle_no = v.findViewById(R.id.editTextVehicleNo);
            bottom_sheet_vehicle_no.setText(cursor.getString(11));

            //StartingPointPlace
            TextView bottom_sheet_desc = v.findViewById(R.id.editTextDesc);
            bottom_sheet_desc.setText(cursor.getString(8));

            //StartingPointPlace
            Button bottom_sheet_edit = v.findViewById(R.id.buttonEdit);
            bottom_sheet_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottomSheetListener.onButtonClicked("edit");
                    dismiss();
                }
            });
        }


        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            bottomSheetListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " Must Implement BottomSheet Listener");
        }
    }

    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }
}
