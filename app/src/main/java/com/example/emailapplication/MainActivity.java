package com.example.emailapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EmailAdapter.EmailItemClicked {


    private static final String EMAIL_ITEM ="email item";
    private List<EmailItem> list = generateEmailList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycle_view_email);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        EmailAdapter emailAdapter = new EmailAdapter(list, this, this);
        recyclerView.setAdapter(emailAdapter);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewEmailActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void itemClickedCallback(int itemPosition) {
        Intent intent = new Intent(this, DetaleEmailActivity.class);
        intent.putExtra(EMAIL_ITEM, list.get(itemPosition));
        startActivity(intent);
    }

    private List<EmailItem> generateEmailList() {
        List<EmailItem> list = new ArrayList<>();
        list.add(new EmailItem("https://storage.vsemayki.ru/images/0/0/674/674416/previews/people_1_pad_front_white_500.jpg", "Metallica", "World wired Tour", "World wired Tour by Metallica still going on!!! Metallica is waiting for you!!!", "Feb 17"));
        list.add(new EmailItem("https://www.clipartmax.com/png/middle/136-1362492_scorpions-logo-scorpion-band-logo.png", "Scorpions", "Our rock band is 54 years today!", "So, if you wanna to join our concert, you must be 54 years old xD", "Jun 4"));
        list.add(new EmailItem("https://banner2.kisspng.com/20180420/hew/kisspng-logo-queen-black-and-white-queen-clipart-5ad97ba9048851.9571282415242024090186.jpg", "Queen", "Do you want to have a nice evenning with your girlfriend?", "Join Royal Pub with your baby at 7 p.m.", "Feb 23"));
        list.add(new EmailItem("data:image/jpeg;base64,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", "AC/DC", "Want to have strange hair?", "Want to have strange hair? Listen to AC/DC!!", "Nov 17"));
        list.add(new EmailItem("https://photoprint.in.ua/public/products/5a4/14d/item/circle-1763_main.o.jpg", "For Jarik", "Interesting? Don't go here! XD", "Oooooooooooooooooooooooooooooooooooooooooooooo Nooooooooooooooooooooooooooooooooooooo. Ok. Subscribe on my instagram: justrealkurt. I'm Kurt Cobain)))", "today"));
        return list;
    }



}