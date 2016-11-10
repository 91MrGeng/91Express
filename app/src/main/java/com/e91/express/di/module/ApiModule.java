package com.e91.express.di.module;

import android.content.Context;

import com.cea.celibrary.utils.L;
import com.cea.celibrary.utils.NetUtils;
import com.e91.express.api.ExpressApi;
import com.e91.express.common.CEConstants;
import com.e91.express.di.qualifier.AppContext;
import com.e91.express.network.GBRxJavaCallAdapterFactory;
import com.github.pwittchen.prefser.library.Prefser;
import com.google.gson.Gson;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Converter;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * @author devin
 * @Class ApiModule
 * @Date 16/5/1
 */

@Module(includes = AppModule.class)
public class ApiModule {
    @Provides
    @Singleton
    public ExpressApi provideRetrofit(OkHttpClient client, Converter.Factory converter) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CEConstants.Url.BASE_URL)
                .addConverterFactory(converter)
                .addCallAdapterFactory(GBRxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(ExpressApi.class);
    }

    @Provides
    @Singleton
    public OkHttpClient provideClient(List<Interceptor> interceptors, @AppContext Context context) {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(CEConstants.Tools.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        client.setWriteTimeout(CEConstants.Tools.WRITE_TIMEOUT, TimeUnit.MILLISECONDS);
        client.setReadTimeout(CEConstants.Tools.READ_TIMEOUT, TimeUnit.MILLISECONDS);
        client.networkInterceptors().addAll(interceptors);
        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        client.setCache(cache);
        return client;
    }

    @Provides
    @Singleton
    public List<Interceptor> provideInterceptors(final Prefser prefser, @AppContext final Context context) {
        List<Interceptor> list = new ArrayList<>();
        /**
         * 日志输出
         */
        Interceptor loggingInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                long t1 = System.nanoTime();
//                L.i(String.format("Sending request %s on %s%n%s",
//                        request.url(), chain.connection(), request.headers()));

                Response response = chain.proceed(request);

                long t2 = System.nanoTime();

                L.i(String.format("Received response for %s in %.1fms%n%s",
                        response.request().url(), (t2 - t1) / 1e6d, response.message()));


                return response;
            }
        };
        /**
         * 提交数据
         */
        // TODO: 16/5/2 未完成 提交数据，要看后台传输接口如何
        System.getProperty("http.agent");

        Interceptor authInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .build();

                //上传文件
                if (request.header("Content-Type") != null && request.header("Content-Type").contains("multipart")) {
                    return chain.proceed(request);
                }
                if (request.method().equals("GET")) {

                } else {
                    if (request.body() != null) { //DELETE 请求体为空 临时解决

                    }
                }
                return chain.proceed(request.newBuilder().build());
            }
        };

        Interceptor encryptInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
//                L.d(response.code() + "  state code");

                if (response.headers().get("Server-Encrypt") != null) {
//                    L.i("需要解密");
//                    if (response.headers().get("Server-Encrypt").equalsIgnoreCase("1")) {
//                        Response response1 = response.newBuilder().body(responseBody).build();
//                        return response1;
//                    }else {
//                       return response;
//                    }
                }
                return response;
            }
        };
        /**
         * 缓存数据
         */
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetUtils.isConnected(context)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                    L.e("no network");
                }
                Response originalResponse = chain.proceed(request);
                if (NetUtils.isConnected(context)) {
                    String cacheControl = originalResponse.cacheControl().toString();
                    return originalResponse.newBuilder()
                            .header("Cache-Control", cacheControl)
                            .removeHeader("Pragma")//??
                            .build();
                } else {
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                            .removeHeader("Pragma")//??
                            .build();
                }
            }
        };

        list.add(loggingInterceptor);
        list.add(authInterceptor);
        list.add(encryptInterceptor);
        list.add(cacheInterceptor);
        return list;
    }

    @Provides
    @Singleton
    public Converter.Factory provideConverter(Gson gson) {
        return GsonConverterFactory.create(gson);
    }
}
