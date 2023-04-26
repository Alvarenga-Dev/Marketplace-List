package com.alvarengadev.marketplacelist.di

import android.content.Context
import androidx.room.Room
import com.alvarengadev.marketplacelist.data.database.MarketplaceDatabase
import com.alvarengadev.marketplacelist.data.database.dao.ItemDao
import com.alvarengadev.marketplacelist.data.model.mapper.IItemMapper
import com.alvarengadev.marketplacelist.data.model.mapper.ItemMapper
import com.alvarengadev.marketplacelist.repository.IItemRepository
import com.alvarengadev.marketplacelist.repository.ItemRepository
import com.alvarengadev.marketplacelist.utils.constants.Constants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
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

@InstallIn(SingletonComponent::class)
@Module
object MapperModule {

    @Singleton
    @Provides
    fun provideMapper(): IItemMapper = ItemMapper()
}

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        dao: ItemDao,
        mapper: ItemMapper
    ): IItemRepository = ItemRepository(dao, mapper)
}
