package wxc.pubgtracker;

public class MyProperties {
    private static MyProperties mInstance= null;

    public PUBGManager pubgManager = new PUBGManager();

    protected MyProperties(){}

    public static synchronized MyProperties getInstance() {
        if(null == mInstance){
            mInstance = new MyProperties();
        }
        return mInstance;
    }
}