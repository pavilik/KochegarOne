package ru.h1n.kochegar.kochegarone.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



import ru.h1n.kochegar.kochegarone.StampKotelDataManager;
import ru.h1n.kochegar.kochegarone.WorkDBsenderKotelList;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent implements WorkDBsenderKotelList.FooListener {

    /**
     * An array of sample (dummy) items.
     */
 //   public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    public List<DummyItem> items = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new LinkedHashMap<String, DummyItem>();




//    static {
//        WorkDBsenderKotelList kotelListFromDB = new WorkDBsenderKotelList();
//       List<StampKotelDataManager> kotelList = kotelListFromDB.getDataKotelFromDB();
//
//
//        for (StampKotelDataManager kotelItem : kotelList) {
//            LinkedHashMap<String,Double> dataDetect = new LinkedHashMap<>();
//            StringBuilder detectorData =new StringBuilder();
//            dataDetect.clear();
//            dataDetect.putAll(kotelItem.getDetectorData());
//           // detectorData.delete(0,detectorData.length());
//
//            for (String detector:dataDetect.keySet()
//                 ) {
//                detectorData.append(detector);
//                detectorData.append(" : ");
//                detectorData.append(dataDetect.get(detector));
//                detectorData.append("\n");
//
//            }
//
//            //detectorData.trimToSize();
//            DummyItem item = new DummyItem(kotelItem.getDateKoteldData(), kotelItem.getNameKotel(), detectorData.toString());
//            ITEMS.add(item);
//            ITEM_MAP.put(item.id, item);
//        }
//
//    }

    public DummyContent() {
        WorkDBsenderKotelList kotelListFromDB = new WorkDBsenderKotelList(this);
        kotelListFromDB.getDataKotelFromDB();
    }



    @Override
    public void onGetData(List<StampKotelDataManager> data) {
        for (StampKotelDataManager kotelItem : data) {
            LinkedHashMap<String,Double> dataDetect = new LinkedHashMap<>();
            StringBuilder detectorData =new StringBuilder();
            dataDetect.clear();
            dataDetect.putAll(kotelItem.getDetectorData());
            // detectorData.delete(0,detectorData.length());

            for (String detector:dataDetect.keySet()
                    ) {
                detectorData.append(detector);
                detectorData.append(" : ");
                detectorData.append(dataDetect.get(detector));
                detectorData.append("\n");

            }

            //detectorData.trimToSize();
            DummyItem item = new DummyItem(kotelItem.getDateKoteldData(), kotelItem.getNameKotel(), detectorData.toString());
            items.add(item);
            ITEM_MAP.put(item.id, item);
        }
    }

    //-----------cut формирование думмиИтем в шаблоне активити
/*
    private static final int COUNT = 25;




    static {
        // Add some sample items.

        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }
*/
//-----------end cut------
    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
