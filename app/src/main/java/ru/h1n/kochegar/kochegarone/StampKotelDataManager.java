package ru.h1n.kochegar.kochegarone;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Created by Павел on 03.10.2017.
 * содержит:
 * 1. штамп данных на определенную дату для котла
 * 2. методы работы с базой
 * 3. методы изменения данных в объекте штампа
 */


public class StampKotelDataManager /*implements Serializable*/{
    private String nameKotel; //имя котла
    private boolean status;     //статус котла вкл\выкл
    private String dateKoteldData; //дата данных с котла предполагается DateToString
    private LinkedHashMap detectorData = new LinkedHashMap<String, Double>(); // список датчиков и их значений ключ имя датчика

    //конструктора котла с имеющимися данными;
    public StampKotelDataManager(String nameNewKotel, boolean setStatus, String newDateKotelData) {
        nameKotel = nameNewKotel;
        status = setStatus;
        dateKoteldData = newDateKotelData;
        }
    public StampKotelDataManager(String nameNewKotel, boolean setStatus, String newDateKoteldData, LinkedHashMap<String, Double> newDetectorData) {
        nameKotel = nameNewKotel;
        status = setStatus;
        dateKoteldData = newDateKoteldData;
        detectorData.putAll(newDetectorData);
    }

    public StampKotelDataManager() {
    }

    //Методы GET
    public String getNameKotel() {//возвращает имя котла
        return nameKotel;
    }


    public boolean isStatus() {//возвращает текущий статус котла
        return status;
    }

    public String getDateKoteldData() { //возвращает дату котла
        return dateKoteldData;
    }

    public LinkedHashMap getDetectorData() {//возвращает данные на котел
        return detectorData;
    }


    ///методы SET

    public void setDetectorData(LinkedHashMap<String, Double> detectorNewDataAdd) {//метод добавляет дополнительные данные и датчики из листа
       if(!detectorData.isEmpty()){
          this.detectorData.putAll(detectorNewDataAdd);

       }
    }

    public void setDetectorData(String sensor, Double value){
        detectorData.put(sensor,value);

    }

    public void setNameNewKotelOnlyName (String nameNewKotel){
        nameKotel = nameNewKotel;

    }

    public void setDateKoteldData(String dateKoteldData) {
        this.dateKoteldData = dateKoteldData;
    }

  /*  public void  setDateKoteldData (Date curdate){
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd' 'HH:mm:ss.SS");
        dateKoteldData = dateFormat.format(curdate);
    }
*/
    public void setStatus(boolean status) {
        this.status = status;
    }
    public void clearData(){
        detectorData.clear();
    }
}
