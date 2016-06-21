package im.wangchao.msharecore;

/**
 * <p>Description  : ShareRequest.</p>
 * <p/>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 16/6/14.</p>
 * <p>Time         : 上午10:55.</p>
 */
public class ShareRequest {

    private String platformName;
    private ShareParams mParams;
    private ShareCallback mCallback;

    private ShareRequest(Builder builder){
        this.platformName = builder.platformName;
        this.mParams = builder.mParamsBuilder.build();
        this.mCallback = builder.mCallback;
    }

    public String platformName(){
        return platformName;
    }

    public ShareParams shareParams(){
        return mParams;
    }

    public ShareCallback shareCallback(){
        return mCallback;
    }

    /**
     * 执行分享
     */
    public void execute(){
        MShareCore.instance().share(this);
    }

    public Builder newBuilder(){
        return new Builder(this);
    }

    public static Builder createBuilder(){
        return new Builder();
    }

    public static class Builder{
        String platformName;
        ShareParams.Builder mParamsBuilder;
        ShareCallback mCallback;

        public Builder(){
            mParamsBuilder = ShareParams.createBuilder();
        }

        private Builder(ShareRequest request){
            this.platformName = request.platformName;
            this.mParamsBuilder = request.mParams.newBuilder();
            this.mCallback = request.mCallback;
        }

        public Builder params(ShareParams params){
            this.mParamsBuilder = params.newBuilder();
            return this;
        }

        public Builder platformName(String platformName){
            this.platformName = platformName;
            return this;
        }

        public Builder callback(ShareCallback callback){
            this.mCallback = callback;
            return this;
        }

        public ShareRequest build(){
            if (platformName == null || platformName.length() == 0){
                throw new IllegalArgumentException("platformName can not be empty.");
            }
            return new ShareRequest(this);
        }

    }
}
