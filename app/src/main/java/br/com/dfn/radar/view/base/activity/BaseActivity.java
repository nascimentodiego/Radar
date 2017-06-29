package br.com.dfn.radar.view.base.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


/**
 * The type Base activity.
 */
/*
 * Copyright (C) 2017 Diego Figueredo do Nascimento.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    /**
     * The Fragment manager.
     */
    public FragmentManager fragmentManager;
    /**
     * The Progress dialog.
     */
    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage(getResources().getText(R.string.loading));
    }

    /**
     * Build toolbar.
     */
    protected void buildToolbar() {
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);*/
    }


    /**
     * Build fragment.
     *
     * @param savedInstanceState the saved instance state
     */
    protected void buildFragment(Bundle savedInstanceState) {

    }

    /**
     * Show progress dialog.
     */
    public void showProgressDialog() {
        progressDialog.show();
    }

    /**
     * Hide progress dialog.
     */
    public void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

}
