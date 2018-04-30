@file:JvmName("CustomGlideModule")

package com.swarawan.corelibrary.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.GlideModule
import com.swarawan.corelibrary.CommonDeps
import com.swarawan.corelibrary.extensions.clazz
import com.swarawan.corelibrary.log.CoreLog
import com.swarawan.corelibrary.CommonDepsProvider
import java.io.InputStream

/**
 * Created by rioswarawan on 2/11/18.
 */
class CustomGlideModule : GlideModule {

    private val TAG = clazz<CustomGlideModule>().simpleName

    companion object {
        val IMAGE_CACHE_SIZE = 30 * 1024 * 1024
        val CACHE_NAME = "swarawan-cached-img"
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDiskCache(ExternalCacheDiskCacheFactory(context, CACHE_NAME, IMAGE_CACHE_SIZE))
    }

    override fun registerComponents(context: Context, glide: Glide) {
        val commonDeps: CommonDeps

        try {
            commonDeps = (context.applicationContext as CommonDepsProvider).getCommonDeps()
        } catch (ex: ClassCastException) {
            CoreLog.e(TAG, ex)
            throw ex
        }

        val nonCachedClient = commonDeps.glideNonCacheOkHttpClient()

        glide.register(clazz<GlideUrl>(), clazz<InputStream>(), OkHttpUrlLoader.Factory(nonCachedClient))
    }
}