package com.example.dp.di.module

import android.content.Context
import com.example.dp.core.utils.AssetProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class UtilsModule {

    @Provides
    @Singleton
    fun provideAssetProvider(context: Context): AssetProvider {
        return AssetProvider(context)
    }
}