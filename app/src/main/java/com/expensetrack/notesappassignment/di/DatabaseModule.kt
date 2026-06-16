package com.expensetrack.notesappassignment.di

import android.content.Context
import androidx.room.Room
import com.expensetrack.notesappassignment.data.NotesDao
import com.expensetrack.notesappassignment.data.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NotesDatabase {
        return Room.databaseBuilder(context,
            NotesDatabase::class.java,
            "notes_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNotesDao(database: NotesDatabase): NotesDao {
        return database.notesDao()
    }
}
