package com.hydinin.base_module.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hydinin.base_module.R;
import com.hydinin.base_module.utils.StatusBarUtil;


public class TitleView {
    private View headerView;

    private ImageView left_image, right_image;
    private TextView left_text, right_text, title_text;
    private RelativeLayout head, titleLayout, search_layout;
    private EditText searchText;
    private TextView cancle_text;

    private Context context;

    public TitleView(Context context, View view) {
        this.headerView = view;
        left_image = (ImageView) headerView.findViewById(R.id.header_img_left);
        right_image = (ImageView) headerView.findViewById(R.id.header_img_tight);
        left_text = (TextView) headerView.findViewById(R.id.header_back);
        right_text = (TextView) headerView.findViewById(R.id.header_btn_right_txt);
        title_text = (TextView) headerView.findViewById(R.id.header_title_txt);

        head = (RelativeLayout) headerView.findViewById(R.id.header);
        titleLayout = (RelativeLayout) headerView.findViewById(R.id.header_title_layout);
        search_layout = (RelativeLayout) headerView.findViewById(R.id.header_search_layout);
        searchText = (EditText) headerView.findViewById(R.id.header_search_edit);
        cancle_text = (TextView) headerView.findViewById(R.id.header_search_cancle_txt);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int barHeight = StatusBarUtil.getStatusBarHeight(context);
            head.setPadding(0, barHeight, 0, 0);
        }

    }

    public void setLeftImage(int drawable, View.OnClickListener listener) {
        left_image.setVisibility(View.VISIBLE);
        left_text.setVisibility(View.GONE);
        left_image.setImageResource(drawable);
        if (listener != null)
            left_image.setOnClickListener(listener);
    }

    public void setLeftImage(int drawable) {
        left_image.setVisibility(View.VISIBLE);
        left_text.setVisibility(View.GONE);
        left_image.setImageResource(drawable);
    }

    public void setLeft_text(String content, View.OnClickListener listener) {
        left_image.setVisibility(View.GONE);
        left_text.setVisibility(View.VISIBLE);
        left_text.setText(content);
        if (listener != null)
            left_text.setOnClickListener(listener);
    }


    public void setTitle(String content) {
        title_text.setVisibility(View.VISIBLE);
        title_text.setText(content);
    }

    public void setRightImage(int drawable, View.OnClickListener listener) {
        right_image.setVisibility(View.VISIBLE);
        right_text.setVisibility(View.GONE);
        right_image.setImageResource(drawable);
        if (listener != null)
            right_image.setOnClickListener(listener);
    }

    public void setRightImage(int drawable) {
        right_image.setVisibility(View.VISIBLE);
        right_text.setVisibility(View.GONE);
        right_image.setImageResource(drawable);
    }

    public void setRight_text(String content, View.OnClickListener listener) {
        right_image.setVisibility(View.GONE);
        right_text.setVisibility(View.VISIBLE);
        right_text.setText(content);
        if (listener != null)
            right_text.setOnClickListener(listener);
    }

    public void setRight_text(String content) {
        right_image.setVisibility(View.GONE);
        right_text.setVisibility(View.VISIBLE);
        right_text.setText(content);
    }


    public void setLeftDrawableText(Drawable drawable, String text) {
        left_image.setVisibility(View.GONE);
        left_text.setVisibility(View.VISIBLE);
        left_text.setText(text);
/// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        left_text.setCompoundDrawablePadding(5);
        left_text.setCompoundDrawables(drawable, null, null, null);
    }

    public void setRightDrawableText(Drawable drawable, String text) {
        right_image.setVisibility(View.GONE);
        right_text.setVisibility(View.VISIBLE);
        right_text.setText(text);
/// 这一步必须要做,否则不会显示.
        right_text.setCompoundDrawablePadding(5);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        right_text.setCompoundDrawables(null, null, drawable, null);
    }

    public void setRightDrawableText(Drawable drawable, String text, int color, View.OnClickListener clickListener) {
        right_image.setVisibility(View.GONE);
        right_text.setVisibility(View.VISIBLE);
        right_text.setText(text);
        right_text.setTextColor(color);
        right_text.setOnClickListener(clickListener);
/// 这一步必须要做,否则不会显示.
        right_text.setCompoundDrawablePadding(5);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        right_text.setCompoundDrawables(null, null, drawable, null);
    }
    public void setRightDrawableText(Drawable drawable, String text, View.OnClickListener clickListener) {
        right_image.setVisibility(View.GONE);
        right_text.setVisibility(View.VISIBLE);
        right_text.setText(text);
        right_text.setOnClickListener(clickListener);
/// 这一步必须要做,否则不会显示.
        right_text.setCompoundDrawablePadding(5);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        right_text.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 显示搜索框
     */
    public void showSearchLayout() {
        titleLayout.setVisibility(View.INVISIBLE);
        search_layout.setVisibility(View.VISIBLE);
    }

    /**
     * 设置搜索框和取消建的监听
     *
     * @param listener
     */
    public void setEdittextListener(TextView.OnEditorActionListener keyListener, View.OnClickListener listener) {
        searchText.setOnEditorActionListener(keyListener);
        cancle_text.setOnClickListener(listener);
    }

    public EditText getSearchText() {
        return searchText;
    }

    public void setSearchText(EditText searchText) {
        this.searchText = searchText;
    }

    public View getHeaderView() {
        return headerView;
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
    }

    public ImageView getLeft_image() {
        return left_image;
    }

    public void setLeft_image(ImageView left_image) {
        this.left_image = left_image;
    }

    public ImageView getRight_image() {
        return right_image;
    }

    public void setRight_image(ImageView right_image) {
        this.right_image = right_image;
    }

    public TextView getLeft_text() {
        return left_text;
    }

    public void setLeft_text(TextView left_text) {
        this.left_text = left_text;
    }

    public TextView getRight_text() {
        return right_text;
    }

    public void setRight_text(TextView right_text) {
        this.right_text = right_text;
    }

    public TextView getTitle_text() {
        return title_text;
    }

    public void setTitle_text(TextView title_text) {
        this.title_text = title_text;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
