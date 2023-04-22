package com.alvarengadev.marketplacelist.di

import android.content.Context
import androidx.room.Room
import com.alvarengadev.marketplacelist.data.database.MarketplaceDatabase
import com.alvarengadev.marketplacelist.utils.constants.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,
        MarketplaceDatabase::class.java,
        Constants.DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDaoItem(database: MarketplaceDatabase) = database.itemDao()
}
