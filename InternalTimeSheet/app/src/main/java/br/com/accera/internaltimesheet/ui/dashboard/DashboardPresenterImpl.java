package br.com.accera.internaltimesheet.ui.dashboard;

import java.util.Calendar;

import javax.inject.Inject;

import br.com.accera.core.presentation.utilities.DateUtilFormat;
import br.com.accera.internaltimesheet.R;
import br.com.accera.internaltimesheet.ui.base.BaseTimesheetPresenter;

/**
 * Created by fobalan on 24/05/18.
 */

public class DashboardPresenterImpl extends BaseTimesheetPresenter<DashboardContract.View> implements DashboardContract.Presenter {

    @Inject
    public DashboardPresenterImpl() {
    }

    @Override
    public void receiveClick(Boolean time) {
        if (time){
            mView.setTimeIn(DateUtilFormat.getHourMinuteSecondDashboard());
        } else {
            mView.setTimeOut(DateUtilFormat.getHourMinuteSecondDashboard());
            setTimeDiff();
        }
    }

    @Override
    public void editTimeIn(int h, int m, int s) {
        mView.setTimeIn(DateUtilFormat.concatHourMinuteSecond(h,m,s));
    }

    @Override
    public void editTimeOut(int h, int m, int s) {
        mView.setTimeOut(DateUtilFormat.concatHourMinuteSecond(h,m,s));
        setTimeDiff();
    }

    private void setTimeDiff() {
        String timeDiff = getTimeDiff();
        mView.setTimeDiff(timeDiff, getColor(timeDiff));
    }

    private String getTimeDiff() {
        String hourOne = mView.getTextFromTextView(R.id.in_hour);
        String hourTwo = mView.getTextFromTextView(R.id.out_hour);
        return DateUtilFormat.getTimeDifference(hourOne, hourTwo);
    }

    private int getColor(String time){
        String[] split = time.split(":");
        int hour = 0;
        int minute = 0;

        try {
            hour = Integer.parseInt(split[0]);
            minute = Integer.parseInt(split[1]);
        } catch (NumberFormatException e){

        }

        if (hour+minute < 56){
            return R.color.pomegranate;
        } else {
            return R.color.emerald;
        }
    }

    @Override
    protected void onViewAttached() {
        super.onViewAttached();
        // Carrega a jornada

        mView.showSpecifcMessage(DateUtilFormat.getBrazilianFormatedString(Calendar.getInstance().getTime()));



    }
}
