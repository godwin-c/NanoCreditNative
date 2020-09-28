package com.mtechcomm.nanocreditnative.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.mtechcomm.nanocreditnative.classes.PhoneNumberClass;

import java.util.ArrayList;

public class PhoneCodeSpinnerAdapter extends ArrayAdapter<PhoneNumberClass> {
    private Context context;
    private ArrayList<PhoneNumberClass> phoneNumberClasses;


    public PhoneCodeSpinnerAdapter(@NonNull Context context, int resource, @NonNull ArrayList<PhoneNumberClass> phoneNumberClasses) {
        super(context, resource, phoneNumberClasses);
        this.context = context;
        this.phoneNumberClasses = phoneNumberClasses;
    }


    @Override
    public int getCount(){
        return phoneNumberClasses.size();
    }

    @Override
    public PhoneNumberClass getItem(int position){
        return phoneNumberClasses.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(phoneNumberClasses.get(position).getCode());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(phoneNumberClasses.get(position).getCode());

        return label;
    }
}
