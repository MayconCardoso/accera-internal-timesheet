package br.com.accera.internaltimesheet.ui.register;

import br.com.accera.core.presentation.ui.baseview.BaseMvpContract;

/**
 * Created by LuisAlmeida on 25/05/18.
 */

public interface RegisterContract {

    interface View extends BaseMvpContract.View{

        void cleanAllErrors();
        void showSecondCard();
        void showFirstCard();
    }

    interface Presenter extends BaseMvpContract.Presenter<RegisterContract.View>{
        void firstStep(User user);
        void secondStep(User user);
        User getUser();
    }
}
