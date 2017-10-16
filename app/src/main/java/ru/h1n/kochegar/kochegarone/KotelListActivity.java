package ru.h1n.kochegar.kochegarone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import ru.h1n.kochegar.kochegarone.dummy.DummyContent;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static ru.h1n.kochegar.kochegarone.R.id.fab;

/**
 * An activity representing a list of KotelList. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link KotelDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class KotelListActivity extends AppCompatActivity {


    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */

    private boolean mTwoPane;

    @Override
    public void onResume() {
        super.onResume();
        final FloatingActionButton fabAddKotelData = (FloatingActionButton) findViewById(R.id.fabAddKotelData);




        fabAddKotelData.setEnabled(false);
        fabAddKotelData.setVisibility(View.INVISIBLE);

        AuthCheckStorMngr checkStorMngr = new AuthCheckStorMngr(KotelListActivity.this);
        if (checkStorMngr.checkAuth()) {
            fabAddKotelData.setEnabled(true);
            fabAddKotelData.setVisibility(View.VISIBLE);
                    }

           fabAddKotelData.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View view){

                   //вызов активити редактирования котла-------------

                   Context context = view.getContext();
                   Intent mainpage = new Intent(context, KotelNameDataEditActivity.class);
                   startActivity(mainpage);


                   ///-----------------

               }

           });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kotel_list);
///**********

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    //вызов активити логин-------------

                    Context context = view.getContext();
                    Intent loginpage = new Intent(context, LoginActivity.class);
                    startActivity(loginpage);


                    ///-----------------
            }
        });


        View recyclerView = findViewById(R.id.kotel_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);


        if (findViewById(R.id.kotel_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter());
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> implements WorkDBsenderKotelList.FooListener {
        List<DummyItem> items = new ArrayList<DummyItem>();
       // public final Map<String, DummyItem> ITEM_MAP = new LinkedHashMap<String, DummyItem>();



        public SimpleItemRecyclerViewAdapter() {
            WorkDBsenderKotelList kotelListFromDB = new WorkDBsenderKotelList(this);
            kotelListFromDB.getDataKotelFromDB();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.kotel_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = items.get(position);
            holder.mIdView.setText(items.get(position).id);
            holder.mContentView.setText(items.get(position).content);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(KotelDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        KotelDetailFragment fragment = new KotelDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.kotel_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, KotelDetailActivity.class);
                        intent.putExtra(KotelDetailFragment.ARG_ITEM_ID, holder.mItem.content);
                    //    intent.putExtra(KotelDetailFragment.ARG_ITEM, holder.mItem.content);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public void onGetData(List<StampKotelDataManager> data) {
            List<DummyItem> newItems = new ArrayList<DummyItem>();
      //  Map<String, DummyItem> ITEM_MAP = new LinkedHashMap<String, DummyItem>();

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


                DummyItem item = new DummyItem(kotelItem.getDateKoteldData(), kotelItem.getNameKotel(), detectorData.toString());
                newItems.add(item);
              //  ITEM_MAP.put(item.id, item);
            }
            items.clear();
            items = newItems;
            notifyDataSetChanged();
        }

        public class DummyItem {
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

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
