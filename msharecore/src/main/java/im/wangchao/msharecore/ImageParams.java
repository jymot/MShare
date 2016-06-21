package im.wangchao.msharecore;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;

/**
 * <p>Description  : ImageParams.</p>
 * <p/>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 16/6/15.</p>
 * <p>Time         : 下午1:34.</p>
 */
public final class ImageParams {

    final private String imageUrl;
    final private Bitmap imageBitmap;
    final private byte[] imageBytes;
    final private String imagePath;

    private ImageParams(Builder builder){
        imageUrl = builder.imageUrl;
        imageBitmap = builder.imageBitmap;
        imageBytes = builder.imageBytes;
        imagePath = builder.imagePath;
    }

    /**
     * 图片网络地址
     */
    public String url(){
        return imageUrl;
    }

    /**
     * 图片bitmap
     */
    public Bitmap bitmap(){
        if (imageBitmap != null){
            return imageBitmap;
        }
        if (imageBytes != null && imageBytes.length != 0){
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        }
        if (!TextUtils.isEmpty(imagePath)){
            return BitmapFactory.decodeFile(imagePath);
        }
        return null;
    }

    /**
     * 图片bytes[]
     */
    public byte[] bytes(){
        if (imageBytes != null && imageBytes.length != 0){
            return imageBytes;
        }
        if (imageBitmap != null){
            return bmpToByteArray(imageBitmap, true);
        }

        return new byte[0];
    }

    /**
     * 图片本地地址
     */
    public String path(){
        return imagePath;
    }

    public Builder newBuilder(){
        return new Builder(this);
    }

    public static Builder createBuilder(){
        return new Builder();
    }

    /**
     * bitmap to array
     */
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            //Silent
        }

        return result;
    }

    public static class Builder {

        String imageUrl;
        Bitmap imageBitmap;
        byte[] imageBytes;
        String imagePath;

        private Builder(){
            imageBytes = new byte[0];
        }

        private Builder(ImageParams params){
            imageUrl = params.imageUrl;
            imageBitmap = params.imageBitmap;
            imageBytes = params.imageBytes;
            imagePath = params.imagePath;
        }

        public Builder url(String url){
            this.imageUrl = url;
            return this;
        }

        public Builder bitmap(Bitmap bitmap){
            this.imageBitmap = bitmap;
            return this;
        }

        public Builder bytes(byte[] bytes){
            this.imageBytes = bytes;
            return this;
        }

        public Builder path(String path){
            this.imagePath = path;
            return this;
        }

        public ImageParams build(){
            return new ImageParams(this);
        }

    }
}
