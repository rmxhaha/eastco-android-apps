package com.sai.customerapp.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sai.customerapp.FragmentClasses.MenuInfo;
import com.sai.customerapp.Fragments.FragmentMenu;
import com.sai.customerapp.Fragments.FragmentOrder;
import com.sai.customerapp.MainActivity;
import com.sai.customerapp.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private Context context;
    private List<MenuInfo> menuInfos;

    public MenuAdapter(Context context, List<MenuInfo> menuInfos) {
        this.context = context;
        this.menuInfos = menuInfos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_menu_adapter_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        MenuInfo menuInfo = menuInfos.get(position);

        holder.textViewMenuName.setText(menuInfo.menuName);
        holder.textViewMenuDescription.setText(menuInfo.menuDescription);
        holder.textViewMenuPrice.setText(menuInfo.menuPrice);

    }


    @Override
    public int getItemCount() {
        return menuInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageViewMenuProfPic;
        public TextView textViewMenuName;
        public TextView textViewMenuDescription;
        public TextView textViewMenuPrice;

        public Button buttonDecreaseAmount;
        public TextView textViewAmount;
        public Button buttonAddAmount;
        public EditText editTextMenuDescription;
        public Button buttonMenuBuy;

        public ViewHolder(View itemView) {

            super(itemView);

            imageViewMenuProfPic = (ImageView) itemView.findViewById(R.id.imageViewMenuProfPic);
            textViewMenuName = (TextView) itemView.findViewById(R.id.textViewMenuName);
            textViewMenuDescription = (TextView) itemView.findViewById(R.id.textViewMenuDescription);
            textViewMenuPrice = (TextView) itemView.findViewById(R.id.textViewMenuPrice);

            buttonDecreaseAmount = (Button) itemView.findViewById(R.id.buttonDecreaseAmount);
            textViewAmount = (TextView) itemView.findViewById(R.id.textViewAmount);
            buttonAddAmount = (Button) itemView.findViewById(R.id.buttonAddAmount);
            editTextMenuDescription = (EditText) itemView.findViewById(R.id.editTextMenuDescription);
            buttonMenuBuy = (Button) itemView.findViewById(R.id.buttonMenuBuy);

            buttonDecreaseAmount.setOnClickListener(this);
            buttonAddAmount.setOnClickListener(this);
            buttonMenuBuy.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int currentAmount;

            switch(v.getId()) {

                case R.id.buttonDecreaseAmount:

                    currentAmount = Integer.parseInt(String.valueOf(textViewAmount.getText()));
                    currentAmount--;

                    textViewAmount.setText(String.valueOf(currentAmount));

                    break;
                case R.id.buttonAddAmount:

                    currentAmount = Integer.parseInt(String.valueOf(textViewAmount.getText()));
                    currentAmount++;

                    textViewAmount.setText(String.valueOf(currentAmount));

                    break;
                case R.id.buttonMenuBuy:

                    if (buttonDecreaseAmount.getVisibility() != View.VISIBLE
                            && textViewAmount.getVisibility() != View.VISIBLE
                            && buttonAddAmount.getVisibility() != View.VISIBLE
                            && editTextMenuDescription.getVisibility() != View.VISIBLE) {

                        // waiting for user input
                        buttonDecreaseAmount.setVisibility(View.VISIBLE);
                        textViewAmount.setVisibility(View.VISIBLE);
                        buttonAddAmount.setVisibility(View.VISIBLE);
                        editTextMenuDescription.setVisibility(View.VISIBLE);

                    } else {

                        // send the order data
                        FragmentOrder fragmentOrder = new FragmentOrder();

                        Bundle data = new Bundle();
                        data.putInt("orderAmount", Integer.parseInt(String.valueOf(textViewAmount.getText())));
                        data.putString("orderDescription", String.valueOf(editTextMenuDescription.getText()));

                        fragmentOrder.setArguments(data);

                        ((MainActivity) v.getContext()).getFragmentManager().beginTransaction()
                                .replace(R.id.Frame_Container, fragmentOrder)
                                .commit();

                    }

                    break;

            }

        }

    }
}