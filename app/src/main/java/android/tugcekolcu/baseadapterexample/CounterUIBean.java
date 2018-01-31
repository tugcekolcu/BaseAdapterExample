package android.tugcekolcu.baseadapterexample;

/**
 * Created by tugcekolcu on 31.01.2018.
 */

public class CounterUIBean {

    private String counterName;
    private String counterValue;

    public CounterUIBean(String counterName,String counterValue){
        this.counterName=counterName;
        this.counterValue=counterValue;

    }
    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public String getCounterValue() {
        return counterValue;
    }

    public void setCounterValue(String counterValue) {
        this.counterValue = counterValue;
    }
}
