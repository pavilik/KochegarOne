package ru.h1n.kochegar.kochegarone;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import ru.h1n.kochegar.kochegarone.KotelListActivity.SimpleItemRecyclerViewAdapter;
import ru.h1n.kochegar.kochegarone.dummy.DummyContent;

/**
 * A fragment representing a single Kotel detail screen.
 * This fragment is either contained in a {@link KotelListActivity}
 * in two-pane mode (on tablets) or a {@link KotelDetailActivity}
 * on handsets.
 */
public class KotelDetailFragment extends Fragment implements WorkDBsenderKotelList.DetailListener {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
   // public static final String ARG_ITEM = "item_detail";

    /**
     * The dummy content this fragment is presenting.
     */
    //   private KotelListActivity.SimpleItemRecyclerViewAdapter.DummyItem mItem;
  //  ContentDetail mItem = new ContentDetail("", "");
    WorkDBsenderKotelList detailData = new WorkDBsenderKotelList(this);

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public KotelDetailFragment() {


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            //

            //  mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
// сюда нужен запрос на получение данных из базы по конкретному идентификатору
            //  WorkDBsenderKotelList detailData = new WorkDBsenderKotelList(this);
            //   detailData.getDetailDataFromDB((getArguments().getString(ARG_ITEM_ID)).toString());


         //   mItem.content = (getArguments().getString(ARG_ITEM_ID)).toString();


            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle((getArguments().getString(ARG_ITEM_ID)).toString());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.kotel_detail, container, false);

     //   if (getArguments().containsKey(ARG_ITEM_ID)) {
            detailData.getDetailDataFromDB((getArguments().getString(ARG_ITEM_ID)).toString());

            // Show the dummy content as text in a TextView.
//            if (mItem != null) {
//                ((TextView) rootView.findViewById(R.id.kotel_detail)).setText(mItem.details);
//            }
    //    }
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        detailData.removeDetailEventListener();
    }

    @Override
    public void onGetDetail(Map<String, Double> kotelDetail) {
        //метод получающий данные деталей котла
        //необходимо создать объект класса хранящего данные
        StringBuilder detectorData = new StringBuilder();
        for (String detector : kotelDetail.keySet()
                ) {
            detectorData.append(detector);
            detectorData.append(" : ");
            detectorData.append(kotelDetail.get(detector));
            detectorData.append("\n");

        }
       // mItem.details = detectorData.toString();
        ((TextView)getView().findViewById(R.id.kotel_detail)).setText(detectorData.toString());
      //  notifyDataSetChanged();
    }


}



