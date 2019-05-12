package pers.life.helper.utils;

import com.bumptech.glide.request.RequestOptions;

import pers.life.helper.R;


public class GlideRequestOptions {
    /**
     * Glide load head image
     * error logo
     * fallback logo
     */
    public static RequestOptions getHeadImage() {
        RequestOptions requestOptions = new RequestOptions()
                .error(R.mipmap.ic_launcher_round)
                .fallback(R.mipmap.ic_launcher_round);
        return requestOptions;
    }

    /**
     * Glide load Goods image
     * error logo
     * fallback logo
     */
    public static RequestOptions getGoodsImage() {
        RequestOptions requestOptions = new RequestOptions()
                .error(R.drawable.no_image)
                .fallback(R.drawable.no_image);
        return requestOptions;
    }
}
