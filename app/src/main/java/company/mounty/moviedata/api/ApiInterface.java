package company.mounty.moviedata.api;

import company.mounty.moviedata.model.ArticleResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top_rated")
    Call<ArticleResponse> getResponse(@Query("api_key") String apiKey, @Query("language") String language);

}
