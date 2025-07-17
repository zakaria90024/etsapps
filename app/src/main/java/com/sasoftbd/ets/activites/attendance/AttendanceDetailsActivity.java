package com.sasoftbd.ets.activites.attendance;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.sasoftbd.ets.R;
import com.squareup.picasso.Picasso;


public class AttendanceDetailsActivity extends AppCompatActivity {


    ConstraintLayout cons_layout;

    ImageView imageViewLocationIcon, imagePreview;
    TextView textView59Back;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_details);

        Intent in = getIntent();


        TextView name = (TextView) findViewById(R.id.txt_name_cardno);
        name.setText(in.getStringExtra("strUSER_NAME") + " (" + in.getStringExtra("strEMP_CARD_NO") + ")");


        TextView textView_DateTime = (TextView) findViewById(R.id.textView_DateTime);
        textView_DateTime.setText(in.getStringExtra("strINSERT_DATE"));


        TextView textView_DateTimeShift = (TextView) findViewById(R.id.textView70);
        textView_DateTimeShift.setText("Shift: " + in.getStringExtra("strATTEN_SHIFT"));


        TextView textView_Type = (TextView) findViewById(R.id.textView71);
        textView_Type.setText("Type: " + in.getStringExtra("strATTEN_STATUS"));


        TextView textView_Action = (TextView) findViewById(R.id.textView74);

        if (in.getStringExtra("strACTION").equals("")) {
            textView_Action.setText("Pending");
        } else {
            textView_Action.setText(in.getStringExtra("strACTION"));
        }

        TextView textView_Comment = (TextView) findViewById(R.id.txt_comment_note);
        textView_Comment.setText(in.getStringExtra("strATTEN_COMMENTS"));


        ImageView locationIcon = (ImageView) findViewById(R.id.imagePreview);


//        byte[] imageBytess = Base64.decode(in.getStringExtra("strEMP_IMAGE"), Base64.DEFAULT);
//        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytess, 0, imageBytess.length);
//        locationIcon.setImageBitmap(decodedImage);

        //Toast.makeText(this, "dsf" + in.getStringExtra("strEMP_IMAGE"), Toast.LENGTH_SHORT).show();


        try {

            Picasso.get()
                    .load(in.getStringExtra("strEMP_IMAGE"))
                    .placeholder(R.drawable.ic_approve_svg) // Optional placeholder
                    .error(R.drawable.ic_approve_svg)             // Optional error image
                    .into(locationIcon);


            locationIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(AttendanceDetailsActivity.this, "Clicked", Toast.LENGTH_SHORT).show();

                }
            });


        } catch (Exception e) {

        }

//
//        Intent i = new Intent(AttendanceActivity.this, AttendanceDetailsActivity.class);
//        i.putExtra("strATTEN_SHIFT",attendanceModelsList.get(0).getStrATTENSHIFT());
//        i.putExtra("strATTEN_TIMEIN",attendanceModelsList.get(0).getStrATTENTIMEIN());
//        i.putExtra("strUSER_NAME",attendanceModelsList.get(0).getStrUSERNAME());
//        i.putExtra("strROLE",attendanceModelsList.get(0).getStrROLE());
//        i.putExtra("strEMP_CARD_NO",attendanceModelsList.get(0).getStrEMPCARDNO());
//        i.putExtra("strLATITUDE",attendanceModelsList.get(0).getStrLATITUDE());
//        i.putExtra("strLONGITUDE",attendanceModelsList.get(0).getStrLONGITUDE());
//        i.putExtra("strADDRESS",attendanceModelsList.get(0).getStrADDRESS());
//        i.putExtra("strACTION",attendanceModelsList.get(0).getStrACTION());
//        i.putExtra("strEMP_IMAGE",attendanceModelsList.get(0).getStrEMPIMAGE());
//        i.putExtra("strATTEN_STATUS",attendanceModelsList.get(0).getStrATTENSTATUS());
//        startActivity(i);


        imageViewLocationIcon = findViewById(R.id.locationIcon);
        textView59Back = findViewById(R.id.textView59Back);

        textView59Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttendanceDetailsActivity.super.onBackPressed();
            }
        });
        imagePreview = findViewById(R.id.imagePreview);
        imageViewLocationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(AttendanceDetailsActivity.this, "kk" + in.getStringExtra("strLATITUDE"), Toast.LENGTH_SHORT).show();


//                Intent intent = new Intent(AttendanceDetailsActivity.this, GoogleMapActivity.class);
//                intent.putExtra("lat", in.getStringExtra("strLATITUDE"));
//                intent.putExtra("long", in.getStringExtra("strLONGITUDE"));
//                startActivity(intent);
            }
        });


        imagePreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder updatedialogbuilder = new AlertDialog.Builder(AttendanceDetailsActivity.this);
                updatedialogbuilder.setCancelable(true);

                View dialogview = LayoutInflater.from(AttendanceDetailsActivity.this).inflate(R.layout.image_full_ui, null);
                updatedialogbuilder.setView(dialogview);

                updatedialogbuilder.show();
                final ImageView imageView16;
                imageView16 = (ImageView) dialogview.findViewById(R.id.imageView900);

                //byte[] imageBytess = Base64.decode(in.getStringExtra("strEMP_IMAGE"), Base64.DEFAULT);
                //Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytess, 0, imageBytess.length);
                //imageView16.setImageBitmap(decodedImage);
                //imageView16.setImageBitmap(decodedImage);

                //Button btnDone, deletebtn, cancelbtn;


                try {

                    Picasso.get()
                            .load(in.getStringExtra("strEMP_IMAGE"))
                            .placeholder(R.drawable.bgprogressbar) // Optional placeholder
                            .error(R.drawable.bgprogressbar)             // Optional error image
                            .into(imageView16);


//                    locationIcon.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Toast.makeText(AttendanceDetailsActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
//
//                        }
//                    });


                } catch (Exception e) {

                }

//
//                itemQty = dialogview.findViewById(R.id.item_qty_et);
//                itemPrice = dialogview.findViewById(R.id.item_rate_tv);
//                itemName = dialogview.findViewById(R.id.item_name_view_Et);
//                saveNotebtn = dialogview.findViewById(R.id.save_note_item_btn);
//                deletebtn = dialogview.findViewById(R.id.delete_item_btn);
//                cancelbtn = dialogview.findViewById(R.id.cancel_popup_btn);
//
//
//                itemName.setText(itemlist.getStrItemName());
//                itemQty.setText(String.valueOf(itemlist.getStrUnit()));
//                itemPrice.setText(String.valueOf(itemlist.getDblClsBalance()));

            }
        });


//
//
//        Bundle bundle = new Bundle();
//        bundle.putString("lat", "22.572645");
//        bundle.putString("long", "88.363892");
//        Fragment fragment = new MapsFragment();
//        fragment.setArguments(bundle);


//        Fragment fragmentt = new MapsFragment();
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.cons_layout, fragmentt);
//        fragmentTransaction.commit();
//
////
        //Intent in = new Intent(AttendanceDetailsActivity.this, MapsFragment.class);


    }
}
