package com.kronosad.android.raft;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private static final String OAUTH_URL = "https://cloud.digitalocean.com/v1/oauth/authorize?client_id=ecb4694ef92bcb1991d71a5ebd68af050ffe83bbfedebe811cdb1d425e914a91&redirect_uri=raft://authorize/&response_type=code";
    private static final String EXTRA_CUSTOM_TABS_SESSION = "android.support.customtabs.extra.SESSION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(OAUTH_URL));

        Bundle extras = new Bundle();
        extras.putBinder(EXTRA_CUSTOM_TABS_SESSION,
                null);
        intent.putExtras(extras);

        startActivity(intent);

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

        openDropletActivity();
    }

    private void openDropletActivity() {


        finish(); // Prevent user from going back to this activity.
    }
}
