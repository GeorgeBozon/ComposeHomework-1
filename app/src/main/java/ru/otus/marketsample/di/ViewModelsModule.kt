package ru.otus.marketsample.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import ru.otus.marketsample.ViewModelsFactory
import ru.otus.marketsample.products.feature.ProductListViewModel
import ru.otus.marketsample.promo.feature.PromoListViewModel
import kotlin.reflect.KClass

@Module
interface ViewModelsModule {

    @Binds
    fun provideViewModelFactory(factory: ViewModelsFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PromoListViewModel::class)
    fun bindPromoListViewModel(viewModel: PromoListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductListViewModel::class)
    fun bindProductViewModel(viewModel: ProductListViewModel): ViewModel
}

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)