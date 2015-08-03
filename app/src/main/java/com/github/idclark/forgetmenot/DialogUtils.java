package com.github.idclark.forgetmenot;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by idclark on 8/1/15.
 */
public class DialogUtils {

    public Activity editActivity;
    public static EditText mEditDueDate;

    public DialogUtils(Activity editActivity) {
        this.editActivity = editActivity;
    }
    public void showDateAndTimeDialog(final Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_date_time,null, false);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        final Calendar calendar = Calendar.getInstance();
        mEditDueDate = (EditText) editActivity.findViewById(R.id.task_due_date);
        viewPager.setAdapter(new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                return viewPager.getChildAt(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Date";
                    case 1:
                        return "Time";
                }
                return super.getPageTitle(position);
            }

            @Override
            public int getCount() {
                return viewPager.getChildCount();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
        final TabLayout layoutTab = (TabLayout) view.findViewById(R.id.layout_tab);
        layoutTab.setupWithViewPager(viewPager);

        final AlertDialog dateAndTimeDialog = new AlertDialog.Builder(context)
                .setView(view)
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel", null)
                .create();
        dateAndTimeDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {

                /*
                    We override the default BUTTON_POSITIVE OnClickListener to prevent the dialog
                    from dismissing when the user presses OK
                 */

                dateAndTimeDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
                        new View.OnClickListener() {

                            public void onClick(View v) {
                                if (viewPager.getCurrentItem() == 0) {
                                    viewPager.setCurrentItem(1);
                                    return;
                                }

                                calendar.set(Calendar.MONTH, datePicker.getMonth());
                                calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                                calendar.set(Calendar.YEAR, datePicker.getYear());

                                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                                // Bitmask used to determine timestamp format
                                int displayMask = DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR;
                                String timestamp = DateUtils.formatDateTime(editActivity.getApplicationContext(), calendar.getTimeInMillis(), displayMask);
                                mEditDueDate.setText(timestamp);

                                // We must dismiss the dialog as we have overriden the default behavior
                                dialog.dismiss();
                            }
                        });
            }
        });

        dateAndTimeDialog.show();

    }
}
