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
    //  private Double rand;
    private List<StampKotelDataManager> stampKotelDataManagers = new LinkedList<>();
    private FooListener listener;
    private DetailListener detailListener;
    private ValueEventListener detailEventListener;
    private ValueEventListener senderEventListener;


    public WorkDBsenderKotelList() {
    }

    public WorkDBsenderKotelList(FooListener listener) {
        this.listener = listener;
    }

    public WorkDBsenderKotelList(DetailListener detailListener) {
        this.detailListener = detailListener;
    }

    public interface FooListener {
        void onGetData(List<StampKotelDataManager> data);
    }

    //
    public interface DetailListener {
        void onGetDetail(Map<String, Double> kotelDetail);
    }
    //

    //методы работы с базой
    public boolean sendDataKotelToDB(final List<StampKotelDataManager> kotels) {//передача данных в базу
// Write a message to the database

        FirebaseDatabase database = FirebaseDatabase.getInstance();
       final DatabaseReference myRef = database.getReference("KotelDB2");

        //****************
myRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
            if (listDetectorInfo!=null) {
                for (String nameDetector : listDetectorInfo.keySet()) {
                    info1.setDetectorData(nameDetector, listDetectorInfo.get(nameDetector));
                }
            }
            kotels.add(info1);
        }

        myRef.setValue(kotels);


    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});



        //**************************************
     //   myRef.setValue(kotels);


        return true;//реализация взаимодействия с firebase

    }

    public void getDetailDataFromDB(final String nameKotel) { //сюда передать имя котла значене деталей которого нужно получить

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef2 = database.getReference("KotelDB2");
        ///далее  код для получение данных из базы и формирование мап листа конкретного котла

        detailEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                    Integer ii = i;
                    if (nameKotel.contains(dataSnapshot.child(ii.toString()).child("nameKotel").getValue(String.class))) {
                        GenericTypeIndicator<Map<String, Double>> genericTypeIndicator2 = new GenericTypeIndicator<Map<String, Double>>() {
                        };
                        Map<String, Double> listDetectorInfo1 = dataSnapshot.child(ii.toString()).child("detectorData").getValue(genericTypeIndicator2);

                        if (detailListener != null && listDetectorInfo1!=null) {
                            detailListener.onGetDetail(listDetectorInfo1);//
                            break;
                        }
                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        myRef2.addValueEventListener(detailEventListener);

    }

    public void removeDetailEventListener() {
        FirebaseDatabase.getInstance().getReference("KotelDB2").removeEventListener(detailEventListener);
    }


    public void getDataKotelFromDB() {//получение данных из базы
        //****эмуляция получения данных из базы //


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("KotelDB2");


        ///-----------
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stampKotelDataManagers.clear();
                for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                    StampKotelDataManager info1 = new StampKotelDataManager();
                    Integer ii = i;
                    info1.setDateKoteldData(dataSnapshot.child(ii.toString()).child("dateKoteldData").getValue(String.class));
                    info1.setNameNewKotelOnlyName(dataSnapshot.child(ii.toString()).child("nameKotel").getValue(String.class));

                    GenericTypeIndicator<Map<String, Double>> genericTypeIndicator2 = new GenericTypeIndicator<Map<String, Double>>() {
                    };

                    Map<String, Double> listDetectorInfo = dataSnapshot.child(ii.toString()).child("detectorData").getValue(genericTypeIndicator2);
                    if (listDetectorInfo!=null) {
                        for (String nameDetector : listDetectorInfo.keySet()) {
                            info1.setDetectorData(nameDetector, listDetectorInfo.get(nameDetector));
                        }
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
