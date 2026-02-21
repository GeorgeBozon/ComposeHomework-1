package ru.otus.marketsample.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.BindsInstance
import dagger.Component
import ru.otus.marketsample.MainActivity
import javax.inject.Singleton

@Component(
    modules = [
        NetworkModule::class,
        DataModule::class,
        ViewModelsModule::class
    ]
)
@Singleton
interface AppComponent
{
    fun provideViewModelFactory(): ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun inject(mainActivity: MainActivity)
}