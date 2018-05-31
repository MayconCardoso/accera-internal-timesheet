package br.com.accera.internaltimesheet.ui.dashboard;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.Calendar;

import javax.inject.Inject;

import br.com.accera.core.presentation.ui.baseview.BaseActivity;
import br.com.accera.core.presentation.utilities.DataBindResolverInstance;
import br.com.accera.core.providers.network.NetworkInfoProvider;
import br.com.accera.internaltimesheet.R;
import br.com.accera.internaltimesheet.databinding.ActivityDashboardBinding;
import dagger.android.AndroidInjection;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class DashboardActivity extends BaseActivity<DashboardContract.View, DashboardContract.Presenter> implements DashboardContract.View{
    private Handler mHandler = new Handler();
    private Runnable mRunnable;
    private boolean mRunnableStopped = false;
    ActivityDashboardBinding binding;

    @Inject
    NetworkInfoProvider mAlertHelper;


    @Override
    public void onResume() {
        this.mRunnableStopped = false;
        this.startBedside();
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();
        this.mRunnableStopped = true;
    }

    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    protected DashboardContract.View getContractView() {
        return this;
    }

    @Override
    protected int getInflateView() {
        return R.layout.activity_dashboard;
    }

    @Override
    protected void onDataBindingReady(ViewDataBinding coreDataBinding) {
        binding = DataBindResolverInstance.getBinding(ActivityDashboardBinding.class, coreDataBinding);

        PushDownAnim.setPushDownAnimTo( binding.imgClock )
                .setScale( MODE_SCALE,
                        0.60f )
                .setDurationPush( PushDownAnim.DEFAULT_PUSH_DURATION )
                .setDurationRelease( PushDownAnim.DEFAULT_RELEASE_DURATION )
                .setInterpolatorPush( PushDownAnim.DEFAULT_INTERPOLATOR )
                .setInterpolatorRelease( PushDownAnim.DEFAULT_INTERPOLATOR )
                .setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick( View view ) {
                       mCorePresenter.receiveClick();
                    }
                } );
    }

    @Override
    public void showSpecifcMessage(String message) {
        binding.dataCabecalho.setText(message);
    }

    private void startBedside() {


        final Calendar calendar = Calendar.getInstance(); //instanciou o calendario do android
        // Runnable é uma interface. Consegue fazer interface pq no java é uma classe anônima. Uma classe anonima não precisa explicitamente escrever Runnable
        this.mRunnable = new Runnable() {
            @Override
            public void run() {

                if( mRunnableStopped ) return;


                calendar.setTimeInMillis( System.currentTimeMillis() ); //pegou o equivalente da hora em millisegundos

                String hourMinutesFormat = String.format( "%02d:%02d:%02d", calendar.get( Calendar.HOUR_OF_DAY ), calendar.get( Calendar.MINUTE ), calendar.get( Calendar.SECOND ) ); //HOUR_OF_DAY é a hora no formato de 24 horas.

                //  mViewHolder.mTextHourMinute.setText(hourMinutesFormat);
                //  mViewHolder.mTextSeconds.setText(secondsFormat);

                binding.horaMolde.setText( hourMinutesFormat );

                long now = SystemClock.uptimeMillis();
                long next = now + (1000 - (now % 1000)); //este calculo faz cair no milisegundo 0 do proximo segundo.

                mHandler.postAtTime( mRunnable, next );
            }
        };
        this.mRunnable.run();
    }

}