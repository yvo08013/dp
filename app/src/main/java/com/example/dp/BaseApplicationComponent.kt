package com.example.dp

import android.app.Application
import com.example.dp.core.utils.AssetProvider
import com.example.dp.core.utils.PrefUtils
import com.example.dp.data.DataBase
import com.example.dp.di.BaseViewModelFactory
import com.example.dp.di.module.BaseApplicationModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [BaseApplicationModule::class])
interface BaseApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun baseApplicationModule(baseApplicationModule: BaseApplicationModule): Builder

        fun build(): BaseApplicationComponent
    }

    fun inject(baseApplication: BaseApplication)

    fun viewModelsFactory(): BaseViewModelFactory

    val dataBase: DataBase

    val assetProvider: AssetProvider

    val prefUtils: PrefUtils
}