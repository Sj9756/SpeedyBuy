package com.example.speedybuy.fragmants;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.speedybuy.Adapters.Adapter_home_fragment;
import com.example.speedybuy.Adapters.Items_list;
import com.example.speedybuy.R;

import java.util.ArrayList;

public class Fragment_home extends Fragment {
    ArrayList<Items_list> itemems=new ArrayList<>();
    SearchView searchView;
    CardView cardView;

    RecyclerView recyclerview_home_fragmet;


    public Fragment_home() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_home, container, false);
        recyclerview_home_fragmet=view.findViewById(R.id.recyclerview_home_fragment);
        setItemems();
        searchView=view.findViewById(R.id.search);
        cardView=view.findViewById(R.id.search_card);
        Adapter_home_fragment ad=new Adapter_home_fragment(requireContext(),itemems);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ad.getFilter().filter(newText);
                return true;
            }
        });

        recyclerview_home_fragmet.setLayoutManager(new GridLayoutManager(requireContext(),2));
        recyclerview_home_fragmet.setAdapter(ad);

        return view;
    }
    private void setItemems(){

        String url="https://rukminim2.flixcart.com/image/832/832/xif0q/t-shirt/u/z/z/m-gucci-tshirt-sti-original-imagzqfh7y4efene.jpeg?q=70&crop=false";
        String url1="https://rukminim2.flixcart.com/image/832/832/l2tcfbk0/t-shirt/q/b/d/4xl-alkcvsgfj80159-allen-solly-original-image2rxvdfpzpt5.jpeg?q=70&crop=false";
        String url2="https://rukminim2.flixcart.com/image/832/832/xif0q/t-shirt/t/2/4/xxs-t595-bl-eyebogler-original-imagkjwhehffcgfg.jpeg?q=70&crop=false";
        String url3="https://rukminim2.flixcart.com/image/832/832/xif0q/t-shirt/f/w/9/xxl-t599-jasbl-eyebogler-original-imagxc23qfgn8yxw.jpeg?q=70&crop=false";
        String url4="https://rukminim2.flixcart.com/image/832/832/xif0q/t-shirt/w/x/p/m-oversizetsrt-101-kajaru-original-imagwgnneyfpkmhu.jpeg?q=70&crop=false";
        String url5="https://rukminim2.flixcart.com/image/832/832/xif0q/shirt/v/d/i/m-men-slim-fit-solid-mandarin-collar-casual-shirt-bluematrix-original-imagr5hzamp6hjuz.jpeg?q=70&crop=false";
        String url6="https://rukminim2.flixcart.com/image/832/832/xif0q/t-shirt/a/5/f/xxl-ask-005-ausk-original-imagqaggr2aacyzy.jpeg?q=70&crop=false";
        String url7="https://rukminim2.flixcart.com/image/832/832/xif0q/t-shirt/l/z/3/s-bul-23bds-7bl-bullmer-original-imagrhftdtfcqrta.jpeg?q=70&crop=false";
        String url8="https://rukminim2.flixcart.com/image/832/832/xif0q/t-shirt/m/i/r/l-kb2k-ronaldotop-7-2021manchesterred-privic-original-imagybcx8wbwxpub.jpeg?q=70&crop=false";


        Items_list List1=new Items_list(url,"BABE","Men Regular Fit Printed Spread Collar Casual Shirt  (Pack of 2)","₹423",2.5f);
        Items_list List2=new Items_list(url1,"Allen Solly","Men Regular Fit Printed Spread Collar Casual Shirt  (Pack of 2)","₹523",5f);
        Items_list List3=new Items_list(url2,"EyeBogler","Men Regular Fit Printed Spread Collar Casual Shirt  (Pack of 2)","₹483",1.5f);
        Items_list List4=new Items_list(url3,"sti","Men Regular Fit Printed Spread Collar Casual Shirt  (Pack of 2)","₹312",3.5f);
        Items_list List5=new Items_list(url4,"KAJARU","Men Regular Fit Printed Spread Collar Casual Shirt  (Pack of 2)","₹4783",4.5f);
        Items_list List6=new Items_list(url5,"Blue dove","Men Regular Fit Printed Spread Collar Casual Shirt  (Pack of 2)","₹523",0.5f);
        Items_list List7=new Items_list(url6,"Praizy","Men Regular Fit Printed Spread Collar Casual Shirt  (Pack of 2)","₹4523",2.5f);
        Items_list List8=new Items_list(url7,"AUSK ","Men Regular Fit Printed Spread Collar Casual Shirt  (Pack of 2)","₹4853",1.5f);

        itemems.add(List1);
        itemems.add(List2);
        itemems.add(List3);
        itemems.add(List4);
        itemems.add(List5);
        itemems.add(List6);
        itemems.add(List7);
        itemems.add(List8);
    }
}