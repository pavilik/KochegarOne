package ru.h1n.kochegar.kochegarone;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;


public class KotelDataEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kotel_data_edit);

        final NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(99);
        numberPicker.setMinValue(0);
        final NumberPicker numberPicker2 = (NumberPicker) findViewById(R.id.numberPicker2);
        numberPicker2.setMaxValue(99);
        numberPicker2.setMinValue(0);
        numberPicker2.setValue(16);
        numberPicker.setValue(57);
        final TextView twDetectorName = (TextView) findViewById(R.id.editNameDetector);


        Button btnSaveAndGoToKNDEA = (Button) findViewById(R.id.buttonSaveAndGoToKNDEA);
        btnSaveAndGoToKNDEA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //нужно сохранить данные с намбер пикеров
//   StampKotelDataManager обект
                ((KochegarApp) getApplication()).getSkdm().setDetectorData(twDetectorName.getText().toString(), ((numberPicker.getValue()) +  ((double)( numberPicker2.getValue()) / 100)));
                //  Context context = view.getContext();
                // Intent page = new Intent(context, KotelNameDataEditActivity.class);
                // startActivity(page);
                finish();
                //переход на форму котелДатаЕдитАктивити
            }
        });


        Button btnAddNewSensor = (Button) findViewById(R.id.buttonAddNewSensor);
        btnAddNewSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((KochegarApp) getApplication()).getSkdm().setDetectorData(twDetectorName.getText().toString(),  ((numberPicker.getValue()) +  ((double)( numberPicker2.getValue()) / 100)));
                //нужно сохранить данные и вывести новую форму KotelDataEditActivity
              twDetectorName.setText("Ещё датчик №");

            }
        });


    }
}
