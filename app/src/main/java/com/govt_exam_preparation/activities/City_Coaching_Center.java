package com.govt_exam_preparation.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.govt_exam_preparation.AppController;
import com.govt_exam_preparation.R;
import com.govt_exam_preparation.dialogs.DialogMsg;
import com.govt_exam_preparation.model.Coaching_List_Model;
import com.govt_exam_preparation.others.CheckNetwork;
import com.govt_exam_preparation.others.MyProgress;
import com.govt_exam_preparation.others.My_URL;
import com.govt_exam_preparation.toolbar_functionality.ToolbarOperation;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;


public class City_Coaching_Center extends AppCompatActivity {

    Dialog progress;
    TextView tv_lat_long;
    Button current_location, find_now;
    LinearLayout main_layout;

    Spinner city_spinner;
    ArrayList<String> city_list = new ArrayList<>();
    ArrayList<String> arrayCityId = new ArrayList<>();
    ArrayList<Coaching_List_Model> center_list = new ArrayList<>();

    String selectedItemText = "";

    int selectedCityId = 1;

    String provider = "", latLong = "";
    double lat = 0.0, lon = 0.0;
    String lat_str, longi_str;
    String newLocation = "";

    Snackbar snackbar;
    HintAdapter hintadapter;

    DialogMsg dialogMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_coaching_center);

        init();

        ToolbarOperation toolbarOperation = new ToolbarOperation(this);
        toolbarOperation.setMyTitleAndToolbar(getString(R.string.coaching_center));
        toolbarOperation.showBackButton(true);


        if (!new CheckNetwork(this).isNetworkOnline()) {
            dialogMsg = new DialogMsg(this);
            dialogMsg.showDialog(getString(R.string.connectionError), true, R.drawable.ic_error_black_24dp);
            return;
        }

        get_city_list();


        city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedItemText = (String) parent.getItemAtPosition(position);
                selectedCityId = Integer.parseInt(arrayCityId.get(position));


                if (!selectedItemText.equalsIgnoreCase("Select City")) {
                    tv_lat_long.setText("");
                } else
                    selectedCityId = 1;


                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {
                    // Notify the selected item text
                   /* Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();*/
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        current_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Current_Location();

                if (newLocation.equalsIgnoreCase("")) {

                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        // only for gingerbread and newer versions
                        provider = Settings.Secure.getString(getContentResolver(),
                                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                        if (!provider.contains("gps")) {
                            //dialog.displayPromptForEnablingGPS();
                            /*Intent i = new Intent(getActivity(), MyDialog.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);*/
                            MyDialog();
                        }
                    } else {
                        turnGPSOn();
                    }
                }
            }
        });

        find_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if (selected_id == 0 || newLocation.equalsIgnoreCase("")) {
                    snackbar = Snackbar
                            .make(main_layout, "Please Select City or near by location", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    if (new CheckNetwork(City_Coaching_Center.this).isNetworkOnline()) {
                        center_list.clear();
                        if (newLocation.equalsIgnoreCase(""))
                            coaching_center_list(My_URL.coaching_center_list, "", "");
                        else
                            coaching_center_list(My_URL.coaching_center_list1, lat_str, longi_str);
                    } else {
                        snackbar = Snackbar
                                .make(main_layout, "No internet connection!!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
                */


                Log.d("address", tv_lat_long.getText().toString());

                if (selectedCityId != 0 || !tv_lat_long.getText().toString().equalsIgnoreCase("")) {
                    if (new CheckNetwork(City_Coaching_Center.this).isNetworkOnline()) {
                        center_list.clear();
                        if (tv_lat_long.getText().toString().equalsIgnoreCase(""))
                            coaching_center_list(My_URL.coaching_center_list, "", "");
                        else
                            coaching_center_list(My_URL.coaching_center_list1, lat_str, longi_str);
                    } else {
                        snackbar = Snackbar
                                .make(main_layout, "No internet connection!!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } else {
                    snackbar = Snackbar
                            .make(main_layout, "Please Select City or Near by Location", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });
    }


    public void MyDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(City_Coaching_Center.this).create();

        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = "Enable either GPS or any other location"
                + " service to find current location.  Click OK to go to"
                + " location services settings to let you do so.";


        alertDialog.setMessage(message);


        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK), 0);
                alertDialog.dismiss();
            }
        });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();

    }


    public void turnGPSOn() {
        try {
            Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
            intent.putExtra("enabled", true);
            sendBroadcast(intent);

            String provider = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if (!provider.contains("gps")) {
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings",
                        "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                sendBroadcast(poke);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            if (!provider.contains("gps")) {
                //dialog.displayPromptForEnablingGPS();
                /*Intent i = new Intent(getActivity(), MyDialog.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);*/
                MyDialog();
            }
            Log.d("EXP", e.toString());
            e.printStackTrace();
        }
    }

    public void Current_Location() {
        SmartLocation.with(City_Coaching_Center.this).location()
                .oneFix()
                .start(new OnLocationUpdatedListener() {

                    @Override
                    public void onLocationUpdated(Location location) {
                        Double lat = location.getLatitude();
                        Double longg = location.getLongitude();

                        lat_str = String.valueOf(lat);
                        longi_str = String.valueOf(longg);


                       /* String lat_str = "26.273914";
                        String longi_str = "73.030934";*/
                        // 26.273914,73.030934

                        Log.d("New Location", lat + " " + longg);

                        newLocation = lat + "," + longg;
                        if (!newLocation.equalsIgnoreCase(""))
                            Address(lat, longg);

                    }
                });
    }


    public void Address(Double lat, Double longg) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(City_Coaching_Center.this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(lat, longg, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            for (int i = 0; i < addresses.size(); i++) {
                Log.d("address", addresses.get(i).getAddressLine(0));
            }

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();


            tv_lat_long.setText(address + ", " + city + ", " + state);
            //city_list.add("Select City");
            selectedItemText = "";
            selectedCityId = 1;

            hintadapter = new HintAdapter(City_Coaching_Center.this, city_list, android.R.layout.simple_spinner_item);
            hintadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            city_spinner.setAdapter(hintadapter);

            // show hint
            //city_spinner.setSelection(hintadapter.getCount());


            //tv_lat_long.setText(address + ", " + city + ", " + state);


        } catch (Exception e) {
            snackbar = Snackbar
                    .make(main_layout, "Can't find your location. Try again", Snackbar.LENGTH_LONG);
            snackbar.show();
            e.printStackTrace();
        }
    }


    public void get_city_list() {

        progress = new MyProgress(City_Coaching_Center.this).getProgressDialog("Please wait...", R.layout.dialog_progress_blue);
        progress.show();

        StringRequest request = new StringRequest(Request.Method.POST, My_URL.city_list, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                Log.d("city_response", s);

                if (s != null) {
                    s = Html.fromHtml(s).toString();
                    try {
                        JSONObject json = new JSONObject(s);
                        if (json.getString("result").equalsIgnoreCase("1")) {
                            arrayCityId.clear();
                            city_list.clear();
                            JSONArray msg_array = json.getJSONArray("msg");
                            for (int i = 0; i < msg_array.length(); i++) {
                                arrayCityId.add(msg_array.getJSONObject(i).getString("city_id"));
                                city_list.add(msg_array.getJSONObject(i).getString("city_name"));
                            }
                        }

                    } catch (Exception e) {

                    }
                }
                if (progress.isShowing())
                    progress.dismiss();

                Log.d("size", city_list.size() + "");


                hintadapter = new HintAdapter(City_Coaching_Center.this, city_list, android.R.layout.simple_spinner_item);

                city_spinner.setAdapter(hintadapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (progress.isShowing())
                    progress.dismiss();
            }
        });


        AppController.getInstance().addToRequestQueue(request, "ViewNotifications");
    }

    public void coaching_center_list(String url, final String lat, final String lng) {

        progress = new MyProgress(City_Coaching_Center.this)
                .getProgressDialog("Please wait...", R.layout.dialog_progress_blue);
        progress.show();

        Log.d("url", url);


        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        if (progress.isShowing())
                            progress.dismiss();

                        Log.d("coaching_center", s);

                        if (s != null) {
                            s = Html.fromHtml(s).toString();
                            try {
                                JSONObject json = new JSONObject(s);
                                String result = json.getString("result");
                                if (result.equalsIgnoreCase("1")) {
                                    center_list.clear();
                                    JSONArray msg_array = json.getJSONArray("msg");
                                    Log.v("ArrayLength", "" + msg_array.length());
                                    for (int i = 0; i < msg_array.length(); i++) {
                                        center_list.add(new Coaching_List_Model(
                                                msg_array.getJSONObject(i).getString("coaching_id"),
                                                msg_array.getJSONObject(i).getString("coaching_name"),
                                                msg_array.getJSONObject(i).getString("coaching_address"),
                                                msg_array.getJSONObject(i).getString("coaching_phone"),
                                                msg_array.getJSONObject(i).getString("coaching_image")));

                                        if (center_list.get(i).getImage().length() > 1) {
                                            Picasso.with(City_Coaching_Center.this)
                                                    .invalidate(My_URL.image_url + center_list.get(i).getImage());
                                        }
                                    }

                                    Intent i = new Intent(City_Coaching_Center.this, Coaching_Center_List.class);
                                    i.putParcelableArrayListExtra("center_list", center_list);
                                    startActivity(i);
                                } else {
                                    snackbar = Snackbar
                                            .make(main_layout, json.getString("msg"), Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }

                            } catch (Exception e) {
                                Log.v("Exception_", "" + e);
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (progress.isShowing())
                    progress.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                if (tv_lat_long.getText().toString().equalsIgnoreCase(""))
                    params.put("city_id", selectedCityId + "");
                else {
                    params.put("my_lat", lat);
                    params.put("my_long", lng);
                    //my_lat=26.2606419&my_long=73.0222764
                }
                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(request, "ViewCoachingCenters");
    }


    public void init() {
        city_spinner = (Spinner) findViewById(R.id.city);
        tv_lat_long = (TextView) findViewById(R.id.tv_lat_long);
        current_location = (Button) findViewById(R.id.current_location);
        find_now = (Button) findViewById(R.id.find_now);

        main_layout = (LinearLayout) findViewById(R.id.main_layout);
    }


    public class HintAdapter extends ArrayAdapter<String> {


        ArrayList<String> loc_name;

//        public HintAdapter(Context theContext, ArrayList<Employee_Model> objects) {
//            super(theContext, objects);
//        }

        public HintAdapter(Context theContext, ArrayList<String> objects, int theLayoutResId) {

            super(theContext, theLayoutResId, objects);

            loc_name = objects;
        }

        @Override
        public int getCount() {
            return arrayCityId.size();
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
            TextView texview = (TextView) view.findViewById(android.R.id.text1);


            texview.setText(loc_name.get(position));


            return view;
        }


        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view;


            view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            TextView texview = (TextView) view.findViewById(android.R.id.text1);

            texview.setText(loc_name.get(position));

            return view;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
