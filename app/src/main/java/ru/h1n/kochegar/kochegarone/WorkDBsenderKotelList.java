package ru.h1n.kochegar.kochegarone;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Павел on 03.10.2017.
 */

public class WorkDBsenderKotelList extends StampKotelDataManager {
    private Double rand;
    private List<StampKotelDataManager> stampKotelDataManagers = new LinkedList<>();
    private FooListener listener;

    public WorkDBsenderKotelList() {
    }

    public WorkDBsenderKotelList(FooListener listener) {
        this.listener = listener;
    }

    public interface FooListener {
        void onGetData(List<StampKotelDataManager> data);
    }

    //методы работы с базой
    public boolean sendDataKotelToDB(List<StampKotelDataManager> kotels) {//передача данных в базу
// Write a message to the database

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("KotelDB2");

        myRef.setValue(kotels);

        return true;//реализация взаимодействия с firebase

    }


    public void getDataKotelFromDB() {//получение данных из базы
        //****эмуляция получения данных из базы //


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("KotelDB2");


        ///-----------
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                    StampKotelDataManager info1 = new StampKotelDataManager();
                    Integer ii = i;
                    info1.setDateKoteldData(dataSnapshot.child(ii.toString()).child("dateKoteldData").getValue(String.class));
                    info1.setNameNewKotelOnlyName(dataSnapshot.child(ii.toString()).child("nameKotel").getValue(String.class));

                    GenericTypeIndicator<Map<String, Double>> genericTypeIndicator2 = new GenericTypeIndicator<Map<String, Double>>() {
                    };
                    Map<String, Double> listDetectorInfo = dataSnapshot.child(ii.toString()).child("detectorData").getValue(genericTypeIndicator2);

                    for (String nameDetector : listDetectorInfo.keySet()) {
                        info1.setDetectorData(nameDetector, listDetectorInfo.get(nameDetector));
                    }

                    stampKotelDataManagers.add(info1);
                    if (listener != null) {
                        listener.onGetData(stampKotelDataManagers);
                    }
                }

                //
                // stampKotelDataManagers.isEmpty();
                //       String str = dataSnapshot.child("0").child("dateKoteldData").getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


     /*   for (int j = 1; j <= 5; j++) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd' 'HH:mm:ss.SS");
            Date date = new Date();
            String datestring = dateFormat.format(date)+j;


            StampKotelDataManager info1 = new StampKotelDataManager("Котел №" +j, true, datestring);


            for (int i = 1; i <= 10; i++) {

                rand = Math.random()+i;
                @SuppressLint("DefaultLocale") String uuu= String.format("%.2f",rand);
                uuu=uuu.replaceAll(",",".");

                Double yyy= Double.valueOf(uuu);
                info1.setDetectorData("датчик №"+i,yyy );//Double.valueOf(String.format( "%.2f", rand ))
            }
            stampKotelDataManagers.add(info1);

        }
*/
        // Конец эмуляции******///


//        return stampKotelDataManagers;///список данных из базы
    }
}
