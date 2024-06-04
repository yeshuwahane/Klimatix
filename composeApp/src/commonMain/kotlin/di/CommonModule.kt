package di

import data.network.WeatherRepositoryImpl
import org.koin.dsl.module
import presentation.MainViewModel

fun CommonModule () = NetworkModule() + module {

    single {
        WeatherRepositoryImpl(get())
    }


    single {
        MainViewModel(get())
    }
}