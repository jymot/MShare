package im.wangchao.msharecore;

/**
 * <p>Description  : ShareParams.</p>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 16/6/14.</p>
 * <p>Time         : 下午2:18.</p>
 */
public class ShareParams {
    final private int type;

    final private String webUrl;
    final private String text;

    final private String title;
    final private String description;
    final private ImageParams.Builder image;

    private ShareParams(Builder builder){
        this.webUrl = builder.webUrl;
        this.text = builder.text;
        this.title = builder.title;
        this.description = builder.description;
        this.image = builder.image;
        this.type = builder.type;
    }

    /**
     * 分享类型
     */
    @ShareType.Type public int type(){
        return type;
    }

    /**
     * 分享的 WebPage url
     */
    public String webUrl(){
        return webUrl;
    }

    /**
     * 分享的文本
     */
    public String text(){
        return text;
    }

    public String title(){
        return title;
    }

    public String description(){
        return description;
    }

    public ImageParams image(){
        return image.build();
    }

    public Builder newBuilder(){
        return new Builder(this);
    }

    public static Builder createBuilder(){
        return new Builder();
    }

    public static class Builder{
        int type;

        String webUrl;
        String text;

        String title;
        String description;
        ImageParams.Builder image;

        private Builder(){
            image = ImageParams.createBuilder();
        }

        private Builder(ShareParams params){
            this.webUrl = params.webUrl;
            this.text = params.text;
            this.title = params.title;
            this.description = params.description;
            this.image = params.image;
            this.type = params.type;
        }

        public Builder type(@ShareType.Type int type){
            this.type = type;
            return this;
        }

        public Builder webUrl(String webUrl){
            this.webUrl = webUrl;
            return this;
        }

        public Builder text(String text){
            this.text = text;
            return this;
        }

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder image(ImageParams image){
            this.image = image.newBuilder();
            return this;
        }

        public ShareParams build(){
            return new ShareParams(this);
        }

    }
}
