package com.bb.retrofittest.retrofittest;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bb.retrofittest.retrofittest.api.CategoryService;
import com.bb.retrofittest.retrofittest.model.MCategory;
import com.bb.retrofittest.retrofittest.model.MCategoryPage;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity implements View.OnClickListener, Callback<MCategoryPage> {

    private Button vButton;
    private TextView vText;
    private LinearLayout vContainer;
    private RestAdapter mRestAdapter;
    private CategoryService mCategoryService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vButton = (Button) findViewById(R.id.button);
        vText = (TextView) findViewById(R.id.text_view);
        vContainer = (LinearLayout) findViewById(R.id.container);

        /**
         * Step 1: Initialize the RestAdapter
         */
        mRestAdapter = new RestAdapter.Builder()
                .setEndpoint("https://mockapi.uat.bbhosted.com")
                .build();

        /**
         * Step 2: Instantiate the services that you want to use using the RestAdapter
         * These services are interfaces, so they can very easily be mocked.
         */
        mCategoryService = mRestAdapter.create(CategoryService.class);

        vButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() != null) {
            /**
             * Step 2: Make a request using the service
             */
            mCategoryService.listCategories(v.getTag().toString(), this);
        } else {
            mCategoryService.listCategories(this);
        }
    }




    /**
     * Step 4: listen for either success or failure callback
     * Success gives you the model defined in the service's invoked method's signature
     * i.e. void listCategories(@Path("id") String categoryId, Callback<MCategoryPage> callback);
     */

    @Override
    public void success(MCategoryPage categoryPage, Response response) {
        if (categoryPage == null) {
            Toast.makeText(this, "This must be a /categories/id/products route.", Toast.LENGTH_SHORT).show();
            return;
        }
        vContainer.removeAllViews();
        TextView tv = new TextView(MainActivity.this);
        tv.setText(categoryPage.title);
        vContainer.addView(tv);
        for(MCategory category : categoryPage.categories) {
            Button b = new Button(MainActivity.this);
            b.setText(category.title);
            b.setTag(category.id);
            b.setOnClickListener(this);
            vContainer.addView(b);
        }
    }

    @Override
    public void failure(RetrofitError error) {
        vContainer.removeAllViews();
        TextView tv = new TextView(MainActivity.this);
        tv.setText(error.toString());
        vContainer.addView(tv);
    }
}
