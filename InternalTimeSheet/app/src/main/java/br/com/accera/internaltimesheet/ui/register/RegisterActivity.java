package br.com.accera.internaltimesheet.ui.register;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import br.com.accera.core.presentation.ui.baseview.BaseActivity;
import br.com.accera.core.presentation.utilities.DataBindResolverInstance;
import br.com.accera.core.presentation.utilities.DateUtil;
import br.com.accera.internaltimesheet.R;
import br.com.accera.internaltimesheet.databinding.ActivityRegisterBinding;
import br.com.accera.internaltimesheet.ui.animation.PushDownAnimHelper;
import br.com.accera.internaltimesheet.ui.helpers.DateTimeDialogHelper;

public class RegisterActivity extends BaseActivity<RegisterContract.View, RegisterContract.Presenter> implements RegisterContract.View {

    ActivityRegisterBinding binding;
    Boolean isSecondStep = false;

    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    protected RegisterContract.View getContractView() {
        return this;
    }

    @Override
    protected int getInflateView() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void cleanAllErrors(){
        binding.firstCard.name.setError(null);
        binding.firstCard.startJourney.setError(null);
        binding.firstCard.startInterval.setError(null);
        binding.firstCard.endInterval.setError(null);
        binding.firstCard.endJourney.setError(null);
        binding.secondCard.email.setError(null);
        binding.secondCard.password.setError(null);
        binding.secondCard.passwordTwo.setError(null);
    }

    @Override
    public void showSecondCard() {
        binding.firstCard.registerCard.setVisibility(View.GONE);
        binding.secondCard.registerCard2.setVisibility(View.VISIBLE);
        binding.firstButton.setVisibility(View.GONE);
        binding.secondButton.setVisibility(View.VISIBLE);
        isSecondStep = true;
    }

    @Override
    public void showFirstCard() {
        binding.firstCard.registerCard.setVisibility(View.VISIBLE);
        binding.secondCard.registerCard2.setVisibility(View.GONE);
        binding.firstButton.setVisibility(View.VISIBLE);
        binding.secondButton.setVisibility(View.GONE);
        isSecondStep = false;
    }

    @Override
    protected void onDataBindingReady(ViewDataBinding coreDataBinding) {
        int colorDialog = mResourceHelper.getColor(R.color.pumpkin);
        String datepickerdialog = "Datepickerdialog";
        String timepickerdialog = "Timepickerdialog";
        binding = DataBindResolverInstance.getBinding(ActivityRegisterBinding.class, coreDataBinding);
        binding.setCadastro(mCorePresenter.getUser());

        PushDownAnimHelper.createDefault(binding.secondButton, v -> mCorePresenter.secondStep(getUserRegister()));

        PushDownAnimHelper.createDefault(binding.firstButton, v -> mCorePresenter.firstStep(getUserRegister()));

        binding.firstCard.startJourney.setOnClickListener(v -> DateTimeDialogHelper.DatePickerDialogDefault(colorDialog,
                (view, year, monthOfYear, dayOfMonth) -> binding.firstCard.startJourney.setText(DateUtil.concatDayMonthYear(dayOfMonth,monthOfYear,year)))
                .show(getFragmentManager(), datepickerdialog)
        );

        binding.firstCard.endJourney.setOnClickListener(v -> DateTimeDialogHelper.DatePickerDialogDefault(colorDialog,
                (view, year, monthOfYear, dayOfMonth) -> binding.firstCard.endJourney.setText(DateUtil.concatDayMonthYear(dayOfMonth, monthOfYear, year)))
                .show(getFragmentManager(), datepickerdialog)
        );

        binding.firstCard.endInterval.setOnClickListener(v -> DateTimeDialogHelper.showTimePickerDialogDefault(colorDialog,
                (view, hourOfDay, minute, second) -> binding.firstCard.endInterval.setText(DateUtil.concatHourMinuteSecond(hourOfDay,minute,0)))
                .show(getFragmentManager(), timepickerdialog)
        );

        binding.firstCard.startInterval.setOnClickListener(v -> DateTimeDialogHelper.showTimePickerDialogDefault(colorDialog,
                (view, hourOfDay, minute, second) -> binding.firstCard.startInterval.setText(DateUtil.concatHourMinuteSecond(hourOfDay,minute,0)))
                .show(getFragmentManager(), timepickerdialog)
        );
    }

    @Override
    public void onBackPressed() {
        if (isSecondStep) {
            showFirstCard();
            return;
        }
        super.onBackPressed();
    }

    private User getUserRegister(){
        return binding.getCadastro();
    }

}