package com.mobiledevpro.app.ui.mainscreen.viewmodel

import androidx.lifecycle.*
import com.mobiledevpro.app.common.BaseViewModel
import com.mobiledevpro.app.common.Event
import com.mobiledevpro.app.utils.dateToSting
import com.mobiledevpro.app.utils.toDecimalFormat
import com.mobiledevpro.domain.model.Country
import com.mobiledevpro.domain.totaldata.TotalDataInteractor
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

/**
 * ViewModel for main fragment
 *
 * Created by Dmitriy Chernysh
 *
 * http://androiddev.pro
 *
 * #MobileDevPro
 */
class TotalViewModel(private val interactor: TotalDataInteractor) : BaseViewModel(),
    LifecycleObserver {

    private val _isShowProgressTotalConfirmed = MutableLiveData<Boolean>()
    val isShowProgressTotalConfirmed: LiveData<Boolean> = _isShowProgressTotalConfirmed

    private val _isShowProgressTotalDeaths = MutableLiveData<Boolean>()
    val isShowProgressTotalDeaths: LiveData<Boolean> = _isShowProgressTotalDeaths

    private val _isShowProgressTotalRecovered = MutableLiveData<Boolean>()
    val isShowProgressTotalRecovered: LiveData<Boolean> = _isShowProgressTotalRecovered

    private val _totalConfirmed = MutableLiveData<String>()
    val totalConfirmed: LiveData<String> = _totalConfirmed

    private val _totalDeaths = MutableLiveData<String>()
    val totalDeaths: LiveData<String> = _totalDeaths

    private val _totalRecovered = MutableLiveData<String>()
    val totalRecovered: LiveData<String> = _totalRecovered

    private val _updateTime = MutableLiveData<String>()
    val updateTime: LiveData<String> = _updateTime

    private val _countriesList = MutableLiveData<ArrayList<Country>>()
    val countriesList: LiveData<ArrayList<Country>> = _countriesList

    private val _eventNavigateTo = MutableLiveData<Event<Navigation>>()
    val eventNavigateTo: LiveData<Event<Navigation>> = _eventNavigateTo


    init {
        observeTotalValues()
        observeCountriesList()
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStartView() {
        interactor.apply {
            refreshTotalData()
                .subscribeBy { /* do nothing */ }
                .addTo(subscriptions)

            refreshCountriesData()
                .subscribeBy { /* do nothing */ }
                .addTo(subscriptions)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStopView() {
        //do something if needed
    }

    fun onClickViewByCountry() {
        _eventNavigateTo.value =
            Event(Navigation.NAVIGATE_TO_COUNTRIES_LIST)
    }

    private fun observeTotalValues() {
        interactor.observeTotalData()
            .doOnSubscribe {
                _isShowProgressTotalConfirmed.value = true
                _isShowProgressTotalDeaths.value = true
                _isShowProgressTotalRecovered.value = true
            }
            .subscribeBy { total ->
                total.apply {
                    _totalConfirmed.value = confirmed.toDecimalFormat()
                    _totalDeaths.value = deaths.toDecimalFormat()
                    _totalRecovered.value = recovered.toDecimalFormat()
                    _updateTime.value = updateTime.dateToSting()

                    _isShowProgressTotalConfirmed.value = confirmed < 0
                    _isShowProgressTotalDeaths.value = confirmed < 0
                    _isShowProgressTotalRecovered.value = confirmed < 0
                }
            }
            .addTo(subscriptions)
    }

    private fun observeCountriesList() {
        interactor.observeCountriesListData()
            .subscribeBy { countries ->
                _countriesList.value = countries
            }
            .addTo(subscriptions)
    }


    enum class Navigation {
        NAVIGATE_TO_COUNTRIES_LIST
    }
}