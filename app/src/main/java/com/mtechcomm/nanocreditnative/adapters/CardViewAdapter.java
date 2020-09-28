package com.mtechcomm.nanocreditnative.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.mtechcomm.nanocreditnative.R;
import com.mtechcomm.nanocreditnative.models.CardViewModel;

import java.util.List;

public class CardViewAdapter extends PagerAdapter {

    private List<CardViewModel> cardViewModels;
    private Context context;

    public CardViewAdapter(List<CardViewModel> cardViewModels, Context context) {
        this.cardViewModels = cardViewModels;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cardViewModels.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cardview_item_layout, container, false);

        TextView txt_account_state = (TextView)view.findViewById(R.id.my_account_state);
        TextView txt_account_bal = (TextView)view.findViewById(R.id.myaccount_bal);

        txt_account_state.setText(cardViewModels.get(position).getState());
        txt_account_bal.setText(cardViewModels.get(position).getBalance());
        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
