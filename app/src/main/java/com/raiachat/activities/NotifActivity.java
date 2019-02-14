package com.raiachat.activities;


import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.raiachat.R;

public class NotifActivity extends AppCompatActivity {

    private static final String TAG = "notif";


    ImageButton ceImgBtnClose;

    MaterialButton ceDismiss;

    RecyclerView ceList;

    ConstraintLayout ceEmptynotif;

    ConstraintLayout ceNotifListview;

    //private FirestoreRecyclerAdapter adapter;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_notif);
        // fixing portrait mode problem for SDK 26 if using windowIsTranslucent = true
        if (Build.VERSION.SDK_INT == 26) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        initViews();

        ceNotifListview.setVisibility(View.GONE);
        ceEmptynotif.setVisibility(View.VISIBLE);
    }

    private void initViews() {
        ceImgBtnClose = findViewById(R.id.ce_imgBtnClose);
        ceDismiss = findViewById(R.id.ce_dismiss);
        ceList = findViewById(R.id.ce_list);
        ceEmptynotif = findViewById(R.id.ce_emptynotif);
        ceNotifListview = findViewById(R.id.ce_notif_listview);

        ceImgBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.ce2_imgBtnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ceDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRecycler() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ceList.setLayoutManager(layoutManager);
        ceList.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        ceList.addItemDecoration(itemDecoration);
        ceList.setItemAnimator(new DefaultItemAnimator());
    }

   /* private void setUpList() {

        initRecycler();

        SharedPreferences preferences = getSharedPreferences(getApplicationContext().getPackageName(), MODE_PRIVATE);
        String topicname = preferences.getString(COURSE_PREF_NAME, "").replace(" ", "")
                + preferences.getString(CURRENT_YEAROFSTUDY_PREF_NAME, "");

        Query query = FirebaseFirestore.getInstance().collection(NOTIFICATIONCOL)
                .whereEqualTo("topic", topicname)
                .orderBy("time", Query.Direction.DESCENDING)
                .limit(7);

        FirestoreRecyclerOptions<Notif> options = new FirestoreRecyclerOptions.Builder<Notif>()
                .setQuery(query, Notif.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Notif, NotifViewHolder>(options) {

            @NonNull
            @Override
            public NotifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_notif, parent, false);

                return new NotifViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final NotifViewHolder holder, int position, @NonNull Notif model) {

                holder.setUpUI(model,NotifActivity.this);
            }

            @Override
            public void onError(FirebaseFirestoreException e) {
                // Show a snackbar on errors
                Snackbar.make(findViewById(android.R.id.content),
                        "Error: check logs for info.", Snackbar.LENGTH_LONG).show();

                Log.d(TAG, "onError: ", e);
            }

            @Override
            public void onDataChanged() {
                // Show/hide content if the query returns empty.
                if (getItemCount() == 0) {
                    ceNotifListview.setVisibility(View.GONE);
                    ceEmptynotif.setVisibility(View.VISIBLE);
                } else {
                    ceNotifListview.setVisibility(View.VISIBLE);
                    ceEmptynotif.setVisibility(View.GONE);
                }
            }

        };

        adapter.startListening();
        adapter.notifyDataSetChanged();
        ceList.setAdapter(adapter);
    }*/
}
