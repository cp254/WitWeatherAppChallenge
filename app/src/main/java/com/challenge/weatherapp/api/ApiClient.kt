package com.challenge.weatherapp.api

import android.provider.Telephony.TextBasedSmsColumns.BODY
import com.challenge.weatherapp.BuildConfig
import okhttp3.CertificatePinner
import okhttp3.ConnectionSpec
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

object ApiClient {
    private val retrofitClient: Retrofit.Builder by lazy {

        // Check build type & only allow logging for debug builds
        val levelType: Level = if (BuildConfig.DEBUG)
            Level.BODY else Level.NONE

        //Set network calls logging interceptor
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(levelType)


        // Perform SSL Certificate Pinning
        val certificatePinner = CertificatePinner.Builder()
            .add(BuildConfig.BUILD_HOSTNAME, BuildConfig.PIN_1)
            .add(BuildConfig.BUILD_HOSTNAME, BuildConfig.PIN_2)
            .add(BuildConfig.BUILD_HOSTNAME, BuildConfig.PIN_3)
            .build()


        // Require the client to use TLS 1.2 which is more secure
        val requireTls12 =
            ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .build()


        // Add all above configurations to OKHttpClient perform hostname verification
        val okHttpclient = OkHttpClient.Builder()
            .connectionSpecs(listOf(requireTls12))
            .addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(true)
            .hostnameVerifier(HostnameVerifier { hostname, session ->
                hostname == BuildConfig.BUILD_HOSTNAME
            })
            .certificatePinner(certificatePinner )
            .build()



        Retrofit.Builder()
            .client(okHttpclient)
            .baseUrl(BuildConfig.SERVICE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: ApiServices by lazy {
        retrofitClient
            .build()
            .create(ApiServices::class.java)
    }


}