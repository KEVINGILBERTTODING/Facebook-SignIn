package com.example.facebook_login_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class DashboardActivity extends AppCompatActivity {

    Button btnLogOut;
    TextView tvUsername;
    ImageView imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        btnLogOut   =   findViewById(R.id.btnLogOut);
        tvUsername  =   findViewById(R.id.username);
        imgProfile  =   findViewById(R.id.imgProfile);




        AccessToken accessToken  = AccessToken.getCurrentAccessToken();

        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {

            @Override
            public void onCompleted( JSONObject jsonObject, GraphResponse graphResponse) {
                try {


                    String fullName = jsonObject.getString("name");
                    String url = jsonObject.getJSONObject("picture").getJSONObject("data").getString("url");

                    tvUsername.setText(fullName);
                    Picasso.get().load(url).into(imgProfile);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
    });

        Bundle parameters=new Bundle();
        parameters.putString("fields","id,name,link,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();




        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                startActivity(new Intent(DashboardActivity.this, MainActivity.class));
                finish();
            }

        });
    }
}