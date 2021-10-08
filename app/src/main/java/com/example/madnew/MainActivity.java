package com.example.madnew;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ShippingRVAdapter.ShippingClickInterface {

    // creating variables for fab, firebase database,
    // progress bar, list, adapter,firebase auth,
    // recycler view and relative layout.
    private FloatingActionButton addShippingFAB;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private RecyclerView shippingRV;
    private ProgressBar loadingPB;
    private ArrayList<ShippingRVModal> shippingRVModalArrayList;
    private ShippingRVAdapter shippingRVAdapter;
    private RelativeLayout homeRL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initializing all our variables.
        shippingRV = findViewById(R.id.idRVShipping);
        loadingPB = findViewById(R.id.idPBLoading);
        addShippingFAB = findViewById(R.id.idFABAddShipping);
        firebaseDatabase = FirebaseDatabase.getInstance();
        shippingRVModalArrayList = new ArrayList<>();
        // on below line we are getting database reference.
        databaseReference = firebaseDatabase.getReference("ShippingDetails");
        // on below line adding a click listener for our floating action button.
        addShippingFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity for adding a shipping details.
                Intent i = new Intent(MainActivity.this, AddShippingDetails.class);
                startActivity(i);
            }
        });
        // on below line initializing our adapter class.
        shippingRVAdapter = new ShippingRVAdapter(shippingRVModalArrayList, this, this::onCardClick);
        // setting layout malinger to recycler view on below line.
        shippingRV.setLayoutManager(new LinearLayoutManager(this));
        // setting adapter to recycler view on below line.
        shippingRV.setAdapter(shippingRVAdapter);
        // on below line calling a method to fetch details from database.
        getShipping();
    }

    private void getShipping() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shippingRVModalArrayList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    ShippingRVModal shippingRVModal = dataSnapshot.getValue(ShippingRVModal.class);
                    String shippingID = dataSnapshot.getKey();
                    shippingRVModal.setShippingID(shippingID);
                    shippingRVModalArrayList.add(shippingRVModal);
                }
                shippingRVAdapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @Override
    public void onCardClick(int position) {
        // calling a method to display a bottom sheet on below line.
        displayBottomSheet(shippingRVModalArrayList.get(position));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // on below line we are inflating our menu
        // file for displaying our menu options.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressLint("SetTextI18n")
    private void displayBottomSheet(ShippingRVModal modal) {
//        TextView tv_shippingName, tv_shippingContact, tv_shippingNIC, tv_shippingEmail, tv_shippingProvince, tv_shippingAddress;

        // on below line we are creating our bottom sheet dialog.
        final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        // on below line we are inflating our layout file for our bottom sheet.
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout, homeRL);
        // setting content view for bottom sheet on below line.
        bottomSheetTeachersDialog.setContentView(layout);
        // on below line we are setting a cancelable
        bottomSheetTeachersDialog.setCancelable(false);
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);
        // calling a method to display our bottom sheet.
        bottomSheetTeachersDialog.show();
        // on below line we are creating variables for
        // our text view and image view inside bottom sheet
        // and initialing them with their ids.
        TextView name = layout.findViewById(R.id.idTVCourseName);
        TextView address = layout.findViewById(R.id.idTVCourseDesc);
        TextView nic = layout.findViewById(R.id.idTVSuitedFor);
        TextView contact = layout.findViewById(R.id.idTVCoursePrice);
//        tv_shippingEmail = findViewById(R.id.);
//        tv_shippingProvince = findViewById(R.id.);
        // on below line we are setting data to different views on below line.
        name.setText(modal.getShippingName());
        contact.setText("Contact: " + modal.getShippingContact());
        nic.setText("NIC: " + modal.getShippingNIC());
        address.setText("Address: " + modal.getShippingAddress());
        Button editBtn = layout.findViewById(R.id.idBtnEditCourse);

        // adding on click listener for our edit button.
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are opening our EditCourseActivity on below line.
                Intent i = new Intent(MainActivity.this, EditShippingDetails.class);
                // on below line we are passing our course modal
                i.putExtra("shipping", modal);
                startActivity(i);
            }
        });

    }
}
