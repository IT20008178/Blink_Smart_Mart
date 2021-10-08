package com.example.madnew;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditShippingDetails extends AppCompatActivity {

    // creating variables for our edit text, firebase database,
    // database reference, rv modal,progress bar.
    Button btnAddShipping, btnDeleteShipping;
    EditText tv_shippingName, tv_shippingContact, tv_shippingNIC, tv_shippingEmail, tv_shippingProvince, tv_shippingAddress;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ProgressBar loadingPB;
    ShippingRVModal shippingRVModal;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shipping);
        // initializing all our variables.
        btnAddShipping = findViewById(R.id.btn_confirm);
        btnDeleteShipping = findViewById(R.id.btn_delete);
        tv_shippingName = findViewById(R.id.tv_name);
        tv_shippingContact = findViewById(R.id.tv_contact);
        tv_shippingNIC = findViewById(R.id.tv_nic);
        tv_shippingEmail = findViewById(R.id.tv_email);
        tv_shippingProvince = findViewById(R.id.tv_province);
        tv_shippingAddress = findViewById(R.id.tv_address);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line creating our database reference.
        // on below line we are getting our modal class on which we have passed.
        shippingRVModal = (ShippingRVModal) getIntent().getSerializableExtra("shipping");
        String shippingID = "";

        if (shippingRVModal != null) {
            // on below line we are setting data to our edit text from our modal class.
            tv_shippingName.setText(shippingRVModal.getShippingName());
            tv_shippingContact.setText(shippingRVModal.getShippingContact());
            tv_shippingNIC.setText(shippingRVModal.getShippingNIC());
            tv_shippingEmail.setText(shippingRVModal.getShippingEmail());
            tv_shippingProvince.setText(shippingRVModal.getShippingProvince());
            tv_shippingAddress.setText(shippingRVModal.getShippingAddress());
            shippingID = shippingRVModal.getShippingID();
        }
        else {
            finish();
        }

        // on below line we are initialing our database reference and we are adding a child as our shipping id.
        databaseReference = firebaseDatabase.getReference("ShippingDetails").child(shippingID);
        // on below line we are adding click listener for our add button.
        btnAddShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are making our progress bar as visible.

                // on below line we are getting data from our edit text.
                String shippingName = tv_shippingName.getText().toString();
                String shippingContact = tv_shippingContact.getText().toString();
                String shippingNIC = tv_shippingNIC.getText().toString();
                String shippingEmail = tv_shippingEmail.getText().toString();
                String shippingProvince = tv_shippingProvince.getText().toString();
                String shippingAddress = tv_shippingAddress.getText().toString();
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
                    databaseReference.setValue(shippingRVModal).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            // displaying a toast message.
                            Toast.makeText(EditShippingDetails.this, "Shipping Details Updated..", Toast.LENGTH_SHORT).show();
                            // starting a main activity.
                            startActivity(new Intent(EditShippingDetails.this, MainActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // displaying a failure message on below line.
                            loadingPB.setVisibility(View.INVISIBLE);
                            Toast.makeText(EditShippingDetails.this, "Failed to update..", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        // adding a click listener for our delete button.
        btnDeleteShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(EditShippingDetails.this);
                builder.setTitle("Delete Shipping Details").setMessage("Are you sure you want to delete this?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Remove data from database
                                databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(EditShippingDetails.this, "Delete Successful!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(EditShippingDetails.this, MainActivity.class));
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(EditShippingDetails.this, "Not deleted!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Cancel delete
                        dialogInterface.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
}
