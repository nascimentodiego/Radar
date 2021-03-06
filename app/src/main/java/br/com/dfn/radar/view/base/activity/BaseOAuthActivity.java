package br.com.dfn.radar.view.base.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.util.DebugUtils;

import com.facebook.AccessToken;

import br.com.dfn.radar.view.login.LoginActivity;

/**
 * The type Base o auth activity.
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
public class BaseOAuthActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (AccessToken.getCurrentAccessToken() == null) {
            logout();
        }
    }


    /**
     * Logout.
     */
    protected void logout() {
        Intent it = new Intent(this, LoginActivity.class);
        startActivity(it);
        finish();
    }
}
