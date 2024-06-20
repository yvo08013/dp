package com.example.dp

import android.app.Application
import com.example.dp.di.module.BaseApplicationModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class BaseApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    lateinit var applicationComponent: BaseApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerBaseApplicationComponent.builder().application(this)
            .baseApplicationModule(BaseApplicationModule()).build()
        applicationComponent.inject(this)
    }
}