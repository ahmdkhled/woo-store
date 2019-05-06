package com.example.woocommerce.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.woocommerce.model.Category;
import com.example.woocommerce.network.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesRepo {

    private static CategoriesRepo categoriesRepo;
    private MutableLiveData<ArrayList<Category>> categories;
    private MutableLiveData<Boolean> isCategoriesLoading=new MutableLiveData<>();
    private MutableLiveData<String> categoriesLoadingError=new MutableLiveData<>();

     public static CategoriesRepo getInstance() {
         if (categoriesRepo==null)
             categoriesRepo=new CategoriesRepo();
        return categoriesRepo;
    }

    private CategoriesRepo() {
    }

    public MutableLiveData<ArrayList<Category>> getCategories (String page, String per_page,
                                                    String parent, String product,
                                                    String search,
                                                    String include,String exclude,
                                                    String slug, String hide_empty,
                                                    String order_by, String order){

         categories=new MutableLiveData<>();
         isCategoriesLoading.setValue(true);
         RetrofitClient.getInstance()
                .getApiService()
                .getCategories(page,per_page,parent,product,search,include,exclude,slug,hide_empty,order_by,order)
                .enqueue(new Callback<ArrayList<Category>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                        categories.setValue(response.body());
                        isCategoriesLoading.setValue(false);
//                        Log.d("CATTTTT", "onResponse: "+response.body().size());
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                        categoriesLoadingError.setValue(t.getMessage());
                        isCategoriesLoading.setValue(false);
                        Log.d("CATTTTT",t.getMessage());
                    }
                });
        return categories;
    }

    public MutableLiveData<Boolean> getIsCategoriesLoading() {
        return isCategoriesLoading;
    }

    public MutableLiveData<String> getCategoriesLoadingError() {
        return categoriesLoadingError;
    }
}
