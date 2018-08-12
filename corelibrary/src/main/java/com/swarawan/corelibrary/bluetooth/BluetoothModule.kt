package com.swarawan.corelibrary.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothClassicService
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothConfiguration
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothWriter
import com.swarawan.corelibrary.R
import dagger.Module
import dagger.Provides
import java.util.UUID
import javax.inject.Singleton

/**
 * Created by Rio Swarawn on 8/12/18.
 */
@Module
class BluetoothModule {

    @Provides
    @Singleton
    fun providesBluetoothAdapter(context: Context): BluetoothAdapter {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        return bluetoothManager.adapter
    }

    @Provides
    @Singleton
    fun providesBluetooth(ctx: Context): BluetoothConfiguration = BluetoothConfiguration().apply {
        context = ctx
        bluetoothServiceClass = BluetoothClassicService::class.java
        bufferSize = 1024
        characterDelimiter = '\n'
        deviceName = ctx.getString(R.string.app_name)
        uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
    }

    @Provides
    @Singleton
    fun providesBluetoothService(configuration: BluetoothConfiguration): BluetoothService {
        BluetoothService.init(configuration)
        return BluetoothService.getDefaultInstance()
    }


    @Provides
    @Singleton
    fun providesBluetoothWriter(service: BluetoothService): BluetoothWriter = BluetoothWriter(service)
}