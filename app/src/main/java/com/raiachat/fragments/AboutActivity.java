package com.raiachat.fragments;


import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.raiachat.BuildConfig;
import com.raiachat.R;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.about_round)
                .setDescription("RaiaChat is a Citizen Engagement Platform that bridges the gap between citizens and their local governments.\n" +
                        "\n" +
                        "It is all about getting everybody involved and empowering people to change the cities they live in\n" +
                        "The whole city on your smartphone! Report everyday issues to your county government, \n" +
                        "express your opinion & shape the future of your neighborhood by taking short surveys.\n" +
                        "Individual Lenders\n" +
                        "Quickly access all the services that matter, find and navigate to all nearby points-of-interest by using an interactive map.\n" +
                        " If your city is on board, all of your reports and requests will be forwarded to their state-of-the-art dashboard in real-time.\n"+
                        " This helps them assign and solve problems faster and more efficiently. Any updates on the progress of your requests is communicated " +
                        "back on your phone in real time. RaiaChat makes your voice count.\n"
                )
                .addItem(new Element().setTitle("Version "+ BuildConfig.VERSION_NAME))
                .addGroup("Connect with us")
                .addEmail("raiachat@gmail.com")
                .addWebsite("http://jobgetabu.me")
                .addTwitter("https://twitter.com/job_getabu")
                .addPlayStore("https://play.google.com/store/apps/details?id=com.job.whatsappstories")
                .create();

        setContentView(view);
    }
}
