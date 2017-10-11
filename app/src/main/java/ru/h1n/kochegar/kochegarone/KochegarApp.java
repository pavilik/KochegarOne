package ru.h1n.kochegar.kochegarone;

import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Павел on 06.10.2017.
 */

public class KochegarApp extends Application {
    private StampKotelDataManager skdm;

    @Override
    public void onCreate() {
        super.onCreate();
        skdm = new StampKotelDataManager();

    }


    public StampKotelDataManager getSkdm() {
        return skdm;
    }


}
