package com.example.madnew;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddShippingDetails extends AppCompatActivity {

    // creating variables for our button, edit text,
    // firebase database, database refrence, progress bar.
    Button btnAddShipping;
    EditText tv_shippingName, tv_shippingContact, tv_shippingNIC, tv_shippingEmail, tv_shippingProvince, tv_shippingAddress;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shipping);
        // initializing all our variables.
        btnAddShipping = findViewById(R.id.btn_confirm);
        tv_shippingName = findViewById(R.id.tv_name);
        tv_shippingContact = findViewById(R.id.tv_contact);
        tv_shippingNIC = findViewById(R.id.tv_nic);
        tv_shippingEmail = findViewById(R.id.tv_email);
        tv_shippingProvince = findViewById(R.id.tv_province);
        tv_shippingAddress = findViewById(R.id.tv_address);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line creating our database reference.
        databaseReference = firebaseDatabase.getReference("ShippingDetails");
        // adding click listener for our add confirm button.
        btnAddShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting data from our edit text.
                String shippingName = tv_shippingName.getText().toString();
                String shippingContact = tv_shippingContact.getText().toString();
                String shippingNIC = tv_shippingNIC.getText().toString();
                String shippingEmail = tv_shippingEmail.getText().toString();
                String shippingProvince = tv_shippingProvince.getText().toString();
                String shippingAddress = tv_shippingAddress.getText().toString();
                String shippingID = databaseReference.push().getKey();
                String contactPattern = "[0-9]{10}";

                if(TextUtils.isEmpty(shippingName)) {
                    tv_shippingName.setError("Name required!");
                }
                else if(TextUtils.isEmpty(shippingContact)) {
                    tv_shippingContact.setError("Contact required!");
                }
                else if(!shippingContact.trim().matches(contactPattern)) {
                    tv_shippingContact.setError("Enter Valid No!");
                }
                else if(TextUtils.isEmpty(shippingNIC)) {
                    tv_shippingNIC.setError("NIC required!");
                }
                else if(TextUtils.isEmpty(shippingEmail)) {
                    tv_shippingEmail.setError("Email required!");
                }
                else if(TextUtils.isEmpty(shippingProvince)) {
                    tv_shippingProvince.setError("Province required!");
                }
                else if(TextUtils.isEmpty(shippingAddress)) {
                    tv_shippingAddress.setError("Address required!");
                }
                else {
                    loadingPB.setVisibility(View.VISIBLE);
                    // on below line we are passing all data to our modal class.
                    ShippingRVModal shippingRVModal = new ShippingRVModal(shippingName, shippingContact, shippingNIC, shippingEmail, shippingProvince, shippingAddress);
                    // on below line we are calling a add value event
                    // to pass data to firebase database.
                    assert shippingID != null;
                    databaseReference.child(shippingID).setValue(shippingRVModal).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            // displaying a toast message.
                            Toast.makeText(AddShippingDetails.this, "Shipping Details Added..", Toast.LENGTH_SHORT).show();
                            // starting a main activity.
                            startActivity(new Intent(AddShippingDetails.this, MainActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // displaying a failure message on below line.
                            loadingPB.setVisibility(View.INVISIBLE);
                            Toast.makeText(AddShippingDetails.this, "Failed to add..", Toast.LENGTH_SHORT).show();
                        }
                    });

                }


            }
        });
    }
}
