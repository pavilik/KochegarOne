package ru.h1n.kochegar.kochegarone;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class KotelNameDataEditActivity extends AppCompatActivity {



    @Override
    public void onResume() {
        super.onResume();
        //во время возвращения необходимо отобразить показания новых датчиков
        TextView listDetectorView = (TextView) findViewById(R.id.textViewListDetectorSaved);
        LinkedHashMap<String, Double> dataDetect = new LinkedHashMap<>();
        StringBuilder detectorData = new StringBuilder();
        dataDetect.putAll(((KochegarApp) getApplication()).getSkdm().getDetectorData());
        for (String detector : dataDetect.keySet()
                ) {
            detectorData.append(detector);
            detectorData.append(" : ");
            detectorData.append(dataDetect.get(detector));
            detectorData.append("\n");
        }
        listDetectorView.setText(detectorData.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kotel_name_data_edit);
        Button buttonToSendBD = (Button) findViewById(R.id.buttonSendToBD);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButtonAddNewSensor);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //нужно сохранить данные и вывести новую форму KotelDataEditActivity
                Context context = view.getContext();
                Intent page = new Intent(context, KotelDataEditActivity.class);
                startActivity(page);
            }
        });


        buttonToSendBD.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                TextView kotelnameText = (TextView) findViewById(R.id.editTextNameKotel);
                //нужно сохранить данные и вывести новую форму KotelDataEditActivity
                ((KochegarApp) getApplication()).getSkdm().setNameNewKotelOnlyName(kotelnameText.getText().toString());//добавляем в объект котла его имя
//добавляем в объект котла дату создания данных котла(текущее время)
                DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd' 'HH:mm:ss.SS");
                Date date = new Date();

                String dateKoteldData1 = dateFormat.format(date);
              ((KochegarApp) getApplication()).getSkdm().setDateKoteldData(dateKoteldData1);//добавляем в объект котла дату создания данных котла(текущее время)
                List<StampKotelDataManager> stampKotelDataManagers = new LinkedList<>();//подготавка листа для передачи
                stampKotelDataManagers.add(((KochegarApp) getApplication()).getSkdm());//добавляем в лист один объект
                //добавляем дубль для тестирования
                dateFormat = new SimpleDateFormat("yyyy.MM.dd' 'HH:ss.SSSS");
                Date date2 = new Date();
                String dateKoteldData = dateFormat.format(date2);
                ((KochegarApp) getApplication()).getSkdm().setDateKoteldData(dateKoteldData);
                stampKotelDataManagers.add(((KochegarApp) getApplication()).getSkdm());
                /////----------end add дубля
                WorkDBsenderKotelList dBsenderKotelList = new WorkDBsenderKotelList();//создаем объект для передачи
                dBsenderKotelList.sendDataKotelToDB(stampKotelDataManagers);//передаем лист объектов котлов для работы с базой данных

                ((KochegarApp) getApplication()).getSkdm().clearData();
                // перейти в общую форму и там не забыть обновить данные
                finish();
            }
        });
    }
}
