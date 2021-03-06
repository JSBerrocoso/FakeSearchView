/*
 * Copyright 2015 Leonardo Rossetto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.leonardoxh.fakesearchview;

import android.annotation.TargetApi;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * The main lib actor this is a custom FrameLayout
 * wrapper with an EditText with a simple interface to perform the search
 * it collects the user input and pass to an Activity or a Fragment or where you need
 *
 * @author Leonardo Rossetto
 */
public class FakeSearchView extends FrameLayout implements TextWatcher,
    TextView.OnEditorActionListener {

  private OnSearchListener searchListener;

  public FakeSearchView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public FakeSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  @TargetApi(21)
  public FakeSearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context);
  }

  public FakeSearchView(Context context) {
    super(context);
    init(context);
  }

  public void setOnSearchListener(OnSearchListener searchListener) {
    this.searchListener = searchListener;
  }

  /**
   * Inflate the layout to this FrameLayout wrapper
   * @param context
   */
  private void init(Context context) {
    View view = LayoutInflater.from(context).inflate(R.layout.fake_search_view, this, true);
    EditText wrappedEditText = (EditText) view.findViewById(R.id.wrapped_search);
    wrappedEditText.addTextChangedListener(this);
    wrappedEditText.setOnEditorActionListener(this);
  }

  @Override
  public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) { }

  @Override
  public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    if (searchListener != null) {
      searchListener.onSearch(charSequence);
    } else {
      Log.w(getClass().getName(), "SearchListener == null");
    }
  }

  @Override
  public void afterTextChanged(Editable editable) { }

  @Override public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
    if (searchListener != null) {
      searchListener.onSearchHint(textView.getText());
    } else {
      Log.w(getClass().getName(), "SearchListener == null");
    }
    return true;
  }

  /**
   * This interface is an custom method to wrapp the
   * TextWatcher implementation and provide the search constraint
   *
   * @author Leonardo Rossetto
   */
  public interface OnSearchListener {

    /**
     * This method is called every time the EditText change it content
     * @param constraint the current input data
     */
    void onSearch(CharSequence constraint);

    /**
     * This method is called when the user press the search button on the keyboard
     * @param constraint the current input data
     */
    void onSearchHint(CharSequence constraint);

  }

}
