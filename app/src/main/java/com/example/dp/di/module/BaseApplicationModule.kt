package com.example.dp.di.module

import dagger.Module
import dagger.android.AndroidInjectionModule


@Module(
    includes = [
        ContextModule::class,
        ViewModelModule::class,
        ContextModule::class,
        RoomModule::class,
        UtilsModule::class,
        AndroidInjectionModule::class
    ]
)
class BaseApplicationModule