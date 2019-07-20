package com.corenet.yohady.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.corenet.yohady.model.Category;
import com.corenet.yohady.repository.CategoriesRepo;

import java.util.ArrayList;

public class CategoriesViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Category>> categories;
    private MutableLiveData<Boolean> isCategoriesLoading;
    private MutableLiveData<String> categoriesLoadingError;


    public MutableLiveData<ArrayList<Category>> getCategories(String page, String per_page,
                                                   String parent,String product,
                                                   String search,
                                                   String include,String exclude,
                                                   String slug,String hide_empty,
                                                   String order_by,String order) {
        if (categories==null)
            categories= CategoriesRepo.getInstance()
                    .getCategories(page,per_page,parent,product,search,include,exclude,slug,hide_empty,order_by,order);
        return categories;
    }

    public MutableLiveData<ArrayList<Category>> getCategories() {
        return categories;
    }

    public MutableLiveData<Boolean> getIsCategoriesLoading() {
        return CategoriesRepo.getInstance().getIsCategoriesLoading();
    }

    public MutableLiveData<String> getCategoriesLoadingError() {
        return CategoriesRepo.getInstance().getCategoriesLoadingError();
    }
}
