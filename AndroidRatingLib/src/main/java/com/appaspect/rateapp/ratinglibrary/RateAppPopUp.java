package com.appaspect.rateapp.ratinglibrary;

/**
 * Created by AppAspect on 25/12/18.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class RateAppPopUp implements View.OnClickListener
{

    private static final String TAG = RateAppPopUp.class.getSimpleName();
    private Context context;
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor editor;
    private String supportEmail;
    private String title = null;
    private String rateText = null;
    private AlertDialog alertDialog;
    private View dialogView;
    private int ratingRestriction = 3;
    private int theme=RateAppPopUp_Data.THEME_LITE_WHITE;
    private RateAppListener rateAppListener;
    private int starColor, changed_rating=5;
    private String titleToAdd="",textToAdd="",appPackageName="",versionName="";
    private String deviceMANUFACTURER="",deviceMODEL="",deviceSDK="",deviceRELEASE="";
    private LaterListener laterListener;
    private  NoThanksListener noThanksListener;
    private String str_bottom_email="",str_Subject="";
    private int header_bg_color=-1,header_text_color=-1;
    public RateAppPopUp(Context context, String supportEmail) {
        this.context = context;
        sharedPrefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();
        this.supportEmail = supportEmail;
    }

    private void build()
    {
        appPackageName = context.getPackageName();

        try
        {
           str_Subject= context.getResources().getString(R.string.app_feedback)+": "+ titleToAdd;

            deviceMANUFACTURER = Build.MANUFACTURER;
             deviceMODEL =  Build.MODEL;
             deviceSDK =  Build.VERSION.SDK;
             deviceRELEASE =  Build.VERSION.RELEASE;

            str_bottom_email=context.getResources().getString(R.string.device_title)+": "+ deviceMANUFACTURER+"\n"+
                    context.getResources().getString(R.string.device_os)+": "+ deviceMODEL+"\n"+
                    context.getResources().getString(R.string.app_version)+": "+ deviceRELEASE+"\n";

            Log.e(TAG, "deviceMANUFACTURER : " + deviceMANUFACTURER);
            Log.e(TAG, "deviceMODEL : " + deviceMODEL);
            Log.e(TAG, "deviceSDK : " + deviceSDK);
            Log.e(TAG, "deviceRELEASE : " + deviceRELEASE);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {

            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = pInfo.versionName;

            Log.e(TAG, "versionName : " + versionName);

        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        sharedPrefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);

        dialogView = inflater.inflate(R.layout.activity_rate_pop_up_lite_gray, null);


        if(theme==RateAppPopUp_Data.THEME_LITE_WHITE)
        {
            dialogView = inflater.inflate(R.layout.activity_rate_pop_up_lite_white, null);
        }
        else if(theme==RateAppPopUp_Data.THEME_LITE_GRAY)
        {
            dialogView = inflater.inflate(R.layout.activity_rate_pop_up_lite_gray, null);
        }
        else if(theme==RateAppPopUp_Data.THEME_LITE_DARK)
        {
            dialogView = inflater.inflate(R.layout.activity_rate_pop_up_lite_dark, null);
        }
        else if(theme==RateAppPopUp_Data.THEME_LITE_DARK_GRAY)
        {
            dialogView = inflater.inflate(R.layout.activity_rate_pop_up_lite_dark_gray, null);
        }
        else if(theme==RateAppPopUp_Data.THEME_DEFAULT_WHITE)
        {
            dialogView = inflater.inflate(R.layout.activity_rate_pop_up_default_white, null);
        }
        else if(theme==RateAppPopUp_Data.THEME_DEFAULT_GRAY)
        {
            dialogView = inflater.inflate(R.layout.activity_rate_pop_up_default_gray, null);
        }
        else if(theme==RateAppPopUp_Data.THEME_DEFAULT_DARK)
        {
            dialogView = inflater.inflate(R.layout.activity_rate_pop_up_default_dark, null);
        }
        else if(theme==RateAppPopUp_Data.THEME_DEFAULT_DARK_GRAY)
        {
            dialogView = inflater.inflate(R.layout.activity_rate_pop_up_default_dark_gray, null);
        }


        builder.setView(dialogView);

        LinearLayout ll_name = (LinearLayout)dialogView.findViewById(R.id.ll_name);

        TextView txt_name = (TextView)dialogView.findViewById(R.id.txt_name);
        TextView txt_message = (TextView)dialogView.findViewById(R.id.txt_message);
        TextView txt_rate_this_app = (TextView)dialogView.findViewById(R.id.txt_rate_this_app);
        TextView txt_remind_me_later = (TextView)dialogView.findViewById(R.id.txt_remind_me_later);
        TextView txt_no_thanks = (TextView)dialogView.findViewById(R.id.txt_no_thanks);
        RatingBar ratingBar = (RatingBar) dialogView.findViewById(R.id.ratingBar);

        if(header_bg_color!=-1)
        {
            ll_name.setBackgroundColor(header_bg_color);
        }

        if(header_text_color!=-1)
        {
            txt_name.setTextColor(header_text_color);
        }


        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();

        if(theme==RateAppPopUp_Data.THEME_LITE_WHITE || theme==RateAppPopUp_Data.THEME_LITE_GRAY)
        {
            stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.bg_color_dark_gray), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(1).setColorFilter(context.getResources().getColor(R.color.bg_color_dark_gray), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.bg_color_dark_gray), PorterDuff.Mode.SRC_ATOP);
        }
        else if(theme==RateAppPopUp_Data.THEME_LITE_DARK || theme==RateAppPopUp_Data.THEME_LITE_DARK_GRAY)
        {
            stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.bg_color_white), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(1).setColorFilter(context.getResources().getColor(R.color.bg_color_white), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.bg_color_white), PorterDuff.Mode.SRC_ATOP);
        }
        else if(theme==RateAppPopUp_Data.THEME_DEFAULT_WHITE || theme==RateAppPopUp_Data.THEME_DEFAULT_GRAY)
        {
            stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.bg_color_dark_gray), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(1).setColorFilter(context.getResources().getColor(R.color.bg_color_dark_gray), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.bg_color_dark_gray), PorterDuff.Mode.SRC_ATOP);
        }
        else if(theme==RateAppPopUp_Data.THEME_DEFAULT_DARK || theme==RateAppPopUp_Data.THEME_DEFAULT_DARK_GRAY)
        {
            stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.bg_color_white), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(1).setColorFilter(context.getResources().getColor(R.color.bg_color_white), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.bg_color_white), PorterDuff.Mode.SRC_ATOP);
        }



        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Log.e(TAG, "Rating changed : " + v);
                changed_rating=(int)v;
            }
        });


         titleToAdd = (title == null) ? context.getString(R.string.rate_your_app_name) : title;
        textToAdd = (rateText == null) ? context.getString(R.string.if_you_enjoy_using_app) : rateText;

        txt_name.setText(titleToAdd);
        txt_message.setText(textToAdd);

        txt_rate_this_app.setOnClickListener(this);
        txt_remind_me_later.setOnClickListener(this);
        txt_no_thanks.setOnClickListener(this);

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void disable()
    {
        editor.putBoolean(RateAppPopUp_Data.RateApp_DISABLED, true);
        editor.apply();
    }

    private void openMarket()
    {
        try
        {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    private void sendEmail()
    {

        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/email");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{supportEmail});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, str_Subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, context.getResources().getString(R.string.we_need_more_details)+"\n\n\n"+str_bottom_email);
        context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    private void show()
    {
        boolean disabled = sharedPrefs.getBoolean(RateAppPopUp_Data.RateApp_DISABLED, false);
        if (!disabled)
        {
            build();
            alertDialog.show();
        }
    }

    public void showAfter(int numberOfAccess)
    {
        build();

        if(numberOfAccess<=0)
        {
            numberOfAccess=5;
        }

        int numOfAccess = sharedPrefs.getInt(RateAppPopUp_Data.RateApp_NUMBER_OF_ACCESS, 0);

        if (numOfAccess == 0)
        {
            show();
            editor.putInt(RateAppPopUp_Data.RateApp_NUMBER_OF_ACCESS, numOfAccess + 1);
            editor.apply();
        }
        else if (numOfAccess == numberOfAccess)
        {
            show();
            editor.putInt(RateAppPopUp_Data.RateApp_NUMBER_OF_ACCESS, 0);
            editor.apply();
        }
        else
        {
            editor.putInt(RateAppPopUp_Data.RateApp_NUMBER_OF_ACCESS, numOfAccess + 1);
            editor.apply();
        }


    }


    @Override
    public void onClick(View v)
    {

        if(v.getId()==R.id.txt_rate_this_app)
        {
            if (changed_rating <= ratingRestriction)
            {
                Toast.makeText(context,str_Subject,Toast.LENGTH_LONG).show();
                sendEmail();
            }
            else
            {
                openMarket();
                disable();
            }

            if (rateAppListener != null)
                rateAppListener.onRateApp();

            alertDialog.dismiss();
        }
        else if(v.getId()==R.id.txt_remind_me_later)
        {

            editor.putInt(RateAppPopUp_Data.RateApp_NUMBER_OF_ACCESS, 1);
            editor.apply();

            if (laterListener != null)
                laterListener.onLater();

            alertDialog.dismiss();
        }
        else if(v.getId()==R.id.txt_no_thanks)
        {
            disable();
            editor.putInt(RateAppPopUp_Data.RateApp_NUMBER_OF_ACCESS, 0);
            editor.apply();

            if (noThanksListener != null)
                noThanksListener.onNoThanks();

            alertDialog.dismiss();
        }

    }

    public RateAppPopUp setHeader_Background_Color(int theme)
    {
        this.header_bg_color = theme;
        return this;

    }
    public RateAppPopUp setHeader_Text_Color(int theme)
    {
        this.header_text_color = theme;
        return this;

    }


    public RateAppPopUp setTheme(int theme)
    {
        this.theme = theme;
        return this;

    }

    public RateAppPopUp setTitle(String title)
    {
        this.title = title;
        return this;

    }

    public RateAppPopUp setStarColor(int color) {
        starColor = color;
        return this;
    }

    public RateAppPopUp setSupportEmail(String supportEmail)
    {
        this.supportEmail = supportEmail;
        return this;
    }

    /**
     * Set the ratingRestriction for the rating.
     * If the rating is >= of the ratingRestriction, the market is opened.
     *
     * @param ratingRestriction the ratingRestriction bound
     * @return the dialog
     */
    public RateAppPopUp setRatingRestriction(int ratingRestriction) {
        this.ratingRestriction = ratingRestriction;
        return this;
    }


    public RateAppPopUp setRateText(String rateText)
    {
        this.rateText = rateText;
        return this;
    }


    /**
     * Set a listener to set Later Rate this App, for example: after 3 or 4 app used.
     *
     * @param laterListener
     * @return
     */
    public RateAppPopUp setLaterListener(LaterListener laterListener)
    {
        this.laterListener = laterListener;
        return this;
    }

    /**
     * Set a listener for no rating or review.
     *
     * @param noThanksListener
     * @return
     */
    public RateAppPopUp setNoThanksListener(NoThanksListener noThanksListener)
    {
        this.noThanksListener = noThanksListener;
        return this;
    }

        /**
         * Set a listener to get Rate this App, for example for tracking purposes
         *
         * @param rateAppListener
         * @return
         */
        public RateAppPopUp setRateAppListener(RateAppListener rateAppListener)
        {
            this.rateAppListener = rateAppListener;
            return this;
        }

}

