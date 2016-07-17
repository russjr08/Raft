package com.kronosad.android.raft;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        SharedPreferences prefs = getSharedPreferences("Authentication", MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(prefs.getString("token", null) != null) {
            openDropletActivity(prefs.getString("token", null));
            finish();
        }

        if(getIntent().getAction().equals(Intent.ACTION_VIEW)) {
            retrieveTokenAndPersist();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @OnClick(R.id.activity_login_btn)
    public void onLoginBtnClick(View v) {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(typedValue.data);

        builder.build().launchUrl(this, Uri.parse(Constants.OAUTH_URL));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void retrieveTokenAndPersist() {
        Intent intent = getIntent();
        String token = intent.getData().getQueryParameter("code");
        Log.d("Raft", "Retrieved Token: " + token);

        SharedPreferences prefs = getSharedPreferences("Authentication", MODE_PRIVATE);
        prefs.edit().putString("token", token).apply();

        openDropletActivity(token);
    }

    private void openDropletActivity(String token) {
        Intent intent = new Intent(this, DropletsActivity.class);

        intent.putExtra(Constants.AUTHENTICATION_FINISHED_WITH_TOKEN, token);

        startActivity(intent);

        finish(); // Prevent user from going back to this activity.
    }
}
