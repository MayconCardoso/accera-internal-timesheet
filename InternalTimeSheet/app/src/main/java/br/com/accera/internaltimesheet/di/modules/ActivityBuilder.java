package br.com.accera.internaltimesheet.di.modules;

import br.com.accera.core.presentation.di.module.CoreSuggestionViewUtilModule;
import br.com.accera.core.presentation.di.scope.ViewScope;
import br.com.accera.internaltimesheet.ui.dashboard.DashboardActivity;
import br.com.accera.internaltimesheet.ui.dashboard.di.DashboardModule;
import br.com.accera.internaltimesheet.ui.main.MainActivity;
import br.com.accera.internaltimesheet.ui.main.di.MainModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author MAYCON CARDOSO on 24/05/2018.
 */
@Module
public abstract class ActivityBuilder {
    @ViewScope
    @ContributesAndroidInjector(modules = {DashboardModule.class, ViewModule.class})
    abstract DashboardActivity bindDashboardActivity();

    @ViewScope
    @ContributesAndroidInjector(modules = {MainModule.class, ViewModule.class})
    abstract MainActivity bindMainActivity();
}