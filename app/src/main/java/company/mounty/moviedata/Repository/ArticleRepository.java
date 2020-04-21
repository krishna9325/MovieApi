package company.mounty.moviedata.Repository;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import company.mounty.moviedata.api.ApiClient;
import company.mounty.moviedata.api.ApiInterface;
import company.mounty.moviedata.model.ArticleResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleRepository {
    private static final String TAG = ArticleRepository.class.getSimpleName();
    private ApiInterface apiRequest;

    public ArticleRepository() {
        apiRequest = ApiClient.getApiClient().create(ApiInterface.class);
    }

    public LiveData<ArticleResponse> getMovieArticles(String Key, String Language) {
        final MutableLiveData<ArticleResponse> data = new MutableLiveData<>();
        apiRequest.getResponse(Key, Language)
                .enqueue(new Callback<ArticleResponse>() {


                    @Override
                    public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                        Log.d(TAG, "onResponse response:: " + response);



                        if (response.body() != null) {
                            data.setValue(response.body());

                            Log.d(TAG, "articles total result:: " + response.body().getTotalResults());
                            Log.d(TAG, "Total Pages: "+ response.body().getTotalPages());
                        }
                    }

                    @Override
                    public void onFailure(Call<ArticleResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
