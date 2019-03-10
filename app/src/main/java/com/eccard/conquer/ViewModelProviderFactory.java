package com.eccard.conquer;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.eccard.conquer.data.DataManager;
import com.eccard.conquer.ui.about.AboutViewModel;
import com.eccard.conquer.ui.feed.opensource.OpenSourceViewModel;
import com.eccard.conquer.ui.login.LoginViewModel;
import com.eccard.conquer.ui.splash.SplashViewModel;
import com.eccard.conquer.utils.rx.SchedulerProvider;
import com.eccard.conquer.ui.feed.FeedViewModel;
import com.eccard.conquer.ui.feed.blogs.BlogViewModel;
import com.eccard.conquer.ui.main.MainViewModel;
import com.eccard.conquer.ui.main.rating.RateUsViewModel;

import javax.inject.Inject;

/**
 * Created by jyotidubey on 22/02/19.
 */

public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

  private final DataManager dataManager;
  private final SchedulerProvider schedulerProvider;

  @Inject
  public ViewModelProviderFactory(DataManager dataManager,
      SchedulerProvider schedulerProvider) {
    this.dataManager = dataManager;
    this.schedulerProvider = schedulerProvider;
  }


  @Override
  public <T extends ViewModel> T create(Class<T> modelClass) {
    if (modelClass.isAssignableFrom(AboutViewModel.class)) {
      //noinspection unchecked
      return (T) new AboutViewModel(dataManager,schedulerProvider);
    } else if (modelClass.isAssignableFrom(FeedViewModel.class)) {
      //noinspection unchecked
      return (T) new FeedViewModel(dataManager,schedulerProvider);
    } else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
      //noinspection unchecked
      return (T) new LoginViewModel(dataManager,schedulerProvider);
    } else if (modelClass.isAssignableFrom(MainViewModel.class)) {
      //noinspection unchecked
      return (T) new MainViewModel(dataManager,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(BlogViewModel.class)) {
      //noinspection unchecked
      return (T) new BlogViewModel(dataManager,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(RateUsViewModel.class)) {
      //noinspection unchecked
      return (T) new RateUsViewModel(dataManager,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(OpenSourceViewModel.class)) {
      //noinspection unchecked
      return (T) new OpenSourceViewModel(dataManager,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(SplashViewModel.class)) {
      //noinspection unchecked
      return (T) new SplashViewModel(dataManager,schedulerProvider);
    }
    throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
  }
}